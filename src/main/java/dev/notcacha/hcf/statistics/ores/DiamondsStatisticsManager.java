package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class DiamondsStatisticsManager implements StatisticsManager {

    private int diamonds;

    public DiamondsStatisticsManager() {
        this.diamonds = 0;
    }

    public DiamondsStatisticsManager(int diamonds) {
        this.diamonds = diamonds;
    }

    @Override
    public int get() {
        return this.diamonds;
    }

    @Override
    public void set(int value) {
        this.diamonds = value;
    }
}
