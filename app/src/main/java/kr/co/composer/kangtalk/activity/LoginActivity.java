package kr.co.composer.kangtalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.api.ApiCaller;
import kr.co.composer.kangtalk.api.LoginApi;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;
import kr.co.composer.kangtalk.utils.FormUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivity extends BaseActivity {
    CustomLoading customLoading;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();
    }

    private void init(){
        actList.add(this);
        customLoading = new CustomLoading(LoginActivity.this);
        Button submitButton = (Button)findViewById(R.id.login_submit_btn);
        Button goJoinButton = (Button)findViewById(R.id.goJoinBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        goJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(LoginActivity.this, JoinActivity.class);
            }
        });
    }
    private void doLogin() {
        JoinForm joinForm = createLoginForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0 ){
            Toast.makeText(this, "아이디와 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            customLoading.show();
//            new OldApi(this, customLoading).login(joinForm);
            new ApiCaller(new LoginApi(),this,customLoading,joinForm);
        }
    }

    private JoinForm createLoginForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setLoginId(FormUtil.getEditTextString(
                this, R.id.loginFormEmail));
        joinForm.setPassword(FormUtil.getEditTextString(this, R.id.loginFormpassword));
        return joinForm;
    }

}