package dev.notcacha.hcf.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.guice.modules.CacheModule;
import dev.notcacha.hcf.guice.modules.LanguageModule;
import dev.notcacha.hcf.guice.modules.LoaderModule;
import dev.notcacha.hcf.guice.modules.ServiceModule;
import dev.notcacha.hcf.guice.modules.StorageModule;

public class BinderModule extends AbstractModule {

    private final HCF plugin;

    public BinderModule(HCF plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(HCF.class).toInstance(this.plugin);

        this.install(new LanguageModule(plugin));
        this.install(new CacheModule());
        this.install(new StorageModule());
        this.install(new LoaderModule());
        this.install(new ServiceModule());
    }
}
