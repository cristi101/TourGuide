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

public class TextSearchRequest extends PlacesRequest<PlacesSearch, TextSearchRequest, TextSearchRequest.Response> {
    @Override
    protected Class<Response> clazz() {
        return Response.class;
    }

    @Override
    protected String url() {
        return BASE_URL + "/textsearch/json";
    }

    @Override
    protected void validate() {

        if (params().containsKey("pagetoken")) {
            return;
        }

        if (!params().containsKey("query")) {
            throw new IllegalArgumentException("Request must contain 'query' or a 'pageToken'.");
        }

        if (params().containsKey("location") && !params().containsKey("radius")) {
            throw new IllegalArgumentException(
                    "Request must contain 'radius' parameter when it contains a 'location' parameter.");
        }
    }

    @Override
    public TextSearchRequest copy() {
        TextSearchRequest request = new TextSearchRequest();
        request.putAll(params());
        return request;
    }

    public TextSearchRequest query(String query) {
        return param("query", query);
    }

    public TextSearchRequest location(LatLng location) {
        return param("location", location);
    }

    public TextSearchRequest radius(int radius) {
        if (radius > 50000) {
            throw new IllegalArgumentException("The maximum allowed radius is 50,000 meters.");
        }
        return param("radius", String.valueOf(radius));
    }

    public TextSearchRequest minPrice(PriceLevel priceLevel) {
        return param("minprice", priceLevel);
    }

    public TextSearchRequest maxPrice(PriceLevel priceLevel) {
        return param("maxprice", priceLevel);
    }

    public TextSearchRequest name(String name) {
        return param("name", name);
    }

    public TextSearchRequest openNow(boolean openNow) {
        return param("opennow", String.valueOf(openNow));
    }

    public TextSearchRequest pageToken(String nextPageToken) {
        return param("pagetoken", nextPageToken);
    }

    public TextSearchRequest rankby(RankBy ranking) {
        return param("rankby", ranking);
    }

    public TextSearchRequest type(PlaceType type) {
        return param("type", type);
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
                    nextPageToken = json.optString("next_page_token");
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
