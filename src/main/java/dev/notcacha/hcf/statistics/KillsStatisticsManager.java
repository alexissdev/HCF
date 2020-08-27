package dev.notcacha.hcf.statistics;

public class KillsStatisticsManager implements StatisticsManager {

    private int kills;

    public KillsStatisticsManager() {
        this.kills = 0;
    }

    @Override
    public int get() {
        return this.kills;
    }

    @Override
    public void set(int value) {
        this.kills = value;
    }
}
