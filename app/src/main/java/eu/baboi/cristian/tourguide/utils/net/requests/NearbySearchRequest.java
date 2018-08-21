package eu.baboi.cristian.tourguide.utils.net.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.model.LatLng;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.model.PlaceType;
import eu.baboi.cristian.tourguide.utils.net.model.PriceLevel;
import eu.baboi.cristian.tourguide.utils.net.model.RankBy;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearch;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;

public class NearbySearchRequest extends PlacesRequest<PlacesSearch, NearbySearchRequest, NearbySearchRequest.Response> implements PlacesRequest.Page {
    private static final String LOG = NearbySearchRequest.class.getName();

    @Override
    protected Class<Response> clazz() {
        return Response.class;
    }

    @Override
    protected String url() {
        return BASE_URL + "/nearbysearch/json";
    }

    @Override
    protected void validate() {
        if (params().containsKey("pagetoken")) {
            return;
        }

        if (params().containsKey("rankby")
                && params().get("rankby").equals(RankBy.DISTANCE.value())
                && params().containsKey("radius")) {
            throw new IllegalArgumentException("Request must not contain radius with rankby=distance");
        }

        if (params().containsKey("rankby")
                && params().get("rankby").equals(RankBy.DISTANCE.value())
                && !params().containsKey("keyword")
                && !params().containsKey("name")
                && !params().containsKey("type")) {
            throw new IllegalArgumentException(
                    "With rankby=distance is specified, then one or more of keyword, name, or type is required");
        }

    }

    @Override
    public NearbySearchRequest copy() {
        NearbySearchRequest request = new NearbySearchRequest();
        request.putAll(params());
        return request;
    }

    public NearbySearchRequest location(LatLng location) {
        return param("location", location);
    }

    public NearbySearchRequest radius(int distance) {
        if (distance > 50000) {
            throw new IllegalArgumentException("The maximum allowed radius is 50,000 meters.");
        }
        return param("radius", String.valueOf(distance));
    }

    public NearbySearchRequest rankby(RankBy ranking) {
        return param("rankby", ranking);
    }

    public NearbySearchRequest keyword(String keyword) {
        return param("keyword", keyword);
    }

    public NearbySearchRequest minPrice(PriceLevel priceLevel) {
        return param("minprice", priceLevel);
    }

    public NearbySearchRequest maxPrice(PriceLevel priceLevel) {
        return param("maxprice", priceLevel);
    }

    public NearbySearchRequest name(String name) {
        return param("name", name);
    }

    public NearbySearchRequest openNow(boolean openNow) {
        return param("opennow", String.valueOf(openNow));
    }

    public NearbySearchRequest pageToken(String nextPageToken) {
        return param("pagetoken", nextPageToken);
    }


    public NearbySearchRequest type(PlaceType type) {
        return param("type", type);
    }

    @Override
    public boolean hasFirst() {
        String val = params().get("pagetoken");
        return val != null;
    }

    @Override
    public void page(String pageToken) {
        pageToken(pageToken);
    }

    public static class Response implements PlacesResponse<PlacesSearch> {

        private String status;
        private String htmlAttributions[];
        private PlacesSearchResult results[];
        private String nextPageToken;
        private String errorMessage;

        private Response(JSONObject json) {
            try {
                status = json.getString("status");
                if (!successful()) errorMessage = json.optString("error_message");
                else {
                    JSONArray array = json.optJSONArray("html_attributions");
                    if (array != null) {
                        int length = array.length();
                        if (length > 0) htmlAttributions = new String[length];
                        for (int i = 0; i < length; i++)
                            htmlAttributions[i] = array.getString(i);
                    }
                    nextPageToken = json.optString("next_page_token", null);
                    results = Model.genericParseArray(PlacesSearchResult.class, "results", json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean successful() {
            return "OK".equals(status) || "ZERO_RESULTS".equals(status);
        }

        @Override
        public PlacesSearch getResult() {
            PlacesSearch result = new PlacesSearch();
            result.htmlAttributions = htmlAttributions;
            result.results = results;
            result.nextPageToken = nextPageToken;
            return result;
        }

        @Override
        public PlacesException getError() {
            if (successful()) {
                return null;
            }
            return PlacesException.from(status, errorMessage);
        }
    }


}
