package com.example.birdshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
    private ListView listView;
    String[] listScore = new String[6];
    private String[] listName = {"Alex", "Bob", "Dan","Cindy", "Alice","Sam"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        listView = findViewById(R.id.listHighScore);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("score", Context.MODE_PRIVATE);
       for (int i =0; i<6;i++){
           String score = preferences.getString("score"+i, "0");
           listScore[i] = score;
       }
        for (int i =0;i<6;i++){
            Log.d("TAG", listScore[i]+" hscore");
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listScore);
        listView.setAdapter(adapter);

    }
    public String[] update(String[] list){
        this.listScore = list;
        return  listScore;
    }
}