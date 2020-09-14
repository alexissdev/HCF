package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.combatlog.CombatLogger;
import dev.notcacha.hcf.cooldown.CooldownManager;;
import dev.notcacha.hcf.user.inventory.UserInventory;
import dev.notcacha.hcf.utils.CooldownUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CombatLoggerListener implements Listener {

    @Inject
    private CacheProvider<String, UserInventory> combatLoggerCache;
    @Inject
    private CacheProvider<String, String> combatLogCache;

    @Inject
    private CooldownManager cooldownManager;
    @Inject
    private CombatLogger combatLogger;


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Optional<Long> combatCooldown = cooldownManager.find(CooldownUtils.COMBAT_COOLDOWN, event.getPlayer().getUniqueId().toString());
        if (combatCooldown.isPresent()) {
            if (combatCooldown.get() > 0) {
                combatLogger.addLogger(event.getPlayer());
            }
            combatLogCache.remove(event.getPlayer().getName());
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
