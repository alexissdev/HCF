package dev.notcacha.hcf.placeholders;

import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.placeholder.PlaceholderApplier;

public class StatisticsPlaceholderApplier implements PlaceholderApplier {

    @Override
    public String set(Object holder, String text) {
        if (holder == null) {
            return text;
        }
        if (!(holder instanceof User)) {
            return text;
        }

        User user = (User) holder;
        return text.replace("%player_name%", user.getName())
                .replace("%player_kills%", String.valueOf(user.getKillsManager().get()))
                .replace("%player_deaths%", String.valueOf(user.getDeathsManager().get()))
                .replace("%player_faction%", (user.getFaction().getFactionName().isPresent()) ? user.getFaction().getFactionName().get() : "None");
    }
}
