package dev.notcacha.hcf.user.options.claim;

import dev.notcacha.hcf.utils.LocationUtils;
import org.bukkit.Location;

import java.util.Optional;

public class UserClaimOptions implements ClaimOptions {

    private String position1;
    private String position2;

    public UserClaimOptions() {
        this.position1 = null;
        this.position2 = null;
    }

    @Override
    public Optional<Location> getPosition(int position) {
        if (position > 2) {
            throw new IllegalArgumentException("The position that has been passed through the parameters is a number greater than the maximum valid, the valid positions are [1, 2]");
        }

        return (position == 1) ? Optional.ofNullable(LocationUtils.toLocation(this.position1)) : Optional.ofNullable(LocationUtils.toLocation(this.position2));
    }

    @Override
    public void setPosition(Location location, int position) {
        if (position > 2) {
            throw new IllegalArgumentException("The position that has been passed through the parameters is a number greater than the maximum valid, the valid positions are [1, 2]");
        }
        if (position == 1) {
            this.position1 = LocationUtils.toString(location);
            return;
        }
        this.position2 = LocationUtils.toString(location);
    }

}
