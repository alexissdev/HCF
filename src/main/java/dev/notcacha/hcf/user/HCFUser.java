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

    public HCFUser(String id, String name) {
        this(id, name, "EN", new KillsStatisticsManager(), new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language) {
        this(id, name, language, new KillsStatisticsManager(), new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language, StatisticsManager killsManager) {
        this(id, name, language, killsManager, new DeathsStatisticsManager());
    }

    public HCFUser(String id, String name, String language, StatisticsManager killsManager, StatisticsManager deathsManager) {
        this.id = id;
        this.name = name;
        this.language = language;
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
    public StatisticsManager getKillsManager() {
        return this.killsManager;
    }

    @Override
    public StatisticsManager getDeathsManager() {
        return this.deathsManager;
    }

}
