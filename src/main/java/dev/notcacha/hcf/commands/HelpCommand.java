package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @ACommand(names = "help", permission = "hcf.help")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        languageLib.getTranslationManager().getTranslation("help.message").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(player::sendMessage);
        });
        return true;
    }
}
