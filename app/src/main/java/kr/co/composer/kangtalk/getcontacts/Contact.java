package kr.co.composer.kangtalk.getcontacts;

import java.lang.String; /**
 * Created by composer on 2015-09-20.
 */
public class Contact {
    private String myPhoneNumber;
    private long photoId;
    private String phoneNumber;
    private String name;

    //setter

    public void setMyPhoneNumber(String myPhoneNumber) {
        this.myPhoneNumber = myPhoneNumber;
    }

    public void setPhotoId(long phothId) {
        this.photoId = phothId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    //getter

    public String getMyPhoneNumber() {
        return myPhoneNumber;
    }

    public long getPhotoId() {
        return photoId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }
}
