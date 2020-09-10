package com.meyoustu.amuse.content;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public final class SharedPrefs {

  private SharedPrefs(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  private static final String FILE_NAME = SharedPrefs.class.getName();

  private static volatile SharedPrefs sharedPrefs;

  private SharedPreferences sharedPreferences;

  public static final SharedPrefs initialize(Context ctx) {
    SharedPrefs tmp = sharedPrefs;
    if (null == tmp) {
      synchronized (SharedPrefs.class) {
        tmp = sharedPrefs;
        if (null == tmp) {
          tmp = new SharedPrefs(ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE));
          sharedPrefs = tmp;
        }
      }
    }

    return sharedPrefs;
  }

  public Map<String, ?> getAll() {
    return sharedPreferences.getAll();
  }

  @Nullable
  public String getString(String key, @Nullable Object defValue) {
    return sharedPreferences.getString(key, null == defValue ? "" : defValue.toString());
  }

  @Nullable
  public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
    return sharedPreferences.getStringSet(key, defValues);
  }

  public int getInt(String key, int defValue) {
    return sharedPreferences.getInt(key, defValue);
  }

  public long getLong(String key, long defValue) {
    return sharedPreferences.getLong(key, defValue);
  }

  public float getFloat(String key, float defValue) {
    return sharedPreferences.getFloat(key, defValue);
  }

  public boolean getBoolean(String key, boolean defValue) {
    return sharedPreferences.getBoolean(key, defValue);
  }

  public boolean contains(String key) {
    return sharedPreferences.contains(key);
  }

  private SharedPreferences.Editor edit() {
    return sharedPreferences.edit();
  }

  public void registerOnSharedPreferenceChangeListener(
      SharedPreferences.OnSharedPreferenceChangeListener listener) {
    sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
  }

  public void unregisterOnSharedPreferenceChangeListener(
      SharedPreferences.OnSharedPreferenceChangeListener listener) {
    sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
  }

  public SharedPrefs putNumber(String key, @NonNull Number value) {
    if (null == value) {
      // Do nothing.
      return this;
    } else if (value.getClass() == int.class || value.getClass() == Integer.class) {
      edit().putInt(key, value.intValue()).apply();
    } else if (value.getClass() == long.class || value.getClass() == Long.class) {
      edit().putLong(key, value.longValue()).apply();
    } else if (value.getClass() == float.class
        || value.getClass() == Float.class
        || value.getClass() == double.class
        || value.getClass() == Double.class) {
      edit().putFloat(key, value.floatValue()).apply();
    }
    return this;
  }

  public SharedPrefs putBoolean(String key, boolean value) {
    edit().putBoolean(key, value).apply();
    return this;
  }

  public SharedPrefs putString(String key, String value) {
    edit().putString(key, value).apply();
    return this;
  }

  public SharedPrefs putStringSet(String key, Set<String> value) {
    edit().putStringSet(key, value).apply();
    return this;
  }
}
