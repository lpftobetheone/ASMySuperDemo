/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;

import com.lpf.mysuperdemo.R;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-7-16
 *@Version:1.1.0
 */
public class CookieTestActivity extends Activity{

	public  static Map<String, String> cookieMap;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		cookieMap = new HashMap<String, String>();
		CookieManager cookieManager = CookieManager.getInstance();
		String cookieStr = cookieManager.getCookie("http://www.baidu.com");
		// 如果cookie存在
		if (cookieStr != null) {
			System.out.println(cookieStr);
			String[] cookieVal = cookieStr.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
					System.out.println("key:" + key + " value:" + value);
				}
			}
		} 
	}
}
