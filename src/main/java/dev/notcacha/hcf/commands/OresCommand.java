package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.placeholders.StatisticsPlaceholderApplier;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.OfflinePlayer;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class OresCommand implements CommandClass {

    @Inject
    private CacheProvider<UUID, User> userCache;

    @Inject
    private LanguageLib languageLib;

    @ACommand(names = "ores", permission = "hcf.ores")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, @Alternative OfflinePlayer target) {

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (target == null) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("stats.ores");
                message.setHolder(user.get())
                        .addPlaceholder(new StatisticsPlaceholderApplier())
                        .colorize();

                message.getMessages(language).forEach(player::sendMessage);
                return true;
            }
            if (target.getPlayer() == null) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");
                message.setVariable("%target_name%", target.getName()).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("stats.ores");
            message.setHolder(userCache.find(target.getUniqueId()).orElse(null))
                    .addPlaceholder(new StatisticsPlaceholderApplier())
                    .colorize();

            message.getMessages(language).forEach(player::sendMessage);
            return true;
        }

        return true;
    }
}
