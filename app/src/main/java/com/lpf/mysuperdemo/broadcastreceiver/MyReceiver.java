package com.lpf.mysuperdemo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION="com.lpf.mysuperdemo.broadcastreceiver.MyReceiver";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String str = intent.getStringExtra("txt");
        System.out.println("on Receive----"+str);
    }
}
