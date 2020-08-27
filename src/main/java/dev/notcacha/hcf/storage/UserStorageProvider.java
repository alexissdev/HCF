package dev.notcacha.hcf.storage;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.file.JsonFile;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.manager.GsonManager;
import dev.notcacha.hcf.user.HCFUser;
import dev.notcacha.hcf.user.User;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserStorageProvider implements StorageProvider<User> {

    @Inject
    private HCF plugin;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    private GsonManager gsonManager;

    public Optional<User> find(String id) {
        if (!exists(id)) {
            return Optional.of(new HCFUser(id, plugin.getServer().getOfflinePlayer(UUID.fromString(id)).getName()));
        }

        JsonFile jsonFile = new JsonFile(plugin, new File(plugin.getDataFolder() + "/users/"), id + ".json");

        Gson gson = gsonManager.getGson();


        return Optional.of(gson.fromJson(jsonFile.getText(), User.class));
    }


    public boolean exists(String id) {
        return new File(plugin.getDataFolder() + "/users/", id + ".json").exists();
    }

    public void save(User user) {
        File file = new File(plugin.getDataFolder() + "/users/");
        if (!file.exists()) {
            file.mkdirs();
        }

        JsonFile jsonFile = new JsonFile(plugin, file, user.getId() + ".json");
        Gson gson = gsonManager.getGson();
        jsonFile.write(gson.toJson(user));

        userCache.remove(UUID.fromString(user.getId()));
    }


    @Override
    public void delete(String s) {
        if (exists(s)) {
            File user = new File(plugin.getDataFolder() + "/users/", s);
            user.delete();
        }
    }

    @Override
    public void delete(User user) {
        delete(user.getId());
    }
}
