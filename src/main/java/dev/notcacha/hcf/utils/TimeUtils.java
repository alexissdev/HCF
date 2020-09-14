package dev.notcacha.hcf.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.notcacha.languagelib.LanguageLib;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Singleton
public class TimeUtils {

    @Inject
    private LanguageLib languageLib;

    public String format(long time, String language) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        long seconds = time / 1000;

        int unitValue = Math.toIntExact(seconds / TimeUnit.DAYS.toSeconds(7));

        if (unitValue > 0) {
            seconds %= TimeUnit.DAYS.toSeconds(7);

            String unit;

            if (unitValue == 1) {
                unit = languageLib.getFileManageable().find(language).getString("format.week");
            } else {
                unit = languageLib.getFileManageable().find(language).getString("format.weeks");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.DAYS.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.DAYS.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = languageLib.getFileManageable().find(language).getString("format.day");
            } else {
                unit = languageLib.getFileManageable().find(language).getString("format.days");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.HOURS.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.HOURS.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = languageLib.getFileManageable().find(language).getString("format.hour");
            } else {
                unit = languageLib.getFileManageable().find(language).getString("format.hours");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.MINUTES.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.MINUTES.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = languageLib.getFileManageable().find(language).getString("format.minute");
            } else {
                unit = languageLib.getFileManageable().find(language).getString("format.minutes");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        if (seconds > 0 || stringJoiner.length() == 0) {
            String unit;

            if (seconds == 1) {
                unit = languageLib.getFileManageable().find(language).getString("format.second");
            } else {
                unit = languageLib.getFileManageable().find(language).getString("format.seconds");
            }

            stringJoiner.add(seconds + " " + unit);
        }

        return stringJoiner.toString();
    }
}
