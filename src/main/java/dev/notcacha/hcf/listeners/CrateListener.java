package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CrateListener implements Listener {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private CacheProvider<UUID, User> userCache;
    @Inject
    private CacheProvider<String, Crate> crateCache;

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String inventoryName = ChatColor.stripColor(event.getInventory().getName());

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            TranslatableMessage title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.items");
            Optional<String> crateName = user.get().getOptions().getCrateEdited();
            if (crateName.isPresent()) {
                title.setVariable("%crate_name%", crateName.get()).colorize();

                if (inventoryName.equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                    Optional<Crate> crate = crateCache.find(crateName.get());
                    if (!crate.isPresent()) {
                        return;
                    }

                    crate.get().setItems(Arrays.asList(event.getInventory().getContents()));
                    TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-items");
                    message.setVariable("%crate_name%", crate.get().getId()).colorize();

                    player.sendMessage(message.getMessage(user.get().getLanguage()));
                }
            }
        }

    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventoryName = ChatColor.stripColor(event.getInventory().getName());

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            TranslatableMessage title = languageLib.getTranslationManager().getTranslation("crate.inventory-name.set.color");
            Optional<String> crateName = user.get().getOptions().getCrateEdited();
            if (!crateName.isPresent()) {
                return;
            }

            title.setVariable("%crate_name%", crateName.get()).colorize();

            if (inventoryName.equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                Optional<Crate> crate = crateCache.find(crateName.get());
                if (!crate.isPresent()) {
                    return;
                }

                switch (event.getSlot()) {
                    case 0: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&f").setVariable("%color_name%", "WHITE").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.WHITE);
                        event.setCancelled(true);
                        break;
                    }
                    case 1: {
                        player.closeInventory();
                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&f").setVariable("%color_name%", "WHITE").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.GOLD);
                        event.setCancelled(true);
                        break;
                    }
                    case 2: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&9").setVariable("%color_name%", "BLUE").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.BLUE);
                        event.setCancelled(true);
                        break;
                    }
                    case 3: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&a").setVariable("%color_name%", "GREEN").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.GREEN);
                        event.setCancelled(true);
                        break;
                    }
                    case 4: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&e").setVariable("%color_name%", "YELLOW").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.YELLOW);
                        event.setCancelled(true);
                        break;
                    }
                    case 5: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&c").setVariable("%color_name%", "RED").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));
                        crate.get().getKey().setColor(ChatColor.RED);
                        event.setCancelled(true);
                        break;
                    }
                    case 6: {
                        player.closeInventory();

                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&4").setVariable("%color_name%", "DARK_RED").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));

                        crate.get().getKey().setColor(ChatColor.DARK_RED);
                        event.setCancelled(true);
                        break;
                    }
                    case 7: {
                        player.closeInventory();
                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&8").setVariable("%color_name%", "BLACK").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));
                        crate.get().getKey().setColor(ChatColor.BLACK);
                        event.setCancelled(true);
                        break;
                    }
                    case 8: {
                        player.closeInventory();
                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("crate.set-color");
                        message.setVariable("%color_char%", "&5").setVariable("%color_name%", "DARK_PURPLE").colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));
                        crate.get().getKey().setColor(ChatColor.DARK_PURPLE);
                        event.setCancelled(true);
                        break;
                    }
                    default: {
                        event.setCancelled(true);
                    }
                }
            }
        }

    }

}
