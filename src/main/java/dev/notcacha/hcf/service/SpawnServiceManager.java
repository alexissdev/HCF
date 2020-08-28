package dev.notcacha.hcf.service;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.service.ServiceManager;

import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.manager.GsonManager;
import dev.notcacha.hcf.spawn.SpawnManager;
import org.bukkit.Location;

import java.io.File;

@Singleton
public class SpawnServiceManager implements ServiceManager {

    @Inject
    private HCF plugin;

    @Inject
    private SpawnManager spawnManager;

    @Inject
    private GsonManager gsonManager;

    @Override
    public void start() {
        File file = new File(plugin.getDataFolder(), "spawn");
        if (!file.exists()) {
            return;
        }

        JsonFile jsonFile = new JsonFile(plugin, "spawn");
        Gson gson = gsonManager.getGson();
        spawnManager.setSpawn(gson.fromJson(jsonFile.getText(), Location.class));

    }

    @Override
    public void stop() {
        File file = new File(plugin.getDataFolder(), "spawn");
        if (!file.exists()) {
            return;
        }

        spawnManager.getSpawn().ifPresent(spawn -> {
            JsonFile jsonFile = new JsonFile(plugin, file, "spawn.json");
            Gson gson = gsonManager.getGson();
            jsonFile.write(gson.toJson(spawn));
        });

    }
}
