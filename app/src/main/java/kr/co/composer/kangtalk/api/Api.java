package kr.co.composer.kangtalk.api;

import android.app.Activity;

import com.android.volley.Response.*;

import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.properties.PreferenceProperties;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;

/**
 * Created by composer10 on 2015. 9. 30..
 */
public interface Api {
    final static String IP_ADDRESS = PreferenceProperties.IP_ADDRESS_SERVER;

    public void setParameters(Activity activity, CustomLoading dialog, JoinForm joinForm);

    public void login();

    public Listener successListener();

    public ErrorListener errorListener();
}
