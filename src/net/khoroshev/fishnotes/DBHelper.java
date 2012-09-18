package net.khoroshev.fishnotes;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "notes";
    private static final String DATABASE_NAME = "fishnotes.db";
    private static final String DICTIONARY_TABLE_CREATE =
                String.format("CREATE table %s (", TABLE_NAME)
                + "id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ",accuracy REAL"
                + ",altitude REAL"
                + ",bearing REAL"
                + ",extras TEXT"
                + ",latitude REAL"
                + ",longitude REAL"
                + ",provider TEXT"
                + ",speed REAL"
                + ",time REAL"
                + ",depth FLOAT, note TEXT"
                + ");";
    private SQLiteDatabase db;
    private String dbDir = Environment.getExternalStorageDirectory() + "/fishnotes/";;

    DBHelper(Activity owner) {
        super(owner, 
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db != null) {
            
            
            File dbDirFile = new File(dbDir);
            if(!dbDirFile.isDirectory()) {
                dbDirFile.mkdirs();
            }
            File dbFile = new File(dbDir + DATABASE_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }
            this.db = SQLiteDatabase.openOrCreateDatabase(dbFile.getAbsolutePath(), null);
            this.db.execSQL(DICTIONARY_TABLE_CREATE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public synchronized void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        super.close();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        super.getReadableDatabase();
        if(this.db == null|| !db.isOpen()) {
            openDb();
        }
        return this.db;
    }

    private void openDb() {
        this.db = SQLiteDatabase.openDatabase(dbDir + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        super.getWritableDatabase();
        if(this.db == null || !db.isOpen()) {
            openDb();
        }
        return this.db;
    }
    
}
