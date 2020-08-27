package dev.notcacha.hcf;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.guice.BinderModule;
import dev.notcacha.hcf.guice.anotations.service.HCFService;
import org.bukkit.plugin.java.JavaPlugin;

public final class HCF extends JavaPlugin {

    @Inject
    @HCFService
    private ServiceManager hcfService;

    @Override
    public void onEnable() {
        BinderModule binderModule = new BinderModule(this);
        Injector injector = binderModule.createInjector();
        injector.injectMembers(this);

        hcfService.start();
    }

    @Override
    public void onDisable() {
        hcfService.stop();
    }
}
