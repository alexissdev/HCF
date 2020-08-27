package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.hcf.guice.anotations.loader.CommandsLoader;
import dev.notcacha.hcf.guice.anotations.loader.ListenersLoader;
import dev.notcacha.hcf.loader.CommandsLoaderManager;
import dev.notcacha.hcf.loader.ListenersLoaderManager;

public class LoaderModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(LoaderManager.class).annotatedWith(CommandsLoader.class).to(CommandsLoaderManager.class);
        this.bind(LoaderManager.class).annotatedWith(ListenersLoader.class).to(ListenersLoaderManager.class);
    }
}
