package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.BaseCrate;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.guice.anotations.cache.CrateCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.hcf.utils.item.ItemBuilder;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = "crate", permission = "hcf.crate")
public class CrateCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @CrateCache
    private CacheProvider<String, Crate> crateCache;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        languageLib.getTranslationManager().getTranslation("crate.usage").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(languageUtils.getLanguage(player)).forEach(player::sendMessage);
        });
        return true;
    }

    @ACommand(names = {"create", "add"}, permission = "hcf.crate.create")
    @Usage(usage = "§cCorrect usage is /crate create <name from crate>")
    public boolean createCommand(@Injected(true) @Sender Player player, String crateName) {
        String language = languageUtils.getLanguage(player);
        if (crateCache.exists(crateName)) {
            languageLib.getTranslationManager().getTranslation("crate.error.exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        crateCache.add(crateName, new BaseCrate(crateName));
        languageLib.getTranslationManager().getTranslation("crate.create").ifPresent(message -> {
            message.setVariable("%crate_name%", crateName).setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        return true;
    }

    @ACommand(names = {"delete", "remove"}, permission = "hcf.crate.delete")
    @Usage(usage = "§cCorrect usage is /crate delete <name from crate>")
    public boolean deleteCommand(@Injected(true) @Sender Player player, String crateName) {
        String language = languageUtils.getLanguage(player);
        if (!crateCache.exists(crateName)) {
            languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        crateCache.remove(crateName);
        languageLib.getTranslationManager().getTranslation("crate.delete").ifPresent(message -> {
            message.setVariable("%crate_name%", crateName).setColor(true);

            player.sendMessage(message.getMessage(language));
        });
        return true;
    }

    @ACommand(names = {"setitems", "additems"}, permission = "hcf.crate.setitems")
    @Usage(usage = "§cCorrect usage is /crate setitems <name from crate>")
    public boolean setItemsCommand(@Injected(true) @Sender Player player, String crateName) {
        String language = languageUtils.getLanguage(player);

        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }
        Optional<TranslatableMessage> title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.items");
        if (title.isPresent()) {
            title.get().setVariable("%crate_name%", crateName).setColor(true);

            Inventory inventory = Bukkit.createInventory(null,
                    54,
                    ChatColor.translateAlternateColorCodes('&', "&6Set items from crate &6" + crateName));

            if (!crate.get().getItems().isEmpty()) {
                crate.get().getItems().forEach(inventory::addItem);
            }

            player.openInventory(inventory);
            userCache.find(player.getUniqueId()).ifPresent(user -> user.getOptions().setCrateEdited(crateName));
        }
        return true;
    }

    @ACommand(names = "setcolor", permission = "hcf.crate.setcolor")
    @Usage(usage = "§cCorrect usage is /crate setcolor <name from crate>")
    public boolean setColorCommand(@Injected(true) @Sender Player player, String crateName) {
        String language = languageUtils.getLanguage(player);

        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                message.setVariable("%crate_name%", crateName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }

        Optional<TranslatableMessage> title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.color");
        if (title.isPresent()) {
            title.get().setVariable("%crate_name%", crateName).setColor(true);

            Inventory inventory = Bukkit.createInventory(null,
                    9,
                    ChatColor.translateAlternateColorCodes('&', title.get().getMessage(language)));

            inventory.setItem(0, new ItemBuilder(Material.WOOL).setName("&fWhite Color", true).build());
            inventory.setItem(1, new ItemBuilder(Material.WOOL).setData(1).setName("&6Orange Color", true).build());
            inventory.setItem(2, new ItemBuilder(Material.WOOL).setData(11).setName("&9Blue Color", true).build());
            inventory.setItem(3, new ItemBuilder(Material.WOOL).setData(5).setName("&2Green Color", true).build());
            inventory.setItem(4, new ItemBuilder(Material.WOOL).setData(4).setName("&eYellow Color", true).build());
            inventory.setItem(5, new ItemBuilder(Material.WOOL).setData(14).setName("&cRed Color", true).build());
            inventory.setItem(6, new ItemBuilder(Material.WOOL).setData(14).setName("&4Dark Red Color", true).build());
            inventory.setItem(7, new ItemBuilder(Material.WOOL).setData(15).setName("&8Black Color", true).build());
            inventory.setItem(8, new ItemBuilder(Material.WOOL).setData(10).setName("&5Purple Color", true).build());

            player.openInventory(inventory);
            userCache.find(player.getUniqueId()).ifPresent(user -> user.getOptions().setCrateEdited(crateName));
        }
        return true;
    }


}
