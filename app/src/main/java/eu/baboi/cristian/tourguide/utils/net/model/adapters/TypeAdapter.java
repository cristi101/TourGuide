package eu.baboi.cristian.tourguide.utils.net.model.adapters;

import org.json.JSONObject;

public abstract class TypeAdapter<T> {
    public abstract T parse(JSONObject json);
}
