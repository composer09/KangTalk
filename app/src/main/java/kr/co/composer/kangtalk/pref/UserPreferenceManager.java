package kr.co.composer.kangtalk.pref;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by composer on 2015-09-09.
 */
public class UserPreferenceManager extends AbstractPreferenceManager{
    private static UserPreferenceManager mSelfInstance = null;

    public static final String USER_ID = "userId";

    public static synchronized UserPreferenceManager getInstance() {
        if (mSelfInstance == null) {
            mSelfInstance = new UserPreferenceManager();
        }
        return mSelfInstance;
    }

    public void setRemoteUserInfo(JSONObject loginUser){
        try {
            setUserId(loginUser.get("loginUser").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void clear(){
        super.removeValue(USER_ID);
    }

    //setter

    public void setUserId(String loginUser) {
        setStringValue(USER_ID, loginUser);
    }


    //getter

    public String getUserId(){
        return getStringValue(USER_ID,"");
    }
}
