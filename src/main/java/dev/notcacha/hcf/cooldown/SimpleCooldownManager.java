package dev.notcacha.hcf.cooldown;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.utils.CooldownName;

import java.util.Optional;

@Singleton
public class SimpleCooldownManager implements CooldownManager {

    @Inject
    @CooldownCache
    private CacheProvider<String, Long> cooldownCache;

    @Override
    public Optional<Long> find(String type) {
        return cooldownCache.find(type);
    }

    @Override
    public Optional<Long> find(String type, String id) {
        return find(type.replace("%id%", id));
    }

    @Override
    public void add(String type, Long time) {
        cooldownCache.add(type, time);
    }

    @Override
    public void add(String type, String id, Long time) {
       add(type.replace("%id%", id), time);
    }

    @Override
    public boolean exists(String type) {
        return cooldownCache.exists(type);
    }

    @Override
    public boolean exists(String type, String id) {
        return exists(type.replace("%id%", id));
    }

    @Override
    public void remove(String type) {
        cooldownCache.remove(type);
    }

    @Override
    public void remove(String type, String id) {
        remove(type.replace("%id%", id));
    }

    @Override
    public void removeAll(String id) {
        cooldownCache.remove(CooldownName.COMBAT_COOLDOWN.replace("%id%", id));
        cooldownCache.remove(CooldownName.PEARL_COOLDOWN.replace("%id%", id));
        cooldownCache.remove(CooldownName.GOLDEN_APPLE.replace("%id%", id));
        cooldownCache.remove(CooldownName.ENCHANT_GOLDEN_APPLE.replace("%id%", id));
    }
}
