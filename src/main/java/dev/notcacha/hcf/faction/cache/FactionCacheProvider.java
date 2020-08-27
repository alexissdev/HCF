package dev.notcacha.hcf.faction.cache;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.faction.Faction;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class FactionCacheProvider implements CacheProvider<String, Faction> {

    private final Map<String, Faction> factionMap;

    public FactionCacheProvider() {
        this.factionMap = new HashMap<>();
    }

    @Override
    public Map<String, Faction> get() {
        return this.factionMap;
    }
}
