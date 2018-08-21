package eu.baboi.cristian.tourguide.utils.net.model;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.net.URLEncoder;
import java.security.SecureRandom;


public class Model {
    private static final String LOG = Model.class.getName();

    public static final String PASSWORD = "once upon a time";
    public static final String PASSWORD_KEY = "password";

    public static final String ID_KEY = "id_key";

    //no constructor
    private Model() {
    }

    // various classes

    // reference to an object
    public static class Ref<T> {
        public T ref;

        public Ref() {
            ref = null;
        }

        public Ref(T ref) {
            this.ref = ref;
        }
    }

    // HTTP request result
    public static class Result {
        public int code;//error code
        public String data;//HTTP response
    }

    //Error objects
    public static class Error extends Exception {

        public String status;
        public String error_message;

        private Error(JSONObject json) {
            try {
                status = json.getString("status");
                error_message = json.optString("error_message");
            } catch (Exception e) {
                status = null;
                error_message = e.getMessage();
            }
        }
    }

    // various ways to construct data model objects given type and json data

    //convert for string to json parse tree
    private static <T> JSONObject jsonObject(Class<T> which, String json) {
        if (which == null) return null;
        if (json == null || json.isEmpty()) return null;
        JSONObject result = null;
        try {
            result = new JSONObject(json);
        } catch (Exception e) {
            Log.e(LOG, String.format("Error parsing %s JSON object", which.getSimpleName()), e);
        }
        return result;
    }

    private static <T> JSONArray jsonArray(Class<T> which, String json) {
        if (which == null) return null;
        if (json == null || json.isEmpty()) return null;
        JSONArray result = null;
        try {
            result = new JSONArray(json);
        } catch (Exception e) {
            Log.e(LOG, String.format("Error parsing %s JSON array", which.getSimpleName()), e);
        }
        return result;
    }

    // return field with specified key
    private static <T> JSONObject getObject(String key, JSONObject json) {
        if (json == null) return null;
        if (key == null || key.isEmpty()) return null;
        return json.optJSONObject(key);
    }

    private static <T> JSONArray getArray(String key, JSONObject json) {
        if (json == null) return null;
        if (key == null || key.isEmpty()) return null;
        return json.optJSONArray(key);
    }

    // return the constructor for given class
    private static <T> Constructor<T> getConstructor(Class<T> which) {
        Constructor<T> constructor;
        try {
            constructor = which.getDeclaredConstructor(JSONObject.class);
            constructor.setAccessible(true);
        } catch (Exception e) {
            Log.e(LOG, String.format("No suitable constructor found for %s!", which.getSimpleName()), e);
            return null;
        }
        return constructor;
    }

    // object parsing
    public static <T> T genericParseObject(Class<T> which, String json) {
        JSONObject object = jsonObject(which, json);
        if (object == null) return null;
        return genericParseObject(which, object);
    }

    public static <T> T genericParseObject(Class<T> which, String key, String json) {
        JSONObject object = jsonObject(which, json);
        if (object == null) return null;
        return genericParseObject(which, key, object);
    }

    public static <T> T genericParseObject(Class<T> which, String key, JSONObject json) {
        JSONObject object = getObject(key, json);
        if (object == null) return null;
        return genericParseObject(which, object);
    }

    public static <T> T genericParseObject(Class<T> which, JSONObject json) {
        if (which == null) return null;
        if (json == null) return null;

        Constructor<T> constructor = getConstructor(which);
        if (constructor == null) return null;

        T result = null;
        try {
            result = constructor.newInstance(json);
        } catch (Exception e) {
            Log.e(LOG, String.format("Error parsing %s JSON object", which.getSimpleName()), e);
        }
        return result;
    }

    // array parsing
    public static <T> T[] genericParseArray(Class<T> which, String json) {
        JSONArray array = jsonArray(which, json);
        if (array == null) return null;
        return genericParseArray(which, array);
    }

    public static <T> T[] genericParseArray(Class<T> which, String key, String json) {
        JSONObject object = jsonObject(which, json);
        if (object == null) return null;
        return genericParseArray(which, key, object);
    }

    public static <T> T[] genericParseArray(Class<T> which, String key, JSONObject json) {
        JSONArray array = getArray(key, json);
        if (array == null) return null;
        return genericParseArray(which, array);
    }

    public static <T> T[] genericParseArray(Class<T> which, JSONArray array) {
        if (which == null) return null;
        if (array == null) return null;

        Constructor<T> constructor = getConstructor(which);
        if (constructor == null) return null;

        T[] result = null;

        try {
            int length = array.length();

            if (length > 0) result = (T[]) Array.newInstance(which, length);
            for (int i = 0; i < length; i++) {
                JSONObject item = array.optJSONObject(i);
                if (item != null)
                    result[i] = constructor.newInstance(item);
            }
        } catch (Exception e) {
            Log.e(LOG, String.format("Error parsing %s JSON array", which.getSimpleName()), e);
            return null;
        }
        return result;
    }

    //generate random string of given length
    public static String getRandomString(int length) {
        if (length < 1) return null;
        SecureRandom random = new SecureRandom();
        byte[] buffer = new byte[length];
        random.nextBytes(buffer);
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    // add an URL encoded parameter
    private static void addURLParameter(StringBuilder builder, String key, String value) {
        if (builder == null || key == null || value == null) return;
        try {
            builder.append(URLEncoder.encode(key, "UTF-8"));
            builder.append('=');
            builder.append(URLEncoder.encode(value, "UTF-8"));
        } catch (Exception e) {
            Log.e(LOG, "No UTF-8 encoding found!", e);
        }
    }

    // Check if there is network connectivity
    public static boolean hasNetwork(android.content.Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
