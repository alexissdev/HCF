package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.kit.SimpleKit;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"kitmanager", "km"})
public class KitManagerCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;
    @Inject
    private CacheProvider<UUID, User> userCache;
    @Inject
    private CacheProvider<String, Kit> kitCache;

    @ACommand(names = "", permission = "hcf.kitmanager")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.usages.main");
            message.colorize();

            message.getMessages(language).forEach(player::sendMessage);
            return true;
        }
        return true;
    }

    @ACommand(names = {"create", "add"}, permission = "hcf.kitmanager.create")
    @Usage(usage = "§cCorrect usage /kitmanager create <name from kit> <true/false set your items inventory from kit> <true/false set your armor items from kit>")
    public boolean createCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName, @Alternative Boolean items, @Alternative Boolean armor) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (kitCache.find(kitName).isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.exists");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.create");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));

            kitCache.add(kitName, new SimpleKit(kitName)
                    .setItems((items != null && items) ? player.getInventory().getContents() : null)
                    .setArmor((armor != null && armor) ? player.getInventory().getArmorContents() : null));
        }
        return true;
    }

    @ACommand(names = {"remove", "delete"}, permission = "hcf.kitmanager.delete")
    @Usage(usage = "§cCorrect usage /kitmanager delete <name from kit>")
    public boolean removeCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (!kitCache.find(kitName).isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exists");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));

                return true;
            }
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.remove");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));

            kitCache.remove(kitName);
        }
        return true;
    }

    @ACommand(names = "setitems", permission = "hcf.kitmanager.setitems")
    @Usage(usage = "§cCorrect usage /kitmanager setitems <name from kit>")
    public boolean setItemsCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exists");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage title = languageLib.getTranslationManager().getTranslation("kitmanager.set.items.inventory-name");
            title.setVariable("%kit_name%", kitName).colorize();

            Inventory inventory = Bukkit.createInventory(null, 54, title.getMessage(language));

            player.openInventory(inventory);

            user.get().getOptions().setKitEdited(kit.get().getId());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.set.items.message");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));
        }
        return true;
    }

    @ACommand(names = "additems", permission = "hcf.kitmanager.additems")
    @Usage(usage = "§cCorrect usage /kitmanager additems <name from kit>")
    public boolean addItemsCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }

            TranslatableMessage title = languageLib.getTranslationManager().getTranslation("kitmanager.add.items.inventory-name");
            title.setVariable("%kit_name%", kitName).colorize();

            Inventory inventory = Bukkit.createInventory(null, 54, title.getMessage(language));

            if (kit.get().getItems().isPresent()) {
                for (ItemStack item : kit.get().getItems().get()) {
                    inventory.addItem(item);
                }
            }
            player.openInventory(inventory);

            user.get().getOptions().setKitEdited(kit.get().getId());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.add.items.message");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));
        }
        return true;
    }

    @ACommand(names = "setarmor", permission = "hcf.kitmanager.setarmor")
    @Usage(usage = "§cCorrect usage /kitmanager setarmor <name from kit>")
    public boolean setArmorCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage title = languageLib.getTranslationManager().getTranslation("kitmanager.set.armor.inventory-name");
            title.setVariable("%kit_name%", kitName).colorize();

            Inventory inventory = Bukkit.createInventory(null, 9, title.getMessage(language));

            player.openInventory(inventory);

            user.get().getOptions().setKitEdited(kit.get().getId());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.set.armor.message");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));
        }
        return true;
    }

    @ACommand(names = "addarmor", permission = "hcf.kitmanager.addarmor")
    @Usage(usage = "§cCorrect usage /kitmanager addarmor <name from kit>")
    public boolean addArmorCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits");
                message.setVariable("%kit_name%", kitName).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage title =  languageLib.getTranslationManager().getTranslation("kitmanager.add.armor.inventory-name");
            title.setVariable("%kit_name%", kitName).colorize();

            Inventory inventory = Bukkit.createInventory(null, 9, title.getMessage(language));

            if (kit.get().getArmor().isPresent()) {
                for (ItemStack item : kit.get().getArmor().get()) {
                    inventory.addItem(item);
                }
            }
            player.openInventory(inventory);

            user.get().getOptions().setKitEdited(kit.get().getId());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("kitmanager.add.items.message");
            message.setVariable("%kit_name%", kitName).colorize();

            player.sendMessage(message.getMessage(language));
        }
        return true;
    }

}
