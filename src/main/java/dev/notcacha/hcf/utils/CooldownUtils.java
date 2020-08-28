package dev.notcacha.hcf.utils;

public class CooldownUtils {

    public static final String PEARL_COOLDOWN = "%id%_pearl";
    public static final String COMBAT_COOLDOWN = "%id%_combat";
    public static final String GOLDEN_APPLE = "%id%_golden_apple";
    public static final String ENCHANT_GOLDEN_APPLE = "%id%_enchant_golden_apple";
    public static final String SOTW_TIMER = "sotw_timer";
    public static final String PVP_TIMER = "%id%_pvp_timer";
    public static final String LOGOUT_COOLDOWN = "%id%_logout";
    public static final String DEATHBAN_COOLDOWN = "%id%_deathban";

    /**
     * @return if {@param text} matches some of the cooldown names
     */

    public static boolean isNotCooldown(String text) {
        return !text.equals(PEARL_COOLDOWN)
                && !text.equals(COMBAT_COOLDOWN)
                && !text.equals(GOLDEN_APPLE)
                && !text.equals(ENCHANT_GOLDEN_APPLE)
                && !text.equals(PVP_TIMER)
                && !text.equals(DEATHBAN_COOLDOWN);
    }

}
