package dev.notcacha.hcf.scheduler;

import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.utils.CooldownName;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class MainScheduler extends BukkitRunnable {

    private final HCF plugin = JavaPlugin.getPlugin(HCF.class);

    private final CacheProvider<String, Long> cooldownCache;
    private final CacheProvider<String, String> combatCache;

    public MainScheduler(CacheProvider<String, Long> cooldownCache, CacheProvider<String, String> combatCache) {
        this.cooldownCache = cooldownCache;
        this.combatCache = combatCache;
    }

    @Override
    public void run() {
        Optional<Long> sotwCooldown = cooldownCache.find("sotw");
        if (sotwCooldown.isPresent()) {
            if (sotwCooldown.get() <= 0) {
                cooldownCache.remove("sotw");
            }
        }

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            Optional<Long> combatCooldown = cooldownCache.find(CooldownName.COMBAT_COOLDOWN.replace("%id%", player.getUniqueId().toString()));
            if (combatCooldown.isPresent()) {
                if (combatCooldown.get() <= 0) {
                    cooldownCache.remove(CooldownName.COMBAT_COOLDOWN.replace("%id%", player.getUniqueId().toString()));
                    cooldownCache.remove(player.getName());
                }
            }

            Optional<Long> goldenCooldown = cooldownCache.find(CooldownName.GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
            if (goldenCooldown.isPresent()) {
                if (goldenCooldown.get() <= 0) {
                    cooldownCache.remove(CooldownName.GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
                }
            }

            Optional<Long> enchantGoldenCooldown = cooldownCache.find(CooldownName.ENCHANT_GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
            if (enchantGoldenCooldown.isPresent()) {
                if (enchantGoldenCooldown.get() <= 0) {
                    cooldownCache.remove(CooldownName.ENCHANT_GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
                }
            }
        });
    }
}
