package com.lpf.mysuperdemo.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lpf.mysuperdemo.R;

public class CustomProgressDialog extends Dialog {

	View progress;
	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		progress = LayoutInflater.from(context).inflate(R.layout.loading, null);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(progress);
	}

}
