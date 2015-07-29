package com.lpf.mysuperdemo.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;

import com.lpf.mysuperdemo.interfaces.AlertDialogInterface;

public class AlertDialogUtil {

	public static void showDialog(final Context context, String title,
			String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
//		builder.set
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				((AlertDialogInterface) context).alertConfirm(dialog);
//				version=a;
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				((AlertDialogInterface) context).alertCancel(dialog);
			}
		});

		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		// 发现如果用户按了KeyEvent.KEYCODE_SEARCH,对话框还是会Dismiss掉,这里的setOnKeyListener作用就是屏蔽用户按下KeyEvent.KEYCODE_SEARCH
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else if(keyCode == KeyEvent.KEYCODE_BACK){
					((AlertDialogInterface) context).alertCancel(dialog);
					dialog.dismiss();
					return false;
				}				
				else {
					return false; // 默认返回 false
				}
			}
		});

		dialog.show();
		
		
	}
}
