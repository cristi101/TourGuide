package eu.baboi.cristian.tourguide.utils.net.results;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import eu.baboi.cristian.tourguide.utils.net.model.Geometry;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.model.OpeningHours;
import eu.baboi.cristian.tourguide.utils.net.model.Photo;
import eu.baboi.cristian.tourguide.utils.net.model.PlaceIdScope;
import eu.baboi.cristian.tourguide.utils.net.model.PlusCode;
import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;
import eu.baboi.cristian.tourguide.utils.net.model.adapters.EnumAdapter;
import eu.baboi.cristian.tourguide.utils.net.model.adapters.PriceLevelAdapter;

public class PlacesSearchResult {
    public URL icon;
    public Geometry geometry;
    public PlusCode plusCode;
    public String name;
    public OpeningHours openingHours;
    public Photo photos[];
    public String placeId;
    public PlaceIdScope scope;
    public PriceLevel priceLevel;
    public float rating;
    public String types[];
    public String vicinity;
    public String formattedAddress;
    public boolean permanentlyClosed;

    private PlacesSearchResult(JSONObject json) {
        try {
            String sIcon = json.optString("icon", null);
            if (sIcon != null) icon = new URL(sIcon);

            geometry = Model.genericParseObject(Geometry.class, "geometry", json);
            plusCode = Model.genericParseObject(PlusCode.class, "plus_code", json);
            name = json.optString("name", null);
            openingHours = Model.genericParseObject(OpeningHours.class, "opening_hours", json);
            photos = Model.genericParseArray(Photo.class, "photos", json);
            placeId = json.optString("place_id", null);
            scope = (new EnumAdapter<PlaceIdScope>("scope", PlaceIdScope.APP)).parse(json);

            priceLevel = (new PriceLevelAdapter("price_level")).parse(json);

            rating = (float) json.optDouble("rating");
            JSONArray array = json.optJSONArray("types");
            if (array != null) {
                int length = array.length();
                if (length > 0) types = new String[length];
                for (int i = 0; i < length; i++)
                    types[i] = array.optString(i);
            }
            vicinity = json.optString("vicinity", null);
            formattedAddress = json.optString("formatted_address", null);
            permanentlyClosed = json.optBoolean("permanently_closed");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
