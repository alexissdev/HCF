package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.hcf.spawn.BaseSpawnManager;
import dev.notcacha.hcf.spawn.SpawnManager;

public class SpawnModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(SpawnManager.class).to(BaseSpawnManager.class);
    }
}
