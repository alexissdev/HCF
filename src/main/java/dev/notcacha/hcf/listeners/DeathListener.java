package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.deathban.Deathban;
import dev.notcacha.hcf.deathban.SimpleDeathban;
import dev.notcacha.hcf.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;
import java.util.UUID;

public class DeathListener implements Listener {

    @Inject
    private CacheProvider<UUID, User> userCache;

    @Inject
    private CacheProvider<UUID, Deathban> deathbanCache;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            user.get().getDeathsManager().add(1);

            /*
            * Add deathban
            * */
            deathbanCache.add(player.getUniqueId(), new SimpleDeathban(player.getUniqueId().toString(), player.getLocation(), Long.parseLong("3600")));

            if (event.getEntity().getKiller() != null) {
                Player target = event.getEntity().getKiller();
                userCache.find(target.getUniqueId()).ifPresent(targetUser -> targetUser.getKillsManager().add(1));
            }

        }
    }

}
