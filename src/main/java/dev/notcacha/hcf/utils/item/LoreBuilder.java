package dev.notcacha.hcf.utils.item;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreBuilder {

    private final List<String> lore;

    public LoreBuilder() {
        this(new ArrayList<>());
    }

    public LoreBuilder(List<String> lore) {
        this.lore = lore;
    }

    public LoreBuilder addLines(String... lines) {
        lore.addAll(Arrays.asList(lines));
        return this;
    }

    public LoreBuilder setColor() {
        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return this;
    }

    public LoreBuilder setVariable(String key, String value) {
        lore.replaceAll(line -> line.replace(key, value));
        return this;
    }

    public List<String> build() {
        return lore;
    }
}
