/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lpf.mysuperdemo.splash_welcome.PackageInfoBean;

import java.util.HashMap;
import java.util.Map;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-8-13
 *@Version:1.1.0
 */
public class GetApkVersion {

	private RequestQueue mQueue;
	private RequestUtil request;
	private Map<String, String> params = new HashMap<String, String>();
	private Handler mHandler;
	private Context mContext;
	
	private int updateType;		//更新类型
	private boolean isLoadApk = false;
	private String path = "";
	
	public GetApkVersion(Context context, Handler handler){
		mContext = context;
		mHandler = handler;
		
		mQueue = MySingleton.getInstance(mContext)
				.getRequestQueue();
		
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
					}

					@Override
					public void onError(VolleyError error, int selectType) {
					}
				}, false);
		// 把请求（在这个例子中请求为StringRequest）添加到请求队列
		mQueue.add(stringRequest);
	}

	// 数据解析方法
	public void analysisReponseData(String response, int selectType) {
		switch (selectType) {
		// 轮播图片
		case 1000:
			PackageInfoBean bean = JsonUtil.json2Bean(response, PackageInfoBean.class);
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
					redirect();	//没有获取到版本号 
				}else{
					//版本号相同或者服务器版本号小于当前版本号
					if(verb.equals(selfVerb) || verb.compareTo(selfVerb)<=0){
						redirect();
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
							Message msg = Message.obtain();
							msg.what = 1;
							msg.obj = bean;
							mHandler.sendMessage(msg);
						}
					}
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}

	}

	/**
	 * 
	 * @Description:
	 */
	private void redirect() {
		// TODO Auto-generated method stub
		
	}
	
	
}
