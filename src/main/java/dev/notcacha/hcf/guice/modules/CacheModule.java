package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.combatlog.cache.CombatCacheProvider;
import dev.notcacha.hcf.combatlog.cache.CombatLoggerCacheProvider;
import dev.notcacha.hcf.cooldown.cache.CooldownCacheProvider;
import dev.notcacha.hcf.crates.cache.CrateCacheProvider;
import dev.notcacha.hcf.deathban.cache.DeathbanCacheProvider;
import dev.notcacha.hcf.faction.cache.FactionCacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.CombatCache;
import dev.notcacha.hcf.guice.anotations.cache.CombatLoggerCache;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.guice.anotations.cache.CrateCache;
import dev.notcacha.hcf.guice.anotations.cache.DeathbanCache;
import dev.notcacha.hcf.guice.anotations.cache.FactionCache;
import dev.notcacha.hcf.guice.anotations.cache.KitCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.kit.cache.KitCacheProvider;
import dev.notcacha.hcf.user.cache.UserCacheProvider;

public class CacheModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(CacheProvider.class).annotatedWith(UserCache.class).to(UserCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(FactionCache.class).to(FactionCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(KitCache.class).to(KitCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CooldownCache.class).to(CooldownCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CombatCache.class).to(CombatCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CombatLoggerCache.class).to(CombatLoggerCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(DeathbanCache.class).to(DeathbanCacheProvider.class);
        this.bind(CacheProvider.class).annotatedWith(CrateCache.class).to(CrateCacheProvider.class);
    }
}
