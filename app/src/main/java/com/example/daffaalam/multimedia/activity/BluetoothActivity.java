package com.example.daffaalam.multimedia.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends Functions implements View.OnClickListener, Switch.OnCheckedChangeListener {

    Switch sTurnBluetooth, sViewBluetooth;
    Button bListBluetooth, bFindBluetooth;
    ListView lvListBluetooth, lvFindBluetooth;

    ArrayList<String> arrayList, arrayFind;
    ArrayAdapter arrayListAdapter, arrayFindAdapter;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    Set<BluetoothDevice> bluetoothDeviceSet;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        sTurnBluetooth = findViewById(R.id.sTurnBluetooth);
        sViewBluetooth = findViewById(R.id.sViewBluetooth);
        bListBluetooth = findViewById(R.id.bListBluetooth);
        bFindBluetooth = findViewById(R.id.bFindBluetooth);
        lvListBluetooth = findViewById(R.id.lvListBluetooth);
        lvFindBluetooth = findViewById(R.id.lvFindBluetooth);

        bListBluetooth.setOnClickListener(this);
        bFindBluetooth.setOnClickListener(this);

        sTurnBluetooth.setOnCheckedChangeListener(this);
        sViewBluetooth.setOnCheckedChangeListener(this);

        arrayList = new ArrayList<>();
        arrayFind = new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            myToast("bluetooth not support");
            onBackPressed();
        }

        if (bluetoothAdapter.isEnabled()) {
            sTurnBluetooth.setChecked(true);
        } else {
            sTurnBluetooth.setChecked(false);
        }

        if (bluetoothAdapter.isDiscovering()) {
            sViewBluetooth.setChecked(true);
        } else {
            sViewBluetooth.setChecked(false);
        }

        arrayListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        arrayFindAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayFind);

        lvListBluetooth.setAdapter(arrayListAdapter);
        lvFindBluetooth.setAdapter(arrayFindAdapter);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                    bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    arrayFind.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
                    arrayFindAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bListBluetooth:
                bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
                if (bluetoothDeviceSet.size() > 0) {
                    for (BluetoothDevice device : bluetoothDeviceSet) {
                        arrayList.add(device.getName() + "\n" + device.getAddress());
                        arrayListAdapter.notifyDataSetChanged();
                    }
                } else {
                    myToast("bluetooth must be enable");
                }
                break;
            case R.id.bFindBluetooth:
                arrayFindAdapter.clear();
                sViewBluetooth.setChecked(true);
                bFindBluetooth.setEnabled(false);
                registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                myToast("scanning for 12 seconds");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bFindBluetooth.setEnabled(true);
                        sViewBluetooth.setChecked(false);
                        unregisterReceiver(broadcastReceiver);
                    }
                }, 12 * 1000);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sTurnBluetooth:
                if (isChecked) {
                    bluetoothAdapter.enable();
                    myToast("bluetooth is enable");
                } else {
                    sViewBluetooth.setChecked(false);
                    bluetoothAdapter.disable();
                    myToast("bluetooth is disable");
                }
                break;
            case R.id.sViewBluetooth:
                if (isChecked) {
                    sTurnBluetooth.setChecked(true);
                    bluetoothAdapter.startDiscovery();
                    myToast("visible for 120 seconds");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sViewBluetooth.setChecked(false);
                        }
                    }, 120 * 1000);
                } else {
                    bluetoothAdapter.cancelDiscovery();
                    myToast("bluetooth invisible");
                }
                break;
        }
    }
}
