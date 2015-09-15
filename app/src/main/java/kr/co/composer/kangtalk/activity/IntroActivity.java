package kr.co.composer.kangtalk.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.login.LoginBO;
import kr.co.composer.kangtalk.ui.progress.CustomLoadingProgress;
import kr.co.composer.kangtalk.volley_test.VolleyTest;

public class IntroActivity extends BaseActivity {
    private FragmentManager fManager;
    private LoginBO loginBO;
    private LoginApplication loginApplication;
    private View introView;
    private CustomLoadingProgress customLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        actList.add(this);
        introView = findViewById(R.id.intro_view);

        customLoadingProgress = new CustomLoadingProgress(this);
        loginApplication = new LoginApplication();
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
        if (loginApplication.hasRememberMeCookie() == true) {
            customLoadingProgress.show();
            login();
        } else {
            redirectAndFinish(IntroActivity.this, LoginActivity.class);
        }
    }

    private void login(){
        // 자동로그인쿠키 갱신과 유저정보 얻어오기 구현
        new VolleyTest(IntroActivity.this,customLoadingProgress).autoLogin();
    }

    @Override
    public void redirectAndFinish(final Context context,
                                  final Class redirectActivity) {
        introView.setVisibility(View.GONE);
        fadeOut(introView, new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                IntroActivity.super
                        .redirectAndFinish(context, redirectActivity);
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
