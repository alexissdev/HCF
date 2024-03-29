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

    default Optional<Long> find(String type, String id) {
        return find(type.replace("%id%", id));
    }

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

    default void add(String type, String id, Long time) {
        add(type.replace("%id%", id), time);
    }

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

    default boolean exists(String type, String id) {
        return exists(type.replace("%id%", id));
    }

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

    default void remove(String type, String id) {
        remove(type.replace("%id%", id));
    }

    /**
     * Remove all cooldowns
     *
     * @param id has been removed all cooldowns from this id
     */

    void removeAll(String id);
}
