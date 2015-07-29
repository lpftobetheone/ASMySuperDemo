package com.lpf.mysuperdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.webkit.WebSettings;
import android.widget.Toast;

public class Util {

	public static void callContact(Activity activity, String cellphone) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ cellphone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
	public static void setVewSettings(WebSettings webSettings){
		if(webSettings == null ){
			return;
		}
		// 如果访问的页面中有Javascript，则webview必须设置支持Javascript。
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setAppCacheEnabled(true);
		// 使用localStorage则必须打开html5特性
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		// webSettings.setRenderPriority(RenderPriority.HIGH);  
		// webSettings.setBlockNetworkImage(true);
		// 启用数据库
		// webSettings.setDatabaseEnabled(true);
		// String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();   
		// 设置数据库路径   
		// webSettings.setDatabasePath(dir);
	}
	
	public static void  showToast(Context context, String msg) {
		Toast toast =Toast.makeText(context.getApplicationContext(),
				msg, Toast.LENGTH_LONG);
			   toast.setGravity(Gravity.CENTER, 0, 0);
			   toast.show();
	}
	
	
	
	
//	public static void getCookieInfo(){
////		super.onPageFinished(view, url);
//		
//		
////		super.onPageFinished(view, url);
//		CookieManager cookieManager = CookieManager.getInstance();
//		String cookieStr = cookieManager.getCookie(Global.HOME_PAGE);
////		String cookieStr = cookieManager.getCookie(Global.COOKIE_URL
////				+ instance.get(Global.LOGINTOKEN));	
//		
//		
//		Log.e("cookie", "cookie is " + cookieStr);
//		if (cookieStr != null) {
//			Log.e("cookie", cookieStr);
//			//有表面登录注册了
//			if (cookieStr.contains("lenovocurrentusername")) {
//				int index=cookieStr.indexOf("lenovocurrentusername");
//				int index_equal = cookieStr.indexOf("=", index);// =
//				int index_soke = cookieStr.indexOf(";", index);// ;
//				String msg =cookieStr.substring(index_equal+1, index_soke);
//				
//				// for debug need to delete
//				Util.showToast(mActivity, msg);
//			}
//			else {
//				//没有登录注册
//			}
//		}
////		closeDialog();
//	}



}
