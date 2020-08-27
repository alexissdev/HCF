package dev.notcacha.hcf.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.commands.*;
import dev.notcacha.hcf.i18n.CustomI18n;
import me.fixeddev.ebcm.Command;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
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

    public CommandsLoaderManager() {
        this.builder = new ReflectionParametricCommandBuilder();
        this.commandManager = new BukkitCommandManager(plugin.getName());
    }

    @Override
    public void load() {
        commandManager.setI18n(i18n);

        List<Command> commandList = new ArrayList<>();
        commandList.addAll(builder.fromClass(this.sotwCommand));
        commandList.addAll(builder.fromClass(this.kitManagerCommand));
        commandList.addAll(builder.fromClass(this.factionCommand));
        commandList.addAll(builder.fromClass(this.addCooldownCommand));
        commandList.addAll(builder.fromClass(this.removeCooldownCommand));

        commandManager.registerCommands(commandList);
    }
}
