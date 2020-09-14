package dev.notcacha.hcf.scheduler;

import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.cooldown.CooldownManager;
import dev.notcacha.hcf.utils.CooldownUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class MainScheduler extends BukkitRunnable {

    private final HCF plugin = JavaPlugin.getPlugin(HCF.class);

    private final CooldownManager cooldownManager;
    private final LanguageLib languageLib;
    private final LanguageUtils languageUtils;

    public MainScheduler(CooldownManager cooldownManager, LanguageLib languageLib, LanguageUtils languageUtils) {
        this.cooldownManager = cooldownManager;
        this.languageLib = languageLib;
        this.languageUtils = languageUtils;
    }

    @Override
    public void run() {
        Optional<Long> sotwCooldown = cooldownManager.find(CooldownUtils.SOTW_TIMER);
        if (sotwCooldown.isPresent()) {
            if (sotwCooldown.get() <= 0) {
                cooldownManager.remove(CooldownUtils.SOTW_TIMER);
            }
        }

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            Optional<Long> logoutCooldown = cooldownManager.find(CooldownUtils.LOGOUT_COOLDOWN, player.getUniqueId().toString());
            if (logoutCooldown.isPresent()) {
                if (logoutCooldown.get() <= 0) {
                    cooldownManager.removeAll(player.getUniqueId().toString());
                    TranslatableMessage message = languageLib.getTranslationManager().getTranslation("logout.leave-message");
                    player.kickPlayer(message.colorize().getMessage(languageUtils.getLanguage(player)));
                }
            }
        });
    }
}
