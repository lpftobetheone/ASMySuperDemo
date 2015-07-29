package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.MyWebChromeClient;
import com.lpf.mysuperdemo.util.MyWebViewClient;
import com.lpf.mysuperdemo.util.NetworkUtil;
import com.lpf.mysuperdemo.util.Util;

public class CommonWebClientActivity extends Activity {
	
	private WebView web;
	private String url;
	private MyWebViewClient myWebViewClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_huodong);
		
		url = getIntent().getStringExtra("detailUrl");
		
		initWebView(url);
	}

	private void initWebView(String url) {
		// TODO Auto-generated method stub
		web = (WebView)this.findViewById(R.id.id_huodong_webview);
		
		myWebViewClient = new MyWebViewClient(CommonWebClientActivity.this);
		
		Util.setVewSettings(web.getSettings());
		web.setWebViewClient(myWebViewClient);
		web.setWebChromeClient(new MyWebChromeClient());
		web.addJavascriptInterface(new HomeInterFace(), "HomeIntent");
		
		web.setDownloadListener(new MyWebViewDownLoadListener());
		
		if(NetworkUtil.isNetworkConnected(this)){
			if(url==null||url.equals("")){
				web.loadUrl("file:///android_asset/networkerror/404.html");
			}else{
				web.loadUrl(url);
			}
		}else{
			web.loadUrl("file:///android_asset/networkerror/index.html");
		}
		
//		web.loadUrl("");
		
		
	}
	
	class HomeInterFace{
		@JavascriptInterface
		public void goBack() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(web.canGoBack()){
						web.goBack();
					}else {
						finish();
					}
				}
			});
		}
//		//返回首页
//		@JavascriptInterface
//		public void goHome() {
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					startActivity(new Intent(CommonWebClientActivity.this, MainActivityShop.class));
//					finish();
//				}
//			});
//		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		myWebViewClient.closeDialog();
	}
	
	private class MyWebViewDownLoadListener implements DownloadListener{  
		  
        @Override  
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,  
                                    long contentLength) {             
            Uri uri = Uri.parse(url);  
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);   
            finish();
        }  
    }  
	
}
