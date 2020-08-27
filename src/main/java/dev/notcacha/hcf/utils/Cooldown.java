package dev.notcacha.hcf.utils;

public class Cooldown {

    public static final String PEARL_COOLDOWN = "%id%_pearl";
    public static final String COMBAT_COOLDOWN = "%id%_combat";
    public static final String GOLDEN_APPLE = "%id%_golden_apple";
    public static final String ENCHANT_GOLDEN_APPLE = "%id%_enchant_golden_apple";
    public static final String SOTW_TIMER = "sotw_timer";
    public static final String PVP_TIMER = "%id%_pvp_timer";

    /**
     * @return if {@param text} matches some of the cooldown names
     */

    public static boolean hasCooldown(String text) {
        return text.equals(PEARL_COOLDOWN)
                || text.equals(COMBAT_COOLDOWN)
                || text.equals(GOLDEN_APPLE)
                || text.equals(ENCHANT_GOLDEN_APPLE)
                || text.equals(PVP_TIMER);
    }

}
