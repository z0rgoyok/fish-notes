package net.khoroshev.fishnotes;

import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditNoteActivity extends Activity {

    private Location location;
    private Button saveButton;
    private TextView locationView;
    private Listener gpsStatusListener;
    private LocationListener locationListener;
    private ProgressBar progressBar;
    private EditText depthInput;
    private EditText descriptionInput;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        saveButton = (Button) findViewById(R.id.note_save_button);
        locationView = (TextView) findViewById(R.id.locationText);
        progressBar = (ProgressBar)findViewById(R.id.editNodeGPSprogressBar);
        depthInput = (EditText) findViewById(R.id.depthInput);
        descriptionInput = (EditText) findViewById(R.id.descriptionInput);
        saveButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void save() {
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = makeValues();
        try {
            db.insert(DBHelper.TABLE_NAME, null, values );
        } catch (Exception e) {} finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        setCurrentLocation(null);
    }

    private ContentValues makeValues() {
        ContentValues values = new ContentValues();
        values.put("accuracy", location.getAccuracy());
        values.put("altitude", location.getAltitude());
        values.put("bearing", location.getBearing());
        values.put("latitude", location.getLatitude());
        values.put("longitude", location.getLongitude());
        values.put("extras", location.getExtras() == null ? "" : location.getExtras().toString());
        values.put("provider", location.getProvider());
        values.put("speed", location.getSpeed());
        values.put("time", location.getTime());
        try {
            final Float depth = Float.valueOf(depthInput.getText().toString());
            values.put("depth", depth);
        } catch (NumberFormatException e) {}
        values.put("note", descriptionInput.getText().toString());
        return values;
    }

    private void startGPSlookup() {
        
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
        {
            Toast.makeText(this, "Please, turn on GPS.", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
            startActivity(myIntent);
        }
        //Criteria criteria = new Criteria();
        //final String bestProvider = lm.getBestProvider(criteria , true);
        final String bestProvider = LocationManager.GPS_PROVIDER;
        final LocationProvider provider = lm.getProvider(bestProvider);
        
        if (locationListener == null) {
            locationListener = new LocationListener() {

                @Override
                public void onStatusChanged(String provider, int status,
                        Bundle extras) {
                    Log.d("onStatusChanged", provider + status + extras);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d("onProviderEnabled", provider);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d("onProviderDisabled", provider);
                }

                @Override
                public void onLocationChanged(Location location) {
                    Log.d("ON LOCATION CHANGED", location == null ? "null"
                            : location.toString());
                    setCurrentLocation(location);
                }
            };
        }
        if (gpsStatusListener == null) {
            gpsStatusListener = new GpsStatus.Listener() {

                @Override
                public void onGpsStatusChanged(int event) {
                    Location loc = lm.getLastKnownLocation(provider.getName());
                    Log.d("LOCATION CHANGED",
                            loc == null ? "null" : loc.toString());
                    setCurrentLocation(loc);
                }
            };
        }
        lm.requestLocationUpdates(bestProvider, 1, 1, locationListener);
        lm.addGpsStatusListener(gpsStatusListener);
    }
    
    private void stopGPSLookup(){
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.removeGpsStatusListener(gpsStatusListener);
        lm.removeUpdates(locationListener);
    }

    protected void setCurrentLocation(Location location) {
        this.location = location;
        String text = location == null ? "" : 
            "" + location.getLatitude() + " x " + location.getLongitude()
            + " : " + location.getAccuracy();
        locationView.setText(text);
        updateSaveButtonStatus();
    }

    private void updateSaveButtonStatus() {
        saveButton.setClickable(location != null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_note, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        stopGPSLookup();
        super.onPause();
    }

    @Override
    protected void onResume() {
        setCurrentLocation(null);
        startGPSlookup();
        super.onResume();
    }
    
}
