package kr.co.composer.kangtalk.application;

import android.app.Activity;
import android.app.Application;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.properties.PreferenceProperties;
import kr.co.composer.kangtalk.utils.StringUtil;

public class LoginApplication extends Application {
    public static ContextWrapper contextWrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        contextWrapper = this;
        UserPreferenceManager.getInstance().initPreferenceData(contextWrapper);
    }


//    //setter

    public void setRememberMeCookie(String rememberMeCookieId) {
        if (StringUtil.isNotEmpty(rememberMeCookieId) == true) {
            SharedPreferences settings = contextWrapper.getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
            SharedPreferences.Editor ed = settings.edit();
            ed.putString(PreferenceProperties.REMEMBER_ME, rememberMeCookieId);
            ed.commit();
        }
    }

//    public void setSessionCookie(String sessionCookie) {
//        if (StringUtil.isNotEmpty(sessionCookie) == true) {
//            SharedPreferences settings = contextWrapper.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
//            SharedPreferences.Editor ed = settings.edit();
//            ed.putString(PreferenceProperties.SESSION_ID, sessionCookie);
//            ed.commit();
//        }
//    }
//
//    //getter
//
//
    public String getRememberMeCookie() {
        SharedPreferences settings = contextWrapper.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
        return settings.getString(PreferenceProperties.REMEMBER_ME, null);
    }
//
    public String getSessionCookie() {
        SharedPreferences settings = contextWrapper.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
        return settings.getString(PreferenceProperties.SESSION_ID, null);
    }


    public boolean hasRememberMeCookie() {
        String rememberMeCookie = getRememberMeCookie();
        return StringUtil.isNotEmpty(rememberMeCookie);

    }

    //clear
    public void clearCookie() {
        SharedPreferences settings = contextWrapper.getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = settings.edit();
        ed.remove(PreferenceProperties.REMEMBER_ME);
//        ed.remove(PreferenceProperties.SESSION_ID);
        ed.commit();
    }

}