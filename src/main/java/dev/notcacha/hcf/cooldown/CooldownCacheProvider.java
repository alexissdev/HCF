package dev.notcacha.hcf.cooldown;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;

import java.util.HashMap;
import java.util.Map;

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
}
