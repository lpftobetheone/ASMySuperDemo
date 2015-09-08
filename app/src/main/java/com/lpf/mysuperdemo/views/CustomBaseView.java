package com.lpf.mysuperdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liupf5 on 2015/9/6.
 */
public abstract class CustomBaseView extends View {

    private MyThread thread;

    public CustomBaseView(Context context) {
        super(context);
    }

    public CustomBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected abstract void logic();
    protected abstract void drawSub(Canvas canvas);

    @Override
    protected void onDraw(Canvas canvas) {
        if(thread == null){
            thread = new MyThread();
        }else{
            drawSub(canvas);
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            logic();
            postInvalidate();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
