package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.combatlog.CombatLogger;
import dev.notcacha.hcf.guice.anotations.cache.CombatCache;
import dev.notcacha.hcf.guice.anotations.cache.CombatLoggerCache;
import dev.notcacha.hcf.user.inventory.UserInventory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class CombatLoggerListener implements Listener {

    @Inject
    @CombatCache
    private CacheProvider<String, String> combatLogCache;

    @Inject
    private CombatLogger combatLogger;

    @Inject
    @CombatLoggerCache
    private CacheProvider<String, UserInventory> combatLoggerCache;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (combatLogCache.exists(event.getPlayer().getName())) {
            combatLogger.addLogger(event.getPlayer());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SKELETON) {
            String[] namePart = entity.getName().split(" ");
            if (namePart[1].equals("&7[§4&lCombat Logger&7]")) {
                combatLoggerCache.find(namePart[0]).ifPresent(userInventory -> {
                    ItemStack[] items = userInventory.getItems();
                    if (items != null) {
                        for (ItemStack item : items) {
                            entity.getWorld().dropItem(entity.getLocation(), item);
                        }
                    }
                    ItemStack[] armor = userInventory.getArmor();
                    if (armor != null) {
                        for (ItemStack item : armor) {
                            entity.getWorld().dropItem(entity.getLocation(), item);
                        }
                    }
                });
            }
        }
    }

}
