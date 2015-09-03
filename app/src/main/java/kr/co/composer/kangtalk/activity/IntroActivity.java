package kr.co.composer.kangtalk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.bo.login.LoginBO;

public class IntroActivity extends AppCompatActivity {
    private FragmentManager fManager;
    private LoginBO loginBO;
    private View introView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introView = findViewById(R.id.intro_view);

        loginBO = new LoginBO(this);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                loginTask();
            }
        }.execute(new Void[0]);




//        fManager = getSupportFragmentManager();
//        FragmentTransaction fTransaction = fManager.beginTransaction();
//        if(fTransaction.isEmpty()){
//            fTransaction.add(R.id.container, new StartFragment()).addToBackStack(null).commit();
//        }
    }

    private void loginTask() {
        if (loginBO.hasRememberMeCookie() == true) {
//            loadingbigfoot.show();
//            login();
        } else {
            redirectAndFinish(this);
//            Intent intent = new Intent(this, IntroActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade, R.anim.hold);
//            finish();
        }
    }


    public void redirectAndFinish(final Context context) {
        introView.setVisibility(View.GONE);
        fadeOut(introView, new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(context, JoinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
//                IntroActivity.super
//                        .redirectAndFinish(context, redirectActivity);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
    }


    private void fadeOut(View view, Animation.AnimationListener animationListener) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setDuration(500);

        if (animationListener != null) {
            fadeOut.setAnimationListener(animationListener);
        }

        view.startAnimation(fadeOut);
    }

}
