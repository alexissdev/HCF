package dev.notcacha.hcf.combatlog;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.inventory.UserInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Singleton
public class CombatLogger {

    @Inject
    private CacheProvider<String, UserInventory> combatLoggerCache;

    /**
     * spawning an entity of type 'Skeleton' using reference to {@param player} in this case it will be your combat logger
     * */

    public void addLogger(Player player) {
        combatLoggerCache.add(player.getName(), new UserInventory(player));

        LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
        entity.getEquipment().setArmorContents(player.getInventory().getArmorContents());

        entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + " &7[&4&lCombat Logger&7]"));
        entity.setCustomNameVisible(true);

    }
}
