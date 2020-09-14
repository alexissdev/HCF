package dev.notcacha.hcf.statistics.ores;

import dev.notcacha.hcf.statistics.StatisticsManager;

public class EmeraldsStatisticsManager implements StatisticsManager {

    private int emeralds;

    public EmeraldsStatisticsManager() {
        this.emeralds = 0;
    }

    public EmeraldsStatisticsManager(int emeralds) {
        this.emeralds = emeralds;
    }

    @Override
    public int get() {
        return this.emeralds;
    }

    @Override
    public void set(int value) {
        this.emeralds = value;
    }
}
