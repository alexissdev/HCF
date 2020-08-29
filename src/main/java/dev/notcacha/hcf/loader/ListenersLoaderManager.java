package dev.notcacha.hcf.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.listeners.*;
import org.bukkit.plugin.PluginManager;

@Singleton
public class ListenersLoaderManager implements LoaderManager {

    @Inject
    private HCF plugin;

    @Inject
    private UserListener userListener;
    @Inject
    private PearlListener pearlListener;
    @Inject
    private CombatListener combatListener;
    @Inject
    private AppleListener appleListener;
    @Inject
    private SotwListener sotwListener;
    @Inject
    private CombatLoggerListener combatLoggerListener;
    @Inject
    private LogoutListener logoutListener;
    @Inject
    private DeathListener deathListener;
    @Inject
    private DeathbanListener deathbanListener;
    @Inject
    private CrateListener crateListener;


    @Override
    public void load() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(this.userListener, this.plugin);
        pluginManager.registerEvents(this.pearlListener, this.plugin);
        pluginManager.registerEvents(this.combatListener, this.plugin);
        pluginManager.registerEvents(this.appleListener, this.plugin);
        pluginManager.registerEvents(this.sotwListener, this.plugin);
        pluginManager.registerEvents(this.combatLoggerListener, this.plugin);
        pluginManager.registerEvents(this.logoutListener, this.plugin);
        pluginManager.registerEvents(this.deathListener, this.plugin);
        pluginManager.registerEvents(this.deathbanListener, this.plugin);
        pluginManager.registerEvents(this.crateListener, this.plugin);
    }
}
