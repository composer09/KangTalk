package kr.co.composer.kangtalk.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;
import kr.co.composer.kangtalk.activity.JoinActivity;
import kr.co.composer.kangtalk.bo.JoinForm;

/**
 * Created by composer10 on 2015. 8. 28..
 */
public class SendJoinApi extends AsyncTask<JoinForm, Void, JSONObject> {
    Activity activity;
    Dialog dialog;
    String response;
    JSONObject responseJSON;

    String ip = "192.168.0.12";
    //    private static final String URL = "http://200.5.40.210:50000/joinData";
    private String URL = "http://"+ip+":50000/joinData";

    public SendJoinApi(Activity activity, Dialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
    }

    @Override
    protected JSONObject doInBackground(JoinForm... joinForm) {
        URL url = null;
        HttpURLConnection httpURLConn = null;
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
            Log.i("joinForm length", joinForm.length + "");
            Log.i("joinForm length", joinForm.length + "");
            Log.i("joinForm 글자수", joinForm[0].getUserId().length() + "");
            Log.i("joinForm 글자수", joinForm[0].getPassword().length() + "");
            jsonObject.put("userId", joinForm[0].getUserId());
            jsonObject.put("password", joinForm[0].getPassword());

            outputStream = httpURLConn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());

            if (httpURLConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConn.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    byteArrayOutputStream.write(byteBuffer, 0, nLength);
                }
                byteData = byteArrayOutputStream.toByteArray();

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
            Log.i("exception", "gogogogogogogogog");
            return null;
        } finally {
            if (url != null) {
                url = null;
            }

            if (httpURLConn != null) {
                httpURLConn.disconnect();

            }

            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }

            } catch (IOException e) {

            }

        }

    }


    @Override
    protected void onPostExecute(JSONObject responseJSON) {
        boolean result = false;
        String message = null;
        try {
            result = (boolean) responseJSON.get("result");
            message = (String) responseJSON.get("res_message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (result) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
            activity.finish();
            //로그인
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
            activity.finish();
        }
        dialog.dismiss();
    }
}
