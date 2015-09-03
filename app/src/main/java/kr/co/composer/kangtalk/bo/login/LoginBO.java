package kr.co.composer.kangtalk.bo.login;

import android.content.Context;

import kr.co.composer.kangtalk.utils.StringUtil;
import kr.co.composer.kangtalk.utils.CookieUtil;

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
