package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.KitCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.kit.SimpleKit;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Alternative;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"kitmanager", "km"})
public class KitManagerCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;
    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;
    @Inject
    @KitCache
    private CacheProvider<String, Kit> kitCache;

    @ACommand(names = "", permission = "hcf.kitmanager")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            languageLib.getTranslationManager().getTranslation("kitmanager.usages.main").ifPresent(message -> {
                message.setColor(true);

                message.getMessages(user.get().getLanguage()).forEach(player::sendMessage);
            });
            return true;
        }
        return true;
    }

    @ACommand(names = {"create", "add"}, permission = "hcf.kitmanager.create")
    @Usage(usage = "§cCorrect usage /kitmanager create <name from kit> <true/false set your items inventory from kit> <true/false set your armor items from kit>")
    public boolean createCommand(@Injected(true) @Sender Player player, String kitName, Integer cost, @Alternative Boolean items, @Alternative Boolean armor) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (kitCache.find(kitName).isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.exists").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.create").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
            kitCache.add(kitName, new SimpleKit(kitName)
                    .setItems((items != null && items) ? player.getInventory().getContents() : null)
                    .setArmor((armor != null && armor) ? player.getInventory().getArmorContents() : null));
        }
        return true;
    }

    @ACommand(names = {"remove", "delete"}, permission = "hcf.kitmanager.delete")
    @Usage(usage = "§cCorrect usage /kitmanager delete <name from kit>")
    public boolean removeCommand(@Injected(true) @Sender Player player, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            if (!kitCache.find(kitName).isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exists").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.remove").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
            kitCache.remove(kitName);
        }
        return true;
    }

    @ACommand(names = "setitems", permission = "hcf.kitmanager.setitems")
    @Usage(usage = "§cCorrect usage /kitmanager setitems <name from kit>")
    public boolean setItemsCommand(@Injected(true) @Sender Player player, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exists").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.set.items.inventory-name").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                Inventory inventory = Bukkit.createInventory(null, 54, message.getMessage(user.get().getLanguage()));

                player.openInventory(inventory);
            });
            user.get().getOptions().setKitEdited(kit.get().getId());
            languageLib.getTranslationManager().getTranslation("kitmanager.set.items.message").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
        }
        return true;
    }

    @ACommand(names = "additems", permission = "hcf.kitmanager.additems")
    @Usage(usage = "§cCorrect usage /kitmanager additems <name from kit>")
    public boolean addItemsCommand(@Injected(true) @Sender Player player, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.add.items.inventory-name").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                Inventory inventory = Bukkit.createInventory(null, 54, message.getMessage(user.get().getLanguage()));

                if (kit.get().getItems().isPresent()) {
                    for (ItemStack item : kit.get().getItems().get()) {
                        inventory.addItem(item);
                    }
                }
                player.openInventory(inventory);
            });
            user.get().getOptions().setKitEdited(kit.get().getId());
            languageLib.getTranslationManager().getTranslation("kitmanager.add.items.message").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
        }
        return true;
    }

    @ACommand(names = "setarmor", permission = "hcf.kitmanager.setarmor")
    @Usage(usage = "§cCorrect usage /kitmanager setarmor <name from kit>")
    public boolean setArmorCommand(@Injected(true) @Sender Player player, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.set.armor.inventory-name").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                Inventory inventory = Bukkit.createInventory(null, 9, message.getMessage(user.get().getLanguage()));

                player.openInventory(inventory);
            });
            user.get().getOptions().setKitEdited(kit.get().getId());
            languageLib.getTranslationManager().getTranslation("kitmanager.set.armor.message").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
        }
        return true;
    }

    @ACommand(names = "addarmor", permission = "hcf.kitmanager.addarmor")
    @Usage(usage = "§cCorrect usage /kitmanager addarmor <name from kit>")
    public boolean addArmorCommand(@Injected(true) @Sender Player player, String kitName) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<Kit> kit = kitCache.find(kitName);
            if (!kit.isPresent()) {
                languageLib.getTranslationManager().getTranslation("kitmanager.error.not-exits").ifPresent(message -> {
                    message.setVariable("%kit_name%", kitName).setColor(true);

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.add.armor.inventory-name").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                Inventory inventory = Bukkit.createInventory(null, 9, message.getMessage(user.get().getLanguage()));

                if (kit.get().getArmor().isPresent()) {
                    for (ItemStack item : kit.get().getArmor().get()) {
                        inventory.addItem(item);
                    }
                }
                player.openInventory(inventory);
            });
            user.get().getOptions().setKitEdited(kit.get().getId());
            languageLib.getTranslationManager().getTranslation("kitmanager.add.items.message").ifPresent(message -> {
                message.setVariable("%kit_name%", kitName).setColor(true);

                player.sendMessage(message.getMessage(user.get().getLanguage()));
            });
        }
        return true;
    }

}
