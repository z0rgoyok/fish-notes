package net.khoroshev.fishnotes;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author Igor
 *
 */
public class DrawableNote extends Drawable {
    //private LayerDrawable ld;
    int height = 30;
    int width = 30;
    private Note note;
    
    public DrawableNote(Context context, Note note) {
        super();
        this.note = note;
        //log("constructor()");
        /*ShapeDrawable area = new ShapeDrawable(new OvalShape());
        area.getPaint().setColor(Color.RED);
        area.getPaint().setAlpha(50);
        area.setBounds(0, 0, 15, 15);
        
        ShapeDrawable stroke = new ShapeDrawable(new OvalShape());
        stroke.getPaint().setColor(Color.RED);
        stroke.getPaint().setStyle(Style.STROKE);
        stroke.setBounds(0, 0, 15, 15);
        
        Drawable marker = context.getResources().getDrawable(R.drawable.iam_marker);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        
        Drawable[] layers = {area, stroke, marker};
        ld = new LayerDrawable(layers);*/
    }

    @Override
    public void draw(Canvas canvas) {
        log("draw()");
        log("bounds = " + getBounds());
        //ld.draw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        //canvas.drawPaint(paint);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setAlpha(128);
        canvas.drawCircle(
                getCenterX(),
                getCenterY(),
                5, paint);
        paint.setColor(Color.YELLOW);
        paint.setAlpha(255);
        canvas.drawText("" + note.getDepth(), getBounds().left, getBounds().bottom, paint);
    }
    
    private int getCenterX() {
        return getBounds().centerX();
    }
    
    private int getCenterY() {
        return getBounds().bottom;
    }

    @Override
    public int getIntrinsicHeight() {
        //log("getIntrinsicHeight()");
        return height;
    }

    @Override
    public int getIntrinsicWidth() {
        //log("getIntrinsicWidth()");
        return width;
    }

    @Override
    public int getOpacity() {
        //log("getOpacity()");
        return PixelFormat.OPAQUE;
    }

    @Override
    public void setAlpha(int arg0) {
        //log("setAlpha()");
        // TODO Auto-generated method stub

    }

    @Override
    public void setColorFilter(ColorFilter arg0) {
        //log("setColorFilter()");
        // TODO Auto-generated method stub

    }

    /*@Override
    public void clearColorFilter() {
        //log("clearColorFilter()");
        // TODO Auto-generated method stub
        super.clearColorFilter();
    }

    @Override
    public int getChangingConfigurations() {
        //log("getChangingConfigurations()");
        // TODO Auto-generated method stub
        return super.getChangingConfigurations();
    }

    @Override
    public ConstantState getConstantState() {
        //log("getConstantState()");
        // TODO Auto-generated method stub
        return super.getConstantState();
    }

    @Override
    public Drawable getCurrent() {
        //log("getCurrent()");
        // TODO Auto-generated method stub
        return super.getCurrent();
    }

    @Override
    public int getMinimumHeight() {
        //log("getMinimumHeight()");
        // TODO Auto-generated method stub
        return super.getMinimumHeight();
    }

    @Override
    public int getMinimumWidth() {
        //log("getMinimumWidth()");
        // TODO Auto-generated method stub
        return super.getMinimumWidth();
    }

    @Override
    public boolean getPadding(Rect padding) {
        //log("getPadding()");
        // TODO Auto-generated method stub
        return super.getPadding(padding);
    }

    @Override
    public int[] getState() {
        //log("getState()");
        // TODO Auto-generated method stub
        return super.getState();
    }

    @Override
    public Region getTransparentRegion() {
        //log("getTransparentRegion()");
        // TODO Auto-generated method stub
        return super.getTransparentRegion();
    }

    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException {
        //log("inflate()");
        // TODO Auto-generated method stub
        super.inflate(r, parser, attrs);
    }

    @Override
    public void invalidateSelf() {
        //log("invalidateSelf()");
        // TODO Auto-generated method stub
        super.invalidateSelf();
    }

    @Override
    public boolean isStateful() {
        //log("isStateful()");
        // TODO Auto-generated method stub
        return super.isStateful();
    }

    @Override
    public Drawable mutate() {
        //log("mutate()");
        // TODO Auto-generated method stub
        return super.mutate();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        //log("onBoundsChange()");
        // TODO Auto-generated method stub
        super.onBoundsChange(bounds);
    }

    @Override
    protected boolean onLevelChange(int level) {
        //log("onLevelChange()");
        // TODO Auto-generated method stub
        return super.onLevelChange(level);
    }

    @Override
    protected boolean onStateChange(int[] state) {
        //log("onStateChange()");
        // TODO Auto-generated method stub
        return super.onStateChange(state);
    }

    @Override
    public void scheduleSelf(Runnable what, long when) {
        //log("scheduleSelf()");
        // TODO Auto-generated method stub
        super.scheduleSelf(what, when);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        log(String.format("setBounds(%s, %s, %s, %s)", left, top, right, bottom));
        // TODO Auto-generated method stub
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setBounds(Rect bounds) {
        log(String.format("setBounds(%s)", bounds));
        // TODO Auto-generated method stub
        super.setBounds(bounds);
    }

    @Override
    public void setChangingConfigurations(int configs) {
        //log("setChangingConfigurations()");
        // TODO Auto-generated method stub
        super.setChangingConfigurations(configs);
    }

    @Override
    public void setColorFilter(int color, Mode mode) {
        //log("setColorFilter()");
        // TODO Auto-generated method stub
        super.setColorFilter(color, mode);
    }

    @Override
    public void setDither(boolean dither) {
        //log("setDither()");
        // TODO Auto-generated method stub
        super.setDither(dither);
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        //log("setFilterBitmap()");
        // TODO Auto-generated method stub
        super.setFilterBitmap(filter);
    }

    @Override
    public boolean setState(int[] stateSet) {
        //log("setState()");
        // TODO Auto-generated method stub
        return super.setState(stateSet);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        //log("setVisible()");
        // TODO Auto-generated method stub
        return super.setVisible(visible, restart);
    }

    @Override
    public void unscheduleSelf(Runnable what) {
        //log("unscheduleSelf()");
        // TODO Auto-generated method stub
        super.unscheduleSelf(what);
    }*/
    
    private void log(String msg) {
        Log.d("DrawableNote " + note, msg);
    }

}
