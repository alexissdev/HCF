package dev.notcacha.hcf.listeners.items;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Optional;

public class EggListener implements Listener {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CooldownManager cooldownManager;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack.getType() == Material.EGG) {
                Optional<Long> cooldown = cooldownManager.find(CooldownUtils.EGG_COOLDOWN, player.getUniqueId().toString());
                if (cooldown.isPresent()) {
                    if (cooldown.get() > 0) {
                        languageLib.getTranslationManager().getTranslation("cooldown.egg").ifPresent(message -> {
                            message.setVariable("%cooldown%", String.valueOf(cooldown.get())).setColor(true);

                            player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
                        });
                        return;
                    }
                }
                cooldownManager.add(CooldownUtils.EGG_COOLDOWN, player.getUniqueId().toString(), Long.parseLong("15"));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (damager instanceof Projectile && damager.getType() == EntityType.SNOWBALL) {
            Projectile projectile = (Projectile) damager;
            ProjectileSource source = projectile.getShooter();
            if (entity instanceof Player && source instanceof Player) {
                Player player = (Player) entity;
                Player shooter = (Player) source;

                Location playerLocation = player.getLocation();
                Location shooterLocation = shooter.getLocation();
                player.teleport(shooterLocation);
                shooter.teleport(playerLocation);

                languageLib.getTranslationManager().getTranslation("ability-items.egg.message.player").ifPresent(message -> {
                    message.setVariable("%target_name%", player.getName()).setColor(true);

                    shooter.sendMessage(message.getMessage(languageUtils.getLanguage(shooter)));
                });
                languageLib.getTranslationManager().getTranslation("ability-items.egg.message.target").ifPresent(message -> {
                    message.setVariable("%player_name%", shooter.getName()).setColor(true);

                    player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
                });
            }
        }
    }

}
