package kr.co.composer.kangtalk.bo.login;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;

import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.properties.PreferenceProperties;
import kr.co.composer.kangtalk.utils.StringUtil;
import kr.co.composer.kangtalk.utils.CookieUtil;

/**
 * Created by composer10 on 2015. 8. 26..
 */
public class LoginBO {
    private LoginApplication loginApplication;

    public LoginBO() {
        loginApplication = new LoginApplication();
    }

    public LoginBO(Context context) {
        loginApplication = new LoginApplication();
    }

//    public JSONObject loginWithRememberCookie{
//
//    }

    public void extractCookie(HttpURLConnection connection) {
        List<String> cookies = connection.getHeaderFields().get("set-cookie");

        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith(PreferenceProperties.REMEMBER_ME)) {
                    loginApplication.setRememberMeCookie(cookie.split(";")[0]);
                }
            }
        }
        Log.i("프리퍼런스에 저장", loginApplication.getRememberMeCookie());
    }

    public boolean hasRememberMeCookie() {
        String rememberMeCookie = loginApplication.getRememberMeCookie();
        return StringUtil.isNotEmpty(rememberMeCookie);

    }
}