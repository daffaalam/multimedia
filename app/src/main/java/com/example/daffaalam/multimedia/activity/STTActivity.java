package com.example.daffaalam.multimedia.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

import java.util.Locale;

public class STTActivity extends Functions implements View.OnClickListener {

    TextView tvSTT;
    Button bSTT;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);
        tvSTT = findViewById(R.id.tvSTT);
        bSTT = findViewById(R.id.bSTT);
        bSTT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HEI");
        try {
            startActivityForResult(intent, 23);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=com.google.android.googlequicksearchbox")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 23 && data != null) {
            tvSTT.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }
}
