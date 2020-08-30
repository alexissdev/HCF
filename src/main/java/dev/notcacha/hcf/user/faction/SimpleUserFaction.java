package dev.notcacha.hcf.user.faction;

import dev.notcacha.hcf.faction.role.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleUserFaction implements UserFaction {

    private String name;
    private final Set<String> invites;
    private Role role;
    private String lastFactionIsMove;

    public SimpleUserFaction() {
        this.name = null;
        this.invites = new HashSet<>();
        this.role = null;
        this.lastFactionIsMove = null;
    }

    @Override
    public Optional<String> getFactionName() {
        return Optional.ofNullable(this.name);
    }

    @Override
    public void setFactionName(String name) {
        this.name = name;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Set<String> getInvites() {
        return this.invites;
    }

    @Override
    public Optional<String> getLastFactionIsMove() {
        return Optional.ofNullable(this.lastFactionIsMove);
    }

    @Override
    public void setLastFactionIsMove(String name) {
        this.lastFactionIsMove = name;
    }
}
