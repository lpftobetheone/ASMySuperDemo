/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-12
 *@Version:1.1.0
 */
public class CustomDialogActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_custom_dialog);
	}
	
	public void functionKey(View view)
	{
		Intent intent = new Intent(this, CustomDialogShowActivity.class);  
        startActivity(intent);  
	}
}
