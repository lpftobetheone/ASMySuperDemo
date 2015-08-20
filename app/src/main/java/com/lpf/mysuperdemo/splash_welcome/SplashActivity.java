/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.splash_welcome;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lpf.mysuperdemo.MainActivity;
import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.AlertDialogUtil;
import com.lpf.mysuperdemo.util.DownLoadFile;
import com.lpf.mysuperdemo.util.Global;
import com.lpf.mysuperdemo.util.JsonUtil;
import com.lpf.mysuperdemo.util.MySingleton;
import com.lpf.mysuperdemo.util.NetworkUtil;
import com.lpf.mysuperdemo.util.PackageInfoUtil;
import com.lpf.mysuperdemo.util.RequestUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-8-12
 * @Version:1.1.0
 */
public class SplashActivity extends Activity implements AlertDialogInterface {

	private Context mContext;
	private RelativeLayout rl_flash;
	private ProgressBar pb;
	private SharedPreferences userinfo;
	private Gson gson;

	private long startTime;
	
	public static String APPID = "8353f26ebf21634c738c7f7de2d82728";//外
	
	private RequestQueue mQueue;
	private RequestUtil request;
	private Map<String, String> params = new HashMap<String, String>();
	
	private int updateType;		//更新类型
	private boolean isLoadApk = false;
	private String path = "";
	
	private Handler mHandler = new Handler(){

		/* 
		 */
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Global.DOWN_LOAD_SUCESS:
				int num_total = (Integer)msg.obj;
				pb.setProgress(num_total);
				if(pb.getMax() == pb.getProgress()){
					rl_flash.setVisibility(View.GONE);
					//下载完成
					File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
							Global.PATH+Global.APKNAME);
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
					startActivity(intent);
				}
				break;
			case Global.DOWN_LOAD_FAIL:
				startOtherActivity();
				break;
			}
		}
		
	};

	/* 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
//		Bmob.initialize(getApplicationContext(),APPID);
		mContext = this;
		
		
		mQueue = MySingleton.getInstance(mContext)
				.getRequestQueue();

		userinfo = getSharedPreferences("user", MODE_PRIVATE);
		
		rl_flash = (RelativeLayout) findViewById(R.id.rl_flash);
		pb = (ProgressBar) findViewById(R.id.pb_flash);

		startTime = System.currentTimeMillis();
		gson = new Gson();
	}

	/* 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if (NetworkUtil.isNetworkConnected(this)) {
			checkVersion();
		} else {
			AlertDialogUtil.showDialog(this, "设置网络", "设置WiFi网络");
		}
	}

	private void checkVersion() {
		
		postData(Global.GET_UPDATE_JSON, 1000); 		//获取版本信息的Json文件
		
	}
	
	public synchronized void postData(String URL, int selectType) {
		// TODO Auto-generated method stub
		request = new RequestUtil(mContext, selectType);
		request.setMethod(Method.GET);
		StringRequest stringRequest = request.getData(URL, params,
				new RequestUtil.VolleyResponseListener() {

					@Override
					public void onResponse(String response, int selectType) {
						analysisReponseData(response, selectType);
						Log.v("c2c","aaa");
					}

					@Override
					public void onError(VolleyError error, int selectType) {
						Log.v("c2c",error+"");
					}
				}, false);
		// 把请求（在这个例子中请求为StringRequest）添加到请求队列
		mQueue.add(stringRequest);
	}

	// 数据解析方法
	public void analysisReponseData(String response, int selectType) {
		switch (selectType) {
		// 获取版本号
		case 1000:
			PackageInfoBean bean = JsonUtil.json2Bean(response, PackageInfoBean.class);
			Log.v("c2c","bbb");
			if(bean == null){
				return;
			}
			updateType = bean.getUpdatetype();
			String remark = bean.getRemark();
			String verb = bean.getVerb();
			String selfVerb;
			try {
				selfVerb = PackageInfoUtil.getVersion(mContext);
				if(verb.equals("")){
					startOtherActivity();	//没有获取到版本号 
				}else{
					//版本号相同或者服务器版本号小于当前版本号
					if(verb.equals(selfVerb) || verb.compareTo(selfVerb)<=0){
						startOtherActivity();
					}else{
						//服务器版本号大，给出提示
						if(updateType == 1){
							//强制更新
							path = bean.getPath();
							isLoadApk = true;
							if(remark.equals("")){
								remark = "强制更新的提示";
							}
							AlertDialogUtil.showDialog(mContext, "版本升级", remark);
						}else{
							path = bean.getPath();
							isLoadApk = true;
							AlertDialogUtil.showDialog(mContext, "版本升级", "发现新版本是否升级？");
						}
					}
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}

	}
	
	//启动其他页面
	private void startOtherActivity(){
		
		String versionNum;
		try {
			versionNum = PackageInfoUtil.getVersion(this);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			versionNum = "version";
		}
		
		Intent intent = new Intent();
		if(userinfo.getBoolean(versionNum,false)){
			intent.setClass(SplashActivity.this,MainActivity.class);
		}else{
			userinfo.edit().putBoolean(versionNum, true).commit();
			intent.setClass(SplashActivity.this, WelcomeActivity.class);
//			intent.addFlags(Intent.Flag_)
		}
		startActivity(intent);
		SplashActivity.this.finish();
	}

	/* 
	 * 点击确定按钮操作
	 */
	@Override
	public void alertConfirm(DialogInterface dialog) {
		// TODO Auto-generated method stub
		if(isLoadApk){
			rl_flash.setVisibility(View.VISIBLE);
			DownLoadFileFromPath(path);
			dialog.dismiss();
		}else{
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		}
	}

	/* 
	 * 点击取消按钮操作
	 */
	@Override
	public void alertCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		if(updateType == 1){
			//强制更新1
			System.exit(0);
		}else{
			dialog.dismiss();
			startOtherActivity();
		}
	}
	
	/**
	 * 下载最新的Apk
	 * @param url
	 * @Description:
	 */
	private void DownLoadFileFromPath(String url){
		new Thread(){
			public void run(){
				DownLoadFile down = new DownLoadFile();
				down.download(path, mHandler, pb);
			}
		}.start();
	}
}
