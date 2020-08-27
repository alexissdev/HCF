package dev.notcacha.hcf.faction.role;

import dev.notcacha.hcf.faction.role.permisions.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoLeader implements Role {

    private final String id;
    private final List<Permissions> permissionsSet;

    public CoLeader(String name) {
        this.id = name;
        this.permissionsSet = new ArrayList<>(Arrays.asList(Permissions.INVITE, Permissions.KICK, Permissions.CHANGE_NAME, Permissions.CLAIM));
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<Permissions> getPermissions() {
        return this.permissionsSet;
    }
}
