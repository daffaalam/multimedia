package com.example.daffaalam.multimedia.tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Functions extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void myIntent(Class destination) {
        startActivity(new Intent(Functions.this, destination));
    }

    public void myToast(String message) {
        Toast.makeText(Functions.this, message, Toast.LENGTH_SHORT).show();
    }
}
