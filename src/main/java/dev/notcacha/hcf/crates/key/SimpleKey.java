package dev.notcacha.hcf.crates.key;

import dev.notcacha.hcf.utils.item.ItemBuilder;
import dev.notcacha.hcf.utils.item.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SimpleKey implements Key {

    private final ChatColor color;
    private final ItemStack item;

    public SimpleKey() {
        this(ChatColor.WHITE, new ItemBuilder(Material.TRIPWIRE_HOOK)
                .setName("&6Example Key", true)
                .setLore(new LoreBuilder().addLines("&7Example lore from this Key"))
                .build());
    }

    public SimpleKey(ChatColor color, ItemStack item) {
        this.color = color;
        this.item = item;
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }
}
