package dev.notcacha.hcf.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.user.User;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserStorageProvider implements StorageProvider<User> {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<UUID, User> userCache;

    @Override
    public Optional<User> find(String s) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(User user) {

    }
}
