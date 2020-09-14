package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class GoldsStatisticsManager implements StatisticsManager {

    private int golds;

    public GoldsStatisticsManager() {
        this.golds = 0;
    }

    public GoldsStatisticsManager(int gold) {
        this.golds = gold;
    }

    @Override
    public int get() {
        return this.golds;
    }

    @Override
    public void set(int value) {
        this.golds = value;
    }
}
