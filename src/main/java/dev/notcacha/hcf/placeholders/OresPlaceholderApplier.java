package dev.notcacha.hcf.placeholders;

import dev.notcacha.hcf.user.User;
import dev.notcacha.languagelib.placeholder.PlaceholderApplier;

public class OresPlaceholderApplier implements PlaceholderApplier {

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
                .replace("%player_emeralds%", String.valueOf(user.getEmeraldsManager().get()))
                .replace("%player_readstone%", String.valueOf(user.getRedstoneManager().get()))
                .replace("%player_lapis%", String.valueOf(user.getLapisManager().get()))
                .replace("%player_gold%", String.valueOf(user.getGoldManager().get()))
                .replace("%player_iron%", String.valueOf(user.getIronManager().get()))
                .replace("%player_coal%", String.valueOf(user.getCoalManager().get()));
    }
}
