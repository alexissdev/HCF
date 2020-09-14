package dev.notcacha.hcf.statistics;

public class DeathsStatisticsManager implements StatisticsManager {

    private int deaths;

    public DeathsStatisticsManager() {
        this.deaths = 0;
    }

    public DeathsStatisticsManager(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public int get() {
        return this.deaths;
    }

    @Override
    public void set(int value) {
        this.deaths = value;
    }
}
