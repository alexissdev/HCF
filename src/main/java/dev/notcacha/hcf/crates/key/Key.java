package dev.notcacha.hcf.crates.key;

import dev.notcacha.hcf.utils.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public interface Key {

    /**
     * @return color from key
     */

    ChatColor getColor();

    /**
     * Set color
     *
     * @param color has been set
     */

    void setColor(ChatColor color);


    /**
     * @return item from key
     */

    ItemBuilder getItem();

    /**
     * Set item
     *
     * @param item has been set
     */

    void setItem(ItemBuilder item);
}
