package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class CoordsCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @ACommand(names = "coords", permission = "hcf.coords")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        languageLib.getTranslationManager().getTranslation("coords.message").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(player::sendMessage);
        });

        return true;
    }
}
