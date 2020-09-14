package dev.notcacha.hcf.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.economy.VaultEconomyWrapper;
import dev.notcacha.hcf.placeholders.PlaceholderAPIWrapper;

@Singleton
public class HCFServiceManager implements ServiceManager {

    @Inject
    @Named("scheduler-service")
    private ServiceManager schedulerService;
    @Inject
    @Named("api-service")
    private ServiceManager apiService;

    @Inject
    @Named("commands-loader")
    private LoaderManager commandsLoader;
    @Inject
    @Named("listeners-loader")
    private LoaderManager listenersLoader;

    @Inject
    private PlaceholderAPIWrapper placeholderAPIWrapper;

    @Inject
    private VaultEconomyWrapper vaultEconomyWrapper;

    @Override
    public void start() {
        this.schedulerService.start();
        this.apiService.start();
        this.commandsLoader.load();
        this.listenersLoader.load();

        placeholderAPIWrapper.register();
        vaultEconomyWrapper.setup();
    }

    @Override
    public void stop() {
        this.schedulerService.stop();
        this.apiService.stop();
    }
}
