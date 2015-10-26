package com.lpf.mysuperdemo.welcomepager.util;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

/**
 * Created by liupf5 on 2015/10/23.
 */
public class SCViewAnimationUtil {

    public static void prepareViewToGetSize(View view){
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec,heightSpec);
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
    }

    public static Point getDispalySize(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        return size;
    }
}
