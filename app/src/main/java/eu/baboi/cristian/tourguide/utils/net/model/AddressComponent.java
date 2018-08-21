package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddressComponent {
    public String longName;
    public String shortName;
    public AddressComponentType[] types;

    private AddressComponent(JSONObject json) {
        longName = json.optString("long_name", null);
        shortName = json.optString("short_name", null);
        JSONArray array = json.optJSONArray("types");
        if (array != null) {
            int length = array.length();
            if (length > 0) types = new AddressComponentType[length];
            for (int i = 0; i < length; i++) {
                String type = array.optString(i);
                if (type != null) types[i] = AddressComponentType.valueOf(type.toUpperCase());
            }
        }
    }
}
