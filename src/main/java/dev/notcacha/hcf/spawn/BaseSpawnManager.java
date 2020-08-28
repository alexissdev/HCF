package dev.notcacha.hcf.spawn;

import com.google.inject.Singleton;
import org.bukkit.Location;

import java.util.Optional;

@Singleton
public class BaseSpawnManager implements SpawnManager {

    private Location spawn;

    public BaseSpawnManager() {
        this.spawn = null;
    }

    @Override
    public Optional<Location> getSpawn() {
        return Optional.ofNullable(this.spawn);
    }

    @Override
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
