package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class CoalStatisticsManager implements StatisticsManager {

    private int coal;

    public CoalStatisticsManager() {
        this.coal = 0;
    }

    @Override
    public int get() {
        return this.coal;
    }

    @Override
    public void set(int value) {
        this.coal = value;
    }
}
