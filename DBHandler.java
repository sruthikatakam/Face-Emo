package com.example.sruthikatakam.faceemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sruthikatakam on 3/1/18.
 */

public class DBHandler extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME ="Imageinfo";

    private static final String TABLE_EMOJI ="emoji";

    private static final String KEY_ID ="id";

    private static final String  KEY_DATE="date";

    private static final String KEY_EMOTION="emotion";



    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_EMOJI + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
        + KEY_EMOTION + " TEXT" + ")";

        db.execSQL(CREATE_IMAGE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOJI);
// Creating tables again
        onCreate(db);



    }

    public void addinfo(Image1 image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_EMOTION, image.get_emotion()); // Emotion
       values.put(KEY_DATE, image.get_date()); // Date and time

// Inserting Row
        db.insert(TABLE_EMOJI, null, values);
        db.close(); // Closing database connection


    }

    public List<Image1> getAllemotions() {
        List<Image1> image1List = new ArrayList<Image1>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EMOJI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image1 image = new Image1();
                image.set_id(Integer.parseInt(cursor.getString(0)));
                image.set_emotion(cursor.getString(1));
                image.set_date(cursor.getString(2));
// Adding contact to list
                image1List.add(image);
            } while (cursor.moveToNext());
        }
        return image1List;
    }





}
