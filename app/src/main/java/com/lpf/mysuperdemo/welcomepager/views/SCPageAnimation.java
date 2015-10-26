package com.lpf.mysuperdemo.welcomepager.views;

import android.view.View;

/**
 * Created by liupf5 on 2015/10/23.
 */
public abstract class SCPageAnimation {
    public int page;
    public abstract void applyTransformation(View onView, float positionOffset);
}
