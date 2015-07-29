/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.lpf.mysuperdemo.util.NetUtils;
import com.lpf.mysuperdemo.util.NetworkHandler;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-2
 * @Version:1.1.0
 */
public class NetReceiver extends BroadcastReceiver {
	
	private final int NET_CONNECTED = 1;
	private final int NET_DISCONNECTED = 0;
	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == NET_CONNECTED){
				NetworkHandler.getInstance().updateNetwork(true);
			}else{
				NetworkHandler.getInstance().updateNetwork(false);
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
			boolean isConnected = NetUtils.isNetworkConnected(context);
			System.out.println("网络状态：" + isConnected);
			System.out.println("wifi状态：" + NetUtils.isWifiConnected(context));
			System.out.println("移动网络状态：" + NetUtils.isMobileConnected(context));
			System.out.println("网络连接类型：" + NetUtils.getConnectedType(context));
			if (isConnected) {
				handler.sendEmptyMessage(NET_CONNECTED);
				Toast.makeText(context, "已经连接网络", Toast.LENGTH_SHORT).show();
			} else {
				handler.sendEmptyMessage(NET_DISCONNECTED);
				Toast.makeText(context, "已经断开网络", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
