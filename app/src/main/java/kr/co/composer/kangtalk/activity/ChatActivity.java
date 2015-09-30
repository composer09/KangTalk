package kr.co.composer.kangtalk.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.adapter.ChatAdapter;
import kr.co.composer.kangtalk.chat.ChatMessage;
import kr.co.composer.kangtalk.chat.ChatNotification;
import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.properties.PreferenceProperties;

/**
 * Created by composer10 on 2015. 9. 3..
 */
public class ChatActivity extends BaseActivity {

    private static final String CURRENT_ACTIVITY = "kr.co.composer.kangtalk.activity.ChatActivity";
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private TextView yourName;
    //////////////
    private Socket socket;
    private String userID;
    private ChatNotification chatNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // 액티비티 일괄제거용 미리등록
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        init();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... chatMessage) {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) chatMessage[0];
                        if (!CURRENT_ACTIVITY.equals(getRunActivity())) {
                            chatNotification.setNoti(data.getString("userId")
                                    , data.getString("message"));
                            Log.i("현재실행여부확인", getRunActivity());
                        }
                        Log.i("input 데이터확인", chatMessage[0] + "");
                        yourName.setText(data.getString("userId"));
                        ChatMessage message = new ChatMessage();
                        message.setUserId(data.getString("userId"));
                        message.setMe(false);
                        message.setMessage(data.getString("message"));
                        message.setDate(data.getString("dateTime"));
                        displayMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    String message;

//                        username = data.getString("username");
//                        message = data.getString("message");


                    // add the message to view
//                    addMessage(username, message);
                }
            });
        }
    };

    private void init() {
        getSupportActionBar().setTitle(R.string.chat_activity_title);
        chatNotification = new ChatNotification(this);
        userID = UserPreferenceManager.getInstance().getUserId();
        try {
            socket = IO.socket(PreferenceProperties.IP_ADDRESS_SOCKET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.on("serverMessage", onNewMessage);
        //////////////////////////////
        initHandler();
        Log.i("로그인 유저 확인", UserPreferenceManager.getInstance().getUserId());

    }

    private void initHandler() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        yourName = (TextView) findViewById(R.id.your_name);
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setUserId(userID);
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                try {
                    jsonObject.put("userId", chatMessage.getUserId());
                    jsonObject.put("isMe", chatMessage.getIsme());
                    jsonObject.put("message", chatMessage.getMessage());
                    jsonObject.put("dateTime", chatMessage.getDate());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("clientMessage", jsonObject);
                messageET.setText("");

                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }


    @Override
    protected void onResume() {
        chatNotification.cancel();
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chat_config:
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    String getRunActivity() {
        ActivityManager activity_manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task_info = activity_manager.getRunningTasks(9999);
        return task_info.get(0).topActivity.getClassName();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
