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
                        .replace("%player_diamonds%", String.valueOf(user.getDiamondsManager().get()))
                        .replace("%player_emeralds%", String.valueOf(user.getEmeraldsManager().get()))
                        .replace("%player_readstone%", String.valueOf(user.getRedstoneManager().get()))
                        .replace("%player_lapis%", String.valueOf(user.getLapisManager().get()))
                        .replace("%player_gold%", String.valueOf(user.getGoldManager().get()))
                        .replace("%player_iron%", String.valueOf(user.getIronManager().get()))
                        .replace("%player_coal%", String.valueOf(user.getCoalManager().get()))
                        .replace("%player_faction%", (user.getFaction().getFactionName().isPresent()) ? user.getFaction().getFactionName().get() : "None")
        ).orElse(text);
    }
}
