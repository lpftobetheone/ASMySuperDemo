package com.lpf.mysuperdemo.splash_welcome;

import java.io.Serializable;

public class PackageInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appkey;
	private String verb;
	private String path;
	private String apkid;
	private int    updatetype;
	private String remark;
	private String ico;
	
	public String getApkid() {
		return apkid;
	}
	public void setApkid(String apkid) {
		this.apkid = apkid;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	
	public int getUpdatetype() {
		return updatetype;
	}
	public void setUpdatetype(int updatetype) {
		this.updatetype = updatetype;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}

}
