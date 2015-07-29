package com.lpf.mysuperdemo;

import java.io.File;
import java.net.MalformedURLException;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MainApplication extends Application {

//	Tracker piwikTracker;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		initErrorHandler();
		initImageLoader(getApplicationContext());
	}
	
	
	
//	public synchronized Tracker getTracker(){
//	    if (piwikTracker != null) {
//	        return piwikTracker;
//	    }
//	    Piwik analytics = Piwik.getInstance(this);
//	    /**
//	     *     siteid
//		 *	商城APP 测试  16
//		 *	商城APP 正式  20
//		 *	这是商城app piwik的siteid在测试环境和正式环境的不同值
//	     * */
//	    try {
//			piwikTracker = (analytics.newTracker("http://click.fm365.com/collect", 20));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	    return piwikTracker;
//	}
//	
//	/** 捕获全局错误异常 */
//	private void initErrorHandler() {
//		CrashHandler handler = CrashHandler.getInstance();
//		handler.init(this);
//	}
	
	/** 初始化ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"lenovo1/Cache");// 获取到缓存的目录地址
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new WeakMemoryCache())
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiscCache(cacheDir)).writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
}
