package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.guice.anotations.storage.*;
import dev.notcacha.hcf.storage.*;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(StorageProvider.class).annotatedWith(UserStorage.class).to(UserStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(FactionStorage.class).to(FactionStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(KitStorage.class).to(KitStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(DeathbanStorage.class).to(DeathbanStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(CrateStorage.class).to(CrateStorageProvider.class);
    }
}
