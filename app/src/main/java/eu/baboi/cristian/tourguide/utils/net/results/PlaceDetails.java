package eu.baboi.cristian.tourguide.utils.net.results;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

import eu.baboi.cristian.tourguide.adapters.ListAdapter;
import eu.baboi.cristian.tourguide.utils.net.model.AddressComponent;
import eu.baboi.cristian.tourguide.utils.net.model.AddressType;
import eu.baboi.cristian.tourguide.utils.net.model.Geometry;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.model.OpeningHours;
import eu.baboi.cristian.tourguide.utils.net.model.Photo;
import eu.baboi.cristian.tourguide.utils.net.model.PlaceIdScope;
import eu.baboi.cristian.tourguide.utils.net.model.PlusCode;
import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;
import eu.baboi.cristian.tourguide.utils.net.model.adapters.EnumAdapter;
import eu.baboi.cristian.tourguide.utils.net.model.adapters.PriceLevelAdapter;

public class PlaceDetails implements Photos, ListAdapter.Page<PlaceDetails> {
    public AddressComponent[] addressComponents;
    public String formattedAddress;
    public String formattedPhoneNumber;
    public String adrAddress;
    public Geometry geometry;
    public PlusCode plusCode;
    public URL icon;
    public String internationalPhoneNumber;
    public String name;
    public OpeningHours openingHours;
    public boolean permanentlyClosed;
    public Photo[] photos;
    public String placeId;
    public PlaceIdScope scope;
    public AlternatePlaceIds[] altIds;
    public PriceLevel priceLevel;
    public float rating;
    public Review[] reviews;
    public AddressType[] types;
    public URL url;
    public int utcOffset;
    public String vicinity;
    public URL website;
    public String[] htmlAttributions;

    private PlaceDetails(JSONObject json) {
        try {
            addressComponents = Model.genericParseArray(AddressComponent.class, "address_components", json);
            formattedAddress = json.optString("formatted_address", null);
            formattedPhoneNumber = json.optString("formatted_phone_number", null);
            adrAddress = json.optString("adr_address", null);
            geometry = Model.genericParseObject(Geometry.class, "geometry", json);
            plusCode = Model.genericParseObject(PlusCode.class, "plus_code", json);

            String sIcon = json.optString("icon", null);
            if (sIcon != null) icon = new URL(sIcon);

            internationalPhoneNumber = json.optString("international_phone_number", null);
            name = json.optString("name", null);
            openingHours = Model.genericParseObject(OpeningHours.class, "opening_hours", json);
            permanentlyClosed = json.optBoolean("permanently_closed");
            photos = Model.genericParseArray(Photo.class, "photos", json);
            placeId = json.optString("place_id", null);
            scope = (new EnumAdapter<PlaceIdScope>("scope", PlaceIdScope.APP)).parse(json);
            altIds = Model.genericParseArray(AlternatePlaceIds.class, "alt_ids", json);
            priceLevel = (new PriceLevelAdapter("price_level")).parse(json);
            rating = (float) json.optDouble("rating");
            reviews = Model.genericParseArray(Review.class, "reviews", json);
            url = new URL(json.optString("url", null));
            utcOffset = json.optInt("utc_offset");
            vicinity = json.optString("vicinity", null);

            String web = json.optString("website", null);
            if (web != null) website = new URL(web);

            JSONArray array = json.optJSONArray("html_attributions");
            if (array != null) {
                int length = array.length();
                if (length > 0) htmlAttributions = new String[length];
                for (int i = 0; i < length; i++)
                    htmlAttributions[i] = array.optString(i);
            }

            array = json.optJSONArray("types");
            if (array != null) {
                int length = array.length();
                if (length > 0) types = new AddressType[length];
                for (int i = 0; i < length; i++) {
                    String type = array.optString(i);
                    if (type != null) types[i] = AddressType.valueOf(type.toUpperCase());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public int count() {
        return 1;
    }

    @Override
    public String token() {
        return null;
    }

    @Override
    public PlaceDetails get(int pos) {
        return this;
    }

    private class Iterator implements Photos.Iterator {
        private int pos = 0;

        @Override
        public Photo next() {
            if (photos == null) return null;
            int len = photos.length;
            len = 1; //1 to download only the first photo
            if (pos >= len) return null;
            int ndx = pos;
            pos++;
            return photos[ndx];
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator();
    }


    public static class AlternatePlaceIds {
        public String placeId;
        public PlaceIdScope scope;
    }

    public static class Review {
        public static class AspectRating {
            public enum RatingType {
                APPEAL,
                ATMOSPHERE,
                DECOR,
                FACILITIES,
                FOOD,
                OVERALL,
                QUALITY,
                SERVICE,
                UNKNOWN
            }

            public RatingType type;
            public int rating;
        }

        public AspectRating[] aspects;
        public String authorName;
        public URL authorUrl;
        public String language;
        public String profilePhotoUrl;
        public int rating;
        public String relativeTimeDescription;
        public String text;
        public Instant time;
    }

}
