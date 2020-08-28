package dev.notcacha.hcf.deathban;

import org.bukkit.Location;

public class SimpleDeathban implements Deathban {

    private final String id;
    private final Location deathLocation;
    private final long expiryMillis;

    public SimpleDeathban(String id, Location deathLocation, long expiryMillis) {
        this.id = id;
        this.deathLocation = deathLocation;
        this.expiryMillis = expiryMillis;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getExpiryMillis() {
        return this.expiryMillis;
    }

    @Override
    public Location getDeathLocation() {
        return this.deathLocation;
    }

}
