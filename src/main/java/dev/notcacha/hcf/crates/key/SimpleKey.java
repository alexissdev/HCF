package dev.notcacha.hcf.crates.key;

import dev.notcacha.hcf.utils.item.ItemBuilder;
import dev.notcacha.hcf.utils.item.LoreBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SimpleKey implements Key {

    private ChatColor color;
    private ItemBuilder item;

    public SimpleKey() {
        this(ChatColor.WHITE, new ItemBuilder(Material.TRIPWIRE_HOOK)
                .setName("&6Example Key", true)
                .setLore(new LoreBuilder().addLines("&7Example lore from this Key")));
    }

    public SimpleKey(ChatColor color, ItemBuilder item) {
        this.color = color;
        this.item = item;
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
    }

    @Override
    public ItemBuilder getItem() {
        return this.item.fakeBuild();
    }

    @Override
    public void setItem(ItemBuilder item) {
        this.item = item;
    }
}
