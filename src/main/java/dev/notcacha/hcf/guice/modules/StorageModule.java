package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.deathban.Deathban;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.storage.CrateStorageProvider;
import dev.notcacha.hcf.storage.DeathbanStorageProvider;
import dev.notcacha.hcf.storage.FactionStorageProvider;
import dev.notcacha.hcf.storage.KitStorageProvider;
import dev.notcacha.hcf.storage.UserStorageProvider;
import dev.notcacha.hcf.user.User;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(new TypeLiteral<StorageProvider<User>>() {}).to(UserStorageProvider.class);
        this.bind(new TypeLiteral<StorageProvider<Faction>>() {}).to(FactionStorageProvider.class);
        this.bind(new TypeLiteral<StorageProvider<Kit>>() {}).to(KitStorageProvider.class);
        this.bind(new TypeLiteral<StorageProvider<Deathban>>() {}).to(DeathbanStorageProvider.class);
        this.bind(new TypeLiteral<StorageProvider<Crate>>() {}).to(CrateStorageProvider.class);
    }
}
