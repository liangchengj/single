package com.meyoustu.amuse.content;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/11 16:54
 */
public final class SharedPreferences {

    private SharedPreferences(android.content.SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private static final String FILE_NAME = SharedPreferences.class.getName();

    private static SharedPreferences sharedPreferences;

    private android.content.SharedPreferences preferences;

    public static final SharedPreferences initialize(Context ctx) {
        if (sharedPreferences == null) {
            synchronized (SharedPreferences.class) {
                sharedPreferences = (sharedPreferences == null)
                        ? new SharedPreferences(
                        ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE))
                        : sharedPreferences;
            }
        }
        return sharedPreferences;
    }


    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    @Nullable
    public String getString(String key, @Nullable String defValue) {
        return preferences.getString(key, defValue);
    }

    @Nullable
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return preferences.getStringSet(key, defValues);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    private android.content.SharedPreferences.Editor edit() {
        return preferences.edit();
    }

    public void registerOnSharedPreferenceChangeListener(
            android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
            android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


    public SharedPreferences putNumber(String key, @NonNull Number value) {
        if (null == value) {
            edit().putString(key, String.valueOf((Object) null));
        } else if (value.getClass() == int.class || value.getClass() == Integer.class) {
            edit().putInt(key, value.intValue()).apply();
        } else if (value.getClass() == long.class || value.getClass() == Long.class) {
            edit().putLong(key, value.longValue()).apply();
        } else if (value.getClass() == float.class || value.getClass() == Float.class) {
            edit().putFloat(key, value.floatValue()).apply();
        }
        return this;
    }


    public SharedPreferences putBoolean(String key, boolean value) {
        edit().putBoolean(key, value).apply();
        return this;
    }

    public SharedPreferences putString(String key, String value) {
        edit().putString(key, value).apply();
        return this;
    }

    public SharedPreferences putStringSet(String key, Set<String> value) {
        edit().putStringSet(key, value).apply();
        return this;
    }
}
