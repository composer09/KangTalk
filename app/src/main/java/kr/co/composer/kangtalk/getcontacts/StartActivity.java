package kr.co.composer.kangtalk.getcontacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.String;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {


    private void logOn() {
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList = getContactList();
        for (int i = 0; i < contactArrayList.size(); i++) {
            Contact contact;
            contact = contactArrayList.get(i);
            Log.i("전화번호 찍어내기", contact.getName());
            Log.i("전화번호 찍어내기", contact.getPhoneNumber());
            Log.i("전화번호 찍어내기", contact.getPhotoId() + "");
        }
        Log.i("내전화 번호", getMyPhoneNumber());
    }

    private String getMyPhoneNumber() {
        TelephonyManager systemService = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        String phoneNumber = systemService.getLine1Number();
        return phoneNumber;
    }

    private ArrayList<Contact> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID, // 연락처 ID -> 사진 정보 가져오는데 사용
                ContactsContract.CommonDataKinds.Phone.NUMBER,        // 연락처
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}; // 연락처 이름.

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = managedQuery(uri, projection, null,
                selectionArgs, sortOrder);

        ArrayList<Contact> contactlist = new ArrayList<Contact>();

        if (contactCursor.moveToFirst()) {
            do {
                String phonenumber = contactCursor.getString(1).replaceAll("-",
                        "");
                if (phonenumber.length() == 10) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 6) + "-"
                            + phonenumber.substring(6);
                } else if (phonenumber.length() > 8) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 7) + "-"
                            + phonenumber.substring(7);
                }

                Contact acontact = new Contact();
                acontact.setPhotoId(contactCursor.getLong(0));
                acontact.setPhoneNumber(phonenumber);
                acontact.setName(contactCursor.getString(2));

                contactlist.add(acontact);
            } while (contactCursor.moveToNext());
        }

        return contactlist;

    }

}
