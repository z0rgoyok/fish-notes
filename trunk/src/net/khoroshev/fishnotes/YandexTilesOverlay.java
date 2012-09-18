package net.khoroshev.fishnotes;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.TilesOverlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class YandexTilesOverlay extends TilesOverlay {
    private final Rect mTileRect = new Rect();
    private final Point mTilePos = new Point();

    public YandexTilesOverlay(MapTileProviderBase aTileProvider,
            Context aContext) {
        super(aTileProvider, aContext);
    }
        //������������� ����� draw ��� ��������� Yandex ������
    @Override
    protected void draw(Canvas c, MapView osmv, boolean shadow) {
        //������� �������
        int zoom = osmv.getZoomLevel();
        final Projection pj = osmv.getProjection();
        //���������� ����� ������� ����� �����
        BoundingBoxE6 bb = osmv.getBoundingBox();
        //�������� ���������� �������� ������ � ������� ������� �����
        double[] MercatorTL = YandexUtils.geoToMercator(new double[] {
                bb.getLonWestE6() / 1E6, bb.getLatNorthE6() / 1E6 });
        double[] TilesTL = YandexUtils.mercatorToTiles(MercatorTL);
        long[] TileTL = YandexUtils.getTile(TilesTL, zoom);

        double[] MercatorRB = YandexUtils.geoToMercator(new double[] {
                bb.getLonEastE6() / 1E6, bb.getLatSouthE6() / 1E6 });
        double[] TilesRB = YandexUtils.mercatorToTiles(MercatorRB);
        long[] TileRB = YandexUtils.getTile(TilesRB, zoom);
        mTileProvider
                .ensureCapacity((int) ((TileRB[1] - TileTL[1] + 1) * (TileRB[0]
                        - TileTL[0] + 1)));
        //������������� �������� ������ ����� Yandex
        double[] reTiles = YandexUtils.ReGetTile(new long[] { TileTL[0],
                TileTL[1] }, zoom);
        long xx = (long) reTiles[0];
        long yy = (long) reTiles[1];
        double[] reMercator = YandexUtils.tileToMercator(new long[] { xx, yy });
        double[] tmp = YandexUtils.mercatorToGeo(reMercator);
        //������������� �������� ������ ����� Yandex ��������� � �������� ���������� osmdroid
        GeoPoint gp = new GeoPoint(tmp[1], tmp[0]);
        pj.toPixels(gp, mTilePos);
        //� ����� ������������ ��� ������� ����� Yandex
        for (int y = (int) TileTL[1]; y <= TileRB[1]; y++) {
            int xcount = 0;

            for (int x = (int) TileTL[0]; x <= TileRB[0]; x++) {

                final MapTile tile = new MapTile(zoom, x, y);
                final Drawable currentMapTile = mTileProvider.getMapTile(tile);
                if (currentMapTile != null) {

                    mTileRect.set(mTilePos.x, mTilePos.y, mTilePos.x + 256,
                            mTilePos.y + 256);
                    currentMapTile.setBounds(mTileRect);
                    currentMapTile.draw(c);

                }
                xcount++;
                mTilePos.x += 256;
            }
            mTilePos.x -= xcount * 256;
            mTilePos.y += 256;
        }
    }

}
