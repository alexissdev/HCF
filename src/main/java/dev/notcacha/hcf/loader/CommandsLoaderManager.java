package dev.notcacha.hcf.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.commands.AddCooldownCommand;
import dev.notcacha.hcf.commands.CoordsCommand;
import dev.notcacha.hcf.commands.CratesCommand;
import dev.notcacha.hcf.commands.FactionCommand;
import dev.notcacha.hcf.commands.HelpCommand;
import dev.notcacha.hcf.commands.KeyCommand;
import dev.notcacha.hcf.commands.KitManagerCommand;
import dev.notcacha.hcf.commands.LanguageCommand;
import dev.notcacha.hcf.commands.LogoutCommand;
import dev.notcacha.hcf.commands.OresCommand;
import dev.notcacha.hcf.commands.RemoveCooldownCommand;
import dev.notcacha.hcf.commands.SOTWCommand;
import dev.notcacha.hcf.commands.StatisticsCommand;
import dev.notcacha.hcf.ebcm.CustomI18n;
import dev.notcacha.hcf.ebcm.parameter.provider.LanguageProvider;
import me.fixeddev.ebcm.Command;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.parameter.provider.Key;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CommandsLoaderManager implements LoaderManager {

    @Inject
    private HCF plugin;

    @Inject
    private CustomI18n i18n;

    @Inject
    private LanguageProvider languageProvider;

    private final ParametricCommandBuilder builder;
    private final BukkitCommandManager commandManager;

    /* COMMANDS HAS BEEN REGISTRY */
    @Inject
    private SOTWCommand sotwCommand;
    @Inject
    private KitManagerCommand kitManagerCommand;
    @Inject
    private FactionCommand factionCommand;
    @Inject
    private AddCooldownCommand addCooldownCommand;
    @Inject
    private RemoveCooldownCommand removeCooldownCommand;
    @Inject
    private LogoutCommand logoutCommand;
    @Inject
    private CoordsCommand coordsCommand;
    @Inject
    private OresCommand oresCommand;
    @Inject
    private StatisticsCommand statisticsCommand;
    @Inject
    private LanguageCommand languageCommand;
    @Inject
    private HelpCommand helpCommand;
    @Inject
    private CratesCommand cratesCommand;
    @Inject
    private KeyCommand keyCommand;

    public CommandsLoaderManager() {
        this.builder = new ReflectionParametricCommandBuilder();
        this.commandManager = new BukkitCommandManager(plugin.getName());
    }

    private void registerCommands(CommandClass... commands) {
        for (CommandClass command : commands) {
            commandManager.registerCommands(builder.fromClass(command));
        }
    }

    @Override
    public void load() {
        commandManager.setI18n(i18n);
        commandManager.getProviderRegistry().registerParameterProvider(new Key<>(LanguageProvider.LANGUAGE_MODIFIER, String.class), this.languageProvider);

        registerCommands(
                sotwCommand,
                kitManagerCommand,
                factionCommand,
                addCooldownCommand,
                removeCooldownCommand,
                logoutCommand,
                coordsCommand,
                oresCommand,
                statisticsCommand,
                languageCommand,
                helpCommand,
                cratesCommand,
                keyCommand
        );
    }
}
