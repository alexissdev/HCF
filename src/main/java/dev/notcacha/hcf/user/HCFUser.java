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
import dev.notcacha.hcf.user.faction.SimpleUserFaction;
import dev.notcacha.hcf.user.faction.UserFaction;
import dev.notcacha.hcf.user.options.SimpleUserOptions;
import dev.notcacha.hcf.user.options.UserOptions;

public class HCFUser implements User {

    private final String id;
    private final String name;
    private String language;
    private boolean timer;

    private final UserFaction faction;
    private final UserOptions options;

    private final StatisticsManager killsManager;
    private final StatisticsManager deathsManager;
    private final StatisticsManager diamondsManager;
    private final StatisticsManager emeraldsManager;
    private final StatisticsManager redstoneManager;
    private final StatisticsManager lapisManager;
    private final StatisticsManager goldManager;
    private final StatisticsManager ironManager;
    private final StatisticsManager coalManager;

    public HCFUser(String id, String name, String language, boolean timer, UserFaction faction, UserOptions options, StatisticsManager killsManager
            , StatisticsManager deathsManager, StatisticsManager diamondsManager, StatisticsManager emeraldsManager, StatisticsManager redstoneManager,
                   StatisticsManager lapisManager, StatisticsManager goldManager, StatisticsManager ironManager, StatisticsManager coalManager) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.timer = timer;
        this.faction = faction;
        this.options = options;
        this.killsManager = killsManager;
        this.deathsManager = deathsManager;
        this.diamondsManager = diamondsManager;
        this.emeraldsManager = emeraldsManager;
        this.redstoneManager = redstoneManager;
        this.lapisManager = lapisManager;
        this.goldManager = goldManager;
        this.ironManager = ironManager;
        this.coalManager = coalManager;
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
    public UserFaction getFaction() {
        return this.faction;
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
    public UserOptions getOptions() {
        return this.options;
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

    public static class Builder implements User.Builder {

        private final String id;
        private String name;
        private String language;
        private boolean timer;

        private UserFaction faction;
        private UserOptions options;

        private StatisticsManager killsManager;
        private StatisticsManager deathsManager;
        private StatisticsManager diamondsManager;
        private StatisticsManager emeraldsManager;
        private StatisticsManager redstoneManager;
        private StatisticsManager lapisManager;
        private StatisticsManager goldManager;
        private StatisticsManager ironManager;
        private StatisticsManager coalManager;

        public Builder(String id) {
            this.id = id;
            this.name = null;
            this.language = "EN";
            this.timer = false;

            this.faction = new SimpleUserFaction();
            this.options = new SimpleUserOptions();

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
        public User.Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public User.Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        @Override
        public User.Builder setTimer(boolean timer) {
            this.timer = timer;
            return this;
        }

        @Override
        public User.Builder setFaction(UserFaction faction) {
            this.faction = faction;
            return this;
        }

        @Override
        public User.Builder setOptions(UserOptions options) {
            this.options = options;
            return this;
        }

        @Override
        public User.Builder setKillsStatistics(StatisticsManager killsStatistics) {
            this.killsManager = killsStatistics;
            return this;
        }

        @Override
        public User.Builder setDeathsStatistics(StatisticsManager deathsStatistics) {
            this.deathsManager = deathsStatistics;
            return this;
        }

        @Override
        public User.Builder setDiamondsStatistics(StatisticsManager diamondsStatistics) {
            this.diamondsManager = diamondsStatistics;
            return this;
        }

        @Override
        public User.Builder setEmeraldsStatistics(StatisticsManager emeraldsStatistics) {
            this.emeraldsManager = emeraldsStatistics;
            return this;
        }

        @Override
        public User.Builder setRedstoneStatistics(StatisticsManager redstoneStatistics) {
            this.redstoneManager = redstoneStatistics;
            return this;
        }

        @Override
        public User.Builder setLapisStatistics(StatisticsManager lapisStatistics) {
            this.lapisManager = lapisStatistics;
            return this;
        }

        @Override
        public User.Builder setGoldStatistics(StatisticsManager goldStatistics) {
            this.goldManager = goldStatistics;
            return this;
        }

        @Override
        public User.Builder setIronStatistics(StatisticsManager ironStatistics) {
            this.ironManager = ironStatistics;
            return this;
        }

        @Override
        public User.Builder setCoalStatistics(StatisticsManager coalStatistics) {
           this.coalManager = coalStatistics;
            return this;
        }

        @Override
        public User build() {
            return new HCFUser(id, name, language, timer, faction, options, killsManager, deathsManager, diamondsManager, emeraldsManager, redstoneManager, lapisManager, goldManager, ironManager, coalManager);
        }
    }
}
