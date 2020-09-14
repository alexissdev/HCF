package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.OfflinePlayer;

import org.bukkit.entity.Player;

import java.util.UUID;

@ACommand(names = "language", permission = "hcf.language")
public class LanguageCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CacheProvider<UUID, User> userCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String playerLanguage, @Alternative OfflinePlayer target) {
        if (target == null) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("language.get.this");
            message.setVariable("%language%", playerLanguage).colorize();

            player.sendMessage(message.getMessage(playerLanguage));
            return true;
        }

        if (target.getPlayer() == null) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");
            message.setVariable("%target_name%", target.getName()).colorize();

            player.sendMessage(message.getMessage(playerLanguage));
            return true;
        }

        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("language.get.other");
        message.setVariable("%target_name%", target.getName())
                .setVariable("%language%", languageUtils.getLanguage(target.getPlayer())).colorize();

        player.sendMessage(message.getMessage(playerLanguage));
        return true;
    }

    @ACommand(names = "set", permission = "hcf.language.set")
    @Usage(usage = "§cCorrect usage is /language set <language has been set>")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String playerLanguage, String language) {
        if (languageUtils.inValid(language)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("language.invalid-language");
            message.setVariable("%language%", language).colorize();

            player.sendMessage(message.getMessage(playerLanguage));
            return true;
        }
        userCache.find(player.getUniqueId()).ifPresent(user -> {
            user.setLanguage(language);

            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("language.set");
            message.setVariable("%language%", user.getLanguage()).colorize();

            player.sendMessage(message.getMessage(user.getLanguage()));
        });

        return true;
    }

}
