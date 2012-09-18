package net.khoroshev.fishnotes;

import org.osmdroid.ResourceProxy.string;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.XYTileSource;

public class MyTileSource extends XYTileSource {

    public MyTileSource(String aName, string aResourceId, int aZoomMinLevel,
            int aZoomMaxLevel, int aTileSizePixels,
            String aImageFilenameEnding, String... aBaseUrl) {
        super(aName, aResourceId, aZoomMinLevel, aZoomMaxLevel,
                aTileSizePixels, aImageFilenameEnding, aBaseUrl);

    }
    //переопределим метод getTileURLString, он будет возвращать ссылку на тайл карты
    @Override
    public String getTileURLString(MapTile aTile) {

        return String.format(getBaseUrl(), aTile.getX(), aTile.getY(),
                aTile.getZoomLevel());
    }

}