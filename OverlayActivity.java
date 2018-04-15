package com.example.sruthikatakam.faceemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class OverlayActivity extends AppCompatActivity {

    private FaceOverlayView mFaceOverlayView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        Bundle extras = getIntent().getExtras();
        path = extras.getString("path");

      //  Toast.makeText(this,"Path of the image" + path,Toast.LENGTH_LONG).show();

        BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
      bmOptions1.inJustDecodeBounds = true;
       bmOptions1.inMutable=true;
     BitmapFactory.decodeFile(path,bmOptions1);
        int photoW = bmOptions1.outWidth;
      int photoH = bmOptions1.outHeight;
     // Toast.makeText(this,"Bitmap width and height" + photoH + photoW ,Toast.LENGTH_LONG).show();



      bmOptions1.inJustDecodeBounds = false;
      bmOptions1.inSampleSize = 2;
       bmOptions1.inPurgeable = true;
        Bitmap bitmap1 = BitmapFactory.decodeFile(path,bmOptions1);



      //  Bitmap bitmap1 = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.raw.sample);










        mFaceOverlayView = (FaceOverlayView) findViewById(R.id.face_overlay);

        mFaceOverlayView.setBitmap(bitmap1);
    }
}
