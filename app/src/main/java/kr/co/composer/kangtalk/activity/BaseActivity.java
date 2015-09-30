package kr.co.composer.kangtalk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import kr.co.composer.kangtalk.R;

/**
 * Created by composer on 2015-09-12.
 */
public class BaseActivity extends AppCompatActivity {
    public static ArrayList<Activity> actList = new ArrayList<Activity>();

    public static void removeActivity(){
        for(Activity activity: actList){
            activity.finish();
        }
    }


    public void redirectActivity(Context context, Class nextActivity) {
        Intent intent = new Intent(context, nextActivity);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    public void redirectAndFinish(final Context context,final Class redirectActivity) {
        Intent intent = new Intent(context, redirectActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);
        finish();
    }
}
