/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.AlertDialogUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-24
 *@Version:1.1.0
 */
public class FenBianLv  extends Activity{
	
	TextView mTextView = null;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_fenbianlv);

		getFenBianLv();
	}
	
	private void getFenBianLv() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;

		mTextView = (TextView) this.findViewById(R.id.id_tv_fenbianlv);
		mTextView.setText(widthPixels + ":" + heightPixels);
	}
	
	public static int getDensityDpi(Context context){
		DisplayMetrics metrics = new DisplayMetrics(); 
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics); 
	    return metrics.densityDpi; 
	}
	
	public String str (){
		String str = "";

		DisplayMetrics dm = new DisplayMetrics();

		dm = this.getApplicationContext().getResources().getDisplayMetrics();

		int screenWidth = dm.widthPixels;

		int screenHeight = dm.heightPixels;

		float density = dm.density;
		
		float densityDpi = dm.densityDpi;

		float xdpi = dm.xdpi;

		float ydpi = dm.ydpi;

		str += "屏幕分辨率为:" + dm.widthPixels + " * " + dm.heightPixels + "\n";

		str += "绝对宽度:" + String.valueOf(screenWidth) + "pixels\n";

		str += "绝对高度:" + String.valueOf(screenHeight)

		+ "pixels\n";

		str += "逻辑密度:" + String.valueOf(density)

		+ "\n";
		
		str +="密度dpi"+String.valueOf(densityDpi)+"\n";
		

		str += "X 维 :" + String.valueOf(xdpi) + "像素每英尺\n";

		str += "Y 维 :" + String.valueOf(ydpi) + "像素每英尺\n";
		return str;
	}
	
}













