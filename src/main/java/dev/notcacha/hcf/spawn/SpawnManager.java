package dev.notcacha.hcf.spawn;

import org.bukkit.Location;

import java.util.Optional;

public interface SpawnManager {

    /**
     * @return spawn in {@link Location} format
     */

    Optional<Location> getSpawn();

    /**
     * Set spawn
     *
     * @param spawn has been set
     */

    void setSpawn(Location spawn);
}
