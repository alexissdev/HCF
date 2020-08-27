package dev.notcacha.hcf.economy;

import net.milkbowl.vault.economy.Economy;

public interface VaultEconomyWrapper {

    /**
     * Setup wrapper from vault economy
     */

    void setup();

    /**
     * @return Economy object from vault economy
     */

    Economy get();

}
