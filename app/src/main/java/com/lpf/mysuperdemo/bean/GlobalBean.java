package com.lpf.mysuperdemo.bean;

import java.util.ArrayList;
import java.util.List;

public class GlobalBean {

	public List<ScrollPictureList> mScrollPictureList = new ArrayList<ScrollPictureList>();
	
	private static class GlobalBeanHolder {
		private static final GlobalBean instance = new GlobalBean();
	}

	private GlobalBean() {
	}

	public static final GlobalBean getInstance() {
		return GlobalBeanHolder.instance;
	}
}
