package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.spawn.SpawnManager;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SpawnCommand implements CommandClass {

    @Inject
    private SpawnManager spawnManager;

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @ACommand(names = "spawn", permission = "hcf.spawn")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        Optional<Location> location = spawnManager.getSpawn();
        if (!location.isPresent()) {
            languageLib.getTranslationManager().getTranslation("spawn.not-exists").ifPresent(message -> {
                message.setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        languageLib.getTranslationManager().getTranslation("spawn.teleport").ifPresent(message -> {
            message.setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        player.teleport(location.get());
        return true;
    }
}
