package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.guice.anotations.cache.CombatCache;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.guice.anotations.storage.UserStorage;
import dev.notcacha.hcf.user.HCFUser;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.CooldownName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;

public class UserListener implements Listener {

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    @UserStorage
    private StorageProvider<User> userStorage;

    @Inject
    @CooldownCache
    private CacheProvider<String, Long> cooldownCache;

    @Inject
    @CombatCache
    private CacheProvider<String, String> combatCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Optional<User> user = userStorage.find(player.getUniqueId().toString());
        if (user.isPresent()) {
            userCache.add(player.getUniqueId(), user.get());
            return;
        }
        userCache.add(player.getUniqueId(), new HCFUser(player.getUniqueId().toString(), player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        cooldownCache.remove(CooldownName.COMBAT_COOLDOWN.replace("%id%", player.getUniqueId().toString()));
        cooldownCache.remove(CooldownName.PEARL_COOLDOWN.replace("%id%", player.getUniqueId().toString()));
        cooldownCache.remove(CooldownName.GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
        cooldownCache.remove(CooldownName.ENCHANT_GOLDEN_APPLE.replace("%id%", player.getUniqueId().toString()));
        combatCache.remove(player.getName());

        userCache.find(player.getUniqueId()).ifPresent(user -> userStorage.save(user));
    }
}
