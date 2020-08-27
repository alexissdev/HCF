package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.guice.anotations.storage.FactionStorage;
import dev.notcacha.hcf.guice.anotations.storage.KitStorage;
import dev.notcacha.hcf.guice.anotations.storage.UserStorage;
import dev.notcacha.hcf.storage.FactionStorageProvider;
import dev.notcacha.hcf.storage.KitStorageProvider;
import dev.notcacha.hcf.storage.UserStorageProvider;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(StorageProvider.class).annotatedWith(UserStorage.class).to(UserStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(FactionStorage.class).to(FactionStorageProvider.class);
        this.bind(StorageProvider.class).annotatedWith(KitStorage.class).to(KitStorageProvider.class);
    }
}
