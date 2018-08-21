package eu.baboi.cristian.tourguide.utils.secret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceDataStore;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// encrypted data store
public class DataStore extends PreferenceDataStore {
    private final SharedPreferences sharedPreferences;
    private final String password;

    private HashMap<Listener, ChangeListener> map;

    public interface Listener {
        void onSharedPreferenceChanged(DataStore dataStore, String key);
    }

    private class ChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
        private final Listener listener;

        ChangeListener(Listener listener) {
            this.listener = listener;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (listener != null)
                listener.onSharedPreferenceChanged(DataStore.this, key);
        }
    }

    public DataStore(Context context, String password) {
        this.password = password;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        map = new HashMap<>();
    }

    public void registerChangeListener(Listener listener) {
        if (listener == null) return;
        ChangeListener changeListener = new ChangeListener(listener);
        map.put(listener, changeListener);
        sharedPreferences.registerOnSharedPreferenceChangeListener(changeListener);
    }

    public void unregisterChangeListener(Listener listener) {
        if (listener == null) return;
        ChangeListener changeListener = map.remove(listener);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    public void putString(String key, @Nullable String value) {
        if (TextUtils.isEmpty(value)) {
            sharedPreferences.edit().remove(key).apply();
            return;
        }
        String newValue = Key.encodeApiKey(Key.encodeApiKey(key, password), value);
        sharedPreferences.edit().putString(key, newValue).apply();
    }

    public void putStringSet(String key, @Nullable Set<String> values) {
        if (values == null) {
            sharedPreferences.edit().remove(key).apply();
            return;
        }
        Set<String> newValues = new HashSet<>();
        String newPassword = Key.encodeApiKey(key, password);
        for (String value : values)
            if (!TextUtils.isEmpty(value))
                newValues.add(Key.encodeApiKey(newPassword, value));
        sharedPreferences.edit().putStringSet(key, newValues).apply();
    }

    public void putInt(String key, int value) {
        String newValue = Key.encodeApiKey(Key.encodeApiKey(key, password), String.valueOf(value));
        sharedPreferences.edit().putString(key, newValue).apply();
    }

    public void putLong(String key, long value) {
        String newValue = Key.encodeApiKey(Key.encodeApiKey(key, password), String.valueOf(value));
        sharedPreferences.edit().putString(key, newValue).apply();
    }


    public void putFloat(String key, float value) {
        String newValue = Key.encodeApiKey(Key.encodeApiKey(key, password), String.valueOf(value));
        sharedPreferences.edit().putString(key, newValue).apply();
    }

    public void putBoolean(String key, boolean value) {
        String newValue = Key.encodeApiKey(Key.encodeApiKey(key, password), String.valueOf(value));
        sharedPreferences.edit().putString(key, newValue).apply();
    }

    //get
    @Nullable
    public String getString(String key, @Nullable String defValue) {
        String value = sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(value) ? defValue : Key.decodeApiKey(Key.encodeApiKey(key, password), value);
    }


    @Nullable
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        Set<String> values = sharedPreferences.getStringSet(key, null);
        if (values == null) return defValues;
        String newPassword = Key.encodeApiKey(key, password);
        Set<String> newValues = new HashSet<>();
        for (String value : values)
            if (!TextUtils.isEmpty(value))
                newValues.add(Key.decodeApiKey(newPassword, value));
        return newValues;
    }

    public int getInt(String key, int defValue) {
        String value = sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(value) ? defValue : Integer.valueOf(Key.decodeApiKey(Key.encodeApiKey(key, password), value));
    }


    public long getLong(String key, long defValue) {
        String value = sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(value) ? defValue : Long.valueOf(Key.decodeApiKey(Key.encodeApiKey(key, password), value));
    }

    public float getFloat(String key, float defValue) {
        String value = sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(value) ? defValue : Float.valueOf(Key.decodeApiKey(Key.encodeApiKey(key, password), value));
    }


    public boolean getBoolean(String key, boolean defValue) {
        String value = sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(value) ? defValue : Boolean.valueOf(Key.decodeApiKey(Key.encodeApiKey(key, password), value));
    }
}
