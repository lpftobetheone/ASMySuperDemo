package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lpf.mysuperdemo.R;

public class CustomSearchResultActivity extends Activity implements OnClickListener{

//	private ImageButton mBtnSearch;
//	private ImageButton mBtnSearchBack;
	
	private LinearLayout mLinearLayoutBack;
	private LinearLayout mLinearLayoutSearch;

	private EditText mEtSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_result);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mLinearLayoutBack = (LinearLayout) findViewById(R.id.id_search_back);
		mLinearLayoutSearch = (LinearLayout) findViewById(R.id.id_search_text);
		mEtSearch = (EditText) findViewById(R.id.et_search);

		mLinearLayoutBack.setOnClickListener(this);
		mLinearLayoutSearch.setOnClickListener(this);
		
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_search_text:
			break;
		case R.id.id_search_back:
			this.finish();
			break;
		}
	}
}
