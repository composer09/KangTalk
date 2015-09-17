package kr.co.composer.kangtalk.chat;

/**
 * Created by composer10 on 2015. 9. 3..
 */
public class ChatMessage {
    private long id;
    private boolean isMe;
    private String message;
    private String userId;
    private String dateTime;

    //setter

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

    //getter
    public String getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }
    public boolean getIsme() {
        return isMe;
    }
    public String getMessage() {
        return message;
    }


    public String getDate() {
        return dateTime;
    }


}
