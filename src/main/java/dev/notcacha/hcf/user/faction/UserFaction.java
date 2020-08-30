package dev.notcacha.hcf.user.faction;

import dev.notcacha.hcf.faction.role.Role;

import java.util.Optional;
import java.util.Set;

public interface UserFaction {

    /**
     * @return faction name from this user
     */

    Optional<String> getFactionName();

    /**
     * Set faction
     *
     * @param name from faction has been set
     */

    void setFactionName(String name);

    /**
     * @return role from this user in faction
     */

    Role getRole();

    /**
     * Set role
     *
     * @param role has been set
     */

    void setRole(Role role);

    /**
     * @return factions invites from this user
     */

    Set<String> getInvites();

    /**
     * @return the name of the last fac that has walked in your claim
     */

    Optional<String> getLastFactionIsMove();

    /**
     * Set last faction is move from user
     *
     * @param name from faction
     */

    void setLastFactionIsMove(String name);


}
