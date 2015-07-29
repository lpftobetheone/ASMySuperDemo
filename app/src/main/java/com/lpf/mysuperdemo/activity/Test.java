/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;
/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-8
 * @Version:1.1.0
 */
public class Test extends Activity {
	
	public ProgressDialog myDialog = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		

		// 用默认浏览器打开扫描得到的地址
		
//		String url = "";
//		if(IsValidUrl(url)){
//			Intent intent = new Intent();
//			intent.setAction("android.intent.action.VIEW");
//			Uri content_url = Uri.parse("http://www.baidu.com");
//			intent.setData(content_url);
//			startActivity(intent);
//		}else{
//			
//		}
		
	}

	public boolean IsValidUrl(String url) {
		Pattern pattern = Pattern
				.compile("http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
}
