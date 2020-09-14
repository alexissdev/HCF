package dev.notcacha.hcf.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.deathban.Deathban;

import java.util.Optional;

@Singleton
public class DeathbanStorageProvider implements StorageProvider<Deathban> {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<String, Deathban> deathbanCache;

    @Override
    public Optional<Deathban> find(String s) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void save(Deathban deathban) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Deathban deathban) {

    }
}
