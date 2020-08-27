package dev.notcacha.hcf.economy.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.hcf.economy.VaultEconomyWrapper;
import dev.notcacha.hcf.economy.manager.EconomyManager;
import org.bukkit.entity.Player;

@Singleton
public class PlayerEconomyManager implements EconomyManager {

    @Inject
    private VaultEconomyWrapper economy;

    @Override
    public double getMoney(Player player) {
        return economy.get().getBalance(player);
    }

    @Override
    public boolean hasMoney(Player player) {
        return economy.get().getBalance(player) > 0;
    }

    @Override
    public boolean hasEnoughMoney(Player player, int amount) {
        return economy.get().getBalance(player) >= amount;
    }

    @Override
    public boolean depositMoney(Player player, double amount) {
        return economy.get().depositPlayer(player, amount).transactionSuccess();
    }

    @Override
    public boolean withdrawMoney(Player player, double amount) {
        return economy.get().withdrawPlayer(player, amount).transactionSuccess();
    }
}
