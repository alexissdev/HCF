package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.cache.CombatCacheProvider;
import dev.notcacha.hcf.cooldown.cache.CooldownCacheProvider;
import dev.notcacha.hcf.faction.cache.FactionCacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.*;
import dev.notcacha.hcf.kit.cache.KitCacheProvider;
import dev.notcacha.hcf.cache.KitManagerCacheProvider;
import dev.notcacha.hcf.user.cache.UserCacheProvider;

public class CacheModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(CacheProvider.class).annotatedWith(UserCache.class).to(UserCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(FactionCache.class).to(FactionCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(KitCache.class).to(KitCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(KitManagerCache.class).to(KitManagerCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CooldownCache.class).to(CooldownCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CombatCache.class).to(CombatCacheProvider.class);
    }
}
