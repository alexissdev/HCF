package dev.notcacha.hcf.scheduler;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.Cooldown;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class MainScheduler extends BukkitRunnable {

    private final HCF plugin = JavaPlugin.getPlugin(HCF.class);

    private final CooldownManager cooldownManager;
    private final CacheProvider<String, String> combatCache;

    public MainScheduler(CooldownManager cooldownManager, CacheProvider<String, String> combatCache) {
        this.cooldownManager = cooldownManager;
        this.combatCache = combatCache;
    }

    @Override
    public void run() {
        Optional<Long> sotwCooldown = cooldownManager.find(Cooldown.SOTW_TIMER);
        if (sotwCooldown.isPresent()) {
            if (sotwCooldown.get() <= 0) {
                cooldownManager.remove(Cooldown.SOTW_TIMER);
            }
        }

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            Optional<Long> combatCooldown = cooldownManager.find(Cooldown.COMBAT_COOLDOWN, player.getUniqueId().toString());
            if (combatCooldown.isPresent()) {
                if (combatCooldown.get() <= 0) {
                    cooldownManager.remove(Cooldown.COMBAT_COOLDOWN, player.getUniqueId().toString());
                    combatCache.remove(player.getName());
                }
            }

            Optional<Long> goldenCooldown = cooldownManager.find(Cooldown.GOLDEN_APPLE, player.getUniqueId().toString());
            if (goldenCooldown.isPresent()) {
                if (goldenCooldown.get() <= 0) {
                    cooldownManager.remove(Cooldown.GOLDEN_APPLE, player.getUniqueId().toString());
                }
            }

            Optional<Long> enchantGoldenCooldown = cooldownManager.find(Cooldown.ENCHANT_GOLDEN_APPLE, player.getUniqueId().toString());
            if (enchantGoldenCooldown.isPresent()) {
                if (enchantGoldenCooldown.get() <= 0) {
                    cooldownManager.remove(Cooldown.ENCHANT_GOLDEN_APPLE, player.getUniqueId().toString());
                }
            }
        });
    }
}
