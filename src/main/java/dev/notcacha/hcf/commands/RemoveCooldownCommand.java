package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class RemoveCooldownCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private CooldownManager cooldownManager;

    @ACommand(names = {"removecooldown", "rmc"}, permission = "hcf.cooldown.remove")
    @Usage(usage = "§cCorrect usage /removecooldown <name from player> <cooldown type>")
    public boolean mainCommand(@Injected(true) CommandSender sender, @Injected(true) @Language String language, OfflinePlayer player, String type) {

        if (player.getPlayer() == null) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");
            message.setVariable("%target_name%", player.getName()).colorize();

            sender.sendMessage(message.getMessage(language));
            return true;
        }

        if (CooldownUtils.isNotCooldown(type)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("cooldown.not-exists-cooldown");
            message.setVariable("%cooldown_name%", type).colorize();

            sender.sendMessage(message.getMessage(language));
            return true;
        }
        if (!cooldownManager.exists(type, player.getUniqueId().toString())) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("cooldown.no-contains");
            message.setVariable("%cooldown_name%", type).setVariable("%player_name%", player.getName())
                    .colorize();

            sender.sendMessage(message.getMessage(language));
            return true;
        }
        cooldownManager.remove(type, player.getUniqueId().toString());
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("cooldown.remove");
        message.setVariable("%cooldown_name%", type).setVariable("%player_name%", player.getName())
                .colorize();

        sender.sendMessage(message.getMessage(language));
        return true;
    }

}
