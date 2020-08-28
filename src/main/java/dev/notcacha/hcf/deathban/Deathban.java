package dev.notcacha.hcf.deathban;

import dev.notcacha.core.model.Model;
import org.bukkit.Location;

public interface Deathban extends Model {

    /**
     * @return time has been expiry deathban
     */

    long getExpiryMillis();

    /**
     * @return time remaining from expire deathban
     */

    default long getRemaining() {
        return getExpiryMillis() - System.currentTimeMillis();
    }

    /**
     * @return location from has been dead player
     */

    Location getDeathLocation();

    /**
     * @return deathban is active or else
     */

    default boolean hasActive() {
        return getRemaining() > 0L;
    }

}
