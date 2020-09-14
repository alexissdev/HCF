package dev.notcacha.hcf.listeners.items;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.Material;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Optional;

public class SnowballListener implements Listener {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CooldownManager cooldownManager;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack.getType() == Material.SNOW_BALL) {
                Optional<Long> cooldown = cooldownManager.find(CooldownUtils.SNOWBALL_COOLDOWN, player.getUniqueId().toString());
                if (cooldown.isPresent()) {
                    if (cooldown.get() > 0) {
                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("cooldown.snowball");
                        message.setVariable("%cooldown%", String.valueOf(cooldown.get())).colorize();

                        player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
                        return;
                    }
                }
                cooldownManager.add(CooldownUtils.SNOWBALL_COOLDOWN, player.getUniqueId().toString(), Long.parseLong("15"));
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

                TranslatableMessage shooterMessage = languageLib.getTranslationManager().getTranslation("ability-items.snowball.message.player");
                shooterMessage.setVariable("%target_name%", player.getName()).colorize();

                shooter.sendMessage(shooterMessage.getMessage(languageUtils.getLanguage(shooter)));

                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("ability-items.snowball.message.target");
                message.setVariable("%player_name%", shooter.getName()).colorize();

                player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));

                player.damage(3);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
            }
        }
    }
}
