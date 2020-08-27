package dev.notcacha.hcf.faction;

import dev.notcacha.hcf.faction.type.FactionType;
import dev.notcacha.hcf.utils.Cuboid;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleFaction implements Faction {

    private final String id;
    private final FactionType type;
    private Cuboid claim;
    private Location home;
    private Set<String> members;

    public SimpleFaction(String id) {
        this(id, FactionType.DEFAULT, null, null, new HashSet<>());
    }

    public SimpleFaction(String id, FactionType type) {
        this(id, type, null, null, new HashSet<>());
    }

    public SimpleFaction(String id, FactionType type, Cuboid claim) {
        this(id, type, claim, null, new HashSet<>());
    }

    public SimpleFaction(String id, FactionType type, Cuboid claim, Set<String> members) {
        this(id, type, claim, null, members);
    }

    public SimpleFaction(String id, FactionType type, Cuboid claim, Location home, Set<String> members) {
        this.id = id;
        this.type = type;
        this.claim = claim;
        this.home = home;
        this.members = members;
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

    @Override
    public Set<String> getMembers() {
        return this.members;
    }
}
