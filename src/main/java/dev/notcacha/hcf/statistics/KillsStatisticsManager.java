package dev.notcacha.hcf.statistics;

public class KillsStatisticsManager implements StatisticsManager {

    private int kills;

    public KillsStatisticsManager() {
        this.kills = 0;
    }

    public KillsStatisticsManager(int kills) {
        this.kills = kills;
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
