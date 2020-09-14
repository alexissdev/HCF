package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.core.storage.StorageProvider;
import dev.notcacha.hcf.deathban.Deathban;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.TimeUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Optional;
import java.util.UUID;

public class DeathbanListener implements Listener {

    @Inject
    private CacheProvider<UUID, Deathban> deathbanCache;

    @Inject
    private StorageProvider<User> userStorage;

    @Inject
    private TimeUtils timeUtils;

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Optional<User> user = userStorage.find(event.getUniqueId().toString());
        if (user.isPresent()) {
            Optional<Deathban> deathban = deathbanCache.find(event.getUniqueId());
            if (deathban.isPresent()) {
                if (deathban.get().hasActive()) {
                    event.setKickMessage("§cYour deathban is still active, you have to wait " + timeUtils.format(deathban.get().getRemaining(), user.get().getLanguage()));
                    return;
                }
                deathbanCache.remove(event.getUniqueId());
            }
        }
    }

}
