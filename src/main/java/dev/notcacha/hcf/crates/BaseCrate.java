package dev.notcacha.hcf.crates;

import dev.notcacha.hcf.crates.key.Key;
import dev.notcacha.hcf.crates.key.SimpleKey;
import dev.notcacha.hcf.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseCrate implements Crate {

    private final String id;
    private final Key key;
    private final List<ItemStack> itemList;

    public BaseCrate(String id) {
        this(id, new SimpleKey(), new ArrayList<>(Collections.singleton(new ItemBuilder(Material.IRON_SWORD)
                .setName("&6Example Sword", true).build())));
    }

    public BaseCrate(String id, Key key) {
        this(id, key, new ArrayList<>(Collections.singleton(new ItemBuilder(Material.IRON_SWORD)
                .setName("&6Example Sword", true).build())));
    }

    public BaseCrate(String id, Key key, List<ItemStack> itemList) {
        this.id = id;
        this.key = key;
        this.itemList = itemList;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public List<ItemStack> getItems() {
        return this.itemList;
    }


}
