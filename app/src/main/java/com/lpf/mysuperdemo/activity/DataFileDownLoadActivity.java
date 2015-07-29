/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.downloadfile.DownloadProgressListener;
import com.lpf.mysuperdemo.downloadfile.FileDownloader;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-28
 * @Version:1.1.0
 */
public class DataFileDownLoadActivity extends Activity implements OnClickListener{

	private static final int PROCESSING = 1;
	private static final int FAILURE = -1;

	private EditText pathText;
	private TextView resultView;

	private Button downloadButton;
	private Button stopButton;

	private ProgressBar progressBar;
	
	private DownloadTask task;
	private Thread myThread;
	private FileDownloader loader;

	private Handler handler = new UIHandler();

	private final class UIHandler extends Handler {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PROCESSING:
				int size = msg.getData().getInt("size");
				progressBar.setProgress(size);
				float num = (float) progressBar.getProgress()
						/ (float) progressBar.getMax();
				int result = (int) (num * 100);
				resultView.setText(result + "%");
				if (progressBar.getProgress() == progressBar.getMax()) {
					Toast.makeText(DataFileDownLoadActivity.this, "下载完成",
							Toast.LENGTH_LONG).show();
				}
				break;
			case -1:
				Toast.makeText(DataFileDownLoadActivity.this, "下载失败",
						Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloadfile);
		
		initViews();
	}
	
	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		pathText = (EditText)this.findViewById(R.id.path);
		resultView = (TextView)this.findViewById(R.id.resultView);
		downloadButton = (Button)this.findViewById(R.id.downloadbutton);
		stopButton = (Button)this.findViewById(R.id.stopbutton);
		
		progressBar = (ProgressBar)this.findViewById(R.id.progressBar);
		downloadButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.downloadbutton:
			String path = pathText.getText().toString();
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				String saveDirStr = Environment.getExternalStorageDirectory().getAbsolutePath()+"/abcd";
				File saveDir = new File(saveDirStr);
				download(path,saveDir);
			}else
			{
				Toast.makeText(DataFileDownLoadActivity.this, "SDCard读取错误",
						Toast.LENGTH_LONG).show();
			}
			downloadButton.setEnabled(false);
			stopButton.setEnabled(true);
			break;
		case R.id.stopbutton:
			exit();
			downloadButton.setEnabled(true);
			stopButton.setEnabled(false);
			break;
		default:
			break;
		}
	}
	
	public void exit(){
		if(task!=null){
			task.exit();
		}
	}
	
	private void download(String path, File saveFile){
		task = new DownloadTask(path,saveFile);
		myThread = new Thread(task);
		myThread.start();
	}
	
	private final class DownloadTask implements Runnable{
		private String path;
		private File saveDir;
		/**
		 * @param path
		 * @param saveDir
		 */
		public DownloadTask(String path, File saveDir) {
			this.path = path;
			this.saveDir = saveDir;
		}
		
		public void exit(){
			if(loader != null){
				loader.exit();
			}
		}
		
		DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
			
			@Override
			public void onDownloadSize(int size) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = PROCESSING;
				msg.getData().putInt("size", size);
				handler.sendMessage(msg);
			}
		};
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				loader = new FileDownloader(DataFileDownLoadActivity.this,path,saveDir,2);
				progressBar.setMax(loader.getFileSize());
				loader.download(downloadProgressListener);
			}catch(Exception e){
				e.printStackTrace();
				handler.sendMessage(handler.obtainMessage(FAILURE));
			}
		}
		
	}

}
