/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-8
 *@Version:1.1.0
 */
public class CustomWidgets extends View {
	
	int ss = 0;	//图形类型

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public CustomWidgets(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomWidgets(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public CustomWidgets(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas c) {
		// TODO Auto-generated method stub
		super.onDraw(c);
		
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		p.setStrokeWidth(10);
		switch (ss) {
		case 0:
			c.drawCircle(200, 200, 100, p);
			break;
		case 1:
			c.drawRect(60, 90,360,360,p);
			break;
		case 2:
			Path path = new Path();
			path.moveTo(80, 100);
			path.lineTo(420, 250);
			path.lineTo(80, 350);
			path.close();
			c.drawPath(path,p);
		default:
			break;
		}
	}
	
	public void changeStyle(){
		ss++;
		if(ss>2){
			ss = 0;
		}
	}
	

}
