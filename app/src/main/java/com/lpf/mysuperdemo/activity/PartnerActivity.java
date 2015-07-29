/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.adapter.PartnerListViewAdpater;
import com.lpf.mysuperdemo.util.ToastUtil;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-30
 *@Version:1.1.0
 */
public class PartnerActivity extends Activity {
	
	private String[] partnerStyle;
	private ListView mListView;
	private PartnerListViewAdpater adapter;
	private Button mBtn_Apply;
	
	private int choosePosition = 0;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_partner);
		
		initViews();
		initData();
	}
	
	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView)this.findViewById(R.id.id_list_partner);
		mBtn_Apply = (Button)this.findViewById(R.id.id_btn_apply);
	}
	
	/**
	 * 
	 * @Description:
	 */
	private void initData() {
		// TODO Auto-generated method stub
		partnerStyle = getResources().getStringArray(R.array.partner_style);
		adapter = new PartnerListViewAdpater(PartnerActivity.this, partnerStyle);
		mListView.setAdapter(adapter);
		adapter.setSelectedIndex(0);
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				choosePosition = position;
				adapter.setSelectedIndex(position);
				ToastUtil.showShort(PartnerActivity.this, "选中"+position);
			}
			
		});
		
		mBtn_Apply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = choosePosition;
				String partnerChoose = partnerStyle[position];
				ToastUtil.showShort(PartnerActivity.this, "选中"+partnerChoose);
			}
		});
	}
}
