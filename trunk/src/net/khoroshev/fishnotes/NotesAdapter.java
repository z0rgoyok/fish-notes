package net.khoroshev.fishnotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author Igor
 *
 */
public class NotesAdapter implements ListAdapter {
    
    private Activity baseActivity;
    private Cursor cursor;
    private SQLiteDatabase db;
    public NotesAdapter(Activity baseActivity, DBHelper dbh) {
        super();
        this.baseActivity = baseActivity;
        this.dbh = dbh;
        //makeCursor();
        
    }

    private void makeCursor() {
        if (db == null || !db.isOpen()) {
            db = dbh.getWritableDatabase();
        }
        cursor = db.query(dbh.TABLE_NAME, new String[]{"*"}, null, null, null, null, "id desc");
    }

    /*private String listview_array[] = { "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX",
            "SEVEN", "EIGHT", "NINE", "TEN" };*/
    private DBHelper dbh;
    @Override
    public int getCount() {
        //return listview_array.length;
        makeCursor();
        final int count = cursor.getCount();
        closeDB();
        return count;
    }

    private void closeDB() {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor = null;
        if (db != null && db.isOpen()) {
            db.close();
        }
        db = null;
    }

    @Override
    public Object getItem(int i) {
        Note result = null;
        try {
            makeCursor();
            cursor.moveToPosition(i);
            result = new Note(cursor);
        } finally {
            closeDB();
        }
        return result;
    }

    
    /**
     * @param count 
     * @return num of last notes
     */
    public List<Note> getLastItems(int num) {
        List<Note> result = new ArrayList<Note>();
        try {
            makeCursor();
            for (int i = 0; i < num && i < cursor.getCount() ; i++) {
                cursor.moveToPosition(i);
                Note note = new Note(cursor);
                result.add(note);
            }
            
        } finally {
            closeDB();
        }
        return result ;
    }
    
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int arg0) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View result = 
                baseActivity.getLayoutInflater()
                    .inflate(R.layout.main_list_item, viewGroup, false);
        TextView text1 = (TextView) result.findViewById(R.id.textView3);
        TextView text2 = (TextView) result.findViewById(R.id.textView4);
        /*try {
            makeCursor();
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            Float latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
            Float longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
            Float depth = cursor.getFloat(cursor.getColumnIndex("depth"));
            //text1.setText(listview_array[i]);
            //text2.setText(listview_array[i]);
            text1.setText(String.format("%s:%s", latitude, longitude));
            text2.setText("" + depth);
        } finally {
            closeDB();
        }*/
        Note note = (Note) getItem(i);
        if (note != null) {
            text1.setText(String.format("%s:%s", note.getLatitude(), note.getLongitude()));
            text2.setText("" + note.getDepth());
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0) {
        return true;
    }

}
