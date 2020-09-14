package dev.notcacha.hcf.storage;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.faction.Faction;;

import java.util.Optional;

public class FactionStorageProvider implements StorageProvider<Faction> {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<String, Faction> factionCache;

    @Override
    public Optional<Faction> find(String s) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void save(Faction faction) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Faction faction) {

    }
}
