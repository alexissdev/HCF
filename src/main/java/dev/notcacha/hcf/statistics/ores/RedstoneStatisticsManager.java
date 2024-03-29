package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class RedstoneStatisticsManager implements StatisticsManager {

    private int redstone;

    public RedstoneStatisticsManager() {
        this.redstone = 0;
    }

    public RedstoneStatisticsManager(int redstone) {
        this.redstone = redstone;
    }

    @Override
    public int get() {
        return this.redstone;
    }

    @Override
    public void set(int value) {
        this.redstone = value;
    }
}
