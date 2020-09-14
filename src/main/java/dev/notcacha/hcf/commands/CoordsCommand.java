package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import org.bukkit.entity.Player;

public class CoordsCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @ACommand(names = "coords", permission = "hcf.coords")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("coords.message");
        message.colorize();

        message.getMessages(language).forEach(player::sendMessage);

        return true;
    }
}
