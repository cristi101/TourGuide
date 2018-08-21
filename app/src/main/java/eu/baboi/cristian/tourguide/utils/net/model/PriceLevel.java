package eu.baboi.cristian.tourguide.utils.net.model;

import eu.baboi.cristian.tourguide.utils.net.requests.PlacesRequest;

public enum PriceLevel implements PlacesRequest.Value {
    FREE("0"),
    INEXPENSIVE("1"),
    MODERATE("2"),
    EXPENSIVE("3"),
    VERY_EXPENSIVE("4"),
    UNKNOWN("Unknown");

    private final String priceLevel;

    PriceLevel(final String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String value() {
        return priceLevel;
    }
}
