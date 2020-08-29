package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.KitCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.kit.Kit;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Optional;
import java.util.UUID;

public class KitListener implements Listener {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;
    @Inject
    @KitCache
    private CacheProvider<String, Kit> kitCache;

    @EventHandler
    public void onSetItems(InventoryCloseEvent event) {
        Optional<User> user = userCache.find(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Optional<String> kitName = user.get().getOptions().getKitEdited();
            if (!kitName.isPresent()) {
                return;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.set.items.inventory-name").ifPresent(title -> {
                title.setVariable("%kit_name%", kitName.get()).setColor(true);

                if (event.getInventory().getName().equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                    kitCache.find(kitName.get()).ifPresent(kit -> kit.setItems(event.getInventory().getContents()));
                }
            });
        }
    }

    @EventHandler
    public void onAddItems(InventoryCloseEvent event) {
        Optional<User> user = userCache.find(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Optional<String> kitName = user.get().getOptions().getKitEdited();
            if (!kitName.isPresent()) {
                return;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.add.items.inventory-name").ifPresent(title -> {
                title.setVariable("%kit_name%", kitName.get()).setColor(true);

                if (event.getInventory().getName().equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                    kitCache.find(kitName.get()).ifPresent(kit -> kit.setItems(event.getInventory().getContents()));
                }
            });
        }
    }

    @EventHandler
    public void onSetArmor(InventoryCloseEvent event) {
        Optional<User> user = userCache.find(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Optional<String> kitName = user.get().getOptions().getKitEdited();
            if (!kitName.isPresent()) {
                return;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.set.armor.inventory-name").ifPresent(title -> {
                title.setVariable("%kit_name%", kitName.get()).setColor(true);

                if (event.getInventory().getName().equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                    kitCache.find(kitName.get()).ifPresent(kit -> kit.setArmor(event.getInventory().getContents()));
                }
            });
        }
    }

    @EventHandler
    public void onAddArmor(InventoryCloseEvent event) {
        Optional<User> user = userCache.find(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Optional<String> kitName = user.get().getOptions().getKitEdited();
            if (!kitName.isPresent()) {
                return;
            }
            languageLib.getTranslationManager().getTranslation("kitmanager.add.armor.inventory-name").ifPresent(title -> {
                title.setVariable("%kit_name%", kitName.get()).setColor(true);

                if (event.getInventory().getName().equalsIgnoreCase(title.getMessage(user.get().getLanguage()))) {
                    kitCache.find(kitName.get()).ifPresent(kit -> kit.setArmor(event.getInventory().getContents()));
                }
            });
        }
    }

}
