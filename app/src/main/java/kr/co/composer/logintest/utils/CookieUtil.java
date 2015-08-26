package kr.co.composer.logintest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import kr.co.composer.logintest.properties.PreferenceProperties;

/**
 * Created by composer10 on 2015. 8. 26..
 */
public class CookieUtil {
    Context context;
    public CookieUtil(Context context){
        this.context = context;
    }
    //setter

    public void setRememberMeCookie(String rememberMeCookieId) {
        if (StringUtil.isNotEmpty(rememberMeCookieId) == true) {
            SharedPreferences settings = context.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed = settings.edit();
            ed.putString(PreferenceProperties.REMEMBER_ME, rememberMeCookieId);
            ed.commit();
        }
    }

    public void setSessionCookie(String sessionCookie) {
        if (StringUtil.isNotEmpty(sessionCookie) == true) {
            SharedPreferences settings = context.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed = settings.edit();
            ed.putString(PreferenceProperties.SESSION_ID, sessionCookie);
            ed.commit();
        }
    }

    //getter

    public String getSessionCookie() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
        return settings.getString(PreferenceProperties.SESSION_ID, null);
    }

    public String getRememberMeCookie() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
        return settings.getString(PreferenceProperties.REMEMBER_ME, null);
    }

    //clear
    public void clearCookie() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = settings.edit();
        ed.remove(PreferenceProperties.REMEMBER_ME);
        ed.remove(PreferenceProperties.SESSION_ID);
        ed.commit();
    }
}
