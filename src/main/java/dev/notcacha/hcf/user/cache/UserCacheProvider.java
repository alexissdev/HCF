package dev.notcacha.hcf.user.cache;

import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserCacheProvider implements CacheProvider<UUID, User> {

    private final Map<UUID, User> userMap;

    public UserCacheProvider() {
        this.userMap = new HashMap<>();
    }

    @Override
    public Map<UUID, User> get() {
        return this.userMap;
    }
}
