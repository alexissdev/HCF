package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.guice.anotations.cache.CrateCache;
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

import java.util.Optional;

@ACommand(names = "key", permission = "hcf.crates.key")
public class KeyCommand implements CommandClass {

    @Inject
    private HCF plugin;

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @CrateCache
    private CacheProvider<String, Crate> crateCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        languageLib.getTranslationManager().getTranslation("crate.key.usage").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(player::sendMessage);
        });
        return true;
    }

    @ACommand(names = "give", permission = "hcf.crates.key.give")
    @Usage(usage = "§cCorrect usage is /key give <crate> <amount> or /key give <target> <crate> <amount>")
    public boolean giveCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language,
                               @Alternative OfflinePlayer target, String crateName, Integer amount) {

        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        if (amount == 0) {
            languageLib.getTranslationManager().getTranslation("crate.key.error.in-valid-amount").ifPresent(message -> {
                message.setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        if (target == null) {
            player.getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());
            languageLib.getTranslationManager().getTranslation("crate.key.give.this").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
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
        target.getPlayer().getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());
        languageLib.getTranslationManager().getTranslation("crate.key.give.other").ifPresent(message -> {
            message.setVariable("%target_name%", target.getName()).setVariable("%crate_name%", crateName).setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        languageLib.getTranslationManager().getTranslation("crate.key.give.this").ifPresent(message -> {
            message.setVariable("%crate_name%", crateName).setColor(true);

            target.getPlayer().sendMessage(message.getMessage(languageUtils.getLanguage(target.getPlayer())));
        });
        return true;
    }

    @ACommand(names = "giveall", permission = "hcf.crates.key.giveall")
    @Usage(usage = "§cCorrect usage is /key giveall <crate> <amount>")
    public boolean giveAllCommand(@Injected(true) @Sender Player player,@Injected(true) @Language String language, String crateName, Integer amount) {
        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        if (amount == 0) {
            languageLib.getTranslationManager().getTranslation("crate.key.error.in-valid-amount").ifPresent(message -> {
                message.setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        languageLib.getTranslationManager().getTranslation("crate.key.give.all").ifPresent(message -> {
            message.setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> {

            onlinePlayer.getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());

            languageLib.getTranslationManager().getTranslation("crate.key.give.this").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                onlinePlayer.sendMessage(message.getMessage(languageUtils.getLanguage(onlinePlayer)));
            });
        });
        return true;
    }

}
