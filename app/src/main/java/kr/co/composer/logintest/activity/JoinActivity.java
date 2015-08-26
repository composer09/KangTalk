package kr.co.composer.logintest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        createJoinForm();
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
    }

    private JoinForm createJoinForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setLoginId(FormUtil.getEditTextString(
                JoinActivity.this, R.id.joinFormEmail));
        joinForm.setPassword(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormpassword));
        joinForm.setPasswordConfirm(FormUtil.getEditTextString(JoinActivity.this, R.id.joinFormPasswordConfirm));
        return joinForm;
    }
}