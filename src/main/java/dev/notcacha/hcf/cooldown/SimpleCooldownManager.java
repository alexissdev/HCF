package dev.notcacha.hcf.cooldown;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.utils.Cooldown;

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
    public void add(String type, Long time) {
        cooldownCache.add(type, time);
    }

    @Override
    public boolean exists(String type) {
        return cooldownCache.exists(type);
    }


    @Override
    public void remove(String type) {
        cooldownCache.remove(type);
    }

    @Override
    public void removeAll(String id) {
        cooldownCache.remove(Cooldown.COMBAT_COOLDOWN.replace("%id%", id));
        cooldownCache.remove(Cooldown.PEARL_COOLDOWN.replace("%id%", id));
        cooldownCache.remove(Cooldown.GOLDEN_APPLE.replace("%id%", id));
        cooldownCache.remove(Cooldown.ENCHANT_GOLDEN_APPLE.replace("%id%", id));
        cooldownCache.remove(Cooldown.LOGOUT_COOLDOWN.replace("%id%", id));
    }
}
