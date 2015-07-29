/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.adapter.HorizontalListViewAdapter;
import com.lpf.mysuperdemo.bean.StarProduct;
import com.lpf.mysuperdemo.bean.StarProductList;
import com.lpf.mysuperdemo.constants.Global;
import com.lpf.mysuperdemo.views.HorizontalListView;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-4
 *@Version:1.1.0
 */
public class HorizontalListViewActivity extends Activity {

	
	private HorizontalListView mHorizontalListView;
	private HorizontalListViewAdapter mAdapter;
	
	private StarProductList mGetStarProductList = new StarProductList();
	private List<StarProduct> mStarProductList = new ArrayList<StarProduct>();
	
	String picUrl[] = Global.mHorizontalListViewUrl;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizontallistview);
		
		initViews();
		
		initDatas();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mHorizontalListView = (HorizontalListView)this.findViewById(R.id.id_horizontallistview);
	}

	/**
	 * 
	 * @Description:
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		for(int i = 0; i<5; i++){
			StarProduct mProduct = new StarProduct();
			mProduct.setPicurl(picUrl[i]);
			mProduct.setProductname("name"+i);
			mProduct.setDetailurl("http://www.baidu.com");
			mStarProductList.add(mProduct);
		}
		mGetStarProductList.setStarproductlist(mStarProductList);
		
		mAdapter = new HorizontalListViewAdapter(HorizontalListViewActivity.this,mGetStarProductList);
		mHorizontalListView.setAdapter(mAdapter);
	}
	
	
}
