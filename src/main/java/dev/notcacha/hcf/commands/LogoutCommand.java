package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class LogoutCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private CooldownManager cooldownManager;

    @ACommand(names = "logout", permission = "hcf.logout")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {

        if (cooldownManager.exists(CooldownUtils.LOGOUT_COOLDOWN, player.getUniqueId().toString())) {
            languageLib.getTranslationManager().getTranslation("logout.is").ifPresent(message -> {
                message.setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }

        long cooldown = Long.parseLong("30");
        languageLib.getTranslationManager().getTranslation("logout.message").ifPresent(message -> {
            message.setVariable("%time%", new SimpleDateFormat("00.0").format(cooldown)).setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        cooldownManager.add(CooldownUtils.LOGOUT_COOLDOWN, player.getUniqueId().toString(), cooldown);
        return true;
    }
}
