package dev.notcacha.hcf.crates.cache;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.Crate;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CrateCacheProvider implements CacheProvider<String, Crate> {

    private final Map<String, Crate> crateMap;

    public CrateCacheProvider() {
        this.crateMap = new HashMap<>();
    }

    @Override
    public Map<String, Crate> get() {
        return this.crateMap;
    }
}
