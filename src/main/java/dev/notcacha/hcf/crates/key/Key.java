package dev.notcacha.hcf.crates.key;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public interface Key {

    /**
     * @return color from key
     */

    ChatColor getColor();


    /**
     * @return item from key
     */

    ItemStack getItem();
}
