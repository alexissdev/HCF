package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.UUID;

@ACommand(names = "language", permission = "hcf.language")
public class LanguageCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Alternative OfflinePlayer target) {
        String playerLanguage = languageUtils.getLanguage(player);
        if (target == null) {
            languageLib.getTranslationManager().getTranslation("language.get.this").ifPresent(message -> {
                message.setVariable("%language%", playerLanguage).setColor(true);

                player.sendMessage(message.getMessage(playerLanguage));
            });
            return true;
        }
        if (target.getPlayer() == null) {
            languageLib.getTranslationManager().getTranslation("general.target-offline").ifPresent(message -> {
                message.setVariable("%target_name%", target.getName()).setColor(true);

                player.sendMessage(message.getMessage(playerLanguage));
            });
            return true;
        }
        languageLib.getTranslationManager().getTranslation("language.get.other").ifPresent(message -> {
            message.setVariable("%target_name%", target.getName())
                    .setVariable("%language%", languageUtils.getLanguage(target.getPlayer())).setColor(true);

            player.sendMessage(message.getMessage(playerLanguage));
        });
        return true;
    }

    @ACommand(names = "set", permission = "hcf.language.set")
    @Usage(usage = "§cCorrect usage is /language set <language has been set>")
    public boolean mainCommand(@Injected(true) @Sender Player player, String language) {
        String playerLanguage = languageUtils.getLanguage(player);
        if (languageUtils.inValid(language)) {
            languageLib.getTranslationManager().getTranslation("language.invalid-language").ifPresent(message -> {
                message.setVariable("%language%", language).setColor(true);

                player.sendMessage(message.getMessage(playerLanguage));
            });
            return true;
        }
        userCache.find(player.getUniqueId()).ifPresent(user -> {
            user.setLanguage(language);

            languageLib.getTranslationManager().getTranslation("language.set").ifPresent(message -> {
                message.setVariable("%language%", user.getLanguage()).setColor(true);

                player.sendMessage(message.getMessage(user.getLanguage()));
            });
        });

        return true;
    }

}
