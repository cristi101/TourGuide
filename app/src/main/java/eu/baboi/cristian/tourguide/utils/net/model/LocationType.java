package eu.baboi.cristian.tourguide.utils.net.model;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public enum LocationType implements PlacesRequest.Value {
    ROOFTOP,
    RANGE_INTERPOLATED,
    GEOMETRIC_CENTER,
    APPROXIMATE,
    UNKNOWN;

    public String value() {
        return name();
    }
}
