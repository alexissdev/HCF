package dev.notcacha.hcf.listeners;

import com.google.inject.Inject;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;

import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LogoutListener implements Listener {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private CooldownManager cooldownManager;

    @Inject
    private LanguageUtils languageUtils;

    @EventHandler
    public void onMoveInLogout(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (cooldownManager.exists(CooldownUtils.LOGOUT_COOLDOWN, player.getUniqueId().toString())) {
            cooldownManager.remove(CooldownUtils.LOGOUT_COOLDOWN, player.getUniqueId().toString());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("logout.cancel");
            message.colorize();

            player.sendMessage(message.getMessage(languageUtils.getLanguage(player)));
        }
    }
}
