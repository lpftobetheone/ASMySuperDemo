/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import com.lpf.mysuperdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-7-13
 *@Version:1.1.0
 */
public class H5BackToNativeActivity extends Activity{

	private TextView mTv_jump;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_h5tonative);
		
		initViews();
	}
	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mTv_jump = (TextView)this.findViewById(R.id.id_jumpToH5);
		mTv_jump.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(H5BackToNativeActivity.this, CommonWebClientActivity.class);
				String url_Path = "file:///android_asset/page_back.html";
				intent.putExtra("detailUrl", url_Path);
				startActivity(intent);
			}
		});
	}
}
