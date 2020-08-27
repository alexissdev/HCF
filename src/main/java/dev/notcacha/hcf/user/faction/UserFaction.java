package dev.notcacha.hcf.user.faction;

import java.util.Optional;

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

    UserFaction setFactionName(String name);
}
