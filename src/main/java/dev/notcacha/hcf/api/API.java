package dev.notcacha.hcf.api;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.user.User;

import java.util.Optional;
import java.util.UUID;

public class API {

    private static CacheProvider<UUID, User> userCache;
    private static CacheProvider<String, Kit> kitCache;
    private static CacheProvider<String, Faction> factionCache;
    private static CacheProvider<String, Crate> crateCache;

    public API(CacheProvider<UUID, User> userCache, CacheProvider<String, Kit> kitCache,
               CacheProvider<String, Faction> factionCache, CacheProvider<String, Crate> crateCache) {
        API.userCache = userCache;
        API.kitCache = kitCache;
        API.factionCache = factionCache;
        API.crateCache = crateCache;
    }

    public static Optional<User> findUser(UUID id) {
        return userCache.find(id);
    }

    public static Optional<Kit> findKit(String id) {
        return kitCache.find(id);
    }

    public static Optional<Faction> findFaction(String id) {
        return factionCache.find(id);
    }

    public static Optional<Crate> findCrate(String id) {
        return crateCache.find(id);
    }

    public void kill() {
        userCache = null;
    }
}
