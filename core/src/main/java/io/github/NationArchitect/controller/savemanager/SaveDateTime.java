package io.github.NationArchitect.controller.savemanager;

import java.util.Calendar;
import java.util.Date;

/**
 * Lightweight serializable timestamp model used by save previews and documents.
 */
public class SaveDateTime {

    private long epochMillis;
    private int hour;
    private int minute;

    public SaveDateTime() {
    }

    public SaveDateTime(long epochMillis, int hour, int minute) {
        this.epochMillis = epochMillis;
        this.hour = hour;
        this.minute = minute;
    }

    public static SaveDateTime now() {
        Calendar calendar = Calendar.getInstance();
        return new SaveDateTime(
            calendar.getTimeInMillis(),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        );
    }

    public Date toDate() {
        return new Date(epochMillis);
    }

    public long getEpochMillis() {
        return epochMillis;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
