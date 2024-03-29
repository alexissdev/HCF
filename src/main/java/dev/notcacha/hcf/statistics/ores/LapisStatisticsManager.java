package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class LapisStatisticsManager implements StatisticsManager {

    private int lapis;

    public LapisStatisticsManager() {
        this.lapis = 0;
    }

    public LapisStatisticsManager(int lapis) {
        this.lapis = lapis;
    }

    @Override
    public int get() {
        return this.lapis;
    }

    @Override
    public void set(int value) {
        this.lapis = value;
    }
}
