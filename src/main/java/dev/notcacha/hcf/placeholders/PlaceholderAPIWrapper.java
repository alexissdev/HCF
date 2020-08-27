package dev.notcacha.hcf.placeholders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class PlaceholderAPIWrapper extends PlaceholderExpansion {

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Override
    public @NotNull String getIdentifier() {
        return "hcf";
    }

    @Override
    public @NotNull String getAuthor() {
        return "NotCacha";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        Optional<User> user = userCache.find(player.getUniqueId());
        if (!user.isPresent()) {
            return "";
        }

        switch (params) {
            case "kills":
                return String.valueOf(user.get().getKillsManager().get());
            case "deaths":
                return String.valueOf(user.get().getDeathsManager().get());
            case "diamonds":
                return String.valueOf(user.get().getDiamondsManager().get());
            case "emeralds":
                return String.valueOf(user.get().getEmeraldsManager().get());
            case "redstone":
                return String.valueOf(user.get().getRedstoneManager().get());
            case "lapis":
                return String.valueOf(user.get().getLapisManager().get());
            case "gold":
                return String.valueOf(user.get().getGoldManager().get());
            case "iron":
                return String.valueOf(user.get().getIronManager().get());
            case "coal":
                return String.valueOf(user.get().getCoalManager().get());
            case "language":
                return user.get().getLanguage();
            default:
                return "";
        }
    }
}
