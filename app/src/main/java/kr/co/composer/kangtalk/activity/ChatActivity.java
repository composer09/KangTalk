package kr.co.composer.kangtalk.activity;

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

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.adapter.ChatAdapter;
import kr.co.composer.kangtalk.chat.ChatMessage;
import kr.co.composer.kangtalk.pref.UserPreferenceManager;
import kr.co.composer.kangtalk.properties.PreferenceProperties;

/**
 * Created by composer10 on 2015. 9. 3..
 */
public class ChatActivity extends BaseActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private TextView yourName;
    //////////////
    private Socket mSocket;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // 액티비티 일괄제거용 미리등록
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        userID = UserPreferenceManager.getInstance().getUserId();
        try {
            mSocket = IO.socket(PreferenceProperties.IP_ADDRESS_SOCKET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        mSocket.on("serverMessage", onNewMessage);
        //////////////////////////////
        init();
        Log.i("로그인 유저 확인", UserPreferenceManager.getInstance().getUserId());
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... chatMessage) {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i("통신 데이터확인", chatMessage[0] + "");
                        JSONObject data = (JSONObject) chatMessage[0];
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
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        yourName = (TextView) findViewById(R.id.your_name);
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

//        loadDummyHistory();

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
                mSocket.emit("clientMessage", jsonObject);
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

    private void loadDummyHistory() {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);



        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }

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
}
