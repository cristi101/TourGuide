package eu.baboi.cristian.tourguide.utils.net.model;

import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.model.adapters.EnumAdapter;

public class Geometry {
    public Bounds bounds;
    public LatLng location;
    public LocationType locationType;
    public Bounds viewport;

    private Geometry(JSONObject json) {
        bounds = Model.genericParseObject(Bounds.class, "bounds", json);
        location = Model.genericParseObject(LatLng.class, "location", json);
        locationType = (new EnumAdapter<LocationType>("location_type", LocationType.UNKNOWN)).parse(json);
        viewport = Model.genericParseObject(Bounds.class, "viewport", json);
    }
}
