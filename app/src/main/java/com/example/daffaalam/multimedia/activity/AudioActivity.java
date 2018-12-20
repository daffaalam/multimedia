package com.example.daffaalam.multimedia.activity;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

public class AudioActivity extends Functions implements AdapterView.OnItemClickListener {

    ListView lvAudio;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        lvAudio = findViewById(R.id.lvAudio);
        lvAudio.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (position) {
            case 0:
                if (Build.VERSION.SDK_INT >= 23) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_UNMUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_DTMF, AudioManager.ADJUST_UNMUTE, 0);
                } else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                    myToast("Already Normal Mode");
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    myToast("Set To Normal Mode");
                }
                break;
            case 1:
                if (Build.VERSION.SDK_INT >= 23) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_MUTE, 0);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_DTMF, AudioManager.ADJUST_MUTE, 0);
                } else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                    myToast("Already Silent Mode");
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    myToast("Set To Silent Mode");
                }
                break;
            case 2:
                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                    myToast("Already Vibrate Mode");
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    myToast("Set To Vibrate Mode");
                }
                break;
        }
    }
}
