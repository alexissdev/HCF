package dev.notcacha.hcf.ebcm.parameter.provider;

import com.google.inject.Inject;
import dev.notcacha.hcf.utils.LanguageUtils;
import me.fixeddev.ebcm.NamespaceAccesor;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parameter.provider.InjectedProvider;
import me.fixeddev.ebcm.part.CommandPart;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class LanguageProvider implements InjectedProvider<String> {

    public static final String LANGUAGE_MODIFIER = "IS_LANGUAGE";

    @Inject
    private LanguageUtils languageUtils;

    @Override
    public Result<String> transform(NamespaceAccesor namespaceAccesor, CommandPart part) {
        CommandSender sender = namespaceAccesor.getObject(CommandSender.class, BukkitCommandManager.SENDER_NAMESPACE);

        if (part.getModifiers().contains(LANGUAGE_MODIFIER)) {
            if (sender == null) {
                return Result.createResult("%bukkit.invalid.commandsender%", new CommandException("Failed to get CommandSender, maybe the namespace wasn't provided with the command sender when executing the command?"));
            }

            return Result.createResult(languageUtils.getLanguage(sender));
        }

        return Result.createResultOfMessage("Internal error.");
    }
}
