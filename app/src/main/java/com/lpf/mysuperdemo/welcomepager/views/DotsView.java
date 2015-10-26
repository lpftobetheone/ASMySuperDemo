package com.lpf.mysuperdemo.welcomepager.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupf5 on 2015/10/23.
 */
public class DotsView extends LinearLayout {

    private List<ImageView> dots;
    private int numberOfPage = 0;
    private int mSelectedResource;
    private int mUnSelectedResource;


    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDotResource(int selectedResource, int unSelectedResource){
        mSelectedResource = selectedResource;
        mUnSelectedResource = unSelectedResource;
    }

    /**
     * 初始化Dots,默认选中第一张图片
     * @param pageNumber
     */
    public void setNumberOfPage(int pageNumber) {
        numberOfPage = pageNumber;
        dots = new ArrayList<>();

        for(int i =0; i<numberOfPage; i++){
            ImageView dot = new ImageView(getContext());
            dot.setImageDrawable(getResources().getDrawable(mUnSelectedResource));
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            addView(dot,params);
            dots.add(dot);
        }

        selectDot(0);
    }

    /**
     * 设置Dots选中状态
     * @param index
     */
    public void selectDot(int index) {
        Resources res = getResources();
        for(int i=0;i <numberOfPage; i++){
            int drawableId = (i==index)?(mSelectedResource):(mUnSelectedResource);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }


}
