package dev.notcacha.hcf.user.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UserInventory {

    private final ItemStack[] items;
    private final ItemStack[] armor;
    private final float experience;
    private final int level;

    public UserInventory(Player player) {
        this(player.getInventory().getContents(), player.getInventory().getArmorContents(), player.getExp(), player.getLevel());
    }

    public UserInventory(ItemStack[] items, ItemStack[] armor, float experience, int level) {
        this.items = items;
        this.armor = armor;
        this.experience = experience;
        this.level = level;
    }

    /**
     * @return inventory items from player
     * */

    public ItemStack[] getItems() {
        return items;
    }

    /**
     * @return armor items from player
     * */

    public ItemStack[] getArmor() {
        return armor;
    }

    /**
     * @return experience from player
     * */

    public float getExperience() {
        return experience;
    }

    /**
     * @return level from player
     * */

    public int getLevel() {
        return level;
    }
}
