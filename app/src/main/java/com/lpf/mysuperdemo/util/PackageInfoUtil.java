package com.lpf.mysuperdemo.util;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageInfoUtil {

	/*
	 * 获取当前程序的包名
	 */
	public static String getVersionName(Context context)
			throws NameNotFoundException {
		return context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0).packageName;
	}

	/*
	 * 获取当前程序的版本号
	 */
	public static String getVersion(Context context)
			throws NameNotFoundException {
		return context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0).versionName;
	}
}
