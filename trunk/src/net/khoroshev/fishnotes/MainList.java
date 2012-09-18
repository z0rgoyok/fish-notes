package net.khoroshev.fishnotes;

import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainList extends Activity {
    private ListView lv;
    //private TextView textView;
    private DBHelper dbh;

    public MainList getMainList() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        lv = (ListView) findViewById(R.id.listView1);
        //textView = (TextView) findViewById(R.id.textView1);
        dbh = new DBHelper(this);

        /*Button buttonNew = (Button) findViewById(R.id.buttonNew);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getMainList(), EditNoteActivity.class);
                getMainList().startActivity(editIntent);
            }
        });
        Button buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getMainList(), MapActivity.class);
                getMainList().startActivity(mapIntent);
            }
        });*/
        
        /*lv.setAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, listview_array));*/
        final NotesAdapter notesAdapter = new NotesAdapter(this, dbh);
        lv.setAdapter(notesAdapter);
    }

    @Override
    protected void onPause() {
        dbh.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main_list, menu);
        menu.add(0, 111, Menu.NONE, "Add");
        menu.add(0, 222, Menu.NONE, "Map");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case (111):
            Intent editIntent = new Intent(getMainList(), EditNoteActivity.class);
            getMainList().startActivity(editIntent);
            return true;
        case (222):
            Intent mapIntent = new Intent(getMainList(), MapActivity.class);
            getMainList().startActivity(mapIntent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        lv.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lv.setVisibility(View.VISIBLE);
    }
}
