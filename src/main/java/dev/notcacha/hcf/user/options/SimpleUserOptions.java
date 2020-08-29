package dev.notcacha.hcf.user.options;

import java.util.Optional;

public class SimpleUserOptions implements UserOptions {

    private String kitEdited;
    private String crateEdited;

    public SimpleUserOptions() {
        this.kitEdited = null;
        this.crateEdited = null;
    }

    @Override
    public Optional<String> getKitEdited() {
        return Optional.ofNullable(this.kitEdited);
    }

    @Override
    public void setKitEdited(String name) {
        this.kitEdited = name;
    }

    @Override
    public Optional<String> getCrateEdited() {
        return Optional.ofNullable(this.crateEdited);
    }

    @Override
    public void setCrateEdited(String name) {
        this.crateEdited = name;
    }
}
