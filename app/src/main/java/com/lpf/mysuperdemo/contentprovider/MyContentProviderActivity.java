package com.lpf.mysuperdemo.contentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

public class MyContentProviderActivity extends Activity {

    TextView mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content_provider);

        mContacts = (TextView) this.findViewById(R.id.id_contacts);

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        String str = "";
        //读取内容
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
            String currentline = "";
            while(phoneCursor.moveToNext()){
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                currentline = "name>>>" + name + "\nnumber>>>" + number;
            }

            phoneCursor.close();
            str += currentline;
            System.out.println(currentline);
        }

        cursor.close();
//        mContacts.setText(str);
    }

}
