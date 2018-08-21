package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.model.adapters.DayOfWeekAdapter;


public class OpeningHours {
    public Boolean openNow;
    public Period[] periods;
    public String[] weekdayText;
    public Boolean permanentlyClosed;

    private OpeningHours(JSONObject json) {
        openNow = json.optBoolean("open_now");
        permanentlyClosed = json.optBoolean("permanently_closed");
        periods = Model.genericParseArray(Period.class, "periods", json);
        JSONArray array = json.optJSONArray("weekday_text");
        if (array != null) {
            int length = array.length();
            if (length > 0) weekdayText = new String[length];
            for (int i = 0; i < length; i++)
                weekdayText[i] = array.optString(i);
        }
    }

    public static class Period {
        public static class OpenClose {
            public enum DayOfWeek {
                SUNDAY("Sunday"),
                MONDAY("Monday"),
                TUESDAY("Tuesday"),
                WEDNESDAY("Wednesday"),
                THURSDAY("Thursday"),
                FRIDAY("Friday"),
                SATURDAY("Saturday"),
                UNKNOWN("Unknown");

                DayOfWeek(String name) {
                    this.name = name;
                }

                private final String name;

                public String getName() {
                    return name;
                }
            }


            public Period.OpenClose.DayOfWeek day;
            public int hh;
            public int mm;

            private OpenClose(JSONObject json) {
                day = (new DayOfWeekAdapter("day")).parse(json);
                String local = json.optString("time", null);
                if (local != null) {
                    hh = Integer.valueOf(local.substring(0, 2));
                    mm = Integer.valueOf(local.substring(2, 4));
                }
            }


        }

        public Period.OpenClose open;
        public Period.OpenClose close;

        private Period(JSONObject json) {
            open = Model.genericParseObject(Period.OpenClose.class, "open", json);
            close = Model.genericParseObject(Period.OpenClose.class, "close", json);
        }
    }
}
