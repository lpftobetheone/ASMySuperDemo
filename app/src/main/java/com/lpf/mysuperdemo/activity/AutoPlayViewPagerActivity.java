package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.bean.ScrollPicture;
import com.lpf.mysuperdemo.constants.Global;
import com.lpf.mysuperdemo.views.AutoPlayViewPager;

/**
 * 
 *@Title:
 *@Description: 图片轮播显示Activity
 *@Author:liupf5
 *@Since:2015-5-31
 *@Version:1.1.0
 */
public class AutoPlayViewPagerActivity extends Activity {

	private AutoPlayViewPager mAutoPlayViewPager;
	public List<ScrollPicture> mScrollPictureList = new ArrayList<ScrollPicture>(); // 图片轮播List数据
	
	String picUrl[] = Global.mAutoPlayUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autoviewpager);
		
		initViews();
		
		initDatas();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mAutoPlayViewPager = (AutoPlayViewPager)this.findViewById(R.id.id_autoplayviewpager);
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		for(int i =0; i<3; i++){
			ScrollPicture bean = new ScrollPicture();
			bean.setPicurl(picUrl[i]);
			bean.setLinkurl("http://www.baidu.com");
			bean.setSort(i);
			mScrollPictureList.add(bean);
		}
		mAutoPlayViewPager.setViewPagerData(this, mScrollPictureList);
	}
}
