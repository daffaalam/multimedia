package com.example.daffaalam.multimedia.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

public class CallActivity extends Functions implements View.OnClickListener {

    EditText etCall;
    Button bContactCall, bCallCall;
    String sNumber;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        etCall = findViewById(R.id.etCall);
        bContactCall = findViewById(R.id.bContactCall);
        bCallCall = findViewById(R.id.bCallCall);
        bContactCall.setOnClickListener(this);
        bCallCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bContactCall:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE), 1);
                break;
            case R.id.bCallCall:
                sNumber = etCall.getText().toString().trim();
                if (sNumber.isEmpty()) {
                    etCall.setError("cannot be empty");
                    etCall.requestFocus();
                } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 2);
                    } else {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sNumber)));
                    }
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
                etCall.setText(cursor.getString(0));
            }
        }
    }
}
