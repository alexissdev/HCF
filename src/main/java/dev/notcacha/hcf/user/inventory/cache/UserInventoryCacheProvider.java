package dev.notcacha.hcf.user.inventory.cache;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.inventory.UserInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserInventoryCacheProvider implements CacheProvider<UUID, UserInventory> {

    private final Map<UUID, UserInventory> userInventoryMap;

    public UserInventoryCacheProvider() {
        this.userInventoryMap = new HashMap<>();
    }

    @Override
    public Map<UUID, UserInventory> get() {
        return this.userInventoryMap;
    }
}
