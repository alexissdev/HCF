package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.scheduler.MainScheduler;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;

@Singleton
public class SchedulerServiceManager implements ServiceManager {

    @Inject
    private HCF plugin;

    @Inject
    private CooldownManager cooldownManager;

    @Inject
    private LanguageLib languageLib;
    @Inject
    private LanguageUtils languageUtils;

    private MainScheduler mainScheduler;

    @Override
    public void start() {
        if (mainScheduler != null) {
            mainScheduler.cancel();
        }

        mainScheduler = new MainScheduler(cooldownManager, languageLib, languageUtils);
        mainScheduler.runTaskTimer(this.plugin, 20, 20);
    }

    @Override
    public void stop() {
        if (mainScheduler != null) {
            mainScheduler.cancel();
        }
    }
}
