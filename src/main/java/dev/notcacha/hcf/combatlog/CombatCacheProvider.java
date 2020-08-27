package dev.notcacha.hcf.combatlog;

import dev.notcacha.core.cache.CacheProvider;

import java.util.HashMap;
import java.util.Map;

public class CombatCacheProvider implements CacheProvider<String, String> {

    private final Map<String, String> combatMap;

    public CombatCacheProvider() {
        this.combatMap = new HashMap<>();
    }

    @Override
    public Map<String, String> get() {
        return this.combatMap;
    }
}
