package com.example.sruthikatakam.faceemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by sruthikatakam on 2/25/18.
 */

public class FaceOverlayView extends View {
    public static String Emotion;
    public static int lefteye1;
    public static int righteye1;
    public static int z1;
    public static int y1;

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;
    // String sample="not opeartional";
    private String sample = "not operational";
    private String sa = "not operational";
    //public String Emotion;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        FaceDetector detector = new FaceDetector.Builder(getContext())
                .setTrackingEnabled(true)
                .setClassificationType(FaceDetector.ALL_LANDMARKS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();

        if (!detector.isOperational()) {
           // Toast.makeText(getContext(),
                //    "Not-operational...", Toast.LENGTH_SHORT).show();


            //Handle contingency
        } else {

            //Toast.makeText(getContext(),
              //      "Is-operational...", Toast.LENGTH_SHORT).show();

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            mFaces = detector.detect(frame);
            detector.release();
        }
        logFaceData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);


            drawFaceLandmarks(canvas, scale);
        }
        else
        {

            Toast.makeText(getContext(),
                    "BITMAP OR MFACES IS NULL", Toast.LENGTH_SHORT).show();
        }

    }

    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //This should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            left = (float) (face.getPosition().x * scale);
            top = (float) (face.getPosition().y * scale);
            right = (float) scale * (face.getPosition().x + face.getWidth());
            bottom = (float) scale * (face.getPosition().y + face.getHeight());

            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawFaceLandmarks(Canvas canvas, double scale) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            for (Landmark landmark : face.getLandmarks()) {
                int cx = (int) (landmark.getPosition().x * scale);
                int cy = (int) (landmark.getPosition().y * scale);
                canvas.drawCircle(cx, cy, 10, paint);
            }

        }
    }

    private void logFaceData() {
        float smilingProbability;
        float leftEyeOpenProbability;
        float rightEyeOpenProbability;
        float eulerY;
        float eulerZ;
        float camp = (float)50.0;
        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            smilingProbability = face.getIsSmilingProbability();
            leftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
            rightEyeOpenProbability = face.getIsRightEyeOpenProbability();
            eulerY = face.getEulerY();
            eulerZ = face.getEulerZ();

            Log.e("Sruthi+ Face Detection", "Smiling: " + smilingProbability);
            Log.e("Sruthi+ Face Detection", "Left eye open: " + leftEyeOpenProbability);
            Log.e("Sruthi+ Face Detection", "Right eye open: " + rightEyeOpenProbability);
            Log.e("Sruthi+ Face Detection", "Euler Y: " + eulerY);
            Log.e("Sruthi+ Face Detection", "Euler Z: " + eulerZ);

            double smi = Math.floor(smilingProbability * 100.0);
            double lefteye = Math.floor(leftEyeOpenProbability * 100.0);
            double righteye = Math.floor(rightEyeOpenProbability * 100.0);
            //double yaxis =  Math.floor(rightEyeOpenProbability * 100.0);
           // double zaxis = Math.floor(rightEyeOpenProbability * 100.0);
            double yaxis = Math.floor(eulerY);
            double zaxis = Math.floor(eulerZ);

            float smile = (float) smi;
            lefteye1 = (int) lefteye;
            righteye1 = (int) righteye;
            y1 = (int) yaxis;
            z1 = (int) zaxis;

            int result = Float.compare(smile,camp);








           if(result > 0) {
               Emotion = "EMOTION DETECTED : SMILING(HAPPY)";


               for (int j = 0; j < 2; j++) {


                   final Toast ImageToast = new Toast(getContext());
                   LinearLayout toastLayout = new LinearLayout(getContext());
                   toastLayout.setOrientation(LinearLayout.HORIZONTAL);
                   ImageView image = new ImageView(getContext());
                   TextView text = new TextView(getContext());
                   image.setImageResource(R.drawable.happymood);
                   text.setText("SMILING%" + smi + "                                          YaxisOrientation  " + yaxis + "           " +
                           "                                ZaxisOrientation  " + zaxis + "  " +
                           "                      LeftEyeOpenProbablity  " + lefteye
                   + "                           RightEyeOpenProbabilty  " + righteye);
                   toastLayout.addView(image);
                   toastLayout.addView(text);
                   ImageToast.setView(toastLayout);
                   ImageToast.setDuration(Toast.LENGTH_LONG);
                   ImageToast.show();
                   new CountDownTimer(9000, 1000) {

                       public void onTick(long millisUntilFinished) {
                           ImageToast.show();
                       }

                       public void onFinish() {
                           ImageToast.show();
                       }

                   }.start();
               }
           } else if(result < 0 )
           {
               Emotion="EMOTION DETECTED : NOT_SMILING(ANGRY/SAD)";

              //else starts

               for (int j = 0; j < 2; j++) {


                   final Toast ImageToast = new Toast(getContext());
                   LinearLayout toastLayout = new LinearLayout(getContext());
                   toastLayout.setOrientation(LinearLayout.HORIZONTAL);
                   ImageView image = new ImageView(getContext());
                   TextView text = new TextView(getContext());
                   image.setImageResource(R.drawable.badmood);
                   //text.setText("NOT-SMILING" + smi + "with EulerY" + yaxis + "EulerZ" + zaxis);
                   text.setText("SMILING%" + smi + "                                          YaxisOrientation  " + yaxis + "           " +
                           "                                ZaxisOrientation  " + zaxis + "  " +
                           "                      LeftEyeOpenProbablity  " + lefteye
                           + "                           RightEyeOpenProbabilty  " + righteye);
                   toastLayout.addView(image);
                   toastLayout.addView(text);
                   ImageToast.setView(toastLayout);
                   ImageToast.setDuration(Toast.LENGTH_LONG);
                   ImageToast.show();
                   new CountDownTimer(9000, 1000) {

                       public void onTick(long millisUntilFinished) {
                           ImageToast.show();
                       }

                       public void onFinish() {
                           ImageToast.show();
                       }

                   }.start();
               }
           } else {
               //else starts
               Emotion="EMOTION DETECTED : NEUTRAL(COOL)";
               for (int j = 0; j < 2; j++) {


                   final Toast ImageToast = new Toast(getContext());
                   LinearLayout toastLayout = new LinearLayout(getContext());
                   toastLayout.setOrientation(LinearLayout.HORIZONTAL);
                   ImageView image = new ImageView(getContext());
                   TextView text = new TextView(getContext());
                   image.setImageResource(R.drawable.neutral);
                   //text.setText("NEUTRAL" + smi + " WITH Y AXIS" + yaxis + "Z AXIS" + zaxis);
                   text.setText("SMILING%" + smi + "                                          YaxisOrientation  " + yaxis + "           " +
                           "                                ZaxisOrientation  " + zaxis + "  " +
                           "                      LeftEyeOpenProbablity  " + lefteye
                           + "                           RightEyeOpenProbabilty  " + righteye);
                   toastLayout.addView(image);
                   toastLayout.addView(text);
                   ImageToast.setView(toastLayout);
                   ImageToast.setDuration(Toast.LENGTH_LONG);
                   ImageToast.show();
                   new CountDownTimer(9000, 1000) {

                       public void onTick(long millisUntilFinished) {
                           ImageToast.show();
                       }

                       public void onFinish() {
                           ImageToast.show();
                       }

                   }.start();
               }

           }








        }  //for loop ends here






    }
}




