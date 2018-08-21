package eu.baboi.cristian.tourguide.utils.net.model.adapters;

import org.json.JSONObject;

import java.util.Locale;

public class EnumAdapter<E extends Enum<E>> extends TypeAdapter<E> {
    private static final String LOG = EnumAdapter.class.getName();

    private final E val;
    private final Class<E> clazz;
    private final String key;

    public EnumAdapter(String key, E val) {
        if (val == null) throw new IllegalArgumentException();
        this.val = val;
        this.clazz = val.getDeclaringClass();
        this.key = key;
    }

    public E parse(JSONObject json) {
        String value = null;

        if (json == null || json.isNull(key)) return val;

        value = json.optString(key);

        try {
            return Enum.valueOf(clazz, value.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return val;
        }
    }
}
