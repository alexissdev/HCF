package dev.notcacha.hcf.faction.role;

import dev.notcacha.hcf.faction.role.permisions.Permissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Captain implements Role {

    private final String id;
    private final List<Permissions> permissionsSet;

    public Captain(String name) {
        this.id = name;
        this.permissionsSet = new ArrayList<>(Collections.singletonList(Permissions.INVITE));
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
