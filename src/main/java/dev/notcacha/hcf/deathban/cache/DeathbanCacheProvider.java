package dev.notcacha.hcf.deathban.cache;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.deathban.Deathban;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathbanCacheProvider implements CacheProvider<UUID, Deathban> {

    private final Map<UUID, Deathban> deathbanMap;

    public DeathbanCacheProvider() {
        this.deathbanMap = new HashMap<>();
    }

    @Override
    public Map<UUID, Deathban> get() {
        return this.deathbanMap;
    }
}
