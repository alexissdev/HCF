package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
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

import java.util.Optional;

@ACommand(names = "key", permission = "hcf.crates.key")
public class KeyCommand implements CommandClass {

    @Inject
    private HCF plugin;

    @Inject
    private LanguageLib languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CacheProvider<String, Crate> crateCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.usage");
        message.colorize();

        message.getMessages(language).forEach(player::sendMessage);
        return true;
    }

    @ACommand(names = "give", permission = "hcf.crates.key.give")
    @Usage(usage = "§cCorrect usage is /key give <crate> <amount> or /key give <target> <crate> <amount>")
    public boolean giveCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, @Alternative OfflinePlayer target, String crateName, Integer amount) {
        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.not-exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }

        if (amount == 0) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.error.in-valid-amount");

            player.sendMessage(message.colorize().getMessage(language));
            return true;
        }

        if (target == null) {
            player.getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.give.this");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }

        if (target.getPlayer() == null) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");
            message.setVariable("%target_name%", target.getName()).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }
        target.getPlayer().getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());

        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.give.other");
        message.setVariable("%target_name%", target.getName()).setVariable("%crate_name%", crateName).colorize();

        player.sendMessage(message.getMessage(language));

        TranslatableMessage targetMessage = languageLib.getTranslationManager().getTranslation("crate.key.give.this");
        targetMessage.setVariable("%crate_name%", crateName).colorize();

        target.getPlayer().sendMessage(targetMessage.getMessage(languageUtils.getLanguage(target.getPlayer())));
        return true;
    }

    @ACommand(names = "giveall", permission = "hcf.crates.key.giveall")
    @Usage(usage = "§cCorrect usage is /key giveall <crate> <amount>")
    public boolean giveAllCommand(@Injected(true) @Sender Player player,@Injected(true) @Language String language, String crateName, Integer amount) {
        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.not-exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }
        if (amount == 0) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.error.in-valid-amount");

            player.sendMessage(message.colorize().getMessage(language));
            return true;
        }
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.key.give.all");

        player.sendMessage(message.colorize().getMessage(language));

        plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> {

            onlinePlayer.getInventory().addItem(crate.get().getKey().getItem().setAmount(amount).build());

            TranslatableMessage targetMessage = languageLib.getTranslationManager().getTranslation("crate.key.give.this");
            message.setVariable("%crate_name%", crateName).colorize();

            onlinePlayer.sendMessage(message.getMessage(languageUtils.getLanguage(onlinePlayer)));
        });
        return true;
    }

}
