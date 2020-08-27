package dev.notcacha.hcf.statistics;

public interface StatisticsManager {

    /**
     * @return statistics account in integer
     */

    int get();

    /**
     * Set value
     *
     * @param value has been set
     */

    void set(int value);

    /**
     * Add value
     *
     * @param value has been added
     */

    default void add(int value) {
        set(get() + value);
    }

    /**
     * Remove value
     *
     * @param value has been removed
     */

    default void remove(int value) {
        set(get() - value);
    }
}
