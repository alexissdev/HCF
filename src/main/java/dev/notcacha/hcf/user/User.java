package dev.notcacha.hcf.user;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.statistics.StatisticsManager;
import dev.notcacha.hcf.user.faction.UserFaction;
import dev.notcacha.hcf.user.options.UserOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface User extends Model {

    /**
     * @return name from this user
     */

    String getName();

    /**
     * @return language from this user
     */

    String getLanguage();

    /**
     * Set language
     *
     * @param language has been set
     */

    User setLanguage(String language);

    /**
     * @return faction manager from this user
     */

    UserFaction getFaction();

    /**
     * @return boolean is responsible for returning value of true/false if the {@link User} has entered for the first time or has already died to set the PvP Timer to have its procedure
     */

    boolean hasTimer();

    /**
     * Set timer state from {@link User}
     *
     * @param timer state from timer
     */

    User setTimer(boolean timer);

    /**
     * @return {@link UserOptions} from manageable options from this user
     */

    UserOptions getOptions();

    /**
     * @return an object 'StatisticsManager' in order to handle the kills of this user and to be able to keep a better control
     */

    StatisticsManager getKillsManager();

    /**
     * @return a 'StatisticsManager' object in order to handle the deaths of this user and to be able to keep a better control
     */

    StatisticsManager getDeathsManager();

    /**
     * @return an object 'StatisticsManager' in order to handle the diamonds of this user and to be able to keep a better control
     */

    StatisticsManager getDiamondsManager();

    /**
     * @return a 'StatisticsManager' object in order to handle the emeralds of this user and to be able to keep a better control
     */

    StatisticsManager getEmeraldsManager();

    /**
     * @return an object 'StatisticsManager' in order to handle the redstone of this user and to be able to keep a better control
     */

    StatisticsManager getRedstoneManager();

    /**
     * @return a 'StatisticsManager' object in order to handle the lapis of this user and to be able to keep a better control
     */

    StatisticsManager getLapisManager();

    /**
     * @return an object 'StatisticsManager' in order to handle the gold of this user and to be able to keep a better control
     */

    StatisticsManager getGoldManager();

    /**
     * @return a 'StatisticsManager' object in order to handle the iron of this user and to be able to keep a better control
     */

    StatisticsManager getIronManager();

    /**
     * @return an object 'StatisticsManager' in order to handle the coal of this user and to be able to keep a better control
     */

    StatisticsManager getCoalManager();

    /**
     * @return Bukkit player using user id
     */

    default Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(getId()));
    }

    interface Builder {

        /**
         * {@param name} has been set from user
         */

        Builder setName(String name);

        /**
         * {@param language} has been set from user
         */

        Builder setLanguage(String language);

        /**
         * {@param timer} has been set from user
         */

        Builder setTimer(boolean timer);

        /**
         * {@param factions} has been set from user
         */

        Builder setFaction(UserFaction faction);

        /**
         * {@param options} has been set from user
         */

        Builder setOptions(UserOptions options);

        /**
         * {@param killsStatistics} has been set from user
         */

        Builder setKillsStatistics(StatisticsManager killsStatistics);

        /**
         * {@param deathsStatistics} has been set from user
         */

        Builder setDeathsStatistics(StatisticsManager deathsStatistics);

        /**
         * {@param diamondsStatistics} has been set from user
         */

        Builder setDiamondsStatistics(StatisticsManager diamondsStatistics);

        /**
         * {@param emeraldsStatistics} has been set from user
         */

        Builder setEmeraldsStatistics(StatisticsManager emeraldsStatistics);

        /**
         * {@param redstoneStatistics} has been set from user
         */

        Builder setRedstoneStatistics(StatisticsManager redstoneStatistics);

        /**
         * {@param lapisStatistics} has been set from user
         */

        Builder setLapisStatistics(StatisticsManager lapisStatistics);

        /**
         * {@param goldStatistics} has been set from user
         */

        Builder setGoldStatistics(StatisticsManager goldStatistics);

        /**
         * {@param ironStatistics} has been set from user
         */

        Builder setIronStatistics(StatisticsManager ironStatistics);

        /**
         * {@param coalStatistics} has been set from user
         */

        Builder setCoalStatistics(StatisticsManager coalStatistics);

        /**
         * @return new {@link User}
         */

        User build();

    }

    static Builder builder(String id) {
        return new HCFUser.Builder(id);
    }
}
