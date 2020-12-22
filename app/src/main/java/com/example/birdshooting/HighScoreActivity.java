package com.example.birdshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
    private ListView listView;
    private String[] listScore ={"30","25","20","15","10","5"} ;
    private String[] listName = {"Alex", "Bob", "Dan","Cindy", "Alice","Sam"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        listView = findViewById(R.id.listHighScore);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listScore);
        listView.setAdapter(adapter);

    }
}