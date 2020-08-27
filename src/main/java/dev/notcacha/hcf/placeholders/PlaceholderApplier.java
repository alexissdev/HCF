package dev.notcacha.hcf.placeholders;

import org.bukkit.entity.Player;

public interface PlaceholderApplier {

    /**
     * Set placeholders
     *
     * @param player from get placeholders
     * @param text   has been set placeholders
     */

    String set(Player player, String text);
}
