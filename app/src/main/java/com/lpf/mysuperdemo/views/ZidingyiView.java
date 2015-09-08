package com.lpf.mysuperdemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.lpf.mysuperdemo.R;

import java.util.Random;

/**
 * Created by liupf5 on 2015/9/6.
 */
public class ZidingyiView extends CustomBaseView {

    private Bitmap mBitmap;
    Paint paint = new Paint();
    private float rx = 0;

    private RectF rectF = new RectF(0,500,100,600);
    private float sweepAngle = 0;

    public ZidingyiView(Context context) {
        this(context, null);
    }

    public ZidingyiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }


    @Override
    protected void logic() {
        Random random = new Random();

        rx += 10;
        if (rx >= getWidth()) {
            rx = 0 - paint.measureText("自定义文字");
        }

        sweepAngle += 1;
        if(sweepAngle >=360){
            sweepAngle = 0;
        }

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        paint.setARGB(255,r,g,b);
    }

    @Override
    protected void drawSub(Canvas canvas) {

        //滚动起来的文字
        paint.setTextSize(50);
        canvas.drawText("自定义文字", rx, 50, paint);

        //画线
        canvas.drawLine(0, 60, 100, 60, paint);

        //        canvas.drawRect(0, 90, 100, 190, paint);
        //        Rect rc = new Rect(0,90,100,190);
        //        canvas.drawRect(rc, paint);

        //圆角矩形
        RectF rcf = new RectF(0, 90, 100, 190);
        canvas.drawRoundRect(rcf, 10, 20, paint);

        //圆形
        //        canvas.drawCircle(50, 270, 50, paint);
        canvas.drawArc(rectF,0,sweepAngle,true,paint);

        //图像
        //        canvas.drawBitmap(mBitmap, 0, 300, paint);
    }

}
