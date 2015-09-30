package kr.co.composer.kangtalk.api;

import android.app.Activity;

import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;

/**
 * Created by composer10 on 2015. 9. 30..
 */
public class ApiCaller {
    private Api api;

    public ApiCaller(Api api, Activity activity, CustomLoading dialog, JoinForm joinForm){
        this.api = api;
        this.api.setParameters(activity,dialog,joinForm);
        this.api.login();
    }

}
