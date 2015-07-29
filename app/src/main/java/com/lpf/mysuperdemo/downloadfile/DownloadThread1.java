/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.downloadfile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-29
 *@Version:1.1.0
 */
public class DownloadThread1 extends Thread{

	private static final String TAG = "DownloadThread1";
	private File saveFile;
	private URL downUrl;
	private int block;
	private int threadId = -1;
	private int downloadedLength;
	private boolean finished = false;
	private FileDownloader1 downloader;
	
	/**
	 * @param saveFile
	 * @param downUrl
	 * @param block
	 * @param threadId
	 * @param downloadedLength
	 * @param finished
	 * @param fileDownloader1
	 */
	public DownloadThread1(FileDownloader1 downloader,URL downUrl,
			File saveFile,int block,int downloadedLength,int threadId)
	{
		this.downUrl=downUrl;
		this.saveFile=saveFile;
		this.block=block;
		this.downloader= downloader;
		this.threadId=threadId;
		this.downloadedLength=downloadedLength;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		if(downloadedLength < block){
			try {
				HttpURLConnection http = (HttpURLConnection)downUrl.openConnection();
				int[] position = ConfigHttpURLConnection(http);
				
				InputStream inStream = http.getInputStream();
				byte[] buffer = new byte[1024];
				int offset = 0;
				if(position!=null){
					print("Thread"+this.threadId+"starts to download from position"+position[0]);
				}
				RandomAccessFile threadFile = new RandomAccessFile(this.saveFile,"rwd");
				threadFile.seek(position[0]);
				while(!downloader.getExited() && (offset =inStream.read(buffer,0,1024))!=-1){
					threadFile.write(buffer,0,offset);
					downloadedLength += offset;
					downloader.update(this.threadId, downloadedLength);
					downloader.append(offset);
				}
				threadFile.close();
				inStream.close();
				if(downloader.getExited()){
					print("Thread"+this.threadId+"has been paused");
				}else{
					print("Thread"+this.threadId+"download finished");
				}
				this.finished = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.downloadedLength = -1;
				print("Thread"+this.threadId+":"+e);
			}
		}
	}

	/**
	 * @param http
	 * @Description:
	 */
	private int[] ConfigHttpURLConnection(HttpURLConnection http) {
		// TODO Auto-generated method stub
		int[] position = new int[2];
		try {
			http.setConnectTimeout(5*1000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg,file/zip"
					+ "application/x-shockwave-flash,application/xaml+xml,application/vnd.ms-xpsdocument,"
					+ "application/x-ms-xbap,application/x-ms-application,"
					+ "application/vnd.ms-excel,application/vnd.ms-powerpoint,"
					+ "application/vnd.msword,*/*");
			http.setRequestProperty("accept-Language", "zh-CN");
			http.setRequestProperty("Referer", downUrl.toString());
			http.setRequestProperty("Charset", "UTF-8");
			int startPos = block * (threadId -1) + downloadedLength;
			int endPos = block*threadId-1;
			http.setRequestProperty("Rnage", "bytes="+startPos+"-"+endPos);
			http.setRequestProperty("User-Agent","Mozilla/4.0(compatible;"
					+ "MSIE 8.0;Windows NT 5.2;Trident/4.0;.NET CLR 1.1.4322;"
					+ ".NET CLR 2.0.50727;.NET CLR 3.0.04506.30;.NET CLR 3.0.4506.2152;"
					+ ".NET CLR 3.5.30729)");
			http.setRequestProperty("Connection", "Keep-Alive");
			position[0] = startPos;
			position[1] = endPos;
			return position;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return position;
		}
		
	}
	
	private static void print(String msg){
		Log.i(TAG,msg);
	}
	
	private boolean isFinished(){
		return finished;
	}
	
	public long getDownloadedLength(){
		return downloadedLength;
	}
}
















