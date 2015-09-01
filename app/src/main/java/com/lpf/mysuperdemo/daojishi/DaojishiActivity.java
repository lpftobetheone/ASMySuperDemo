package com.lpf.mysuperdemo.daojishi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;


public class DaojishiActivity extends Activity implements View.OnClickListener, ServiceConnection {


    private Button mBtn_Start;
    private Button mBtn_Stop;
    private Button mBtn_Bind;
    private Button mBtn_UnBind;

    private TextView mTextview;

    private Context mContext;
    private Intent mServiceIntent;

    private Daojishiservice mService;
    private static final int SHOW_NUMBER = 1;       //显示当前编码
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_NUMBER:
                    mTextview.setText(msg.arg1+"");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daojishi);

        mContext = DaojishiActivity.this;
        mServiceIntent = new Intent(mContext, Daojishiservice.class);

        initViews();

        initData();
    }

    private void initViews() {
        mBtn_Start = (Button) this.findViewById(R.id.btn_start);
        mBtn_Stop = (Button) this.findViewById(R.id.btn_stop);
        mBtn_Bind = (Button) this.findViewById(R.id.btn_bind);
        mBtn_UnBind = (Button) this.findViewById(R.id.btn_unbind);

        mTextview = (TextView) this.findViewById(R.id.textView);

        mBtn_Start.setOnClickListener(this);
        mBtn_Stop.setOnClickListener(this);
        mBtn_Bind.setOnClickListener(this);
        mBtn_UnBind.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startService(mServiceIntent);
                break;
            case R.id.btn_stop:
                stopService(mServiceIntent);
                mService = null;
                break;
            case R.id.btn_bind:
                bindService(mServiceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                if (mService != null) {
                    unbindService(this);
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        System.out.println("on service connected");
        mService = ((Daojishiservice.MyBinder) binder).getService();

        if (mService != null) {
            new Thread() {
                @Override
                public void run() {
                    boolean flag = true;
                    while (flag) {

                        int number = mService.getCurrentNumber();
                        if(number == 0){
                            flag = false;
                        }

                        try {
                            Message msg = mHandler.obtainMessage();
                            msg.what = SHOW_NUMBER;
                            msg.arg1 = number;
                            mHandler.sendMessage(msg);       //更新前台显示的数字
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
