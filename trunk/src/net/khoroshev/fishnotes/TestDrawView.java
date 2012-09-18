package net.khoroshev.fishnotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TestDrawView extends View {

    public TestDrawView(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        // ����������� ����� ����� ������
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // ������ ������ ����
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(450, 30, 25, paint);
        // ������ �����
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        canvas.drawText("������� ��� �����", 30, 200, paint);
        
        DrawableNote dn = new DrawableNote(getContext(), null);
        dn.draw(canvas);
    }
    
}
