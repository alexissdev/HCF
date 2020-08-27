package dev.notcacha.hcf.api;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.User;

import java.util.Optional;
import java.util.UUID;

public class API {

    private static CacheProvider<UUID, User> userCache;

    public API(CacheProvider<UUID, User> cache) {
        API.userCache = cache;
    }

    public static Optional<User> findUser(UUID id) {
        return userCache.find(id);
    }

    public void kill() {
        userCache = null;
    }
}
