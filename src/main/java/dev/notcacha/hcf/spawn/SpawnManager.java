package dev.notcacha.hcf.spawn;

import org.bukkit.Location;

import java.util.Optional;

public class SpawnManager {

    private Location location;

    public SpawnManager() {
        this.location = null;
    }

    public SpawnManager(Location location) {
        this.location = location;
    }

    public Optional<Location> get() {
        return Optional.ofNullable(this.location);
    }

    public void set(Location location) {
        this.location = location;
    }
}
