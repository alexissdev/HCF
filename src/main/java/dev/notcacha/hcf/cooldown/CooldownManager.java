package dev.notcacha.hcf.cooldown;

import java.util.Optional;

public interface CooldownManager {

    /**
     * Find cooldown
     *
     * @param type from cooldown
     * */

    Optional<Long> find(String type);

    /**
     * Find cooldown
     *
     * @param type from cooldown
     * @param id   from identify cooldown
     * */

    Optional<Long> find(String type, String id);

    /**
     * Add cooldown
     *
     * @param type from cooldown
     * @param time from cooldown
     */

    void add(String type, Long time);

    /**
     * Add cooldown
     *
     * @param type from cooldown
     * @param id   from identify cooldown
     * @param time from cooldown
     */

    void add(String type, String id, Long time);

    /**
     * Return exists cooldown
     *
     * @param type from cooldown
     * */

    boolean exists(String type);

    /**
     * Return exists cooldown
     *
     * @param type from cooldown
     * @param id from identify
     * */

    boolean exists(String type, String id);

    /**
     * Remove specify cooldown
     *
     * @param type from cooldown
     * */

    void remove(String type);

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
