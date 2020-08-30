package dev.notcacha.hcf.faction;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.faction.type.FactionType;
import dev.notcacha.hcf.utils.Cuboid;
import org.bukkit.Location;

import java.util.Optional;
import java.util.Set;

public interface Faction extends Model {

    /**
     * Set new name from faction
     *
     * @param name has been set
     */

    void setName(String name);

    /**
     * @return leader name from this faction
     */

    Optional<String> getLeader();

    /**
     * Set leader
     *
     * @param name from new leader has been set
     */

    Faction setLeader(String name);

    /**
     * @return type from this faction
     **/

    FactionType getType();

    /**
     * @return dtr from faction
     */

    double getDTR();

    /**
     * Set DTR from faction
     *
     * @param dtr has been set
     */

    void setDTR(double dtr);

    /**
     * Add DTR from faction
     *
     * @param dtr has been added
     */

    default void addDTR(double dtr) {
        setDTR(getDTR() + dtr);
    }

    /**
     * Take DTR from faction
     *
     * @param dtr has been take
     */

    default void takeDTR(double dtr) {
        setDTR(getDTR() - dtr);
    }

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

    /**
     * @return members from this clan
     */

    Optional<Set<String>> getMembers();

    /**
     * Set members
     *
     * @param members has been set from faction
     */

    Faction setMembers(Set<String> members);
}
