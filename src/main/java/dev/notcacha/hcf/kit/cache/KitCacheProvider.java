package dev.notcacha.hcf.kit.cache;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.kit.Kit;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class KitCacheProvider implements CacheProvider<String, Kit> {

    private final Map<String, Kit> kitMap;

    public KitCacheProvider() {
        this.kitMap = new HashMap<>();
    }

    @Override
    public Map<String, Kit> get() {
        return this.kitMap;
    }
}
