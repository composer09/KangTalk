package kr.co.composer.logintest.application;

import android.app.Activity;
import android.app.Application;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import kr.co.composer.logintest.properties.PreferenceProperties;
import kr.co.composer.logintest.utils.StringUtil;

public class LoginApplication extends Application {
    public static ContextWrapper contextWrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        contextWrapper = this;
//        ConfigPreferenceManager.getInstance().initPreferenceData(contextWrapper);
    }


//    //setter
//
//    public void setRememberMeCookie(String rememberMeCookieId) {
//        if (StringUtil.isNotEmpty(rememberMeCookieId) == true) {
//            SharedPreferences settings = getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
//            SharedPreferences.Editor ed = settings.edit();
//            ed.putString(PreferenceProperties.REMEMBER_ME, rememberMeCookieId);
//            ed.commit();
//        }
//    }
//
//    public void setSessionCookie(String sessionCookie) {
//        if (StringUtil.isNotEmpty(sessionCookie) == true) {
//            SharedPreferences settings = getSharedPreferences(PreferenceProperties.REMEMBER_ME, Activity.MODE_PRIVATE);
//            SharedPreferences.Editor ed = settings.edit();
//            ed.putString(PreferenceProperties.SESSION_ID, sessionCookie);
//            ed.commit();
//        }
//    }
//
//    //getter
//
//    public String getSessionCookie() {
//        SharedPreferences settings = getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
//        return settings.getString(PreferenceProperties.SESSION_ID, null);
//    }
//
//    public String getRememberMeCookie() {
//        SharedPreferences settings = getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
//        return settings.getString(PreferenceProperties.REMEMBER_ME, null);
//    }
//
//    //clear
//    public void clearCookie() {
//        SharedPreferences settings = getSharedPreferences(PreferenceProperties.REMEMBER_ME , Activity.MODE_PRIVATE);
//        SharedPreferences.Editor ed = settings.edit();
//        ed.remove(PreferenceProperties.REMEMBER_ME);
//        ed.remove(PreferenceProperties.SESSION_ID);
//        ed.commit();
//    }

}