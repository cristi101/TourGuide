package eu.baboi.cristian.tourguide.utils.net.requests;

import android.net.Uri;
import android.os.Bundle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import eu.baboi.cristian.tourguide.utils.net.HTTP;
import eu.baboi.cristian.tourguide.utils.net.model.Model;

public abstract class PlacesRequest<T, A extends PlacesRequest<T, A, R>, R extends PlacesResponse<T>> {
    private static final String LOG = PlacesRequest.class.getName();
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";

    private HashMap<String, String> params = new HashMap<>();

    public interface Value {
        String value();
    }

    public interface Page {
        boolean hasFirst();

        void page(String pageToken);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();

        for (String key : params.keySet()) {
            String val = params.get(key);
            if (val != null) bundle.putString(key, val);
        }
        return bundle;
    }

    public void fromBundle(Bundle bundle) {
        for (String key : bundle.keySet()) {
            String val = bundle.getString(key);
            if (val != null) params.put(key, val);
        }
    }

    protected abstract Class<R> clazz();

    protected abstract String url();

    protected abstract void validate();

    public abstract A copy();

    protected A instance() {
        return (A) this;
    }

    protected A param(String key, String val) {
        if (key == null) return instance();
        if (val == null) params.remove(key);
        else params.put(key, val);
        return instance();
    }

    protected A param(String key, Value val) {
        return param(key, val.value());
    }

    protected Map<String, String> params() {
        return Collections.unmodifiableMap(params);
    }

    protected void putAll(Map<String, String> map) {
        params.putAll(map);
    }

    public final A language(String language) {
        return param("language", language);
    }

    public final A key(String key) {
        return param("key", key);
    }

    public A custom(String parameter, String value) {
        return param(parameter, value);
    }

    private Uri buildUri() {
        Uri.Builder builder = Uri.parse(url()).buildUpon();
        for (String key : params.keySet()) {
            String val = params.get(key);
            if (val != null) builder.appendQueryParameter(key, val);
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return buildUri().toString();
    }

    public R get() {
        validate();

        Model.Result result = null;

        Uri uri = buildUri();
        if (uri == null) return null;

        Class<R> clazz = clazz();
        if (clazz == null) return null;

        if (clazz.equals(PhotoRequest.Response.class)) {
            PhotoRequest request = (PhotoRequest) this;
            Uri uri2 = HTTP.getPicture(request.getDir(), params.get("photoreference"), uri.toString());
            return (R) new PhotoRequest.Response(uri2);
        }

        result = HTTP.getData(uri.toString(), null);
        if (result == null) return null;
        else return Model.genericParseObject(clazz(), result.data);
    }
}
