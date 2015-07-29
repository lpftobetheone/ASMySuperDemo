/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-5-31
 *@Version:1.1.0
 */
public class RequestForDataActivity extends Activity {

	private TextView mTextView;
	private Button mBtnGetData;
	private int mRequestCode = 1;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requestfordata);
		
		initViews();
		
		initDatas();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mTextView = (TextView)this.findViewById(R.id.id_request_textview);
		mBtnGetData = (Button)this.findViewById(R.id.id_request_getdata);
	}

	/**
	 * 
	 * @Description:
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		mBtnGetData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RequestForDataActivity.this, ResponseForDataActivity.class);
				startActivityForResult(intent, mRequestCode);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null){
			String returnData = data.getExtras().getString("returnData");
			if (returnData != null && returnData.length() > 0) {
				mTextView.setText(returnData);
			}
		}
	}
	
	
}
