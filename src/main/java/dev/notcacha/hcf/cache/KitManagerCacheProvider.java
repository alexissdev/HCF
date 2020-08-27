package dev.notcacha.hcf.cache;

import dev.notcacha.core.cache.CacheProvider;

import java.util.HashMap;
import java.util.Map;

public class KitManagerCacheProvider implements CacheProvider<String, String> {

    private final Map<String, String> kitManagerMap;

    public KitManagerCacheProvider() {
        this.kitManagerMap = new HashMap<>();
    }

    @Override
    public Map<String, String> get() {
        return this.kitManagerMap;
    }
}
