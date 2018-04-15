package com.example.sruthikatakam.faceemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final DBHandler db = new DBHandler(this);

        LinearLayout l1 = (LinearLayout) findViewById(R.id.linearsample);

        Log.d("Reading", "All Records from databse .....");
        List<Image1> images = db.getAllemotions();

        for (Image1 image2 : images) {
            String log = "Id:" + image2.get_id() + "Date:" + image2.get_date() + "Emotion stores" + image2.get_emotion();
            TextView tv = new TextView(this);
            tv.setText(log);
            l1.addView(tv);

            Log.d("Image::", log);
        }
    }
}



