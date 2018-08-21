package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public class LatLng implements PlacesRequest.Value {
    public double lat;
    public double lng;

    private LatLng(JSONObject json) {
        try {
            if (json.has("lat")) lat = json.getDouble("lat");
            else if (json.has("latitude")) lat = json.getDouble("latitude");

            if (json.has("lng")) lng = json.getDouble("lng");
            else if (json.has("longitude")) lng = json.getDouble("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String value() {
        return String.format(Locale.ENGLISH, "%.8f,%.8f", lat, lng);
    }


    @Override
    public String toString() {
        return value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatLng latLng = (LatLng) o;
        return Double.compare(latLng.lat, lat) == 0 && Double.compare(latLng.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new double[]{lat, lng});
    }

}
