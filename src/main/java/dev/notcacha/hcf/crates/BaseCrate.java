package dev.notcacha.hcf.crates;

import dev.notcacha.hcf.crates.key.Key;
import dev.notcacha.hcf.crates.key.SimpleKey;
import dev.notcacha.hcf.utils.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseCrate implements Crate {

    private final String id;
    private final Key key;
    private final List<Location> locationList;
    private List<ItemStack> itemList;

    public BaseCrate(String id) {
        this(id, new SimpleKey(), new ArrayList<>(Collections.singleton(new ItemBuilder(Material.IRON_SWORD)
                .setName("&6Example Sword", true).build())),new ArrayList<>());
    }

    public BaseCrate(String id, Key key) {
        this(id, key, new ArrayList<>(Collections.singleton(new ItemBuilder(Material.IRON_SWORD)
                .setName("&6Example Sword", true).build())), new ArrayList<>());
    }

    public BaseCrate(String id, Key key, List<ItemStack> itemList, List<Location> locationList) {
        this.id = id;
        this.key = key;
        this.itemList = itemList;
        this.locationList = locationList;
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
    public List<Location> getLocations() {
        return this.locationList;
    }

    @Override
    public List<ItemStack> getItems() {
        return this.itemList;
    }

    @Override
    public void setItems(List<ItemStack> itemList) {
        this.itemList = itemList;
    }


}
