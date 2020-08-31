package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.guice.anotations.placeholders.OresPlaceholder;
import dev.notcacha.hcf.placeholders.PlaceholderApplier;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class OresCommand implements CommandClass {

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @OresPlaceholder
    private PlaceholderApplier oresPlaceholder;

    @ACommand(names = "ores", permission = "hcf.ores")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, @Alternative OfflinePlayer target) {

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (target == null) {
                languageLib.getTranslationManager().getTranslation("stats.ores").ifPresent(message -> {
                    message.setColor(true);

                    message.getMessages(language).forEach(finalMessage -> player.sendMessage(oresPlaceholder.set(player, finalMessage)));
                });
                return true;
            }
            if (target.getPlayer() == null) {
                languageLib.getTranslationManager().getTranslation("general.target-offline").ifPresent(message -> {
                    message.setVariable("%target_name%", target.getName()).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("stats.ores").ifPresent(message -> {
                message.setColor(true);

                message.getMessages(language).forEach(finalMessage -> player.sendMessage(oresPlaceholder.set(target.getPlayer(), finalMessage)));
            });
            return true;
        }

        return true;
    }
}
