package eu.baboi.cristian.tourguide.utils.net.requests;

import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;

public interface PlacesResponse<T> {
    boolean successful();

    T getResult();

    PlacesException getError();
}
