package kr.co.composer.kangtalk.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;
import kr.co.composer.kangtalk.activity.LoginActivity;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.properties.PreferenceProperties;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;
import kr.co.composer.kangtalk.utils.FormUtil;

/**
 * Created by composer10 on 2015. 9. 30..
 */
public class AutoApi implements Api {
    private Activity activity;
    private CustomLoading dialog;
    private LoginApplication loginApplication;


    @Override
    public void setParameters(Activity activity, CustomLoading dialog, JoinForm joinForm) {
        this.activity = activity;
        this.dialog = dialog;
        loginApplication = new LoginApplication();
    }

    @Override
    public void login() {
        String url = IP_ADDRESS+"/auto_login";
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, successListener()
                , errorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put(PreferenceProperties.REMEMBER_ME, loginApplication.getRememberMeCookie());
                return hash;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers.get("Set-Cookie") != null) {
                    Log.i("오토로그인 헤더확인 : ", response.headers + "");
                    String cookie = response.headers.get("Set-Cookie");
                    loginApplication.setRememberMeCookie(FormUtil.splitCookie(cookie));
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    @Override
    public Listener successListener() {
        return new Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("저장된 쿠키", loginApplication.getRememberMeCookie());
                dialog.dismiss();
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade, R.anim.hold);
                activity.finish();
            }
        };
    }

    @Override
    public ErrorListener errorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("autoLoginErrorMessage", error.toString());
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade, R.anim.hold);
                activity.finish();
            }
        };
    }


}
