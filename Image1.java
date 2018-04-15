package com.example.sruthikatakam.faceemo;

/**
 * Created by sruthikatakam on 3/1/18.
 */

public class Image1 {

    String _date;
    String _emotion;
    int _id;

    public Image1()
    {

    }

    public Image1(int id,String emotion,String date)
    {
        this._date=date;
        this._id=id;
        this._emotion=emotion;
    }

    public Image1(String emotion,String date)
    {
        this._date=date;

        this._emotion=emotion;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_emotion() {
        return _emotion;
    }

    public void set_emotion(String _emotion) {
        this._emotion = _emotion;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
