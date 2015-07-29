/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.interfaces.INetObserver;
import com.lpf.mysuperdemo.receiver.NetReceiver;
import com.lpf.mysuperdemo.util.NetworkHandler;

/**
 * @Title:
 * @Description:网络监听判断
 * @Author:liupf5
 * @Since:2015-6-2
 * @Version:1.1.0
 */
public class NetChangeActivity extends Activity implements INetObserver {

	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();

	private TextView mTextView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_change);

		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);

		// 添加网络监听
		NetworkHandler.getInstance().addNetObserver(this);

		initViews();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mTextView = (TextView) this.findViewById(R.id.id_netstate_change);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lpf.mysuperdemo.interfaces.INetObserver#updateNetState(boolean)
	 */
	@Override
	public void updateNetState(boolean connected) {
		// TODO Auto-generated method stub
		Log.v("debug_test", "NetWork State is connected? "+connected);
		if(connected){
			mTextView.setText("已连接网络");
		}else{
			mTextView.setText("已断开网络");
		}
	}

}
