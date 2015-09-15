package kr.co.composer.kangtalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.ui.progress.CustomLoadingProgress;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.utils.FormUtil;
import kr.co.composer.kangtalk.volley_test.VolleyTest;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivity extends BaseActivity {
    CustomLoadingProgress customLoadingProgress;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();
    }

    private void init(){
        customLoadingProgress = new CustomLoadingProgress(LoginActivity.this);
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
                redirectActivity(LoginActivity.this, JoinActivityBak.class);
            }
        });
    }
    private void doLogin() {
        JoinForm joinForm = createLoginForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0 ){
            Toast.makeText(this, "아이디와 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            customLoadingProgress.show();
            new VolleyTest(this, customLoadingProgress).login(joinForm);
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