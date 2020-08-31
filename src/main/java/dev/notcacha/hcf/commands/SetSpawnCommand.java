package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.spawn.SpawnManager;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandClass {

    @Inject
    private SpawnManager spawnManager;

    @Inject
    private LanguageLib<Configuration> languageLib;

    @ACommand(names = "setspawn", permission = "hcf.setspawn")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        Location location = player.getLocation();

        spawnManager.setSpawn(location);

        languageLib.getTranslationManager().getTranslation("spawn.set").ifPresent(message -> {
            message.setVariable("%x%", String.valueOf(location.getX()))
                    .setVariable("%y%", String.valueOf(location.getY()))
                    .setVariable("%z%", String.valueOf(location.getZ()))
                    .setVariable("%world_name%", location.getWorld().getName())
                    .setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        return true;
    }
}
