package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.spawn.SpawnManager;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SpawnCommand implements CommandClass {

    @Inject
    private SpawnManager spawnManager;

    @Inject
    private LanguageLib languageLib;

    @ACommand(names = "spawn", permission = "hcf.spawn")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {

        Optional<Location> location = spawnManager.get();
        if (!location.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("spawn.not-exists");

            player.sendMessage(message.colorize().getMessage(language));
            return true;
        }
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("spawn.teleport");

        player.sendMessage(message.colorize().getMessage(language));

        player.teleport(location.get());
        return true;
    }
}
