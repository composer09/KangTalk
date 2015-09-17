package kr.co.composer.kangtalk.volley_test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.bo.login.LoginBO;
import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.properties.PreferenceProperties;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;

import static com.android.volley.Request.Method.*;

/**
 * Created by composer10 on 2015. 8. 28..
 */
public class VolleyTest {
    final static String IP_ADDRESS = PreferenceProperties.IP_ADDRESS_SERVER;
    String cookie;
    //
    Activity activity;
    CustomLoading dialog;
    LoginApplication loginApplication = null;

    public VolleyTest(Activity activity, CustomLoading dialog) {
        this.activity = activity;
        this.dialog = dialog;
        loginApplication = new LoginApplication();
    }

    public void autoLogin() {
        String url = IP_ADDRESS+"/auto_login";
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        StringRequest request = new StringRequest(Method.POST, url, autoSuccessListener()
                , autoErrorListener()) {

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
                    cookie = response.headers.get("Set-Cookie");
                    loginApplication.setRememberMeCookie(splitCookie(cookie));
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    public void login(JoinForm joinForm) {
        String url = IP_ADDRESS+"/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", joinForm.getUserId());
            jsonObject.put("password", joinForm.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, jsonObject, joinSuccessListener(), reqErrorListener()) {

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

    public void join(JoinForm joinForm) {
        String url = IP_ADDRESS+"/join";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", joinForm.getUserId());
            jsonObject.put("password", joinForm.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, jsonObject, joinSuccessListener(), reqErrorListener()) {
            //            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> hash = new HashMap<String, String>();
//                return hash;
//            }
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

    //auto_login successListener
    private Listener<String> autoSuccessListener() {
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


    //join successListener
    private Listener<JSONObject> joinSuccessListener() {
        return new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        if ((boolean) response.get("result")) {
                            String rememberCookie = splitCookie(cookie);
                            loginApplication.setRememberMeCookie(rememberCookie);
                            UserPreferenceManager.getInstance().setRemoteUserInfo(response);
                            Toast.makeText(activity, (String) response.get("res_message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, (String) response.get("res_message"), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private ErrorListener autoErrorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("autoLoginErrorMessage", error.toString());
                activity.finish();
            }
        };
    }


    private ErrorListener reqErrorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("requestErrorMessage", error.toString());
            }
        };
    }

    private String splitCookie(String cookie) {
        if (cookie != null && cookie.length() > 0) {
            String[] splitCookie = cookie.split(";");
            String[] splitSessionId = splitCookie[0].split("=");
            cookie = splitSessionId[1];
            Log.i("쿠키값확인", cookie);
        }
        return cookie;
    }
}