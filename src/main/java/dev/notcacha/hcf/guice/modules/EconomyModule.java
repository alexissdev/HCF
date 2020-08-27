package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.hcf.economy.SimpleVaultEconomyWrapper;
import dev.notcacha.hcf.economy.VaultEconomyWrapper;
import dev.notcacha.hcf.economy.manager.EconomyManager;
import dev.notcacha.hcf.economy.player.PlayerEconomyManager;

public class EconomyModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(VaultEconomyWrapper.class).to(SimpleVaultEconomyWrapper.class);
        this.bind(EconomyManager.class).to(PlayerEconomyManager.class);
    }
}
