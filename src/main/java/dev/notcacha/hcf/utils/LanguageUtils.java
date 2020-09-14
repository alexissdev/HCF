package dev.notcacha.hcf.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class LanguageUtils {

    @Inject
    private CacheProvider<UUID, User> userCache;

    /**
     * @return a language, in any case that {@param sender} is a player it will return the language of that player or if it does not return the default
     */

    public String getLanguage(CommandSender sender) {
        String language = "EN";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                language = user.get().getLanguage();
            }
        }

        return language;
    }

    /**
     * @return language from {@param player} get from {@link User}
     */

    public String getLanguage(Player player) {
        String language = "EN";

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            language = user.get().getLanguage();
        }

        return language;
    }

    /**
     * @return if {@param language} is a valid language among the registered languages
     */

    public boolean inValid(String language) {
        return !language.toLowerCase().equalsIgnoreCase("EN")
                || !language.toLowerCase().equalsIgnoreCase("ES");
    }
}
