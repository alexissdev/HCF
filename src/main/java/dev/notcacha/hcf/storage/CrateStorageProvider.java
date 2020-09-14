package dev.notcacha.hcf.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.crates.Crate;

import java.util.Optional;

@Singleton
public class CrateStorageProvider implements StorageProvider<Crate> {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<String, Crate> crateCache;

    @Override
    public Optional<Crate> find(String s) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void save(Crate crate) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Crate crate) {

    }
}
