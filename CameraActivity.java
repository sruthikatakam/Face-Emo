package com.example.sruthikatakam.faceemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

//import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by sruthikatakam on 2/24/18.
 */

public class CameraActivity extends AppCompatActivity {
    Bitmap bitmap;
    InputStream inputStream;

    Button camerabtn;
    Button detect;
    Button save;
    ImageView imageView;
    Uri fileUri;
    FaceOverlayView mFaceOverlayView;
    //TextView t;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=100;
    public static final int MEDIA_TYPE_IMAGE=1;

    String mcurrentPhotopath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camerabtn = (Button) findViewById(R.id.camera);
        detect = (Button) findViewById(R.id.detect);
        imageView = (ImageView) findViewById(R.id.imageView);
        save = (Button) findViewById(R.id.save);




        // mFaceOverlayView = (FaceOverlayView) findViewById(R.;

       // inputStream = getResources().openRawResource(R.raw.sample);
       // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // mFaceOverlayView.setBitmap(bitmap);



        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);





                intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
               // startActivityForResult(intent,0);
                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



            }




        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(CameraActivity.this,OverlayActivity.class);
                intent1.putExtra("path",mcurrentPhotopath);
                startActivity(intent1);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emo = FaceOverlayView.Emotion;
                Log.e("Camera acticity emo ", emo);
                Intent intent1 = new Intent(CameraActivity.this,StorageActivity.class);
               // intent1.putExtra("emotion",emo);
                startActivity(intent1);




            }
        });





    }

    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    private File getOutputMediaFile(int mediaTypeImage) {


        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES), "MyCameraApp");
               // Environment.DIRECTORY_PICTURES), "faceemo");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (mediaTypeImage == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(mediaTypeImage == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        mcurrentPhotopath = mediaFile.getAbsolutePath();

        return mediaFile;
    }









        //ENDS HERE



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


       double targetW = imageView.getWidth();
        double targetH = imageView.getHeight();



        // /storage/sdcard0/DCIM/Camera



        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inMutable=true;
        BitmapFactory.decodeFile(mcurrentPhotopath,bmOptions);
        double photoW = bmOptions.outWidth;
        double photoH = bmOptions.outHeight;

     int scaleFactor = (int) Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mcurrentPhotopath,bmOptions);
        imageView.setImageBitmap(bitmap);

       // mFaceOverlayView = (FaceOverlayView) findViewById(R.id.face_overlay);

     //   mFaceOverlayView.setBitmap(bitmap);







        // imageView.setImageURI(fileUri);


        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {


                // say that we saved image

               TextView t = (TextView) findViewById(R.id.textView_Results);

                t.setText("Success");

              Toast.makeText(this,"Image saved to" + fileUri,Toast.LENGTH_LONG).show();


            } else if (resultCode == RESULT_CANCELED) {

                // User cancelled the image capture

                TextView t = (TextView) findViewById(R.id.textView_Results);

                t.setText("Camera Cancelled");


            } else {

                // Image capture failed, advise user

               TextView t = (TextView) findViewById(R.id.textView_Results);

                t.setText("Failure");

            }
        }





















    }

}
