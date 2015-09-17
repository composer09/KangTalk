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
    //////////////
    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actList.add(this); // 액티비티 일괄제거용 미리등록
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        try {
            mSocket = IO.socket(PreferenceProperties.IP_ADDRESS_SOCKET);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        mSocket.on("serverMessage",onNewMessage);
        //////////////////////////////
        initControls();
        Log.i("로그인 유저 확인", UserPreferenceManager.getInstance().getUserId());
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
//            JSONObject data = (JSONObject) args[0];

            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("통신 데이터확인",args[0]+"");
                    show((String)args[0]);
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

    private void show(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView myName = (TextView) findViewById(R.id.my_name);
        TextView yourName = (TextView) findViewById(R.id.your_name);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        yourName.setText("상대방");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                mSocket.emit("clientMessage",messageText);

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

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

    private void loadDummyHistory(){

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

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
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
        switch(item.getItemId()){
            case R.id.chat_config:
                Intent intent = new Intent(this,ConfigActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
