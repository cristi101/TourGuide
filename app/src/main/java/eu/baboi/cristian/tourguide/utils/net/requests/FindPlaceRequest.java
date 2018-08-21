package eu.baboi.cristian.tourguide.utils.net.requests;

import org.json.JSONException;
import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.model.LatLng;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.results.FindPlace;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;

public class FindPlaceRequest extends PlacesRequest<FindPlace, FindPlaceRequest, FindPlaceRequest.Response> {

    @Override
    protected Class<Response> clazz() {
        return Response.class;
    }

    @Override
    protected String url() {
        return BASE_URL + "/findplacefromtext/json";
    }

    @Override
    protected void validate() {
        if (!params().containsKey("input")) {
            throw new IllegalArgumentException("Request must contain 'input'.");
        }
        if (!params().containsKey("inputtype")) {
            throw new IllegalArgumentException("Request must contain 'inputType'.");
        }
    }

    @Override
    public FindPlaceRequest copy() {
        FindPlaceRequest request = new FindPlaceRequest();
        request.putAll(params());
        return request;
    }

    public enum InputType implements Value {
        TEXT_QUERY("textquery"),
        PHONE_NUMBER("phonenumber");
        private final String inputType;

        InputType(final String inputType) {
            this.inputType = inputType;
        }

        public String value() {
            return inputType;
        }
    }

    public FindPlaceRequest input(String input) {
        return param("input", input);
    }


    public FindPlaceRequest inputType(InputType inputType) {
        return param("inputtype", inputType);
    }


    public FindPlaceRequest fields(Fields... fields) {
        StringBuilder builder = new StringBuilder();
        boolean rest = false;
        for (Value field : fields) {
            if (rest) builder.append(',');
            else rest = true;
            builder.append(field.value());
        }
        return param("fields", builder.toString());
    }

    public FindPlaceRequest locationBias(LocationBias locationBias) {
        return param("locationbias", locationBias);
    }

    public interface LocationBias extends Value {
    }

    public static class LocationBiasIP implements LocationBias {
        public String value() {
            return "ipbias";
        }
    }

    public static class PointLocationBias implements LocationBias {
        private final LatLng point;

        public PointLocationBias(LatLng point) {
            this.point = point;
        }

        @Override
        public String value() {
            return String.format("point:%s", point.value());
        }
    }

    public static class CircleLocationBias implements LocationBias {
        private final LatLng center;
        private final int radius;

        public CircleLocationBias(LatLng center, int radius) {
            this.center = center;
            this.radius = radius;
        }

        @Override
        public String value() {
            return String.format("circle:%s@%s", radius, center.value());
        }
    }

    public static class RectangleLocationBias implements LocationBias {
        private final LatLng southWest;
        private final LatLng northEast;

        public RectangleLocationBias(LatLng southWest, LatLng northEast) {
            this.southWest = southWest;
            this.northEast = northEast;
        }

        @Override
        public String value() {
            return String.format("rectangle:%s|%s", southWest.value(), northEast.value());
        }
    }


    public enum Fields implements Value {
        FORMATTED_ADDRESS("formatted_address"),
        GEOMETRY("geometry"),
        ICON("icon"),
        ID("id"),
        NAME("name"),
        PERMANENTLY_CLOSED("permanently_closed"),
        PHOTOS("photos"),
        PLACE_ID("place_id"),
        PLUS_CODE("plus_code"),
        SCOPE("scope"),
        TYPES("types"),
        OPENING_HOURS("opening_hours"),
        PRICE_LEVEL("price_level"),
        RATING("rating");

        private final String field;

        Fields(final String field) {
            this.field = field;
        }

        @Override
        public String value() {
            return field;
        }
    }


    //the response class
    public static class Response implements PlacesResponse<FindPlace> {

        private String status;
        private PlacesSearchResult candidates[];
        private String errorMessage;

        private Response(JSONObject json) {
            try {
                status = json.getString("status");
                if (!successful()) errorMessage = json.optString("error_message");
                else
                    candidates = Model.genericParseArray(PlacesSearchResult.class, "candidates", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean successful() {
            return "OK".equals(status) || "ZERO_RESULTS".equals(status);
        }

        @Override
        public FindPlace getResult() {
            FindPlace result = new FindPlace();
            result.candidates = candidates;
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
