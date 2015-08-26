package kr.co.composer.logintest.bo.login;

import android.content.Context;

import kr.co.composer.logintest.application.LoginApplication;
import kr.co.composer.logintest.utils.CookieUtil;
import kr.co.composer.logintest.utils.StringUtil;

/**
 * Created by composer10 on 2015. 8. 26..
 */
public class LoginBO {
    private CookieUtil cookieUtil;

    public LoginBO(Context context) {
        cookieUtil = new CookieUtil(context);
    }


    public boolean hasRememberMeCookie() {
        String rememberMeCookie = cookieUtil.getRememberMeCookie();
        return StringUtil.isNotEmpty(rememberMeCookie);

    }
}
