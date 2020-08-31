package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.guice.anotations.cache.FactionCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.Cuboid;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
    private LanguageLib<Configuration> languageLib;
    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;
    @Inject
    @FactionCache
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

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            for (Faction faction : factionCache.get().values()) {
                Optional<Cuboid> claim = faction.getClaim();
                if (!claim.isPresent()) {
                    return;
                }
                Optional<String> lastFaction = user.get().getFaction().getLastFactionIsMove();
                if (claim.get().has(player.getLocation())) {
                    if (lastFaction.isPresent()) {
                        if (lastFaction.get().equals(faction.getId())) {
                            return;
                        }
                    }
                    languageLib.getTranslationManager().getTranslation("faction.on.enter").ifPresent(message -> {
                        message.setVariable("%faction_name%", faction.getId()).setColor(true);

                        player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
                    });
                    user.get().getFaction().setLastFactionIsMove(faction.getId());
                } else {
                    if (lastFaction.isPresent()) {
                        if (!lastFaction.get().equals(faction.getId())) {
                            return;
                        }
                        languageLib.getTranslationManager().getTranslation("faction.on.left").ifPresent(message -> {
                            message.setVariable("%faction_name%", faction.getId()).setColor(true);

                            player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
                        });
                        user.get().getFaction().setLastFactionIsMove(null);
                    }
                }
            }
        }
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
                languageLib.getTranslationManager().getTranslation("faction.error.is-your-faction").ifPresent(message -> {
                    message.setVariable("%player_name%", player.getName()).setColor(true);

                    damager.sendMessage(message.getMessage(languageUtils.getLanguage(damager)));
                });
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (!user.isPresent()) return;

        if (item != null && item.getType() == Material.GOLD_SPADE) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (player.isSneaking()) {
                    Optional<Location> positionOne = user.get().getOptions().getClaimOptions().getPosition(1);
                    Optional<Location> positionTwo = user.get().getOptions().getClaimOptions().getPosition(2);
                    if (!positionOne.isPresent()) {
                        languageLib.getTranslationManager().getTranslation("faction.claim.no-contains-position").ifPresent(message -> {
                            message.setVariable("%position%", String.valueOf(1)).setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        return;
                    }
                    if (!positionTwo.isPresent()) {
                        languageLib.getTranslationManager().getTranslation("faction.claim.no-contains-position").ifPresent(message -> {
                            message.setVariable("%position%", String.valueOf(2)).setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        return;
                    }
                    //create claim
                    return;
                }
                user.get().getOptions().getClaimOptions().setPosition(event.getClickedBlock().getLocation(), 1);
                languageLib.getTranslationManager().getTranslation("faction.claim.set-position").ifPresent(message -> {
                    message.setVariable("%position%", String.valueOf(1)).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return;
            }
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);

                if (player.isSneaking()) {
                    Optional<Location> positionOne = user.get().getOptions().getClaimOptions().getPosition(1);
                    Optional<Location> positionTwo = user.get().getOptions().getClaimOptions().getPosition(2);
                    if (!positionOne.isPresent()) {
                        languageLib.getTranslationManager().getTranslation("faction.claim.no-contains-position").ifPresent(message -> {
                            message.setVariable("%position%", String.valueOf(1)).setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        return;
                    }
                    if (!positionTwo.isPresent()) {
                        languageLib.getTranslationManager().getTranslation("faction.claim.no-contains-position").ifPresent(message -> {
                            message.setVariable("%position%", String.valueOf(2)).setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        return;
                    }

                    user.get().getOptions().getClaimOptions().setPosition(event.getClickedBlock().getLocation(), 2);
                    languageLib.getTranslationManager().getTranslation("faction.claim.set-position").ifPresent(message -> {
                        message.setVariable("%position%", String.valueOf(2)).setColor(true);

                        player.sendMessage(message.getMessage(language));
                    });
                }
            }
        }
    }

}
