package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class IronStatisticsManager implements StatisticsManager {

    private int iron;

    public IronStatisticsManager() {
        this.iron = 0;
    }

    public IronStatisticsManager(int iron) {
        this.iron = iron;
    }

    @Override
    public int get() {
        return this.iron;
    }

    @Override
    public void set(int value) {
        this.iron = value;
    }
}
