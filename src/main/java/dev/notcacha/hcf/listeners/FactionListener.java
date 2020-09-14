package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class FactionListener implements Listener {

    @Inject
    private LanguageLib languageLib;
    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CacheProvider<UUID, User> userCache;
    @Inject
    private CacheProvider<String, Faction> factionCache;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            Optional<User> user = userCache.find(player.getUniqueId());
            Optional<User> damagerUser = userCache.find(damager.getUniqueId());
            if (!user.isPresent() || !damagerUser.isPresent()) {
                return;
            }
            Optional<String> playerClanName = user.get().getFaction().getFactionName();
            Optional<String> damagerClanName = damagerUser.get().getFaction().getFactionName();
            if (!playerClanName.isPresent() || !damagerClanName.isPresent()) {
                return;
            }
            if (playerClanName.get().equals(damagerClanName.get())) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.error.is-your-faction");
                message.setVariable("%player_name%", player.getName()).colorize();

                damager.sendMessage(message.getMessage(languageUtils.getLanguage(damager)));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        String language = languageUtils.getLanguage(player);
    }

}
