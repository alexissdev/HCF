package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.guice.anotations.cache.CombatCache;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.scheduler.MainScheduler;

@Singleton
public class SchedulerServiceManager implements ServiceManager {

    @Inject
    private HCF plugin;

    @Inject
    @CooldownCache
    private CacheProvider<String, Long> cooldownCache;

    @Inject
    @CombatCache
    private CacheProvider<String, String> combatCache;

    private MainScheduler mainScheduler;

    @Override
    public void start() {
        if (mainScheduler != null) {
            mainScheduler.cancel();
        }

        mainScheduler = new MainScheduler(cooldownCache, combatCache);
        mainScheduler.runTaskTimer(this.plugin, 20, 20);
    }

    @Override
    public void stop() {
        if (mainScheduler != null) {
            mainScheduler.cancel();
        }
    }
}
