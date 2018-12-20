package com.example.daffaalam.multimedia.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

public class WifiActivity extends Functions implements Switch.OnCheckedChangeListener {

    Switch sWifi;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE}, 111);
            }
        }

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        sWifi = findViewById(R.id.sWifi);
        sWifi.setChecked(wifiManager.isWifiEnabled());
        sWifi.setOnCheckedChangeListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults.length != 3 || grantResults[0] != 0 || grantResults[1] != 0 || grantResults[2] != 0) {
                myToast("give permission first");
                onBackPressed();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            wifiManager.setWifiEnabled(true);
            myToast("turn on wifi...");
        } else {
            wifiManager.setWifiEnabled(false);
            myToast("turn off wifi...");
        }
    }
}
