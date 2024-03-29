package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.api.events.user.UserJoinEvent;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.CooldownUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.UUID;

public class UserListener implements Listener {

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<UUID, User> userCache;

    @Inject
    private StorageProvider<User> userStorage;

    @Inject
    private CooldownManager cooldownManager;

    @Inject
    private CacheProvider<String, String> combatCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (!user.get().hasTimer()) {
                cooldownManager.add(CooldownUtils.PVP_TIMER, player.getUniqueId().toString(), Long.parseLong("3600"));
                user.get().setTimer(true);
            }

            userCache.add(player.getUniqueId(), user.get());
            plugin.getServer().getPluginManager().callEvent(new UserJoinEvent(user.get()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        cooldownManager.removeAll(player.getUniqueId().toString());
        combatCache.remove(player.getName());

        userCache.find(player.getUniqueId()).ifPresent(user -> {
            plugin.getServer().getPluginManager().callEvent(new UserJoinEvent(user));

            userStorage.save(user);
        });
    }
}
