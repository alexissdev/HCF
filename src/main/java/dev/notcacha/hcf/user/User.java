package dev.notcacha.hcf.user;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.statistics.StatisticsManager;

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
     * @return an object 'StatisticsManager' in order to handle the kills of this user and to be able to keep a better control
     */

    StatisticsManager getKillsManager();

    /**
     * @return a 'StatisticsManager' object in order to handle the deaths of this user and to be able to keep a better control
     */

    StatisticsManager getDeathsManager();

}
