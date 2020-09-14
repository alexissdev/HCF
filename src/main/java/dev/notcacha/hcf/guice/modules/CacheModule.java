package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.combatlog.cache.CombatCacheProvider;
import dev.notcacha.hcf.combatlog.cache.CombatLoggerCacheProvider;
import dev.notcacha.hcf.cooldown.cache.CooldownCacheProvider;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.crates.cache.CrateCacheProvider;
import dev.notcacha.hcf.deathban.Deathban;
import dev.notcacha.hcf.deathban.cache.DeathbanCacheProvider;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.faction.cache.FactionCacheProvider;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.kit.cache.KitCacheProvider;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.user.cache.UserCacheProvider;
import dev.notcacha.hcf.user.inventory.UserInventory;

import java.util.UUID;

public class CacheModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(new TypeLiteral<CacheProvider<UUID, User>>() {}).to(UserCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, Faction>>() {}).to(FactionCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, Kit>>() {}).to(KitCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, Long>>() {}).to(CooldownCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, String>>() {}).to(CombatCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, UserInventory>>() {}).to(CombatLoggerCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<UUID, Deathban>>() {}).to(DeathbanCacheProvider.class);
        this.bind(new TypeLiteral<CacheProvider<String, Crate>>() {}).to(CrateCacheProvider.class);
    }
}
