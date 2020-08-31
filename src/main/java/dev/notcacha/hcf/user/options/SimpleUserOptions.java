package dev.notcacha.hcf.user.options;

import dev.notcacha.hcf.user.options.claim.ClaimOptions;
import dev.notcacha.hcf.user.options.claim.UserClaimOptions;

import java.util.Optional;

public class SimpleUserOptions implements UserOptions {

    private String kitEdited;
    private String crateEdited;
    private final ClaimOptions claimOptions;

    public SimpleUserOptions() {
        this.kitEdited = null;
        this.crateEdited = null;
        this.claimOptions = new UserClaimOptions();
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

    @Override
    public ClaimOptions getClaimOptions() {
        return this.claimOptions;
    }
}
