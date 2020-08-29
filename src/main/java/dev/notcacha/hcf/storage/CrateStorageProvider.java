package dev.notcacha.hcf.storage;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.guice.anotations.cache.CrateCache;
import dev.notcacha.hcf.manager.GsonManager;

import java.io.File;
import java.util.Optional;

@Singleton
public class CrateStorageProvider implements StorageProvider<Crate> {

    @Inject
    private HCF plugin;

    @Inject
    private GsonManager gsonManager;

    @Inject
    @CrateCache
    private CacheProvider<String, Crate> crateCache;

    @Override
    public Optional<Crate> find(String id) {
        if (!exists(id)) {
            return Optional.empty();
        }

        JsonFile jsonFile = new JsonFile(plugin, new File(plugin.getDataFolder() + "/crates/"), id + ".json");

        Gson gson = gsonManager.getGson();


        return Optional.of(gson.fromJson(jsonFile.getText(), Crate.class));
    }

    @Override
    public boolean exists(String id) {
        return new File(plugin.getDataFolder() + "/crates/", id + ".json").exists();
    }

    @Override
    public void save(Crate crate) {
        File file = new File(plugin.getDataFolder() + "/crates/");
        if (!file.exists()) {
            file.mkdirs();
        }

        JsonFile jsonFile = new JsonFile(plugin, file, crate.getId() + ".json");
        Gson gson = gsonManager.getGson();
        jsonFile.write(gson.toJson(crate));

        crateCache.remove(crate.getId());
    }

    @Override
    public void delete(String s) {
        if (exists(s)) {
            File crate = new File(plugin.getDataFolder() + "/crates/", s);
            crate.delete();
        }
    }

    @Override
    public void delete(Crate crate) {
        delete(crate.getId());
    }
}
