package dev.notcacha.hcf.faction;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.faction.type.FactionType;
import dev.notcacha.hcf.utils.Cuboid;
import org.bukkit.Location;

import java.util.Optional;

public interface Faction extends Model {

    /**
     * @return type from this faction
     **/

    FactionType getType();

    /**
     * @return home location from this faction
     */

    Optional<Location> getHome();

    /**
     * Set home
     *
     * @param home has been set
     */

    Faction setHome(Location home);

    /**
     * @return cuboid from this claim has been created from this faction
     */

    Optional<Cuboid> getClaim();

    /**
     * Set claim from faction
     *
     * @param claim has been set
     */

    Faction setClaim(Cuboid claim);
}
