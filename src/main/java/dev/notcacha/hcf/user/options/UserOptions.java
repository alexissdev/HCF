package dev.notcacha.hcf.user.options;

import dev.notcacha.hcf.user.options.claim.ClaimOptions;

import java.util.Optional;

public interface UserOptions {

    /**
     * @return {@link dev.notcacha.hcf.kit.Kit} has been edited by user
     */

    Optional<String> getKitEdited();

    /**
     * Set {@link dev.notcacha.hcf.kit.Kit} has been edited
     *
     * @param name from {@link dev.notcacha.hcf.kit.Kit}
     */

    void setKitEdited(String name);

    /**
     * @return {@link dev.notcacha.hcf.crates.Crate} has been edited by user
     */

    Optional<String> getCrateEdited();

    /**
     * Set {@link dev.notcacha.hcf.crates.Crate} has been edited
     *
     * @param name from {@link dev.notcacha.hcf.crates.Crate}
     */

    void setCrateEdited(String name);

    /**
     * @return {@link ClaimOptions} instance from manageable claim from this user
     */

    ClaimOptions getClaimOptions();
}
