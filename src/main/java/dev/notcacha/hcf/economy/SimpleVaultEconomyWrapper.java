package dev.notcacha.hcf.economy;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.hcf.HCF;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

@Singleton
public class SimpleVaultEconomyWrapper implements VaultEconomyWrapper {

    @Inject
    private HCF plugin;

    private Economy economy;

    @Override
    public void setup() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            this.plugin.getLogger().log(Level.SEVERE, "An error occurred when creating a Wrapper to the vault economy, the error is caused by the plugin not being detected among the server plugins!");
            return;
        }

        RegisteredServiceProvider<Economy> registerService = plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (registerService == null) {
            plugin.getLogger().log(Level.SEVERE, "An error has occurred with the vault providers registry!");
            return;
        }

        this.economy = registerService.getProvider();
    }

    @Override
    public Economy get() {
        return this.economy;
    }
}
