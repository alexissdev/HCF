package dev.notcacha.hcf.faction;

import dev.notcacha.hcf.faction.type.FactionType;
import dev.notcacha.hcf.utils.Cuboid;
import org.bukkit.Location;

import java.util.Optional;

public class SimpleFaction implements Faction {

    private final String id;
    private final FactionType type;
    private Cuboid claim;
    private Location home;

    public SimpleFaction(String id) {
        this(id, FactionType.DEFAULT, null, null);
    }

    public SimpleFaction(String id, FactionType type) {
        this(id, type, null, null);
    }

    public SimpleFaction(String id, FactionType type, Cuboid claim) {
        this(id, type, claim, null);
    }

    public SimpleFaction(String id, FactionType type, Cuboid claim, Location home) {
        this.id = id;
        this.type = type;
        this.claim = claim;
        this.home = home;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public FactionType getType() {
        return this.type;
    }

    @Override
    public Optional<Location> getHome() {
        return Optional.ofNullable(this.home);
    }

    @Override
    public Faction setHome(Location home) {
        this.home = home;
        return this;
    }

    @Override
    public Optional<Cuboid> getClaim() {
        return Optional.ofNullable(this.claim);
    }

    @Override
    public Faction setClaim(Cuboid claim) {
        this.claim = claim;
        return this;
    }
}
