/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.views.CustomWidgets;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-8
 *@Version:1.1.0
 */
public class CustomWidgetActivity extends Activity {
	
	CustomWidgets mCv;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_widgets);
		initViews();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mCv = (CustomWidgets)this.findViewById(R.id.id_cusView);
		mCv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessage(message);
			}
		});
	}
	
	Handler myHandler = new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				mCv.changeStyle();
				mCv.invalidate();
				break;
			}
			super.handleMessage(msg);
		}
		
	};
}
