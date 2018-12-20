package com.example.daffaalam.multimedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

public class EmailActivity extends Functions implements View.OnClickListener {

    EditText etAddressEmail, etSubjectEmail, etMessageEmail;
    String sAddressEmail, sSubjectEmail, sMessageEmail;
    Button bResetEmail, bSendEmail;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        etAddressEmail = findViewById(R.id.etAddressEmail);
        etSubjectEmail = findViewById(R.id.etSubjectEmail);
        etMessageEmail = findViewById(R.id.etMessageEmail);
        bResetEmail = findViewById(R.id.bResetEmail);
        bSendEmail = findViewById(R.id.bSendEmail);
        bResetEmail.setOnClickListener(this);
        bSendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bResetEmail:
                etAddressEmail.setText("");
                etSubjectEmail.setText("");
                etMessageEmail.setText("");
                break;
            case R.id.bSendEmail:
                sAddressEmail = etAddressEmail.getText().toString();
                sSubjectEmail = etSubjectEmail.getText().toString();
                sMessageEmail = etMessageEmail.getText().toString();
                if (sAddressEmail.isEmpty()) {
                    etAddressEmail.requestFocus();
                    etAddressEmail.setError("cannot be empty");
                } else if (sSubjectEmail.isEmpty()) {
                    etSubjectEmail.requestFocus();
                    etSubjectEmail.setError("cannot be empty");
                } else if (sMessageEmail.isEmpty()) {
                    etMessageEmail.requestFocus();
                    etMessageEmail.setError("cannot be empty");
                } else {
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{sAddressEmail});
                    intent.putExtra(Intent.EXTRA_SUBJECT, sSubjectEmail);
                    intent.putExtra(Intent.EXTRA_TEXT, sMessageEmail);
                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Choose Email Client"));
                }
                break;
        }
    }
}
