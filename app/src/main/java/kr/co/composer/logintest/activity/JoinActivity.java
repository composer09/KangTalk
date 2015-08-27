package kr.co.composer.logintest.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import kr.co.composer.logintest.R;
import kr.co.composer.logintest.bo.JoinForm;
import kr.co.composer.logintest.utils.FormUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class JoinActivity extends AppCompatActivity {

    public JoinActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dojoin();
                    }
                }
        );
//        createJoinForm();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.activity_join, container, false);
//        Button submitBtn = (Button) view.findViewById(R.id.submit_btn);
//        submitBtn.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dojoin();
//                    }
//                }
//        );
//        createJoinForm();
//        return view;
//    }

    private void dojoin() {
        JoinForm joinForm = createJoinForm();
        new SendHttp().execute(joinForm);
//        sendHttpWithMsg("http://200.5.41.225:50000/pushData", joinForm);
//        sendHttpWithMsg(joinForm);
    }

    private JoinForm createJoinForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setLoginId(FormUtil.getEditTextString(
                JoinActivity.this, R.id.joinFormEmail));
        joinForm.setPassword(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormpassword));
        joinForm.setPasswordConfirm(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormPasswordConfirm));
        return joinForm;
    }


    private class SendHttp extends AsyncTask<JoinForm, Void, String> {

//        String url = "http://200.5.41.225:50000/joinData";
        String url = "http://192.168.219.108:50000/joinData";

        @Override
        protected String doInBackground(JoinForm... joinForm) {

//기본적인 설정
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);
            post.setHeader("Content-type", "application/json; charset=utf-8");

//            JSONObject 생성
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("loginId", joinForm[0].getLoginId());
                jObj.put("password", joinForm[0].getPassword());

            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            try {
// JSON을 String 형변환하여 httpEntity에 넣어줍니다.
                StringEntity se;
                se = new StringEntity(jObj.toString());
                HttpEntity he = se;
                post.setEntity(he);

            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }


            try {
//httpPost 를 서버로 보내고 응답을 받습니다.
                HttpResponse response = client.execute(post);
// 받아온 응답으로부터 내용을 받아옵니다.
// 단순한 string으로 읽어와 그내용을 리턴해줍니다.
                BufferedReader bufReader =
                        new BufferedReader(new InputStreamReader(
                                response.getEntity().getContent(),
                                "utf-8"
                        )
                        );

                String line = null;
                String result = "";

                while ((line = bufReader.readLine()) != null) {
                    result += line;
                }
                return result;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return "Error" + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error" + e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(JoinActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private String sendHttpWithMsg(String url, JoinForm joinForm) {

//기본적인 설정
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 3000);
        HttpConnectionParams.setSoTimeout(params, 3000);
        post.setHeader("Content-type", "application/json; charset=utf-8");

// JSON OBject를 생성하고 데이터를 입력합니다.
//여기서 처음에 봤던 데이터가 만들어집니다.
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("ID", joinForm.getLoginId());
            jObj.put("PW", joinForm.getPassword());

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        try {
// JSON을 String 형변환하여 httpEntity에 넣어줍니다.
            StringEntity se;
            se = new StringEntity(jObj.toString());
            HttpEntity he = se;
            post.setEntity(he);

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }


        try {
//httpPost 를 서버로 보내고 응답을 받습니다.
            HttpResponse response = client.execute(post);
// 받아온 응답으로부터 내용을 받아옵니다.
// 단순한 string으로 읽어와 그내용을 리턴해줍니다.
            BufferedReader bufReader =
                    new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent(),
                            "utf-8"
                    )
                    );

            String line = null;
            String result = "";

            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            return result;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "Error" + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.toString();
        }
    }


}