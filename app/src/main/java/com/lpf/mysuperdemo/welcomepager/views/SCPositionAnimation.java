package com.lpf.mysuperdemo.welcomepager.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liupf5 on 2015/10/26.
 */
public class SCPositionAnimation extends SCPageAnimation {

    public int xPosition;
    public int yPosition;

    private float xStartPosition;
    private float yStartPosition;

    public SCPositionAnimation(Context context, int forPage, int dx,int dy) {
        this.page = forPage;
        this.xPosition = dx;
        this.yPosition = dy;
        this.xStartPosition = -1;
        this.yStartPosition = -1;
    }

    @Override
    public void applyTransformation(View onView, float positionOffset) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)onView.getLayoutParams();

        if(positionOffset <= 0.0001){
            xStartPosition = onView.getTranslationX();
            yStartPosition = onView.getTranslationY();
            return;
        }
        System.out.println("positionOffset"+positionOffset);
        System.out.println(page+"xPosition"+xPosition);
        System.out.println(xPosition*positionOffset);
        onView.setTranslationX((int)(xPosition*positionOffset)+xStartPosition);
        onView.setTranslationY((int)(yPosition*positionOffset)+yStartPosition);

        onView.requestLayout();
    }
}
