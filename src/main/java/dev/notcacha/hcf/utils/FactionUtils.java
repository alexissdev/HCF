package dev.notcacha.hcf.utils;

public class FactionUtils {

    public static boolean isNotPermittedFactionName(String name) {
        return name.toLowerCase().equals("spawn")
                || name.toLowerCase().equals("north road")
                || name.toLowerCase().equals("south road")
                || name.toLowerCase().equals("west road")
                || name.toLowerCase().equals("east road");
    }
}
