package com.lpf.mysuperdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lpf.mysuperdemo.activity.CustomDeleteEditTextActivity;
import com.lpf.mysuperdemo.activity.CustomDemosActivity;
import com.lpf.mysuperdemo.activity.DataDemosActivity;
import com.lpf.mysuperdemo.activity.FrameDemosActivity;
import com.lpf.mysuperdemo.activity.H5BackToNativeActivity;
import com.lpf.mysuperdemo.activity.NetChangeActivity;
import com.lpf.mysuperdemo.activity.PictureDemosActivity;
import com.lpf.mysuperdemo.activity.FrameSlidingMenuActivity;
import com.lpf.mysuperdemo.activity.CustomSearchActivity;
import com.lpf.mysuperdemo.activity.Test;
import com.lpf.mysuperdemo.adapter.SwingRightInAnimationAdapter;

public class MainActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter mAdapter;
	private String[] mListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		initDatas();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.id_list_demos);
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		mListItems = getResources().getStringArray(R.array.demonames);

		mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
				mListItems);

		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(
				mAdapter);
		swingRightInAnimationAdapter.setListView(mListView);

		mListView.setAdapter(swingRightInAnimationAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent.setClass(MainActivity.this,
							PictureDemosActivity.class);
					startActivity(intent);
					break;

				// 数据操作
				case 2:
					intent.setClass(MainActivity.this, DataDemosActivity.class);
					startActivity(intent);
					break;
				// 网络操作
				case 3:
					intent.setClass(MainActivity.this, NetChangeActivity.class);
					startActivity(intent);
					break;
				//自定义控件
				case 4:
					intent.setClass(MainActivity.this, CustomDemosActivity.class);
					startActivity(intent);
					break;
				// 页面框架布局
				case 5:
					intent.setClass(MainActivity.this,
							FrameDemosActivity.class);
					startActivity(intent);
					break;
				//H5页面跳转
				case 6:
					intent.setClass(MainActivity.this,
							H5BackToNativeActivity.class);
					startActivity(intent);
					break;
				// 当前测试
				case 7:
					intent.setClass(MainActivity.this,
							Test.class);
					startActivity(intent);
					break;
				default:
					break;
				}

			}
		});
	}

}
