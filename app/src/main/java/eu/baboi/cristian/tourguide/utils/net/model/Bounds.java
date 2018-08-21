package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONObject;

public class Bounds {
    public LatLng northeast;
    public LatLng southwest;

    private Bounds(JSONObject json) {
        northeast = Model.genericParseObject(LatLng.class, "northeast", json);
        southwest = Model.genericParseObject(LatLng.class, "southwest", json);
    }
}
