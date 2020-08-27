package dev.notcacha.hcf.cooldown;

import java.util.Optional;

public interface CooldownManager {

    Optional<Long> find(String type, String id);

    /**
     * Add cooldown
     *
     * @param type from cooldown
     * @param id   from identify cooldown
     * @param time from cooldown
     */

    void add(String type, String id, Long time);

    /**
     * Remove specify cooldown
     *
     * @param type from cooldown
     * @param id   from identify
     */

    void remove(String type, String id);

    /**
     * Remove all cooldowns
     *
     * @param id has been removed all cooldowns from this id
     */

    void removeAll(String id);
}
