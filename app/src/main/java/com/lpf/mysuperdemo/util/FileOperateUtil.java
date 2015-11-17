package com.lpf.mysuperdemo.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 * 
 * @author liupf5
 * 
 */
public class FileOperateUtil {

	public static String TAG = "FileOperateUtil";

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	// 保存BitMap图片
	public static void saveBitmap(Bitmap bitmap, String tempDirName,
			String picName) {
		Log.d(TAG, SDPATH);

		try {
			if (!isFileExist(tempDirName, picName)) {
				createSDDir(picName);
			}
			File newFile = new File(SDPATH, picName + ".jpg");
			if (newFile.exists()) {
				newFile.delete();
			}
			FileOutputStream out = new FileOutputStream(newFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e(TAG, "已经保存了");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 保存文件
	 * 
	 * @param dirName
	 *            文件夹名称
	 * @param fileName
	 *            文件名称
	 * @param type
	 *            文件类型
	 */
	public static void saveFile(String dirName, String fileName, String type) {
		Log.e(TAG, "保存文件");
		Log.d(TAG, SDPATH);
		try {
			if (!isFileExist(dirName, fileName)) {
				createSDDir(dirName);
			}
			File newFile = new File(SDPATH + dirName, fileName + "." + type);
			if (newFile.exists()) {
				newFile.delete();
			}
			FileOutputStream out = new FileOutputStream(newFile);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 判断一个文件是否存在
	public static boolean isFileExist(String dirName, String fileName) {
		File file = new File(SDPATH + dirName + "/" + fileName);
		file.isFile();
		return file.exists();
	}

	// 创建文件目录
	public static File createSDDir(String tempDirName) throws IOException {
		File dirName = new File(SDPATH + tempDirName);
		// 如果存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			System.out.println("Dir Path:" + dirName.getAbsolutePath());
			System.out.println("create Dir:" + dirName.mkdir());
		}
		return dirName;
	}

	/**
	 * 删除文件或者文件夹下所有文件
	 */
	public static void deleteDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		if (dir == null || !dir.exists() || !dir.isDirectory()) {
			Log.e(TAG, "文件或文件夹不存在");
		}
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete(); // 删除文件
			} else if (file.isDirectory()) {
				String subDirName = dirName +"/";
				subDirName += file.getName();
				deleteDir(subDirName); // 删除文件夹下所有文件
			}
		}
		dir.delete();// 删除文件目录
	}

	/**
	 * 删除文件
	 * 
	 * @param dirName
	 *            目录
	 * @param fileName
	 *            名称
	 */
	public static void deleteFile(String dirName, String fileName) {
		File file = new File(SDPATH + dirName + "/" + fileName);
		if (file.exists()) {
			file.delete();
		} else {
			Log.e(TAG, "文件不存在");
		}
	}

}
