package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Optional;
import java.util.UUID;

public class CombatListener implements Listener {

    @Inject
    private LanguageLib languageLib;
    @Inject
    private CacheProvider<UUID, User> userCache;
    @Inject
    private CooldownManager cooldownManager;
    @Inject
    private CacheProvider<String, String> combatCache;

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("combat.message");

            Optional<String> damagerFromPlayer = combatCache.find(player.getName());

            if (damagerFromPlayer.isPresent()) {
                if (!damagerFromPlayer.get().equals(damager.getName())) {
                    Optional<User> user = userCache.find(player.getUniqueId());
                    if (user.isPresent()) {
                        message.setVariable("%target_name%", damager.getName()).colorize();

                        player.sendMessage(message.getMessage(user.get().getLanguage()));
                    }
                }
            }

            Optional<String> damagerFromDamager = combatCache.find(damager.getName());

            if (damagerFromDamager.isPresent()) {
                if (!damagerFromDamager.get().equals(player.getName())) {
                    Optional<User> user = userCache.find(damager.getUniqueId());
                    if (user.isPresent()) {
                        message.setVariable("%target_name%", player.getName()).colorize();

                        damager.sendMessage(message.getMessage(user.get().getLanguage()));
                    }
                }
            }

            cooldownManager.add(CooldownUtils.COMBAT_COOLDOWN, player.getUniqueId().toString(), Long.parseLong("30"));
            cooldownManager.add(CooldownUtils.COMBAT_COOLDOWN, damager.getUniqueId().toString(), Long.parseLong("30"));
            combatCache.add(player.getName(), damager.getName());
            combatCache.add(damager.getName(), player.getName());
        }
    }
}
