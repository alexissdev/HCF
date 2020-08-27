package dev.notcacha.hcf.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.guice.anotations.cache.KitCache;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.manager.GsonManager;

import java.io.File;
import java.util.Optional;

@Singleton
public class KitStorageProvider implements StorageProvider<Kit> {

    @Inject
    private HCF plugin;

    @Inject
    @KitCache
    private CacheProvider<String, Kit> kitCache;

    @Inject
    private GsonManager gsonManager;

    @Override
    public Optional<Kit> find(String s) {
        if (!exists(s)) {
            return Optional.empty();
        }
        JsonFile jsonFile = new JsonFile(plugin, new File(plugin.getDataFolder() + "/kits/"), s + ".json");
        Gson gson = gsonManager.getGson();
        return Optional.of(gson.fromJson(jsonFile.getText(), Kit.class));
    }

    @Override
    public boolean exists(String s) {
        return new File(plugin.getDataFolder() + "/kits/", s + ".json").exists();
    }

    @Override
    public void save(Kit kit) {
        File file = new File(plugin.getDataFolder() + "/kits/");
        if (!file.exists()) {
            file.mkdirs();
        }

        JsonFile jsonFile = new JsonFile(plugin, file, kit.getId() + ".json");
        Gson gson = gsonManager.getGson();
        jsonFile.write(gson.toJson(kit));

        kitCache.remove(kit.getId());
    }

    @Override
    public void delete(String s) {
        if (exists(s)) {
            File kit = new File(plugin.getDataFolder() + "/kits/", s);
            kit.delete();
        }
    }

    @Override
    public void delete(Kit kit) {
        delete(kit.getId());
    }
}
