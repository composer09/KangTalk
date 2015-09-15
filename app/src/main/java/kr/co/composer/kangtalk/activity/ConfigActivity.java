package kr.co.composer.kangtalk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.adapter.ConfigAdapter;
import kr.co.composer.kangtalk.application.LoginApplication;
import kr.co.composer.kangtalk.bo.login.LoginBO;
import kr.co.composer.kangtalk.config.Config;
import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.ui.progress.CustomLoadingProgress;

public class ConfigActivity extends BaseActivity {
    private static final String POSITION0_ALERT_TITLE = "로그아웃";
    LoginApplication loginApplication;
    ArrayList<Config> configList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        init();

    }

    private ArrayList<Config> listViewGo() {
        configList = new ArrayList<Config>();
        Config config = new Config();
        config.setTitle("로그아웃");
        config.setText("자동로그인이 해제됩니다.");
        configList.add(config);
        return configList;
    }


    private void init() {
        loginApplication = new LoginApplication();
        ListView listView = (ListView) findViewById(R.id.config_listview);
        ConfigAdapter configAdapter = new ConfigAdapter(this, listViewGo());
        listView.setAdapter(configAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        new AlertDialog.Builder(ConfigActivity.this)
                                .setTitle(POSITION0_ALERT_TITLE)
                                .setMessage(R.string.logout_message)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UserPreferenceManager.getInstance().clear();
                                        loginApplication.clearCookie();
                                        Toast.makeText(ConfigActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
                                        //기존 쌓였던 Activity 삭제
                                        for(int i = 0; i < actList.size(); i++)
                                            actList.get(i).finish();
                                        Intent intent = new Intent(getBaseContext(), JoinActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade, R.anim.hold);
                                        finish();
                                    }
                                })
                                .setNeutralButton("최소", null)
                                .create().show();
                        break;
                }
            }
        });
        //액션바 홈버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ConfigActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
