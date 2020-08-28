package dev.notcacha.hcf.cooldown.cache;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class CooldownCacheProvider implements CacheProvider<String, Long> {

    private final Map<String, Long> cooldownMap;

    public CooldownCacheProvider() {
        this.cooldownMap = new HashMap<>();
    }

    @Override
    public Map<String, Long> get() {
        return this.cooldownMap;
    }

    @Override
    public Optional<Long> find(String id) {
        if (!exists(id)) {
            return Optional.empty();
        }

        return Optional.of((cooldownMap.get(id) - System.currentTimeMillis()) / 1000L);
    }
}
