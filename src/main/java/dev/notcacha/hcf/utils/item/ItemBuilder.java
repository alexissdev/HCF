package dev.notcacha.hcf.utils.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final Material material;
    private int amount;
    private short data;

    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> flags;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    public ItemBuilder(Material material, int amount, short data) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setName(String name, boolean color) {
        this.name = (color) ? ChatColor.translateAlternateColorCodes('&', name) : name;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(LoreBuilder lore) {
        this.lore = lore.build();
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemBuilder setData(int data) {
        this.data = (short) data;
        return this;
    }

    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, Integer level) {
        enchantments.put(enchantment, level);
        return this;
    }


    public ItemBuilder setFlags(List<ItemFlag> flags) {
        this.flags = flags;
        return this;
    }

    public ItemBuilder addFlag(ItemFlag itemFlag) {
        flags.add(itemFlag);
        return this;
    }

    public ItemBuilder fakeBuild() {
        return new ItemBuilder(this.material, this.amount, this.data);
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, data);

        ItemMeta meta = item.getItemMeta();

        enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));

        meta.setDisplayName(name);
        meta.setLore(lore);

        flags.forEach(meta::addItemFlags);

        item.setItemMeta(meta);

        return item;
    }

}
