package net.khoroshev.fishnotes;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MapActivity extends Activity {
    MapView mMap;
    MapController mMapController;
    //YandexTilesOverlay tilesOverlayYandex;
    TilesOverlay tilesOverlayGoogle;
    TilesOverlay tilesOverlayMapnik;
    //MapTileProviderBasic tileProviderYandex;
    MapTileProviderBasic tileProviderGoogle;
    MapTileProviderBasic tileProviderMapnik;
    private MapTileProviderBasic tileProviderYandex;
    private YandexTilesOverlay tilesOverlayYandex;
    private DBHelper dbh;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.activity_map);
        //разметка
        RelativeLayout rl = new RelativeLayout(this);
        //карта
        mMap = new MapView(this, 256);
        //включить встроенные кнопки управления масштабом
        mMap.setBuiltInZoomControls(true);
        //добавляем карту в разметку
        rl.addView(mMap, new RelativeLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setContentView(rl);
        //устанавливаем начальный масштаб и координаты центра карты
        mMapController = mMap.getController();
        mMapController.setZoom(15);
        //55.024,73.262415
        //mMapController.setCenter(new GeoPoint(55.024, 73.262415));
        //добавляем источник тайлов Yandex
        //ссылку можно подсмотреть в firebug 
        //getTileURLString подставит вместо %s  координаты тайла и масштаб
        ITileSource tileSourceYandex = new MyTileSource(
                "YandexMap",
                null,
                0,
                23,
                256,
                ".png",
                "http://vec04.maps.yandex.net/tiles?l=map&v=2.28.0&x=%s&y=%s&z=%s&lang=ru-RU",
                "http://vec03.maps.yandex.net/tiles?l=map&v=2.28.0&x=%s&y=%s&z=%s&lang=ru-RU",
                "http://vec02.maps.yandex.net/tiles?l=map&v=2.28.0&x=%s&y=%s&z=%s&lang=ru-RU",
                "http://vec01.maps.yandex.net/tiles?l=map&v=2.28.0&x=%s&y=%s&z=%s&lang=ru-RU");
        //добавляем источник тайлов Google
        //https://khms1.google.com/kh/v=116&src=app&x=183&y=80&z=8&s=Galil
        ITileSource tileSourceGoogle = new MyTileSource(
                "Google-Map",
                null,
                0,
                23,
                256,
                ".png",
                "https://khms0.google.com/kh/v=116&src=app&x=%s&y=%s&z=%s&s=Galileo",
                "https://khms1.google.com/kh/v=116&src=app&x=%s&y=%s&z=%s&s=Galileo",
                "https://khms2.google.com/kh/v=116&src=app&x=%s&y=%s&z=%s&s=Galileo",
                "https://khms3.google.com/kh/v=116&src=app&x=%s&y=%s&z=%s&s=Galileo");
        //создаем поставщика тайлов и задаем для него источник тайлов Yandex
        tileProviderYandex = new MapTileProviderBasic(getApplicationContext());
        tileProviderYandex.setTileSource(tileSourceYandex);
        tileProviderYandex.setTileRequestCompleteHandler(mMap
                .getTileRequestCompleteHandler());
        //создаем слой Yandex карты
        tilesOverlayYandex = new YandexTilesOverlay(tileProviderYandex,
                this.getBaseContext());
        //создаем поставщика тайлов и задаем для него источник тайлов Google
        tileProviderGoogle = new MapTileProviderBasic(getApplicationContext());
        tileProviderGoogle.setTileRequestCompleteHandler(mMap
                .getTileRequestCompleteHandler());
        tileProviderGoogle.setTileSource(tileSourceGoogle);
        //создаем слой Google карты
        tilesOverlayGoogle = new TilesOverlay(tileProviderGoogle,
                this.getBaseContext());
        //создаем поставщика тайлов и задаем для него источник тайлов Mapnik
        tileProviderMapnik = new MapTileProviderBasic(getApplicationContext());
        tileProviderMapnik.setTileRequestCompleteHandler(mMap
                .getTileRequestCompleteHandler());
        tileProviderMapnik.setTileSource(TileSourceFactory.MAPNIK);
        //создаем слой Mapnik карты
        tilesOverlayMapnik = new TilesOverlay(tileProviderMapnik,
                this.getBaseContext());
        //устанавливаем Google карты текущим слоем
        mMap.getOverlayManager().setTilesOverlay(tilesOverlayGoogle);
        //Insert points    

        dbh = new DBHelper(this);
        final NotesAdapter notesAdapter = new NotesAdapter(this, dbh);
        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>(); 
        List<Note> notes = notesAdapter.getLastItems(Integer.MAX_VALUE);
        
        final Note lastNote = notes.get(0);
        if (lastNote == null) {
            mMapController.setCenter(new GeoPoint(55.024, 73.262415));
        } else {
            mMapController.setCenter(new GeoPoint(lastNote.getLatitude(), lastNote.getLongitude()));
        }
        for (Note note : notes) {
            final OverlayItem overlayItem = new OverlayItem(
                    "" + note.getDepth(), 
                    note.getNote(), 
                    note.getPoint());
            final DrawableNote drawableNote = new DrawableNote(this, note);
            int w = drawableNote.getIntrinsicWidth();
            int h = drawableNote.getIntrinsicHeight();
            //Log.d("x,y", "" + w + "," + h);
            drawableNote.setBounds(-w / 2, -h, w / 2, 0);
            overlayItem.setMarker(drawableNote);
            items.add(overlayItem);
        }
        
        ItemizedIconOverlay<OverlayItem> myLocationOverlay 
            = new ItemizedIconOverlay<OverlayItem>(this, items, null);          
        
        mMap.getOverlays().add(myLocationOverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_map, menu);
        menu.add(0, 11, Menu.NONE, "Add");
        SubMenu sm = menu.addSubMenu("Выбор карты");
        sm.add(0, 21, Menu.NONE, "Yandex");
        sm.add(0, 22, Menu.NONE, "Google");
        sm.add(0, 23, Menu.NONE, "Mapnik");
        menu.add(0, 99, Menu.NONE, "Test");
        return true;
    }
    
    //в зависимости от выбора устанавливаем текущую карту/слой
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case (11):
            Intent editIntent = new Intent(this, EditNoteActivity.class);
            startActivity(editIntent);
            return true;
        case (21):
            mMap.getOverlayManager().setTilesOverlay(tilesOverlayYandex);
            mMap.invalidate();
            return true;
        case (22):
            mMap.getOverlayManager().setTilesOverlay(tilesOverlayGoogle);
            mMap.invalidate();
            return true;
        case (23):
            mMap.getOverlayManager().setTilesOverlay(tilesOverlayMapnik);
            mMap.invalidate();
            return true;
        case (99):
            Intent testIntent = new Intent(this, TestActivity.class);
            startActivity(testIntent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    
}
