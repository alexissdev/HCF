package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.guice.anotations.loader.CommandsLoader;
import dev.notcacha.hcf.guice.anotations.loader.ListenersLoader;
import dev.notcacha.hcf.guice.anotations.service.APIService;
import dev.notcacha.hcf.guice.anotations.service.SchedulerService;

@Singleton
public class HCFServiceManager implements ServiceManager {

    @Inject
    @SchedulerService
    private ServiceManager schedulerService;
    @Inject
    @APIService
    private ServiceManager apiService;

    @Inject
    @CommandsLoader
    private LoaderManager commandsLoader;
    @Inject
    @ListenersLoader
    private LoaderManager listenersLoader;

    @Override
    public void start() {
        this.schedulerService.start();
        this.apiService.start();
        this.commandsLoader.load();
        this.listenersLoader.load();
    }

    @Override
    public void stop() {
        this.schedulerService.stop();
        this.apiService.stop();
    }
}
