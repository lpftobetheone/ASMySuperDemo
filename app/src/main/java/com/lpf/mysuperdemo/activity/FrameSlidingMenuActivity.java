/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Interpolator;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.opensourcelibs.slidingmenu.SlidingMenu;
import com.lpf.mysuperdemo.opensourcelibs.slidingmenu.SlidingMenu.CanvasTransformer;
import com.lpf.mysuperdemo.opensourcelibs.slidingmenu.app.SlidingFragmentActivity;
import com.lpf.mysuperdemo.slidingmenu.LeftMenuFragment;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-7-1
 * @Version:1.1.0
 */
public class FrameSlidingMenuActivity extends SlidingFragmentActivity {

	private CanvasTransformer mTransformer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity#onCreate
	 * (android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initAnimation(); // 初始化SlidingMenu动画

		initSlidingMenu(); // 初始化两侧菜单
	}

	/**
	 * 
	 * @Description:
	 */
	private void initAnimation() {
		// TODO Auto-generated method stub
		mTransformer = new CanvasTransformer() {

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				// 三种菜单滑动效果

				canvas.scale(interp.getInterpolation(percentOpen),
						interp.getInterpolation(percentOpen),
						canvas.getWidth() / 2, canvas.getHeight() / 2);

				// 平移动画
				// canvas.translate(0,
				// canvas.getHeight()*(1-interp.getInterpolation(percentOpen)));
				// 缩放动画
				// canvas.scale(percentOpen, 1,0,0);

			}
		};
	}

	/**
	 * 
	 * @Description:
	 */
	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		// 设置滑动菜单的属性值
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBackgroundResource(R.drawable.slidingmenu_bg);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0f);
		getSlidingMenu().setBehindCanvasTransformer(mTransformer);
		
		//设置页面对应xml布局
		setContentView(R.layout.activity_slidingmenu);
		
		//设置左侧菜单
		setBehindContentView(R.layout.activity_slidingmenu_left_content);
		getSupportFragmentManager().beginTransaction().replace(R.id.left_content_id, new LeftMenuFragment()).commit();

		// 设置右侧菜单
		// getSlidingMenu().setSecondaryMenu(R.layout.right_content);
		// getSupportFragmentManager().beginTransaction().replace(R.id.right_content_id,
		// new RightMenuFragment()).commit();
		
	}

	/**
	 * 自定加速效果
	 */
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};
}
