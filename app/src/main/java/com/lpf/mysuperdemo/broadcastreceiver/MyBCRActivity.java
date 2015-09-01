package com.lpf.mysuperdemo.broadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lpf.mysuperdemo.R;

public class MyBCRActivity extends Activity implements View.OnClickListener {

    private Button mBtn_BCR;
    private Button mBtn_register;
    private Button mBtn_unregister;

    private MyReceiver mMyReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bcr);

        initViews();
    }

    private void initViews() {
        mBtn_BCR = (Button) this.findViewById(R.id.btn_sendBCR);
        mBtn_register = (Button) this.findViewById(R.id.btn_register);
        mBtn_unregister = (Button) this.findViewById(R.id.btn_unregister);

        mBtn_BCR.setOnClickListener(this);
        mBtn_register.setOnClickListener(this);
        mBtn_unregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendBCR:
//                Intent i = new Intent(MyBCRActivity.this, MyReceiver.class);
                Intent i = new Intent(MyReceiver.ACTION);
                i.putExtra("txt", "我是传递过来的数据哈哈");
                sendBroadcast(i);
                break;
            case R.id.btn_register:
                registerReceiver(mMyReceiver,new IntentFilter(MyReceiver.ACTION));
                break;
            case R.id.btn_unregister:
                unregisterReceiver(mMyReceiver);
                break;
        }
    }
}
