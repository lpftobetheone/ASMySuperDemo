/**
 * @author wangjian   
 */
package com.lpf.mysuperdemo.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Caichunfeng
 */
public class RequestUtil {

	private VolleyResponseListener volleyResponseListener;

	private int mMethod = Method.POST;

	private Context mContext;

	private int selectType;

	private boolean showDialogFlag;

	public void setShowDialogFlag(boolean showDialogFlag) {
		this.showDialogFlag = showDialogFlag;
	}

	public RequestUtil(Context context) {
		this.mContext = context;

	}

	public RequestUtil(Context context, int selectType) {
		this.mContext = context;
		this.selectType = selectType;
	}

	/**
	 * 获取模块数据
	 */
	public StringRequest getData(String url, final Map<String, String> params,
			VolleyResponseListener responseListener) {
		return getData(url, params, responseListener, true);
	}

	/**
	 * 获取模块数据
	 */
	public StringRequest getData(String url, final Map<String, String> params,
			boolean showDialog) {
		return getData(url, params, null, showDialog);
	}

	/**
	 * 获取模块数据
	 */
	public StringRequest getData(String url, final Map<String, String> params) {
		return getData(url, params, null, true);
	}

	/**
	 * 获取模块数据
	 */
	public StringRequest getData(String url, final Map<String, String> params,
			VolleyResponseListener responseListener, boolean showDialog) {
		StringRequest stringRequest = null;
		try {
			this.volleyResponseListener = responseListener;
			this.showDialogFlag = showDialog;
			
			stringRequest = new StringRequest(mMethod, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							
//							try {
//								Gson gson = new Gson();
//								BaseResponseBean result = gson.fromJson(response,					
//										BaseResponseBean.class);
//							} catch (Exception e) {
//								Util.showToast(mContext,  "网络异常！请检查网络");
//							}
							

							if (volleyResponseListener != null) {
								volleyResponseListener.onResponse(response,selectType);
							}
							
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							error.printStackTrace();
//							Util.showToast(mContext, "网络异常，请稍后再试!");
							if (volleyResponseListener != null) {
								volleyResponseListener.onError(error,selectType);
							}
						}
					}) 
			
			{
				protected Map<String, String> getParams()
						throws AuthFailureError {
					return params;
				}
				@Override
				protected Response<String> parseNetworkResponse(NetworkResponse response) {
				    String parsed=null;
//				    try {
////				        parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//				    	 parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
////				    	parsed = new String(response.data);
////						Log.v("debug_test", parsed);
////						Log.v("debug_test", HttpHeaderParser.parseCharset(response.headers));
//				    } catch (UnsupportedEncodingException e) {
//				        parsed = new String(response.data);
//				    }catch (Exception je) {
//	                		Log.e("debug_test", "error ", je);
//	                        return Response.error(new ParseError(je));
//
//	                }
////				    parsed = new String(response.data);
				    
				    try {
						parsed = new String(response.data,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 把请求（在这个例子中请求为StringRequest）添加到请求队列
		return stringRequest;
	}
	
	/**
	 * 获取模块数据，访问数据请求时加入cookie（此时只传入了JSESSIONID值）
	 */
	public StringRequest getData(String url, final Map<String, String> params, final String cookie,
			VolleyResponseListener responseListener, boolean showDialog) {
		StringRequest stringRequest = null;
		try {
			this.volleyResponseListener = responseListener;
			this.showDialogFlag = showDialog;
			
			stringRequest = new StringRequest(mMethod, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							if (volleyResponseListener != null) {
								volleyResponseListener.onResponse(response,selectType);
							}
							
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							error.printStackTrace();
//							Util.showToast(mContext, "网络异常，请稍后再试!");
							if (volleyResponseListener != null) {
								volleyResponseListener.onError(error,selectType);
							}
						}
					}) 
			
			{
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap<String,String> localHashMap = new HashMap<String,String> ();
					localHashMap.put("Cookie", cookie);
					return localHashMap;
				}
				protected Map<String, String> getParams()
						throws AuthFailureError {
					return params;
				}
				@Override
				protected Response<String> parseNetworkResponse(NetworkResponse response) {
				    String parsed=null;
				    try {
						parsed = new String(response.data,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 把请求（在这个例子中请求为StringRequest）添加到请求队列
		return stringRequest;
	}

	public void setVolleyResponseListener(
			VolleyResponseListener volleyResponseListener) {
		this.volleyResponseListener = volleyResponseListener;
	}

	public void setMethod(int method) {
		this.mMethod = method == Method.POST ? Method.POST : Method.GET;
	}

	public interface VolleyResponseListener {

		public void onResponse(String response, int selectType);

		public void onError(VolleyError error, int selectType);

	}
}
