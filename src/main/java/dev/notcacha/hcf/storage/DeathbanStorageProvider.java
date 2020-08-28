package dev.notcacha.hcf.storage;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.deathban.Deathban;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.guice.anotations.cache.DeathbanCache;
import dev.notcacha.hcf.manager.GsonManager;

import java.io.File;
import java.util.Optional;

@Singleton
public class DeathbanStorageProvider implements StorageProvider<Deathban> {

    @Inject
    private HCF plugin;

    @Inject
    @DeathbanCache
    private CacheProvider<String, Deathban> deathbanCache;

    @Inject
    private GsonManager gsonManager;

    @Override
    public Optional<Deathban> find(String s) {
        if (!exists(s)) {
            return Optional.empty();
        }

        JsonFile jsonFile = new JsonFile(plugin, new File(plugin.getDataFolder() + "/deathbans/"), s + ".json");
        Gson gson = gsonManager.getGson();
        return Optional.of(gson.fromJson(jsonFile.getText(), Deathban.class));
    }

    @Override
    public boolean exists(String s) {
        return new File(plugin.getDataFolder() + "/deathbans/", s + ".json").exists();
    }

    @Override
    public void save(Deathban deathban) {
        File file = new File(plugin.getDataFolder() + "/deathbans/");
        if (!file.exists()) {
            file.mkdirs();
        }

        JsonFile jsonFile = new JsonFile(plugin, file, deathban.getId() + ".json");
        Gson gson = gsonManager.getGson();
        jsonFile.write(gson.toJson(deathban));

        deathbanCache.remove(deathban.getId());
    }

    @Override
    public void delete(String s) {
        if (exists(s)) {
            File deathban = new File(plugin.getDataFolder() + "/deathbans/", s);
            deathban.delete();
        }
    }

    @Override
    public void delete(Deathban deathban) {
        delete(deathban.getId());
    }
}
