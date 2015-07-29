package com.lpf.mysuperdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lpf.mysuperdemo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageUtil {

	//初始化Imageloader，设定缓存大小
	public static void InitImageLoader(Context context){
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
	
	public static void DisplayImage(String path,ImageView image){
		ImageLoader.getInstance().displayImage(path, image, getListOptions());
	}
	

	 public static DisplayImageOptions getListOptions() {
	        DisplayImageOptions options = new DisplayImageOptions.Builder()
	                //加载过程中显示的图片
	                .showImageOnLoading(R.drawable.default_img1)
	                //空uri显示的图片
	                .showImageForEmptyUri(R.drawable.default_img1)
	                //加载图片失败后显示的图片
	                .showImageOnFail(R.drawable.default_img1)
	                //是否在内存中进行缓存
	                .cacheInMemory(true)
	                //是否在磁盘中进行缓存
	                .cacheOnDisk(true)
	                .considerExifParams(true)
	                //设定图片显示方式
	                 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
	                // 设定图片质量
	                .bitmapConfig(Bitmap.Config.RGB_565)
	                // .decodingOptions(android.graphics.BitmapFactory.Options
	                // decodingOptions)//
	                .considerExifParams(true)
	                // 设置延迟部分时间才开始加载，默认为0
	                // .delayBeforeLoading(int delayInMillis)//int
	                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
	                // .displayer(new RoundedBitmapDisplayer(20))//是否设置圆角，弧度大小
	                .displayer(new FadeInBitmapDisplayer(100))//图片加载好后的渐入时间
	                .build();
	        return options;
	    }
}
