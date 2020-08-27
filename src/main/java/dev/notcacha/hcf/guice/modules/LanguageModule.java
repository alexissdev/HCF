package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.file.YamlFile;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.bukkit.BukkitLanguageLib;
import org.bukkit.configuration.Configuration;

public class LanguageModule extends AbstractModule {

    private final HCF plugin;

    public LanguageModule(HCF plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bind(LanguageLib.class).toProvider(() -> {
            LanguageLib<Configuration> languageLib = new BukkitLanguageLib("en", new YamlFile(this.plugin, "language_en"));
            languageLib.getFileManager().addFile("es", new YamlFile(this.plugin, "language_es"));

            return languageLib;
        }).in(Scopes.SINGLETON);
    }
}
