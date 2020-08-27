package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.guice.anotations.cache.CombatCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.guice.anotations.storage.UserStorage;
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
    private CooldownManager cooldownManager;

    @Inject
    @CombatCache
    private CacheProvider<String, String> combatCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (!user.get().hasTimer()) {
                cooldownManager.add(CooldownName.PVP_TIMER, player.getUniqueId().toString(), Long.parseLong("3600"));
            }

            userCache.add(player.getUniqueId(), user.get());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        cooldownManager.removeAll(player.getUniqueId().toString());
        combatCache.remove(player.getName());

        userCache.find(player.getUniqueId()).ifPresent(user -> userStorage.save(user));
    }
}
