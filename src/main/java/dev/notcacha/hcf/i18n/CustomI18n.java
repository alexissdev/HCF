package dev.notcacha.hcf.i18n;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.NamespaceAccesor;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.i18n.I18n;
import me.fixeddev.ebcm.i18n.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * This class has the purpose of adapting the 'i18n' of the Command Manager EBCM to the multi language of the core
 */

public class CustomI18n implements I18n {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    private final Map<String, String> messageMap;

    public CustomI18n() {
        this.messageMap = new HashMap<>();

        this.messageMap.put(Message.INVALID_SUBCOMMAND.getId(), "Invalid sub-command, valid values: %s");
        this.messageMap.put(Message.MISSING_ARGUMENT.getId(), "Missing arguments for required part %s minimum arguments required: %s");
        this.messageMap.put(Message.MISSING_SUBCOMMAND.getId(), "Missing argument for required part %s, available values: %s");
    }

    @Override
    public String getMessage(String messageId, NamespaceAccesor namespaceAccesor) {
        CommandSender sender = namespaceAccesor.getObject(CommandSender.class, BukkitCommandManager.SENDER_NAMESPACE);
        String language = "ES";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<User> user = userCache.find(player.getUniqueId());
            if (user.isPresent()) {
                language = user.get().getLanguage();
            }
        }

        if (messageId.equals(Message.COMMAND_NO_PERMISSIONS.getId())) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("general.no-permissions");
            if (message.isPresent()) {
                return message.get().setColor(true).getMessage(language);
            }
        }
        if (messageId.equals("provider.invalid.boolean")) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.invalid.boolean");
            if (message.isPresent()) {
                return message.get().setVariable("%s%", "%s").setColor(true).getMessage(language);
            }
        }
        if (messageId.equals("provider.invalid.double")) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.double");
            if (message.isPresent()) {
                return message.get().setVariable("%s%", "%s").setColor(true).getMessage(language);
            }

        }
        if (messageId.equals("provider.invalid.int")) {
            Optional<TranslatableMessage> message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.integer");
            if (message.isPresent()) {
                return message.get().setVariable("%s%", "%s").setColor(true).getMessage(language);
            }
        }

        return this.messageMap.get(messageId);
    }


}
