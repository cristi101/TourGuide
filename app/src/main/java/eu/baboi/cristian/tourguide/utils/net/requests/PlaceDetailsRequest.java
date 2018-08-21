package eu.baboi.cristian.tourguide.utils.net.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.baboi.cristian.tourguide.utils.net.errors.PlacesException;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.results.PlaceDetails;

public class PlaceDetailsRequest extends PlacesRequest<PlaceDetails, PlaceDetailsRequest, PlaceDetailsRequest.Response> {

    @Override
    protected Class<Response> clazz() {
        return Response.class;
    }

    @Override
    protected String url() {
        return BASE_URL + "/details/json";
    }

    @Override
    protected void validate() {
        if (!params().containsKey("placeid")) {
            throw new IllegalArgumentException("Request must contain 'placeId'.");
        }
    }

    @Override
    public PlaceDetailsRequest copy() {
        PlaceDetailsRequest request = new PlaceDetailsRequest();
        request.putAll(params());
        return request;
    }

    public PlaceDetailsRequest placeId(String placeId) {
        return param("placeid", placeId);
    }

    public PlaceDetailsRequest region(String region) {
        return param("region", region);
    }

    public PlaceDetailsRequest fields(Fields... fields) {
        StringBuilder builder = new StringBuilder();
        boolean rest = false;
        for (Value field : fields) {
            if (rest) builder.append(',');
            else rest = true;
            builder.append(field.value());
        }
        return param("fields", builder.toString());
    }


    public static class Response implements PlacesResponse<PlaceDetails> {
        private String status;
        private PlaceDetails result;
        private String[] htmlAttributions;
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
                    result = Model.genericParseObject(PlaceDetails.class, "result", json);
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
        public PlaceDetails getResult() {
            if (result != null) {
                result.htmlAttributions = htmlAttributions;
            }
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

    public enum Fields implements PlacesRequest.Value {
        ADDRESS_COMPONENT("address_component"),
        ADR_ADDRESS("adr_address"),
        ALT_ID("alt_id"),
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
        URL("url"),
        UTC_OFFSET("utc_offset"),
        VICINITY("vicinity"),
        FORMATTED_PHONE_NUMBER("formatted_phone_number"),
        INTERNATIONAL_PHONE_NUMBER("international_phone_number"),
        OPENING_HOURS("opening_hours"),
        WEBSITE("website"),
        PRICE_LEVEL("price_level"),
        RATING("rating"),
        REVIEWS("reviews"),
        REFERENCE("reference");

        private final String field;

        Fields(final String field) {
            this.field = field;
        }

        @Override
        public String value() {
            return field;
        }
    }

}
