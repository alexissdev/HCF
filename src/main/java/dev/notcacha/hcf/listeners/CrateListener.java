package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.crates.Crate;
import dev.notcacha.hcf.guice.anotations.cache.CrateCache;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.Optional;

public class CrateListener implements Listener {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @CrateCache
    private CacheProvider<String, Crate> crateCache;

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String language = languageUtils.getLanguage(player);

        String inventoryName = ChatColor.stripColor(event.getInventory().getName());
        String[] inventoryNamePart = inventoryName.split(" ");

        if (inventoryNamePart.length == 5) {
            if (inventoryName.equalsIgnoreCase("Set items from crate " + inventoryNamePart[4])) {
                Optional<Crate> crate = crateCache.find(inventoryNamePart[4]);
                if (!crate.isPresent()) {
                    languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                        message.setVariable("%crate_name%", inventoryNamePart[4]).setColor(true);

                        player.sendMessage(message.getMessage(language));
                    });
                    return;
                }

                crate.get().setItems(Arrays.asList(event.getInventory().getContents()));
                languageLib.getTranslationManager().getTranslation("crate.set-items").ifPresent(message -> {
                    message.setVariable("%crate_name%", inventoryNamePart[4]).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String language = languageUtils.getLanguage(player);

        String inventoryName = ChatColor.stripColor(event.getInventory().getName());
        String[] inventoryNamePart = inventoryName.split(" ");

        if (inventoryNamePart.length == 5) {
            if (inventoryName.equalsIgnoreCase("Set color from crate " + inventoryNamePart[4])) {
                Optional<Crate> crate = crateCache.find(inventoryNamePart[4]);
                if (!crate.isPresent()) {
                    languageLib.getTranslationManager().getTranslation("crate.error.not-exists").ifPresent(message -> {
                        message.setVariable("%crate_name%", inventoryNamePart[4]).setColor(true);

                        player.sendMessage(message.getMessage(language));
                    });
                    return;
                }

                switch (event.getSlot()) {
                    case 0: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&f").setVariable("%color_name%", "WHITE").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.WHITE);
                        event.setCancelled(true);
                        break;
                    }
                    case 1: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&6").setVariable("%color_name%", "GOLD").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.GOLD);
                        event.setCancelled(true);
                        break;
                    }
                    case 2: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&9").setVariable("%color_name%", "BLUE").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.BLUE);
                        event.setCancelled(true);
                        break;
                    }
                    case 3: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&a").setVariable("%color_name%", "GREEN").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.GREEN);
                        event.setCancelled(true);
                        break;
                    }
                    case 4: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&e").setVariable("%color_name%", "YELLOW").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.YELLOW);
                        event.setCancelled(true);
                        break;
                    }
                    case 5: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&c").setVariable("%color_name%", "RED").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.RED);
                        event.setCancelled(true);
                        break;
                    }
                    case 6: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&4").setVariable("%color_name%", "DARK_RED").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.DARK_RED);
                        event.setCancelled(true);
                        break;
                    }
                    case 7: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&8").setVariable("%color_name%", "BLACK").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        crate.get().getKey().setColor(ChatColor.BLACK);
                        event.setCancelled(true);
                        break;
                    }
                    case 8: {
                        player.closeInventory();
                        languageLib.getTranslationManager().getTranslation("crate.set-color").ifPresent(message -> {
                            message.setVariable("%color_char%", "&5").setVariable("%color_name%", "DARK_PURPLE").setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
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
