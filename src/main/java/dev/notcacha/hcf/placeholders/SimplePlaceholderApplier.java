package dev.notcacha.hcf.placeholders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import org.bukkit.entity.Player;

import java.util.UUID;

@Singleton
public class SimplePlaceholderApplier implements PlaceholderApplier {

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Override
    public String set(Player player, String text) {
        return userCache.find(player.getUniqueId()).map(user ->
                text.replace("%player_name%", user.getName())
                        .replace("%player_kills%", String.valueOf(user.getKillsManager().get()))
                        .replace("%player_deaths%", String.valueOf(user.getDeathsManager().get()))
        ).orElse(text);
    }
}
