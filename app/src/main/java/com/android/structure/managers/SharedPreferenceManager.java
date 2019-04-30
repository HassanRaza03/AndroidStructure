package com.android.structure.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.android.structure.constatnts.AppConstants;
import com.android.structure.models.receiving_model.UserModel;
import com.android.structure.models.sending_model.InsertRegisteredDeviceModel;
import com.android.structure.models.sending_model.RegisteredDeviceModel;

import static com.android.structure.constatnts.AppConstants.*;

/**
 * Class that can be extended to make available simple preference
 * setter/getters.
 * <p>
 * Should be extended to provide Facades.
 */
public class SharedPreferenceManager {
    private static SharedPreferences pref;
    private static SharedPreferenceManager factory;


    public static SharedPreferenceManager getInstance(Context context) {
        if (pref == null)
            pref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);

        if (factory == null)
            factory = new SharedPreferenceManager();

        return factory;
    }

    public void clearDB() {
        // Save Registered Device Data
        InsertRegisteredDeviceModel object = getObject(AppConstants.KEY_INSERT_REGISTERED_DEVICE, InsertRegisteredDeviceModel.class);
        RegisteredDeviceModel object2 = getObject(AppConstants.KEY_REGISTERED_DEVICE, RegisteredDeviceModel.class);
        if (object == null) {
            pref.edit().clear().commit();
        } else {
            object.setRegcardno(null);
            object2.setRegcardno(null);
            pref.edit().clear().commit();
            putObject(AppConstants.KEY_INSERT_REGISTERED_DEVICE, object);
            putObject(AppConstants.KEY_REGISTERED_DEVICE, object2);
        }
    }

    public void clearKey(String key) {
        pref.edit().remove(key).commit();
    }


    public void putValue(String key, Object value) {
        if (value instanceof Boolean)
            pref.edit().putBoolean(key, (Boolean) value).commit();
        else if (value instanceof String)
            pref.edit().putString(key, (String) value).commit();
        else if (value instanceof Integer)
            pref.edit().putInt(key, (int) value).commit();
        else if (value instanceof Long)
            pref.edit().putLong(key, (long) value).commit();

//        else
//            pref.edit().putString(key, (String) value).apply();
    }

    public int getInt(String key) {
        return pref.getInt(key, -1);
    }

    public long getLong(String key) {
        return pref.getLong(key, -1);
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public void putObject(String key, Object object) {
        if (object == null || object.equals("")) {
            pref.edit().putString(key, (String) object).commit();
            return;
        }

        pref.edit().putString(key, new Gson().toJson(object)).commit();
    }

    public void removeObject(String key) {
        pref.edit().remove(key).commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String json = pref.getString(key, null);
        if (json == null) {
            return null;
        } else {
            try {
                return new Gson().fromJson(json, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + key + " is instance of other class");
            }
        }
    }

    public boolean hasValue(String key) {
        return pref.contains(key);
    }

    public UserModel getCurrentUser() {
        return getObject(KEY_CURRENT_USER_MODEL, UserModel.class);
    }

    public boolean isForcedRestart() {
        return getBoolean(FORCED_RESTART);
    }

    public void setForcedRestart(boolean isForcedRestart) {
        putValue(FORCED_RESTART, isForcedRestart);
    }

    public void removePreference(Context context, String prefsName,
                                 String key) {

        SharedPreferences preferences = context.getSharedPreferences(
                prefsName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(key);
        editor.commit();
    }

}
