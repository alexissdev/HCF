package dev.notcacha.hcf.user;

import dev.notcacha.hcf.statistics.DeathsStatisticsManager;
import dev.notcacha.hcf.statistics.KillsStatisticsManager;
import dev.notcacha.hcf.statistics.StatisticsManager;

public class HCFUser implements User {

    private final String id;
    private final String name;
    private String language;
    private final StatisticsManager killsManager;
    private final StatisticsManager deathsManager;
    private boolean timer;

    public HCFUser(String id, String name) {
        this(id, name, "EN", false, new KillsStatisticsManager(), new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language) {
        this(id, name, language, false, new KillsStatisticsManager(), new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language, boolean timer) {
        this(id, name, language, timer, new KillsStatisticsManager(), new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language, boolean timer, StatisticsManager killsManager) {
        this(id, name, language, timer, killsManager, new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language, boolean timer, StatisticsManager killsManager, StatisticsManager deathsManager) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.timer = timer;
        this.killsManager = killsManager;
        this.deathsManager = deathsManager;
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

}
