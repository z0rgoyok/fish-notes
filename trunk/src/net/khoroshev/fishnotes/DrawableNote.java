package net.khoroshev.fishnotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.style.StyleSpan;

public class DrawableNote extends Drawable {
    private LayerDrawable ld;
    int height = 30;
    int width = 30;
    
    public DrawableNote(Context context, Note note) {
        super();
        ShapeDrawable area = new ShapeDrawable(new OvalShape());
        area.getPaint().setColor(Color.RED);
        area.getPaint().setAlpha(50);
        area.setBounds(0, 0, 15, 15);
        
        ShapeDrawable stroke = new ShapeDrawable(new OvalShape());
        stroke.getPaint().setColor(Color.RED);
        stroke.getPaint().setStyle(Style.STROKE);
        stroke.setBounds(0, 0, 15, 15);
        
        /*Drawable marker = context.getResources().getDrawable(R.drawable.iam_marker);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());*/
        
        Drawable[] layers = {area, stroke/*, marker*/};
        ld = new LayerDrawable(layers);
    }

    @Override
    public void draw(Canvas canvas) {
        //ld.draw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        //canvas.drawPaint(paint);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 5, paint);
        /*Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(height);
        canvas.drawText("" + height + "x" + width, 20, height, paint);*/
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void setAlpha(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setColorFilter(ColorFilter arg0) {
        // TODO Auto-generated method stub

    }

}
