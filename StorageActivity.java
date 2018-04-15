package com.example.sruthikatakam.faceemo;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        final String str;
       Button save2 = (Button) findViewById(R.id.save1);
      Button previous = (Button) findViewById(R.id.previous);

        ImageView im = (ImageView) findViewById(R.id.image2);
        TextView tv = (TextView) findViewById(R.id.emodetect2);
        Log.e("bit activitty", "Stotageactivity");


        //str= getIntent().getStringExtra("emo");

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        Date today = Calendar.getInstance().getTime();

        final String reportDate = df.format(today);




        str=FaceOverlayView.Emotion;
        int right =FaceOverlayView.righteye1;
        int left =FaceOverlayView.lefteye1;

        int yaxis = FaceOverlayView.y1;
        int zaxis = FaceOverlayView.z1;

        final DatabaseReference dbcode = FirebaseDatabase.getInstance().getReference("faceemo");



        Log.e("String activitty", str);

       // String result = str + "            HAVING FOLLOWING PARAMETERS              " +"                                " +
         //       ""+ right+"%  "            + " Right-EYE_OPEN              "  + "                   "
           //     + left+"%  "               + " LEFT-EYE_OPEN               " + "                 "
             //   +  yaxis+ " YAXIS-ORIENTATION        "
               // +  zaxis+ " ZAXIS-ORIENTATION        ";

        String result = str;


        if (str.equals("EMOTION DETECTED : SMILING(HAPPY)"))
        {
            im.setImageResource(R.mipmap.happy);

        }

        if (str.equals("EMOTION DETECTED : NOT_SMILING(ANGRY/SAD)"))
        {
            im.setImageResource(R.mipmap.notsmiling);

        }

        if (str.equals("EMOTION DETECTED : NEUTRAL(COOL)"))
        {
            im.setImageResource(R.mipmap.neutral);

        }


        tv.setText(result);

        final DBHandler db = new DBHandler(this);

        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String id = dbcode.push().getKey();

               // db.addinfo(new Image1(str,reportDate));
              //  dbcode.child(id).setValue(new Image1(str,reportDate));



                Log.e("Saved to database ", "SAVED");
                Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_LONG).show();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                Log.d("Reading", "All Records from databse .....");
               // List<Image1> images = db.getAllemotions();

               // for (Image1 image2 : images) {
               //     String log = "Id:" + image2.get_id() + "Date:" + image2.get_date() + "Emotion stores" + image2.get_emotion();
                  //  Log.d("Image::", log);
               // }

                Intent intent = new Intent(StorageActivity.this,ResultActivity.class);
                startActivity(intent);




            }
        });













     //   Log.e("storageacticity emo ", str);

        // DBHandler db = new DBHandler(this);


     //  db.addinfo(new Image1(str,reportDate));

        Log.e("String activitty",str + reportDate);




        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
    }
}
