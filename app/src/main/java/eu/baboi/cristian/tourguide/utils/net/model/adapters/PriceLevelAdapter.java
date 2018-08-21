package eu.baboi.cristian.tourguide.utils.net.model.adapters;

import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;

public class PriceLevelAdapter extends TypeAdapter<PriceLevel> {
    private final String key;

    public PriceLevelAdapter(String key) {
        this.key = key;
    }

    @Override
    public PriceLevel parse(JSONObject json) {
        if (json == null || json.isNull(key)) return PriceLevel.UNKNOWN;
        int level = json.optInt(key);
        switch (level) {
            case 0:
                return PriceLevel.FREE;
            case 1:
                return PriceLevel.INEXPENSIVE;
            case 2:
                return PriceLevel.MODERATE;
            case 3:
                return PriceLevel.EXPENSIVE;
            case 4:
                return PriceLevel.VERY_EXPENSIVE;
        }
        return PriceLevel.UNKNOWN;
    }
}
