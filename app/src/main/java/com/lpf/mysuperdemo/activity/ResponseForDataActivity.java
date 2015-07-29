/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.StringUtils;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-5-31
 * @Version:1.1.0
 */
public class ResponseForDataActivity extends Activity implements
		OnClickListener {

	private EditText mEditText;
	private Button mBtnSendData;
	private int resultCode = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responsefordata);

		initViews();

		initDatas();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mEditText = (EditText) this.findViewById(R.id.id_response_edittext);
		mBtnSendData = (Button) this.findViewById(R.id.id_response_senddata);
		mBtnSendData.setOnClickListener(this);
	}

	/**
	 * 
	 * @Description:
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_response_senddata:
			if (StringUtils.isEmpty(mEditText.getText().toString())) {
				Toast.makeText(this, R.string.edit_text_cannot_empty,
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				intent.putExtra("returnData", mEditText.getText().toString());
				this.setResult(resultCode, intent);
				this.finish();
			}
			break;
		}
	}

}
