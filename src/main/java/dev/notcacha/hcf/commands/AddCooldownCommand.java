package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class AddCooldownCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CooldownManager cooldownManager;

    @ACommand(names = {"addcooldown", "addc"}, permission = "hcf.cooldown.remove")
    @Usage(usage = "§cCorrect usage /addcooldown <name from player> <cooldown type>")
    public boolean mainCommand(@Injected(true) CommandSender sender, OfflinePlayer player, String type, Long time) {
        String language = languageUtils.getLanguage(sender);

        if (player.getPlayer() == null) {
            languageLib.getTranslationManager().getTranslation("general.target-offline").ifPresent(message -> {
                message.setVariable("%target_name%", player.getName()).setColor(true);

                sender.sendMessage(message.getMessage(language));
            });
            return true;
        }

        if (CooldownUtils.isNotCooldown(type)) {
            languageLib.getTranslationManager().getTranslation("cooldown.not-exists-cooldown").ifPresent(message -> {
                message.setVariable("%cooldown_name%", type).setColor(true);

                sender.sendMessage(message.getMessage(language));
            });
            return true;
        }

        if (cooldownManager.exists(type, player.getUniqueId().toString())) {
            languageLib.getTranslationManager().getTranslation("cooldown.contains").ifPresent(message -> {
                message.setVariable("%cooldown_name%", type).setVariable("%player_name%", player.getName())
                        .setColor(true);

                sender.sendMessage(message.getMessage(language));
            });
            return true;
        }

        cooldownManager.add(type, player.getUniqueId().toString(), time);
        languageLib.getTranslationManager().getTranslation("cooldown.add").ifPresent(message -> {
            message.setVariable("%cooldown_name%", type).setVariable("%player_name%", player.getName())
                    .setColor(true);

            sender.sendMessage(message.getMessage(language));
        });
        return true;
    }

}
