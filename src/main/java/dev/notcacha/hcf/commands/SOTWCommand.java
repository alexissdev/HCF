package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.CooldownCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = "sotw")
public class SOTWCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    @CooldownCache
    private CacheProvider<String, Long> cooldownCache;

    @ACommand(names = "", permission = "hcf.sotw")
    public boolean mainCommand(@Injected(true) CommandSender sender) {
        String language = "EN";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                language = user.get().getLanguage();
            }
        }

        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.usage");
        if (message.isPresent()) {
            message.get().setColor(true);

            message.get().getMessages(language).forEach(sender::sendMessage);
        }

        return true;
    }

    @ACommand(names = "start", permission = "hcf.sotw.start")
    @Usage(usage = "§cCorrect usage is /sotw start <time>")
    public boolean startCommand(@Injected(true) CommandSender sender, Integer time) {
        String language = "EN";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                language = user.get().getLanguage();
            }
        }

        if (cooldownCache.exists("sotw")) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.error.is-started");
            if (message.isPresent()) {
                sender.sendMessage(message.get().setColor(true).getMessage(language));
            }
            return true;
        }

        cooldownCache.add("sotw", Long.parseLong(String.valueOf(time)));
        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.start");
        if (message.isPresent()) {
            message.get().setVariable("%time%", String.valueOf(time))
                    .setVariable("%staff_name%", sender.getName())
                    .setColor(true);

            sender.sendMessage(message.get().getMessage(language));
        }
        return true;
    }

    @ACommand(names = "stop", permission = "hcf.sotw.stop")
    public boolean stopCommand(@Injected(true) CommandSender sender) {
        String language = "EN";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                language = user.get().getLanguage();
            }
        }

        if (!cooldownCache.exists("sotw")) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.error.not-started");
            if (message.isPresent()) {
                sender.sendMessage(message.get().setColor(true).getMessage(language));
            }
            return true;
        }

        cooldownCache.remove("sotw");
        Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("sotw.stop");
        if (message.isPresent()) {
            message.get().setVariable("%staff_name%", sender.getName()).setColor(true);

            sender.sendMessage(message.get().getMessage(language));
        }

        return true;
    }

}
