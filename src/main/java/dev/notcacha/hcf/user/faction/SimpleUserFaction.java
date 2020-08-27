package dev.notcacha.hcf.user.faction;

import java.util.Optional;

public class SimpleUserFaction implements UserFaction {

    private String name;

    @Override
    public Optional<String> getFactionName() {
        return Optional.ofNullable(this.name);
    }

    @Override
    public UserFaction setFactionName(String name) {
        this.name = name;
        return this;
    }
}
