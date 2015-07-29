/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.downloadfile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-29
 * @Version:1.1.0
 */
public class FileDownloader1 {

	private static final String TAG = "FileDownloader";
	private static final int RESPONSEOK = 200;

	private Context context;
	private FileService fileService;
	private boolean exited;
	private int downloadedSize = 0;
	private int fileSize = 0;

	private DownloadThread1[] threads;

	private File saveFile;
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	private int block;
	private String downloadUrl;

	public int getThreadSize() {
		return threads.length;
	}

	public void exit() {
		this.exited = true;
	}

	public boolean getExited() {
		return this.exited;
	}

	public int getFileSize() {
		return fileSize;
	}

	protected synchronized void append(int size) {
		downloadedSize += size;
	}

	protected synchronized void update(int threadId, int pos) {
		this.data.put(threadId, pos);
		this.fileService.update(this.downloadUrl, threadId, pos);
	}

	public FileDownloader1(Context context, String downloadUrl,
			File fileSaveDir, int threadNum) {
		this.context = context;
		this.downloadUrl = downloadUrl;

		try {
			fileService = new FileService(this.context);
			URL url = new URL(this.downloadUrl);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			this.threads = new DownloadThread1[threadNum];
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			// 设置客户端可以接受的媒体类型
			conn.setRequestProperty(
					"Accept",
					"image/gif,image/jpeg,image/pjpeg,"
							+ "application/x-shockwave-flash,application/xaml+xml,application/vnd.ms-xpsdocument,"
							+ "application/x-ms-xbap,application/x-ms-application,"
							+ "application/vnd.ms-excel,application/vnd.ms-powerpoint,"
							+ "application/vnd.msword,*/*");
			// 设置客户端语言
			conn.setRequestProperty("Accept-Language", "zh-CN");
			// 设置请求的来源页面，便于服务端进行来源统计
			conn.setRequestProperty("Referer", downloadUrl);
			// 设置客户端编码
			conn.setRequestProperty("Charset", "UTF-8");
			// 设置用户代理
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0(compatible;"
							+ "MSIE 8.0;Windows NT 5.2;Trident/4.0;.NET CLR 1.1.4322;"
							+ ".NET CLR 2.0.50727;.NET CLR 3.0.04506.30;.NET CLR 3.0.4506.2152;"
							+ ".NET CLR 3.5.30729)");
			// 设置Connection的方式
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			printResponseHeader(conn);
			if (conn.getResponseCode() == RESPONSEOK) {
				this.fileSize = conn.getContentLength();
				if (this.fileSize <= 0)
					throw new RuntimeException("Unknown file size");
				String filename = getFileName(conn);
				this.saveFile = new File(fileSaveDir, filename);
				Map<Integer, Integer> logdata = fileService
						.getData(downloadUrl);
				if (logdata.size() > 0) {
					for (Map.Entry<Integer, Integer> entry : logdata.entrySet()) {
						data.put(entry.getKey(), entry.getValue());
					}
				}
				if (this.data.size() == this.threads.length) {
					for (int i = 0; i < this.threads.length; i++) {
						this.downloadedSize += this.data.get(i);
					}
					print("已经下载的长度" + this.downloadedSize + "个字节");
				}
				this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize
						/ this.threads.length
						: this.fileSize / this.threads.length + 1;
			} else {
				print("服务器响应错误" + conn.getResponseCode()
						+ conn.getResponseMessage());
				throw new RuntimeException("server response error");
			}
		} catch (Exception e) {
			print(e.toString());
			throw new RuntimeException("Cannot Connection this url");
		}
	}

	private String getFileName(HttpURLConnection conn) {
		String filename = this.downloadUrl.substring(this.downloadUrl
				.lastIndexOf("/") + 1);
		if (filename == null || "".equals(filename.trim())) {
			for (int i = 0;; i++) {
				String mine = conn.getHeaderField(i);
				if (mine == null) {
					break;
				}
				if ("content-disposition".equals(conn.getHeaderFieldKey(i)
						.toLowerCase())) {
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(
							mine.toLowerCase());
					if (m.find()) {
						return m.group(1);
					}
				}
			}
			filename = UUID.randomUUID() + ".tmp";
		}
		return filename;
	}

	public int download(DownloadProgressListener listener) throws Exception{
		try{
			RandomAccessFile randOut = new RandomAccessFile(this.saveFile,"rwd");
			if(this.fileSize > 0){
				randOut.setLength(this.fileSize);
			}
			randOut.close();
			URL url = new URL(this.downloadUrl);
			if(this.data.size() != this.threads.length){
				this.data.clear();
				for(int i =0; i<this.threads.length; i++){
					this.data.put(i+1, 0);
				}
				this.downloadedSize = 0;
			}
			
			for(int i =0; i<this.threads.length; i++){
				int downloadedLength = this.data.get(i+1);
				if(downloadedLength <this.block && this.downloadedSize <this.fileSize){
					this.threads[i]=new DownloadThread1(this,url,
							this.saveFile,this.block,this.data.get(i+1),i+1);					
					this.threads[i].setPriority(7);
				}else{
					this.threads[i].start();
				}
			}
			
			fileService.delete(this.downloadUrl);
			fileService.save(this.downloadUrl, this.data);
			boolean notFinished = true;
			while(notFinished){
				Thread.sleep(900);
				notFinished = false;
				for(int i =0; i<this.threads.length;i++){
					if(this.threads[i]!= null && this.threads[i].isAlive()){
						notFinished = true;
						if(this.threads[i].getDownloadedLength() == -1){
							//重新开辟下载线程
							this.threads[i]=new DownloadThread1(this,url,this.saveFile
									,this.block,this.data.get(i+1),i+1);
							//设置下载的优先级
							this.threads[i].setPriority(7);
							this.threads[i].start();
						}
					}
				}
				if(listener != null){
				listener.onDownloadSize(this.downloadedSize);
				}
			}
			if(downloadedSize == this.fileSize){
				fileService.delete(this.downloadUrl);
			}
			
		}catch(Exception e){
			print(e.toString());
			throw new Exception("文件下载异常");
		}
		return this.downloadedSize;
	}
	
	
	public static Map<String,String> getHttpResponseHeader(HttpURLConnection http){
		Map<String,String> header = new LinkedHashMap<String,String>();
		for(int i =0; ; i++){
			String fieldValue = http.getHeaderField(i);
			if(fieldValue == null){
				break;
			}
			header.put(http.getHeaderFieldKey(i), fieldValue);
		}
		return header;
	}
	
	public static void printResponseHeader(HttpURLConnection http){
		Map<String,String> header = getHttpResponseHeader(http);
		for(Map.Entry<String, String> entry:header.entrySet()){
			String key = entry.getKey() != null ? entry.getKey() +":":"";
			print(key+entry.getValue());
		}
	}
	
	private static void print(String msg){
		Log.i(TAG,msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
