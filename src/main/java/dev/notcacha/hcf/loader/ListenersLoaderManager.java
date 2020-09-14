package dev.notcacha.hcf.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.core.loader.LoaderManager;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.listeners.*;
import dev.notcacha.hcf.listeners.items.EggListener;
import dev.notcacha.hcf.listeners.items.SnowballListener;
import org.bukkit.event.Listener;
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
    @Inject
    private KitListener kitListener;
    @Inject
    private SnowballListener snowballListener;
    @Inject
    private EggListener eggListener;


    private void register(Listener... listeners) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    @Override
    public void load() {

        register(
                userListener,
                pearlListener,
                combatListener,
                appleListener,
                sotwListener,
                combatListener,
                logoutListener,
                deathbanListener,
                deathListener,
                crateListener,
                kitListener,
                snowballListener,
                eggListener,
                combatLoggerListener
        );
    }
}
