package kr.co.composer.kangtalk.bo.join;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.bo.login.LoginBO;

/**
 * Created by composer10 on 2015. 8. 28..
 */
public class SendJoinApi extends AsyncTask<JoinForm, Void, JSONObject> {
    Activity activity;
    Dialog dialog;
    String response;
    JSONObject responseJSON;
    HttpURLConnection httpURLConn = null;
    LoginApplication loginApplication = null;
    LoginBO loginBO = null;

    String ip = "1.241.246.58";
    //    private static final String URL = "http://200.5.40.210:50000/joinData";
    private String URL = "http://" + ip + ":50000/joinData";



    public SendJoinApi(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
        loginApplication = new LoginApplication();
        loginBO = new LoginBO();
    }

    @Override
    protected JSONObject doInBackground(JoinForm... joinForm) {
        URL url = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            url = new URL(URL);
            httpURLConn = (HttpURLConnection) url.openConnection();
            httpURLConn.setRequestMethod("POST");
            httpURLConn.setConnectTimeout(3 * 1000);
            httpURLConn.setReadTimeout(3 * 1000);
            httpURLConn.setRequestProperty("Cache-Control", "no-cache");
            httpURLConn.setRequestProperty("Content-Type", "application/json");
            httpURLConn.setRequestProperty("Accept", "application/json");
            httpURLConn.setDoOutput(true);
            httpURLConn.setDoInput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", joinForm[0].getUserId());
            jsonObject.put("password", joinForm[0].getPassword());

            outputStream = httpURLConn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            if (httpURLConn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                inputStream = httpURLConn.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    byteArrayOutputStream.write(byteBuffer, 0, nLength);
                }
                inputStream.close();
                byteData = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                response = new String(byteData);

                JSONObject responseJSON = new JSONObject(response);

                return responseJSON;
            } else {
                //응답실패
                try {
                    responseJSON = new JSONObject();
                    responseJSON.put("res_mssage", "응답실패");
                    responseJSON.put("result", false);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                return responseJSON;
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                responseJSON = new JSONObject();
                responseJSON.put("res_message", e.toString());
                responseJSON.put("result", false);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return responseJSON;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                responseJSON = new JSONObject();
                responseJSON.put("res_message", e.toString());
                responseJSON.put("result", false);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return responseJSON;
        }

    }


    @Override
    protected void onPostExecute(JSONObject responseJSON) {
        try {
            if ((boolean) responseJSON.get("result")) {
                Toast.makeText(activity, (String) responseJSON.get("res_message"), Toast.LENGTH_SHORT).show();

//                List<String> cookies = httpURLConn.getHeaderFields().get("set-cookie");
//                if (cookies != null) {
//                    for (String cookie : cookies) {
//                        Log.d("쿠키확인", cookie.split(";")[0]);
//                    }
//                }
//                loginBO.extractCookie(httpURLConn);

            Intent intent = new Intent(activity, ChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
            activity.finish();
            } else {
                Toast.makeText(activity, (String) responseJSON.get("res_message"), Toast.LENGTH_SHORT).show();
//                List<String> cookies = httpURLConn.getHeaderFields().get("set-cookie");
//                if (cookies != null) {
//                    for (String cookie : cookies) {
//                        Log.d("쿠키확인", cookie.split(";")[0]);
//                    }
//                }
//                loginBO.extractCookie(httpURLConn);
//                Log.i("프리퍼런스에 저장", loginApplication.getRememberMeCookie());
            }
            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
