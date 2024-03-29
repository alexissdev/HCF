package dev.notcacha.hcf.user.faction;

import dev.notcacha.hcf.faction.role.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleUserFaction implements UserFaction {

    private String name;
    private final Set<String> invites;
    private Role role;

    public SimpleUserFaction() {
        this(null, new HashSet<>(), null);
    }

    public SimpleUserFaction(String name, Set<String> invites, Role role) {
        this.name = name;
        this.invites = invites;
        this.role = role;
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

}
