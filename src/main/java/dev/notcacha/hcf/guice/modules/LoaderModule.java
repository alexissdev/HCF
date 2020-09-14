package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dev.notcacha.core.loader.LoaderManager;;
import dev.notcacha.hcf.loader.CommandsLoaderManager;
import dev.notcacha.hcf.loader.ListenersLoaderManager;

public class LoaderModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(LoaderManager.class).annotatedWith(Names.named("commands-loader")).to(CommandsLoaderManager.class);
        this.bind(LoaderManager.class).annotatedWith(Names.named("listeners-loader")).to(ListenersLoaderManager.class);
    }
}
