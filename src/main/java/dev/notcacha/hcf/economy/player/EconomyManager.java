package dev.notcacha.hcf.economy.player;

import org.bukkit.entity.Player;

public interface EconomyManager {

    /**
     * @return money from {@param player}
     */

    double getMoney(Player player);

    /**
     * @return if the amount of the {@param player} is greater than 0
     */

    boolean hasMoney(Player player);

    /**
     * @return if the {@param player} has the amount of {@param amount}
     */

    boolean hasEnoughMoney(Player player, int amount);

    /**
     * Deposit money from player
     *
     * @param player to deposit
     * @param amount has been deposit
     */

    boolean depositMoney(Player player, double amount);

    /**
     * Take money
     *
     * @param player to take
     * @param amount has been take
     */

    boolean withdrawMoney(Player player, double amount);

}
