package kr.co.composer.kangtalk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.bo.join.JoinForm;
import kr.co.composer.kangtalk.bo.join.SendJoinApi;
import kr.co.composer.kangtalk.ui.progress.CustomLoading;
import kr.co.composer.kangtalk.ui.progress.CustomLoadingProgress;
import kr.co.composer.kangtalk.utils.FormUtil;
import kr.co.composer.kangtalk.volley_test.VolleyTest;

/**
 * A placeholder fragment containing a simple view.
 */
public class JoinActivityBak extends BaseActivity {
    CustomLoading customLoading;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_join_bak);
        init();
    }


    private void init(){
        //액션바 홈버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        customLoading = new CustomLoading(JoinActivityBak.this);
        Button submitBtn = (Button) findViewById(R.id.join_submit_btn);
        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dojoinV2();
                    }
                }
        );
    }


    private void dojoinV2() {
        JoinForm joinForm = createJoinForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0
                || joinForm.getPasswordConfirm().length() == 0) {
            Toast.makeText(this, "모든정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                customLoading.show();
                new VolleyTest(this,customLoading).join(joinForm);
            }
        }
    }

    private JoinForm createJoinForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setLoginId(FormUtil.getEditTextString(
                this, R.id.joinFormEmail));
        joinForm.setPassword(FormUtil.getEditTextString(this, R.id.joinFormpassword));
        joinForm.setPasswordConfirm(FormUtil.getEditTextString(this, R.id.joinFormPasswordConfirm));
        return joinForm;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                JoinActivityBak.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void dojoin() {
        JoinForm joinForm = createJoinForm();
        if (joinForm.getUserId().length() == 0 || joinForm.getPassword().length() == 0
                || joinForm.getPasswordConfirm().length() == 0) {
            Toast.makeText(this, "모든정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                customLoading.show();
//                new SendJoinApi(this, customLoadingProgress)
//                        .execute(joinForm);
            }
        }
    }

}