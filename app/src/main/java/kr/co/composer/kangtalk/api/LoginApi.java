package kr.co.composer.kangtalk.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;
import kr.co.composer.kangtalk.utils.FormUtil;

/**
 * Created by composer10 on 2015. 9. 30..
 */
public class LoginApi implements Api {
    private String cookie;
    private Activity activity;
    private CustomLoading dialog;
    private JoinForm joinForm;
    private LoginApplication loginApplication;

    @Override
    public void setParameters(Activity activity, CustomLoading dialog, JoinForm joinForm) {
        this.activity = activity;
        this.dialog = dialog;
        this.joinForm = joinForm;
        loginApplication = new LoginApplication();
    }

    @Override
    public void login() {

        String url = IP_ADDRESS+"/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", joinForm.getUserId());
            jsonObject.put("password", joinForm.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener(), errorListener()) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.headers.get("Set-Cookie") != null) {
                    Log.i("response 헤더확인 : ", response.headers + "");
                    cookie = response.headers.get("Set-Cookie");
                }
                Log.i("response 상태코드", response.statusCode + "");
                return super.parseNetworkResponse(response);
            }

        };
        queue.add(request);
    }

    @Override
    public Listener successListener() {

        return new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        if (response.getBoolean("result")) {
                            String rememberCookie = FormUtil.splitCookie(cookie);
                            loginApplication.setRememberMeCookie(rememberCookie);
                            UserPreferenceManager.getInstance().setRemoteUserInfo(response);
                            Toast.makeText(activity, response.getString("res_message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, response.getString("res_message"), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
                Log.i("requestErrorMessage", error.toString());
            }
        };
    }
}
