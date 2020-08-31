package dev.notcacha.hcf.user.options.claim;

import org.bukkit.Location;

import java.util.Optional;

public interface ClaimOptions {

    /**
     * @return position {@param position} has been set user
     */

    Optional<Location> getPosition(int position);

    /**
     * Set position
     *
     * @param location has been set
     * @param position from location
     */

    void setPosition(Location location, int position);
}
