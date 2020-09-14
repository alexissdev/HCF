package dev.notcacha.hcf.ebcm;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.NamespaceAccesor;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.i18n.I18n;
import me.fixeddev.ebcm.i18n.Message;
import org.bukkit.command.CommandSender;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class has the purpose of adapting the 'i18n' of the Command Manager EBCM to the multi language of the core
 */

@Singleton
public class CustomI18n implements I18n {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private LanguageUtils languageUtils;

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

        String language = languageUtils.getLanguage(sender);

        if (messageId.equals(Message.COMMAND_NO_PERMISSIONS.getId())) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.no-permissions");
            return message.colorize().getMessage(language);
        }
        if (messageId.equals("bukkit.invalid.commandsender")) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.invalid.command-sender");
            return message.colorize().getMessage(language);

        }
        if (messageId.equals("bukkit.only.players")) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.invalid.only-player");
            return message.colorize().getMessage(language);

        }
        if (messageId.equals("bukkit.player.not.online")) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");

            return message.setVariable("%target_name%", "%s").colorize().getMessage(language);

        }
        if (messageId.equals("provider.invalid.double")) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.double");
            return message.setVariable("%s%", "%s").colorize().getMessage(language);

        }
        if (messageId.equals("provider.invalid.int")) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("ebcm-i18n.integer");
            return message.setVariable("%s%", "%s").colorize().getMessage(language);
            
        }

        return this.messageMap.get(messageId);
    }


}
