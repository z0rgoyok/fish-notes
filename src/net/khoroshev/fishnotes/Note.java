package net.khoroshev.fishnotes;

import org.osmdroid.util.GeoPoint;

import android.database.Cursor;

public class Note {

    private int id;
    private float accuracy;
    private float altitude;
    private float bearing;
    private String extras;
    private float latitude;
    private float longitude;
    private String provider;
    private float speed;
    private long time;
    private float depth;
    private String note;

    public Note(Cursor cursor) {
        id = cursor.getInt(0);
        accuracy = cursor.getFloat(cursor.getColumnIndex("accuracy"));
        altitude = cursor.getFloat(cursor.getColumnIndex("altitude"));
        bearing = cursor.getFloat(cursor.getColumnIndex("bearing"));
        extras = cursor.getString(cursor.getColumnIndex("extras"));
        latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
        longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
        provider = cursor.getString(cursor.getColumnIndex("provider"));
        speed = cursor.getFloat(cursor.getColumnIndex("speed"));
        time = cursor.getLong(cursor.getColumnIndex("time"));
        depth = cursor.getFloat(cursor.getColumnIndex("depth"));
        note = cursor.getString(cursor.getColumnIndex("note"));
    }

    public int getId() {
        return id;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public float getAltitude() {
        return altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public String getExtras() {
        return extras;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getProvider() {
        return provider;
    }

    public float getSpeed() {
        return speed;
    }

    public long getTime() {
        return time;
    }

    public float getDepth() {
        return depth;
    }

    public String getNote() {
        return note;
    }
    public GeoPoint getPoint() {
        return new GeoPoint(latitude, longitude, altitude);
    }
}
