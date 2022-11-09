package com.example.appbanhang.Activity.TienIch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanhang.R;

import java.util.ArrayList;

public class ContentProvider extends AppCompatActivity {
    private Button btn_Show_Contact,btn_CallLog,CONTENT_PROVIDER,btn_getDSSV;
    private ListView lv_ShowContact;
    private ArrayList<String> list  = new ArrayList<String>();
    Toolbar toolbar;

    private static final int PERMISSION_REQUEST_READ_CONTACTS =1;
    private static final int PERMISSION_REQUEST_READ_CALL_LOG =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        AnhXa();
        ActionToolbar();


        //==============================//=========================//===================//==========================//
        btn_Show_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS)
                            == PackageManager.PERMISSION_DENIED){
                        // CHUA CAP QUYEN`
                        ActivityCompat.requestPermissions(ContentProvider.this,
                                new String []{Manifest.permission.READ_CONTACTS},
                                PERMISSION_REQUEST_READ_CONTACTS
                        );
                    }else {
                        // CAP QUYEN`
                        Load_Data();
                    }

                }catch (Exception e){
                    Toast.makeText(ContentProvider.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("TEST", e.getMessage());
                }

            }
        });
        btn_CallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CALL_LOG)
                            == PackageManager.PERMISSION_DENIED){
                        // CHUA CAP QUYEN`
                        ActivityCompat.requestPermissions(ContentProvider.this,
                                new String []{Manifest.permission.READ_CALL_LOG},
                                PERMISSION_REQUEST_READ_CALL_LOG
                        );
                    }else {
                        // CAP QUYEN`
                        Load_CallLog();
                    }

                }catch (Exception e){
                    Toast.makeText(ContentProvider.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("TEST", e.getMessage());
                }
            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        btn_Show_Contact = findViewById(R.id.btnShow_Contact);
        lv_ShowContact = findViewById(R.id.listview_ShowContact);
        btn_CallLog = findViewById(R.id.btnShow_CallLog);
        toolbar = findViewById(R.id.toobar_Content);

    }

    public void Load_Data(){
        Uri uri = Uri.parse("content://contacts/people");
//                    CursorLoader loader = new CursorLoader(getBaseContext(),
//                            uri,
//                            null,
//                            null,
//                            null,
//                            null);
//                    Cursor cursor = loader.loadInBackground();

        Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            String ID_col = ContactsContract.Contacts._ID;
            String NAME_col = ContactsContract.Contacts.DISPLAY_NAME;

            int idxID = cursor.getColumnIndex(ID_col);// idxID - idxNAME
            int id = cursor.getInt(idxID);

            int idxNAME = cursor.getColumnIndex(NAME_col);// idxID - idxNAME
            String name = cursor.getString(idxNAME);

            list.add(id + " - " + name);
            cursor.moveToNext();

        }
        cursor.close();
        ArrayAdapter adapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_list_item_1, list);
        lv_ShowContact.setAdapter(adapter);
    }// LOAD_DATA();

    public void Load_CallLog(){
        Uri uri = Uri.parse("content://contacts/people");
//                    CursorLoader loader = new CursorLoader(getBaseContext(),
//                            uri,
//                            null,
//                            null,
//                            null,
//                            null);
//                    Cursor cursor = loader.loadInBackground();

        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){

            String ID_col = CallLog.Calls._ID;
            String NAME_col = CallLog.Calls.NUMBER;
            String TIME_col = CallLog.Calls.DURATION;// DURATION -- TIME
            String TYPE_col = CallLog.Calls.TYPE;// TYPE -- TIME



            int idxID = cursor.getColumnIndex(ID_col);// idxID - idxNAME
            int id = cursor.getInt(idxID);

            int idxNAME = cursor.getColumnIndex(NAME_col);// idxID - idxNAME
            String name = cursor.getString(idxNAME);

            int idxTIME = cursor.getColumnIndex(TIME_col);// idxNAME - idxTIME
            String time = cursor.getString(idxTIME);

            int idxTYPE = cursor.getColumnIndex(TYPE_col);// idxTIME - idxTYPE
            String type = cursor.getString(idxTYPE);

            list.add(id + " - " + name + " - "+ time + " - " + type);
            cursor.moveToNext();

        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_list_item_1, list);
        lv_ShowContact.setAdapter(adapter);
    }// Load_CallLog();

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode ){
            case PERMISSION_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    Load_Data();
                }else {
                    Toast.makeText(this, "Chua cap quyen", Toast.LENGTH_SHORT).show();
                }
                break;

            case PERMISSION_REQUEST_READ_CALL_LOG:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    Load_CallLog();
                }else{
                    Toast.makeText(this, "chua cap quyen", Toast.LENGTH_SHORT).show();
                }
                break;


        }//SWICH

    }
}