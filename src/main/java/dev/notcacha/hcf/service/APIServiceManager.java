package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.api.API;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.user.User;

import java.util.UUID;

@Singleton
public class APIServiceManager implements ServiceManager {

    @Inject
    private CacheProvider<UUID, User> userCache;
    @Inject
    private static CacheProvider<String, Kit> kitCache;
    @Inject
    private static CacheProvider<String, Faction> factionCache;
    @Inject
    private static CacheProvider<String, Crate> crateCache;

    private API api;

    @Override
    public void start() {
        api = new API(userCache, kitCache, factionCache, crateCache);
    }

    @Override
    public void stop() {
        if (api != null) {
            api.kill();
        }
    }
}
