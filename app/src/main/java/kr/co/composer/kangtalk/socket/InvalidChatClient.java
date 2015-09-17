//package kr.co.composer.kangtalk.socket;
//
//import android.content.Context;
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.MalformedURLException;
//
//import io.socket.IOAcknowledge;
//import io.socket.IOCallback;
//import io.socket.SocketIO;
//import io.socket.SocketIOException;
//import kr.co.composer.kangtalk.properties.PreferenceProperties;
//
///**
// * Created by composer10 on 2015. 9. 17..
// */
//public class ChatClient {
//    public static ChatClient instance;
//    private Context context;
//
//    public static ChatClient get(Context context){
//        if(instance == null){
//            instance = new ChatClient(context);
//        }
//        return instance;
//    }
//
//    public ChatClient(Context context){
//        this.context = context;
//    }
//    public void runIO(){
//        try {
//            SocketIO socket = new SocketIO(PreferenceProperties.IP_ADDRESS_SOCKET);
//            socket.connect(new IOCallback() {
//                @Override
//                public void on(String event, IOAcknowledge ack, Object... args) {
//                    if ("serverMessage".equals(event) && args.length > 0) {
//                        Log.d("SocketIO", "" + args[0]);
//
//                    }
//                }
//
//                @Override
//                public void onMessage(JSONObject json, IOAcknowledge ack) {
//                    try {
//                        System.out.println("Server said:" + json.toString(2));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onMessage(String data, IOAcknowledge ack) {
//                    System.out.println("Server said: " + data);
//                }
//
//                @Override
//                public void onError(SocketIOException socketIOException) {
//                    System.out.println("an Error occured");
//                    socketIOException.printStackTrace();
//                }
//
//                @Override
//                public void onDisconnect() {
//                    System.out.println("Connection terminated.");
//                }
//
//                @Override
//                public void onConnect() {
//                    System.out.println("Connection established");
//                }
//
//
//            });
//
//            // This line is cached until the connection is establisched.
//            socket.send("clientMessage");
////            socket.emit("clientMessage","소켓 성공이다.");
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//}
