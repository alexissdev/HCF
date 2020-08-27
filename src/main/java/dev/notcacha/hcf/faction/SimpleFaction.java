package dev.notcacha.hcf.faction;

import dev.notcacha.hcf.faction.type.FactionType;
import dev.notcacha.hcf.utils.Cuboid;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleFaction implements Faction {

    private final String id;
    private String leader;
    private final FactionType type;
    private Cuboid claim;
    private Location home;
    private Set<String> members;

    public SimpleFaction(String id, String leader) {
        this(id, leader, FactionType.DEFAULT, null, null, new HashSet<>());
    }

    public SimpleFaction(String id, String leader, FactionType type) {
        this(id, leader, type, null, null, new HashSet<>());
    }

    public SimpleFaction(String id, String leader, FactionType type, Cuboid claim) {
        this(id, leader, type, claim, null, new HashSet<>());
    }

    public SimpleFaction(String id, String leader, FactionType type, Cuboid claim, Set<String> members) {
        this(id, leader, type, claim, null, members);
    }

    public SimpleFaction(String id, String leader, FactionType type, Cuboid claim, Location home, Set<String> members) {
        this.id = id;
        this.leader = (type == FactionType.DEFAULT) ? leader : null;
        this.type = type;
        this.claim = claim;
        this.home = (type == FactionType.DEFAULT) ? home : null;
        this.members = (type == FactionType.DEFAULT) ? members : null;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Optional<String> getLeader() {
        return Optional.ofNullable(this.leader);
    }

    @Override
    public Faction setLeader(String name) {
        this.leader = name;
        return this;
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
    public Optional<Set<String>> getMembers() {
        return Optional.ofNullable(this.members);
    }

    @Override
    public Faction setMembers(Set<String> members) {
        this.members = members;
        return this;
    }
}
