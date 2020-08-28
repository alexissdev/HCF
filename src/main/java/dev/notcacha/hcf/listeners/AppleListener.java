package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

public class AppleListener implements Listener {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    private CooldownManager cooldownManager;

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType() == Material.GOLDEN_APPLE) {
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                if (item.getDurability() == 1) {
                    Optional<Long> cooldown = cooldownManager.find(CooldownUtils.ENCHANT_GOLDEN_APPLE, player.getUniqueId().toString());
                    if (cooldown.isPresent()) {
                        if (cooldown.get() > 0) {
                            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("cooldown.enchant-golden-apple");
                            if (message.isPresent()) {
                                message.get().setVariable("%cooldown%", new SimpleDateFormat("hh:mm:ss").format(cooldown.get())).setColor(true);

                                player.sendMessage(message.get().getMessage(user.get().getLanguage()));
                            }
                            return;
                        }
                    }
                    cooldownManager.add(CooldownUtils.ENCHANT_GOLDEN_APPLE, player.getUniqueId().toString(), Long.parseLong("3600"));
                    return;
                }
                Optional<Long> cooldown = cooldownManager.find(CooldownUtils.GOLDEN_APPLE, player.getUniqueId().toString());
                if (cooldown.isPresent()) {
                    if (cooldown.get() > 0) {
                        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("cooldown.golden-apple");
                        if (message.isPresent()) {
                            message.get().setVariable("%cooldown%", new SimpleDateFormat("mm:ss").format(cooldown.get())).setColor(true);

                            player.sendMessage(message.get().getMessage(user.get().getLanguage()));
                        }
                        return;
                    }
                }
                cooldownManager.add(CooldownUtils.GOLDEN_APPLE, player.getUniqueId().toString(), Long.parseLong("30"));
            }

        }

    }
}
