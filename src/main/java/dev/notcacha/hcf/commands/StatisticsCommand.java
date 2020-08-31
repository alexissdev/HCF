package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.guice.anotations.placeholders.StatisticsPlaceholder;
import dev.notcacha.hcf.placeholders.PlaceholderApplier;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class StatisticsCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @StatisticsPlaceholder
    private PlaceholderApplier statisticsPlaceholder;

    @ACommand(names = "stats", permission = "hcf.stats")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, @Alternative OfflinePlayer target) {
        if (target == null) {
            languageLib.getTranslationManager().getTranslation("stats.general").ifPresent(message -> {
                message.setColor(true);

                message.getMessages(language).forEach(finalMessage -> {
                    player.sendMessage(statisticsPlaceholder.set(player, finalMessage));
                });
            });
            return true;
        }
        languageLib.getTranslationManager().getTranslation("stats.general").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(finalMessage -> {
                player.sendMessage(statisticsPlaceholder.set(target.getPlayer(), finalMessage));
            });
        });
        return true;
    }
}
