package com.example.daffaalam.multimedia.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.AlarmReceiver;
import com.example.daffaalam.multimedia.tool.Functions;

import java.util.Calendar;

public class AlarmActivity extends Functions implements View.OnClickListener {

    Button bAlarm;
    TextView tvAlarm;
    Calendar calendarA, calendarB, calendarC;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        bAlarm = findViewById(R.id.bAlarm);
        tvAlarm = findViewById(R.id.tvAlarm);
        bAlarm.setOnClickListener(this);
        tvAlarm.setText("alarm has not been set");
    }

    @Override
    public void onClick(View v) {
        calendarA = Calendar.getInstance();
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarB = Calendar.getInstance();
                calendarC = (Calendar) calendarB.clone();
                calendarC.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarC.set(Calendar.MINUTE, minute);
                calendarC.set(Calendar.SECOND, 0);
                calendarC.set(Calendar.MILLISECOND, 0);

                if (calendarC.compareTo(calendarB) <= 0) {
                    calendarC.add(Calendar.DATE, 1);
                }

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendarC.getTimeInMillis(), PendingIntent.getBroadcast(AlarmActivity.this, 1, new Intent(AlarmActivity.this, AlarmReceiver.class), 0));
                tvAlarm.setText("alarm set on " + calendarC.getTime());
            }
        };
        new TimePickerDialog(AlarmActivity.this, timeSetListener, calendarA.get(Calendar.HOUR_OF_DAY), calendarA.get(Calendar.MINUTE), true).show();
    }
}
