package com.example.daffaalam.multimedia;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.daffaalam.multimedia.tool.Functions;

public class MainActivity extends Functions implements AdapterView.OnItemClickListener {

    ListView lvMain;
    String listMainClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = findViewById(R.id.lvMain);
        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            listMainClass = "com.example.daffaalam.multimedia.activity." + getResources().getStringArray(R.array.main_list_activity)[position];
            myIntent(Class.forName(listMainClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
