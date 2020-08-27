package dev.notcacha.hcf.user;

import dev.notcacha.hcf.statistics.DeathsStatisticsManager;
import dev.notcacha.hcf.statistics.KillsStatisticsManager;
import dev.notcacha.hcf.statistics.StatisticsManager;
import dev.notcacha.hcf.statistics.ores.CoalStatisticsManager;
import dev.notcacha.hcf.statistics.ores.DiamondsStatisticsManager;
import dev.notcacha.hcf.statistics.ores.EmeraldsStatisticsManager;
import dev.notcacha.hcf.statistics.ores.GoldsStatisticsManager;
import dev.notcacha.hcf.statistics.ores.IronStatisticsManager;
import dev.notcacha.hcf.statistics.ores.LapisStatisticsManager;
import dev.notcacha.hcf.statistics.ores.RedstoneStatisticsManager;

public class HCFUser implements User {

    private final String id;
    private final String name;
    private String language;
    private boolean timer;

    private final StatisticsManager killsManager;
    private final StatisticsManager deathsManager;
    private final StatisticsManager diamondsManager;
    private final StatisticsManager emeraldsManager;
    private final StatisticsManager redstoneManager;
    private final StatisticsManager lapisManager;
    private final StatisticsManager goldManager;
    private final StatisticsManager ironManager;
    private final StatisticsManager coalManager;

    public HCFUser(String id, String name) {
        this(id, name, "EN", false);
    }

    public HCFUser(String id, String name, String language) {
        this(id, name, language, false);
    }

    public HCFUser(String id, String name, String language, boolean timer) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.timer = timer;

        this.killsManager = new KillsStatisticsManager();
        this.deathsManager = new DeathsStatisticsManager();
        this.diamondsManager = new DiamondsStatisticsManager();
        this.emeraldsManager = new EmeraldsStatisticsManager();
        this.redstoneManager = new RedstoneStatisticsManager();
        this.lapisManager = new LapisStatisticsManager();
        this.goldManager = new GoldsStatisticsManager();
        this.ironManager = new IronStatisticsManager();
        this.coalManager = new CoalStatisticsManager();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public User setLanguage(String language) {
        this.language = language;
        return this;
    }

    @Override
    public boolean hasTimer() {
        return this.timer;
    }

    @Override
    public User setTimer(boolean timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public StatisticsManager getKillsManager() {
        return this.killsManager;
    }

    @Override
    public StatisticsManager getDeathsManager() {
        return this.deathsManager;
    }

    @Override
    public StatisticsManager getDiamondsManager() {
        return this.diamondsManager;
    }

    @Override
    public StatisticsManager getEmeraldsManager() {
        return this.emeraldsManager;
    }

    @Override
    public StatisticsManager getRedstoneManager() {
        return this.redstoneManager;
    }

    @Override
    public StatisticsManager getLapisManager() {
        return this.lapisManager;
    }

    @Override
    public StatisticsManager getGoldManager() {
        return this.goldManager;
    }

    @Override
    public StatisticsManager getIronManager() {
        return this.ironManager;
    }

    @Override
    public StatisticsManager getCoalManager() {
        return this.coalManager;
    }

}
