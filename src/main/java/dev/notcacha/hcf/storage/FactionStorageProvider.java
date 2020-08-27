package dev.notcacha.hcf.storage;

import com.google.gson.Gson;
import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.guice.anotations.cache.FactionCache;
import dev.notcacha.hcf.manager.GsonManager;

import java.io.File;
import java.util.Optional;

public class FactionStorageProvider implements StorageProvider<Faction> {

    @Inject
    private HCF plugin;

    @Inject
    @FactionCache
    private CacheProvider<String, Faction> factionCache;

    @Inject
    private GsonManager gsonManager;

    @Override
    public Optional<Faction> find(String s) {
        if (!exists(s)) {
            return Optional.empty();
        }
        JsonFile jsonFile = new JsonFile(plugin, new File(plugin.getDataFolder() + "/factions/"), s + ".json");
        Gson gson = gsonManager.getGson();
        return Optional.of(gson.fromJson(jsonFile.getText(), Faction.class));
    }

    @Override
    public boolean exists(String s) {
        return new File(plugin.getDataFolder() + "/factions/", s + ".json").exists();
    }

    @Override
    public void save(Faction faction) {
        File file = new File(plugin.getDataFolder() + "/factions/");
        if (!file.exists()) {
            file.mkdirs();
        }

        JsonFile jsonFile = new JsonFile(plugin, file, faction.getId() + ".json");
        Gson gson = gsonManager.getGson();
        jsonFile.write(gson.toJson(faction));

        factionCache.remove(faction.getId());
    }

    @Override
    public void delete(String s) {
        if (exists(s)) {
            File kit = new File(plugin.getDataFolder() + "/factions/", s);
            kit.delete();
        }
    }

    @Override
    public void delete(Faction faction) {
        delete(faction.getId());
    }
}
