package eu.baboi.cristian.tourguide.utils.net.model.adapters;

import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.model.OpeningHours;

public class DayOfWeekAdapter extends TypeAdapter<OpeningHours.Period.OpenClose.DayOfWeek> {
    private final String key;

    public DayOfWeekAdapter(String key) {
        this.key = key;
    }

    @Override
    public OpeningHours.Period.OpenClose.DayOfWeek parse(JSONObject json) {
        if (json == null || json.isNull(key))
            return OpeningHours.Period.OpenClose.DayOfWeek.UNKNOWN;
        int day = json.optInt(key);
        switch (day) {
            case 0:
                return OpeningHours.Period.OpenClose.DayOfWeek.SUNDAY;
            case 1:
                return OpeningHours.Period.OpenClose.DayOfWeek.MONDAY;
            case 2:
                return OpeningHours.Period.OpenClose.DayOfWeek.TUESDAY;
            case 3:
                return OpeningHours.Period.OpenClose.DayOfWeek.WEDNESDAY;
            case 4:
                return OpeningHours.Period.OpenClose.DayOfWeek.THURSDAY;
            case 5:
                return OpeningHours.Period.OpenClose.DayOfWeek.FRIDAY;
            case 6:
                return OpeningHours.Period.OpenClose.DayOfWeek.SATURDAY;

        }
        return OpeningHours.Period.OpenClose.DayOfWeek.UNKNOWN;
    }
}
