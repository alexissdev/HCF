package dev.notcacha.hcf.kit;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SimpleKit implements Kit {

    private final String name;
    private ItemStack[] items;
    private ItemStack[] armor;

    public SimpleKit(String name) {
        this(name, null, null);
    }

    public SimpleKit(String name, ItemStack[] items, ItemStack[] armor) {
        this.name = name;
        this.items = items;
        this.armor = armor;
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public Optional<ItemStack[]> getItems() {
        return Optional.ofNullable(this.items);
    }

    @Override
    public Kit setItems(ItemStack[] items) {
        this.items = items;
        return null;
    }

    @Override
    public Optional<ItemStack[]> getArmor() {
        return Optional.ofNullable(this.armor);
    }

    @Override
    public Kit setArmor(ItemStack[] armor) {
        this.armor = armor;
        return this;
    }
}
