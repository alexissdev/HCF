package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.api.API;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;

import java.util.UUID;

@Singleton
public class APIServiceManager implements ServiceManager {

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    private API api;

    @Override
    public void start() {
        api = new API(userCache);
    }

    @Override
    public void stop() {
        if (api != null) {
            api.kill();
        }
    }
}
