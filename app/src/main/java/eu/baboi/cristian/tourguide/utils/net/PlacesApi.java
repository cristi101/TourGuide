package eu.baboi.cristian.tourguide.utils.net;

import eu.baboi.cristian.tourguide.utils.net.model.LatLng;
import eu.baboi.cristian.tourguide.utils.net.requests.FindPlaceRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.NearbySearchRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PhotoRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.PlaceDetailsRequest;
import eu.baboi.cristian.tourguide.utils.net.requests.TextSearchRequest;

// https://github.com/googlemaps/google-maps-services-java
public class PlacesApi {
    private PlacesApi() {
    }

    ;

    public static FindPlaceRequest findPlace(String input, FindPlaceRequest.InputType inputType) {
        FindPlaceRequest request = new FindPlaceRequest();
        request.input(input).inputType(inputType);
        return request;
    }

    public static PhotoRequest photo(String photoReference) {
        PhotoRequest request = new PhotoRequest();
        request.photoReference(photoReference);
        return request;
    }

    public static PlaceDetailsRequest placeDetails(String placeId) {
        PlaceDetailsRequest request = new PlaceDetailsRequest();
        request.placeId(placeId);
        return request;
    }


    public static NearbySearchRequest nearbySearchQuery(LatLng location) {
        NearbySearchRequest request = new NearbySearchRequest();
        request.location(location);
        return request;
    }

    public static NearbySearchRequest nearbySearchNextPage(String nextPageToken) {
        NearbySearchRequest request = new NearbySearchRequest();
        request.pageToken(nextPageToken);
        return request;
    }

    public static TextSearchRequest textSearchQuery(String query) {
        TextSearchRequest request = new TextSearchRequest();
        request.query(query);
        return request;
    }

    public static TextSearchRequest textSearchNextPage(String nextPageToken) {
        TextSearchRequest request = new TextSearchRequest();
        request.pageToken(nextPageToken);
        return request;
    }
}
