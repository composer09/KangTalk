package kr.co.composer.kangtalk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.bo.join.SendJoinApi;
import kr.co.composer.kangtalk.ui.progress.CustomLoadingProgress;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.utils.FormUtil;
import kr.co.composer.kangtalk.volley_test.VolleyTest;

/**
 * A placeholder fragment containing a simple view.
 */
public class JoinActivity extends AppCompatActivity {
    CustomLoadingProgress customLoadingProgress;

    public JoinActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        customLoadingProgress = new CustomLoadingProgress(JoinActivity.this);
        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        VolleyTest volleyTest = new VolleyTest(JoinActivity.this,customLoadingProgress);
//                        volleyTest.join(dojoinV2());
//                        dojoin();
                        dojoinV2();
                    }
                }
        );
    }

    private void dojoinV2() {
        JoinForm joinForm = createJoinForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0
                || joinForm.getPasswordConfirm().length() == 0) {
            Toast.makeText(JoinActivity.this, "모든정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
                Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                customLoadingProgress.show();
                new VolleyTest(JoinActivity.this,customLoadingProgress).join(joinForm);
            }
        }
    }

    private void dojoin() {
        JoinForm joinForm = createJoinForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0
                || joinForm.getPasswordConfirm().length() == 0) {
            Toast.makeText(JoinActivity.this, "모든정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
                Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                customLoadingProgress.show();
                new SendJoinApi(this, customLoadingProgress)
                        .execute(joinForm);
            }
        }
    }

    private JoinForm createJoinForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setLoginId(FormUtil.getEditTextString(
                JoinActivity.this, R.id.joinFormEmail));
        joinForm.setPassword(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormpassword));
        joinForm.setPasswordConfirm(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormPasswordConfirm));
        return joinForm;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}