package com.lpf.mysuperdemo.util;

public class Global {
	 public static final String SERVER = "http://m.shopt.lenovo.com.cn/";
//	 public static final String SERVER = "http://m.shop.lenovo.com.cn/";
	// public static final String SERVER = "http://10.99.11.124/";
	 /** android 服务器网址 */
		public static final String SERVER_HOST = SERVER + "android/";
		public static final String SERVER_SRV = SERVER+"srv/";
	//更新版本接口的测试地址
//	public static final String BaseURL = "http://10.99.187.163:1111/WebService/Portal/APPWSPortal.asmx";
	//更新版本接口的正式地址
	 public static final String BaseURL = "http://mobile.lenovo.com.cn/ConAppService/Portal/APPWSPortal.asmx";
	// public static final String UPGRADE_MED = "GetLenovoMallAPKInfoByKey"; 

	public  static final String URL_C2CHome = SERVER_SRV+"c2cproductscarousel.do?plat=3";
	public  static final String URL_C2CPartnerApply = SERVER_SRV+"applyc2csalesmanapp.do";
	public  static final String URL_C2CIsC1 =  SERVER_SRV+"isc1.do";
	public  static final String URL_C2CPartnerType =  SERVER_SRV+"getc2cusertype.do?plat=3";
	public  static final String URL_C2COrderDetail =  SERVER_SRV+"payedOrderList.do";
//	public  static final String URL_C2CHome_Rule =  SERVER_SRV+"/android/c2c/rule-dsc.html";
	public  static final String URL_C2CAutoLogin =  SERVER_SRV+"autologin.do";
	
	public  static final String URL_Home_ImageScroll =  SERVER_HOST+"data/scroll.json";
	public  static final String URL_Home_QuickLink = SERVER_HOST+"data/quicklink.json";
	public  static final String URL_Home_starproduct = SERVER_HOST+"data/starproduct.json";
	public  static final String URL_Home_floorproducts = SERVER_HOST+"data/floorproduct.json";
	public  static final String URL_Home_advertise = SERVER_HOST+"data/c2chomead.json";
//	public  static final String URL_Search = SERVER_SRV+"data/searchtags.json";
	public  static final String URL_Search_Tag = SERVER_HOST+"data/searchtag.json";
	public  static final String URL_Search_Key = SERVER_HOST+"search-result.html?key=";
//	public  static final String URL_Search_Label = SERVER_HOST+"search-result.html?label=";

	

}



