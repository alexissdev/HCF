package dev.notcacha.hcf.faction.role;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.faction.role.permisions.Permissions;

import java.util.List;


public interface Role extends Model {

    /**
     * @return this permissions from this role
     */

    List<Permissions> getPermissions();

}
