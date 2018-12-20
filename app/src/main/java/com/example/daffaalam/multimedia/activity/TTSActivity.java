package com.example.daffaalam.multimedia.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

import java.util.Locale;

public class TTSActivity extends Functions implements View.OnClickListener, TextToSpeech.OnInitListener {

    EditText etTTS;
    Button bTTS;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        textToSpeech = new TextToSpeech(this, this);
        etTTS = findViewById(R.id.etTTS);
        bTTS = findViewById(R.id.bTTS);
        bTTS.setOnClickListener(this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (textToSpeech.setLanguage(Locale.getDefault()) == TextToSpeech.LANG_MISSING_DATA || textToSpeech.setLanguage(Locale.getDefault()) == TextToSpeech.LANG_NOT_SUPPORTED) {
                bTTS.setEnabled(false);
                myToast("language is not support");
            } else {
                onClick(bTTS);
            }
        } else {
            bTTS.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        textToSpeech.speak(etTTS.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }
}
