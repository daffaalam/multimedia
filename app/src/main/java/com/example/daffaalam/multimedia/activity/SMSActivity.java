package com.example.daffaalam.multimedia.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

public class SMSActivity extends Functions implements View.OnClickListener {

    EditText etNumberSMS, etMessageSMS;
    ImageView ivContactSMS;
    Button bMoreSMS, bSendSMS;
    String sNumberSMS, sMessageSMS;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        etNumberSMS = findViewById(R.id.etNumberSMS);
        etMessageSMS = findViewById(R.id.etMessageSMS);
        ivContactSMS = findViewById(R.id.ivContactSMS);
        bMoreSMS = findViewById(R.id.bMoreSMS);
        bSendSMS = findViewById(R.id.bSendSMS);
        ivContactSMS.setOnClickListener(this);
        bMoreSMS.setOnClickListener(this);
        bSendSMS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sNumberSMS = etNumberSMS.getText().toString();
        sMessageSMS = etMessageSMS.getText().toString();
        switch (v.getId()) {
            case R.id.ivContactSMS:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE), 1);
                break;
            case R.id.bMoreSMS:
                if (sNumberSMS.isEmpty()) {
                    etNumberSMS.setError("cannot be empty");
                    etNumberSMS.requestFocus();
                } else if (sMessageSMS.isEmpty()) {
                    etMessageSMS.setError("cannot be empty");
                    etMessageSMS.requestFocus();
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW).setType("vnd.android-dir/mms-sms").putExtra("address", sNumberSMS).putExtra("sms_body", sMessageSMS));
                }
                break;
            case R.id.bSendSMS:
                if (sNumberSMS.isEmpty()) {
                    etNumberSMS.setError("cannot be empty");
                    etNumberSMS.requestFocus();
                } else if (sMessageSMS.isEmpty()) {
                    etMessageSMS.setError("cannot be empty");
                    etMessageSMS.requestFocus();
                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    SmsManager.getDefault().sendTextMessage(sNumberSMS, null, sMessageSMS, null, null);
                    myToast("message sent successfully");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cursor = getContentResolver().query(data.getData(), new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                etNumberSMS.setText(cursor.getString(0));
            }
        }
    }
}
