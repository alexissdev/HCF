package dev.notcacha.hcf.combatlog;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.inventory.UserInventory;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CombatLoggerCacheProvider implements CacheProvider<String, UserInventory> {

    private final Map<String, UserInventory> combatLoggerMap;

    public CombatLoggerCacheProvider() {
        this.combatLoggerMap = new HashMap<>();
    }

    @Override
    public Map<String, UserInventory> get() {
        return this.combatLoggerMap;
    }
}
