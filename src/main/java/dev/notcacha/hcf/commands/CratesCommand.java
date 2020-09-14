package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.BaseCrate;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.user.User;
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

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = "crate", permission = "hcf.crates")
public class CratesCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private CacheProvider<String, Crate> crateCache;
    @Inject
    private CacheProvider<UUID, User> userCache;

    @ACommand(names = "")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.usage");
        message.colorize();

        message.getMessages(language).forEach(player::sendMessage);
        return true;
    }

    @ACommand(names = {"create", "add"}, permission = "hcf.crates.create")
    @Usage(usage = "§cCorrect usage is /crate create <name from crate>")
    public boolean createCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String crateName) {
        if (crateCache.exists(crateName)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }
        crateCache.add(crateName, new BaseCrate(crateName));
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.create");
        message.setVariable("%crate_name%", crateName).colorize();

        player.sendMessage(message.getMessage(language));
        return true;
    }

    @ACommand(names = {"delete", "remove"}, permission = "hcf.crates.delete")
    @Usage(usage = "§cCorrect usage is /crate delete <name from crate>")
    public boolean deleteCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String crateName) {
        if (!crateCache.exists(crateName)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.not-exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }
        crateCache.remove(crateName);
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.delete");
        message.setVariable("%crate_name%", crateName).colorize();

        player.sendMessage(message.getMessage(language));
        return true;
    }

    @ACommand(names = {"setitems", "additems"}, permission = "hcf.crates.setitems")
    @Usage(usage = "§cCorrect usage is /crate setitems <name from crate>")
    public boolean setItemsCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String crateName) {
        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.not-exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }

        TranslatableMessage title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.items");
        title.setVariable("%crate_name%", crateName).colorize();

        Inventory inventory = Bukkit.createInventory(null,
                54,
                ChatColor.translateAlternateColorCodes('&', title.getMessage(language)));

        if (!crate.get().getItems().isEmpty()) {
            crate.get().getItems().forEach(inventory::addItem);
        }

        player.openInventory(inventory);
        userCache.find(player.getUniqueId()).ifPresent(user -> user.getOptions().setCrateEdited(crateName));
        return true;
    }

    @ACommand(names = "setcolor", permission = "hcf.crates.setcolor")
    @Usage(usage = "§cCorrect usage is /crate setcolor <name from crate>")
    public boolean setColorCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String crateName) {
        Optional<Crate> crate = crateCache.find(crateName);
        if (!crate.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.error.not-exists");
            message.setVariable("%crate_name%", crateName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }

        TranslatableMessage title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.color");
        title.setVariable("%crate_name%", crateName).colorize();

        Inventory inventory = Bukkit.createInventory(null,
                9,
                ChatColor.translateAlternateColorCodes('&', title.getMessage(language)));

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
        return true;
    }


}
