package eu.baboi.cristian.tourguide.utils.net.model;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

public class Photo {
    public String photoReference;
    public int height;
    public int width;
    public String[] htmlAttributions;
    public Uri uri;

    private Photo(JSONObject json) {
        photoReference = json.optString("photo_reference", null);
        height = json.optInt("height");
        width = json.optInt("width");
        JSONArray array = json.optJSONArray("html_attributions");
        if (array != null) {
            int length = array.length();
            if (length > 0) htmlAttributions = new String[length];
            for (int i = 0; i < length; i++)
                htmlAttributions[i] = array.optString(i);
        }
    }
}
