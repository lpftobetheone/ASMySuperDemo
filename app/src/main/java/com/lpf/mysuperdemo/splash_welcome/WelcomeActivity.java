/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.splash_welcome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

import java.util.ArrayList;
import java.util.List;


/**引导页面
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-8-13
 *@Version:1.1.0
 */
public class WelcomeActivity extends Activity{

	private ViewPager mViewPager;
	private TextView  mTextView;
	private Context mContext;
	
	
	private int pageNumber = 3;	//引导页的个数
	private List<ImageView> mImageViewsList = new ArrayList<ImageView>();
	private List<View> mDotViewsList = new ArrayList<View>();
	/* 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		mContext = this;
		
		initViews();
		
		setViewPager();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager)findViewById(R.id.vp);
		mTextView = (TextView)findViewById(R.id.start);
		
		LinearLayout mDotLayout = (LinearLayout) findViewById(R.id.startpagepoints);
		for(int i =0; i<pageNumber; i++){
			ImageView dotView = new ImageView(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			mDotLayout.addView(dotView , params);
			mDotViewsList.add(dotView);
		}
		
		for (int i = 0; i < mDotViewsList.size(); i++) {
			if (i == 0) {
				mDotViewsList.get(i).setBackgroundResource(
						R.drawable.dot_focus);
			} else {
				mDotViewsList.get(i).setBackgroundResource(
						R.drawable.dot_blur);
			}
		}
		
		for(int i =0; i< pageNumber; i++){
			ImageView view = new ImageView(mContext);
			switch (i) {
			case 0:
				view.setBackgroundResource(R.drawable.page1);
				break;
			case 1:
				view.setBackgroundResource(R.drawable.page2);
				break;
			case 2:
				view.setBackgroundResource(R.drawable.page3);
				break;
			}
			mImageViewsList.add(view);
		}
		
	}
	
	/**
	 * 
	 * @Description:
	 */
	@SuppressWarnings("deprecation")
	private void setViewPager() {
		// TODO Auto-generated method stub
		PagerAdapter mPagerAdapter = new PagerAdapter(){

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mDotViewsList.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			/* 
			 */
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mImageViewsList.get(position));
			}

			/* 
			 */
			@Override
			public Object instantiateItem(ViewGroup container, int position) {

				container.addView(mImageViewsList.get(position));
				return mImageViewsList.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int pos) {
				//修改显示点状态
				for (int i = 0; i < mDotViewsList.size(); i++) {
					if (i == pos % mImageViewsList.size()) {
						((View) mDotViewsList.get(i))
								.setBackgroundResource(R.drawable.dot_focus);
					} else {
						((View) mDotViewsList.get(i))
								.setBackgroundResource(R.drawable.dot_blur);
					}
				}

				//最后一页显示Start按钮
				if( pos == mDotViewsList.size()-1 ){
					findViewById(R.id.start).setVisibility(View.VISIBLE);
					findViewById(R.id.start).setOnClickListener(new View.OnClickListener(){
						@Override
						public void onClick(View v) {
							WelcomeActivity.this.finish();
						}
					});
				}else{
					findViewById(R.id.start).setVisibility(View.GONE);
				}
				
			}
			
		});
	}
}


























