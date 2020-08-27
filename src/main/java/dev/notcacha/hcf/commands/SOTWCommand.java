package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownName;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Optional;

@ACommand(names = "sotw")
public class SOTWCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private CooldownManager cooldownManager;

    @Inject
    private LanguageUtils languageUtils;

    @ACommand(names = "", permission = "hcf.sotw")
    public boolean mainCommand(@Injected(true) CommandSender sender) {
        String language = languageUtils.getLanguage(sender);

        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.usage");
        if (message.isPresent()) {
            message.get().setColor(true);

            message.get().getMessages(language).forEach(sender::sendMessage);
        }

        return true;
    }

    @ACommand(names = "start", permission = "hcf.sotw.start")
    @Usage(usage = "§cCorrect usage is /sotw start <time>")
    public boolean startCommand(@Injected(true) CommandSender sender, Integer time) {
        String language = languageUtils.getLanguage(sender);

        if (cooldownManager.exists(CooldownName.SOTW_TIMER)) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.error.is-started");
            message.ifPresent(translatableMessage -> sender.sendMessage(translatableMessage.setColor(true).getMessage(language)));
            return true;
        }

        cooldownManager.add(CooldownName.SOTW_TIMER, Long.parseLong(String.valueOf(time)));
        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.start");
        if (message.isPresent()) {
            message.get().setVariable("%time%", String.valueOf(time))
                    .setVariable("%staff_name%", sender.getName())
                    .setColor(true);

            sender.sendMessage(message.get().getMessage(language));
        }
        return true;
    }

    @ACommand(names = "stop", permission = "hcf.sotw.stop")
    public boolean stopCommand(@Injected(true) CommandSender sender) {
        String language = languageUtils.getLanguage(sender);

        if (!cooldownManager.exists(CooldownName.SOTW_TIMER)) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.error.not-started");
            message.ifPresent(translatableMessage -> sender.sendMessage(translatableMessage.setColor(true).getMessage(language)));
            return true;
        }

        cooldownManager.remove(CooldownName.SOTW_TIMER);
        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.stop");
        if (message.isPresent()) {
            message.get().setVariable("%staff_name%", sender.getName()).setColor(true);

            sender.sendMessage(message.get().getMessage(language));
        }

        return true;
    }

}
