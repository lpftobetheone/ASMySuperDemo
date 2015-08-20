package com.lpf.mysuperdemo.util;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoadFile {
	public void download(String urlPath,Handler handler,ProgressBar pb) {
		FileOutputStream os = null;
		try {
			URL url = new URL(urlPath);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int code = conn.getResponseCode();

			if (code == 200) {
				int total = conn.getContentLength();
				pb.setMax(total);
				InputStream is = conn.getInputStream();

				File fileDir = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath(),
						Global.PATH);
				
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				File apkFile = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath(),
						Global.PATH + Global.APKNAME);

				if (!apkFile.exists()) {
					apkFile.createNewFile();
				}

				os = new FileOutputStream(apkFile);

				int len = -1;
				byte[] b = new byte[1024 * 4];
				int num_total = 0;
				while ((len = is.read(b)) != -1) {
					os.write(b, 0, len);
					os.flush();
					num_total += len;
					Message msg = Message.obtain();
					msg.what = Global.DOWN_LOAD_SUCESS;
					msg.obj = num_total;
					handler.sendMessage(msg);
				}

				os.flush();
				os.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Message msg = Message.obtain();
			msg.what = Global.DOWN_LOAD_FAIL;
			handler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
			Message msg = Message.obtain();
			msg.what = Global.DOWN_LOAD_FAIL;
			handler.sendMessage(msg);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				os = null;
			}
		}
	}
}
