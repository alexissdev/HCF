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
import org.bukkit.command.CommandSender;

@ACommand(names = "sotw")
public class SOTWCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private CooldownManager cooldownManager;

    @ACommand(names = "", permission = "hcf.sotw")
    public boolean mainCommand(@Injected(true) CommandSender sender, @Injected(true) @Language String language) {

        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("sotw.usage");
        message.colorize();

        message.getMessages(language).forEach(sender::sendMessage);
        return true;
    }

    @ACommand(names = "start", permission = "hcf.sotw.start")
    @Usage(usage = "§cCorrect usage is /sotw start <time>")
    public boolean startCommand(@Injected(true) CommandSender sender, @Injected(true) @Language String language, Integer time) {
        if (cooldownManager.exists(CooldownUtils.SOTW_TIMER)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("sotw.error.is-started");

            sender.sendMessage(message.colorize().getMessage(language));
            return true;
        }

        cooldownManager.add(CooldownUtils.SOTW_TIMER, Long.parseLong(String.valueOf(time)));
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("sotw.start");
        message.setVariable("%time%", String.valueOf(time))
                .setVariable("%staff_name%", sender.getName())
                .colorize();

        sender.sendMessage(message.getMessage(language));
        return true;
    }

    @ACommand(names = "stop", permission = "hcf.sotw.stop")
    public boolean stopCommand(@Injected(true) CommandSender sender, @Injected(true) @Language String language) {
        if (!cooldownManager.exists(CooldownUtils.SOTW_TIMER)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("sotw.error.not-started");

            sender.sendMessage(message.colorize().getMessage(language));
            return true;
        }

        cooldownManager.remove(CooldownUtils.SOTW_TIMER);
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("sotw.stop");
        message.setVariable("%staff_name%", sender.getName())
                .colorize();

        sender.sendMessage(message.getMessage(language));

        return true;
    }

}
