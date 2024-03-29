package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.cooldown.BaseCooldownManager;

public class ManagerModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(CooldownManager.class).to(BaseCooldownManager.class);
    }
}
