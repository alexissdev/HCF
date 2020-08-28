package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.languagelib.LanguageLib;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

public class PearlListener implements Listener {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    private CooldownManager cooldownManager;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Optional<User> user = userCache.find(player.getUniqueId());
        ItemStack itemStack = event.getItem();

        if (user.isPresent()) {
            if (itemStack != null && itemStack.getType() == Material.ENDER_PEARL) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Optional<Long> cooldown = cooldownManager.find(CooldownUtils.PEARL_COOLDOWN, player.getUniqueId().toString());
                    if (cooldown.isPresent()) {
                        if (cooldown.get() > 0) {
                            languageLib.getTranslationManager().getTranslation("cooldown.pearl").ifPresent(message -> {
                                message.setVariable("%cooldown%", new SimpleDateFormat("mm:ss").format(cooldown.get()))
                                        .setColor(true);

                                player.sendMessage(message.getMessage(user.get().getLanguage()));
                            });
                            return;
                        }
                        cooldownManager.remove(CooldownUtils.PEARL_COOLDOWN, player.getUniqueId().toString());
                    }
                    cooldownManager.add(CooldownUtils.PEARL_COOLDOWN, player.getUniqueId().toString(), Long.parseLong("30"));
                }
            }
        }
    }

}
