package kr.co.composer.kangtalk.pref;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class AbstractPreferenceManager {
    private SharedPreferences mPreference = null;
    public static final String INFO_ID = "infoID";

    public void initPreferenceData(ContextWrapper ctxWrapper) {
        mPreference = ctxWrapper.getSharedPreferences(INFO_ID, Context.MODE_PRIVATE);
    }

    // set메소드

    public boolean setIntValue(String prefKey, int value) {
        if (mPreference != null) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.putInt(prefKey, value);
            return editor.commit();
        } else {
            return false;
        }

    }

    public boolean setStringValue(String prefKey, String value) {
        if (mPreference != null) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.putString(prefKey, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public boolean setLongValue(String prefKey, long value) {
        if (mPreference != null) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.putLong(prefKey, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public boolean setBooleanValue(String prefKey, boolean value) {
        if (mPreference != null) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.putBoolean(prefKey, value);
            return editor.commit();
        } else {
            return false;
        }
    }


    // get메소드

    public int getIntValue(String prefKey, int value) {
        if (mPreference != null) {
            return mPreference.getInt(prefKey, value);
        }
        return value;
    }

    public long getLongValue(String prefKey, long value) {
        if (mPreference != null) {
            return mPreference.getLong(prefKey, value);
        }
        return value;
    }

    public String getStringValue(String prefKey, String value) {
        if (mPreference != null) {
            return mPreference.getString(prefKey, value);
        }
        return value;
    }

    public boolean getBooleanValue(String prefKey, boolean value) {
        if (mPreference != null) {
            return mPreference.getBoolean(prefKey, value);
        }
        return value;
    }


    public boolean removeValue(String key) {
        if (mPreference != null) {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.remove(key);
            return editor.commit();
        }
        return false;
    }

}