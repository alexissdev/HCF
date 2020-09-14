package dev.notcacha.hcf.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.kit.Kit;

import java.util.Optional;


@Singleton
public class KitStorageProvider implements StorageProvider<Kit> {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<String, Kit> kitCache;

    @Override
    public Optional<Kit> find(String s) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void save(Kit kit) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Kit kit) {

    }
}
