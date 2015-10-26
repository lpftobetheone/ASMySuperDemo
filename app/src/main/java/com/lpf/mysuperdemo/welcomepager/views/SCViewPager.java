package com.lpf.mysuperdemo.welcomepager.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by liupf5 on 2015/10/23.
 */
public class SCViewPager extends ViewPager{

    private ArrayList<SCViewAnimation> mViewAnimation;

    public SCViewPager(Context context) {
        super(context);
        this.mViewAnimation = new ArrayList<SCViewAnimation>();
    }

    public SCViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mViewAnimation = new ArrayList<SCViewAnimation>();
    }

    public void addAnimation(SCViewAnimation inViewAnimation){
        mViewAnimation.add(inViewAnimation);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);

        for(int i =0; i<mViewAnimation.size(); i++){
            mViewAnimation.get(i).applyAnimation(position,offset);
        }
    }
}
