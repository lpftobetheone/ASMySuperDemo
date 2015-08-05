package com.lpf.mysuperdemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.adapter.SearchAdapter;
import com.lpf.mysuperdemo.interfaces.INetObserver;
import com.lpf.mysuperdemo.util.Global;
import com.lpf.mysuperdemo.util.JsonUtil;
import com.lpf.mysuperdemo.util.MySingleton;
import com.lpf.mysuperdemo.util.NetworkUtil;
import com.lpf.mysuperdemo.util.PreferencesUtil;
import com.lpf.mysuperdemo.util.RequestUtil;
import com.lpf.mysuperdemo.views.SearchFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CustomSearchActivity extends Activity implements OnClickListener,INetObserver{

	private LinearLayout mLinearLayoutBack;
	private LinearLayout mLinearLayoutSearch;

	private EditText mEtSearch;
	private SearchFlowLayout mSearchFlowLayout; // 热门搜索标签
	private TextView mExchange; // 换一批
	private List<String> mValues = new ArrayList<String>(); // 热门搜索标签内容
	private List<String> mShowValues = new ArrayList<String>();
	private ListView mListView;

	private List<String> mListData = new ArrayList<String>();
	private SearchAdapter mListAdapter;

	private LayoutInflater mInflater;
	private View mHeaderView;
	private View mFooterView;

	private RequestQueue mQueue;
	private RequestUtil request;
	private Map<String, String> params = new HashMap<String, String>();

	Set<String> set = new HashSet<String>();
	private int pageSize = 5; // 一次显示多少个标签
	private int counter = 0; // 记录点击换一批的次数
	private int num = 0; // 返回的数据有多少页

	// 接受数据，更新ListView数据适配器
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					setSearchFlowLayout(mShowValues);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		mQueue = MySingleton.getInstance(CustomSearchActivity.this).getRequestQueue();

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mLinearLayoutBack = (LinearLayout) findViewById(R.id.id_search_back);
		mLinearLayoutSearch = (LinearLayout) findViewById(R.id.id_search_text);
		mEtSearch = (EditText) findViewById(R.id.et_search);
		mSearchFlowLayout = (SearchFlowLayout) findViewById(R.id.search_hot_search);
		mListView = (ListView) findViewById(R.id.search_historylist);
		mExchange = (TextView) findViewById(R.id.id_search_exchange);

		mExchange.setOnClickListener(this);
		mLinearLayoutBack.setOnClickListener(this);
		mLinearLayoutSearch.setOnClickListener(this);

		mInflater = (LayoutInflater) getBaseContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		// 从SharedPreferences中获取搜索历史记录
		getFromSharedPreference();

		// 热门搜索
		initHotSearch();

		// 历史搜索
		initHistorySearch();

	}

	/**
	 * 热门搜索标签
	 */
	private void initHotSearch() {
		// TODO Auto-generated method stub

		if (NetworkUtil.isNetworkConnected(CustomSearchActivity.this)) {
			// 从服务器获取json数据
			updateNetState(true);
		} else {
			Toast.makeText(CustomSearchActivity.this, "没有连接到网络!", Toast.LENGTH_SHORT)
					.show();
			analysisReponseData(PreferencesUtil.getString(CustomSearchActivity.this,
					"searchtags"), 1000);
		}
	}

	boolean isShow = true;

	@Override
	public void updateNetState(boolean connected) {
		// TODO Auto-generated method stub
		Log.v("c2c", "NetWork State is connected? " + connected);
		if (connected) {
			if (isShow) {
				isShow = false;
				postData(Global.URL_Search_Tag, 1000); // 换一批搜索标签
			}
		} else {
			isShow = true;
		}
	}

	/**
	 *
	 * @Description:
	 */
	private void setSearchFlowLayout(List<String> mShowValues) {
		LayoutInflater mInflater = LayoutInflater.from(this);
		mSearchFlowLayout.removeAllViews();
		for (int i = 0; i < mShowValues.size(); i++) {
			final TextView mHotText = (TextView) mInflater.inflate(
					R.layout.search_textview, mSearchFlowLayout, false);
			mHotText.setText(mShowValues.get(i));
			mHotText.setTextSize(12);
			mSearchFlowLayout.addView(mHotText);
			mHotText.setOnClickListener(new View.OnClickListener() {
				// 点击热门标签进行搜索
				@Override
				public void onClick(View v) {
					String label = mHotText.getText().toString();
					addToSharedPreference(label);
					showList();
					goToSearchResultActivityFromKey(label);
				}
			});
		}
	}

	/**
	 * 历史搜索列表
	 */
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void initHistorySearch() {
		mListAdapter = new SearchAdapter(CustomSearchActivity.this, mListData);

		mHeaderView = mInflater.inflate(R.layout.activity_search_list_header,
				null);
		mFooterView = mInflater.inflate(R.layout.activity_search_list_footer,
				null);
		mListView.addHeaderView(mHeaderView);
		mListView.addFooterView(mFooterView);
		Button mListClear = (Button) mFooterView
				.findViewById(R.id.search_listClear);

		mListClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 清空SharedPreferences
				clearSharedPreference();

				// 对搜索列表进行清除
				mListData.clear();
				mListAdapter.notifyDataSetChanged();
				// 是否显示历史搜索列表
				showList();
			}
		});
		// 要在addFooterView之后调用
		mListView.setAdapter(mListAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// addToSharedPreference((String) mListView
				// .getItemAtPosition(position-1));

				Log.v("c2c", mListData.size() + "");
				if(position == 0 || position > mListData.size()){
					return;
				}else{
					String key = mListData.get(position - 1);
					addToSharedPreference(key);
					Log.v("c2c", key);
					goToSearchResultActivityFromKey(key);
				}
			}
		});
		// 是否显示历史搜索列表
		showList();
	}

	/**
	 * 是否显示历史搜索列表
	 */
	private void showList() {
		// TODO Auto-generated method stub
		// 对当前ListView数据的个数进行判断
		if (mListData.size() > 0 && !mListData.isEmpty()) {
			mListView.setVisibility(View.VISIBLE);
		} else {
			mListView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.id_search_text:
				String str = mEtSearch.getText().toString();
				if (TextUtils.isEmpty(str) || str.trim().equals("")) {
					Toast.makeText(CustomSearchActivity.this, R.string.search_is_null,
							Toast.LENGTH_SHORT).show();
				} else {
					// 搜索内容不为空，将其添加到SharedPreferences中，同时更新ListView列表
					String key = mEtSearch.getText().toString();
					addToSharedPreference(key);
					showList();
					goToSearchResultActivityFromKey(key);
				}
				break;
			case R.id.id_search_back:
				this.finish();
				break;
			case R.id.id_search_exchange:
				changeHotSeach();
				break;
		}
	}

	/**
	 *
	 * @Description:更换一批热搜Tag标签
	 */
	private void changeHotSeach() {
		// TODO Auto-generated method stub
		mShowValues.clear();
		counter++;
		if (num > 0) {
			int start = counter % num;
			int end = (start + 1) * pageSize < mValues.size() ? (start + 1)
					* pageSize : mValues.size();
			for (int i = start * pageSize; i < end; i++) {
				mShowValues.add(mValues.get(i));
			}

			setSearchFlowLayout(mShowValues);
		}

	}

	/**
	 * 将搜索关键词保存到sharedPreference中
	 *
	 * @param keyValue
	 *            搜索词
	 */
	private void addToSharedPreference(String keyValue) {

		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = getSp.edit();

		/*
		 * if(set.contains(keyValue)){ set.remove(keyValue); }
		 * set.add(keyValue); editor.putStringSet("searchKey", set);
		 */

		// 判断搜索记录中是否已经存在该搜索内容
		String searchStr = getSp.getString("history", "");
		String[] searchArrays = searchStr.split(",");
		int startPosition = 0;
		int endPosition = 0;
		for (int i = 0; i < searchArrays.length; i++) {
			if (searchArrays[i].equals(keyValue)) {
				// 得到重复字符串的终点位置
				endPosition = startPosition + keyValue.length() + 1;
				searchStr = searchStr.substring(0, startPosition)
						+ searchStr.substring(endPosition);
				// 从搜索历史列表中移除重复搜索关键字
				mListData.remove(keyValue);
				break;
			}
			startPosition += searchArrays[i].length() + 1;
		}

		/*
		 * if(searchStr.length() > 0){ if(searchStr.contains(keyValue)){
		 * searchStr.replace(keyValue+",", ""); mListData.remove(keyValue); } }
		 */
		StringBuilder sb = new StringBuilder(searchStr);
		sb.append(keyValue + ",");
		mListData.add(0, keyValue);
		mListAdapter.notifyDataSetChanged();
		editor.putString("history", sb.toString());
		editor.commit();
	}

	/**
	 * 从SharedPreferences从获取搜索记录
	 */
	private void getFromSharedPreference() {

		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);

		if (getSp != null) {
			/*
			 * Set<String> keyValues = getSp.getStringSet("searchKey", null); //
			 * 如果存在搜索记录 if (keyValues != null) { Iterator<String> it =
			 * keyValues.iterator(); while (it.hasNext()) { String keyValue =
			 * it.next(); set.add(keyValue); mListData.add(0, keyValue); } }
			 */

			String searchStr = getSp.getString("history", "");
			String[] searchArrays = searchStr.split(",");
			mListData.clear();

			for (int i = 0; i < searchArrays.length; i++) {
				// 如果不为空，则加入到List中
				if (!searchArrays[i].equals("") && searchArrays[i] != null) {
					mListData.add(0, searchArrays[i]);
				}
			}
		}
	}

	/**
	 * 情况SharedPreference
	 */
	private void clearSharedPreference() {
		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = getSp.edit();
		if (getSp != null) {
			editor.clear();
			editor.commit();
		}
	}

	private void goToSearchResultActivityFromKey(String key) {
		// Intent intent = new Intent(CustomSearchActivity.this,
		// SearchResultActivity.class);
		// startActivity(intent);

		Intent intent = new Intent(CustomSearchActivity.this,
				CommonWebClientActivity.class);
		String searchUrl = Global.URL_Search_Key;
		searchUrl += key;
		intent.putExtra("detailUrl", searchUrl);// 修改为搜索列表页面
		startActivity(intent);

	}
//
//	private void goToSearchResultActivityFromLabel(String label) {
//		Intent intent = new Intent(CustomSearchActivity.this,
//				CommonWebClientActivity.class);
//		String searchUrl = Global.URL_Search_Key;
//		searchUrl += label;
//		intent.putExtra("detailUrl", searchUrl);// 修改为搜索列表页面
//		startActivity(intent);
//	}

	public synchronized void postData(String URL, int selectType) {
		// TODO Auto-generated method stub
		request = new RequestUtil(CustomSearchActivity.this, selectType);
		request.setMethod(Request.Method.GET);
		StringRequest stringRequest = request.getData(URL, params,
				new RequestUtil.VolleyResponseListener() {

					@Override
					public void onResponse(String response, int selectType) {
						analysisReponseData(response, selectType);
					}

					@Override
					public void onError(VolleyError error, int selectType) {
						Log.v("c2c", error + "");
						Toast.makeText(CustomSearchActivity.this, "请稍后重试!",
								Toast.LENGTH_SHORT).show();
						// analysisReponseData(PreferencesUtil.getString(CustomSearchActivity.this,
						// "searchtags"), 1000);
					}
				}, false);
		// 把请求（在这个例子中请求为StringRequest）添加到请求队列
		mQueue.add(stringRequest);
	}

	// 数据解析方法
	public void analysisReponseData(String response, int selectType) {
		switch (selectType) {
			// 换一批请求数据
			case 1000:
				PreferencesUtil.putString(CustomSearchActivity.this, "searchtags",
						response);
				if (response != null && response != "") {
					mValues = JsonUtil.json2List(response);
				}

				if (mValues != null && mValues.size() > 0) {
					num = mValues.size() / pageSize;
					if (mValues.size() % pageSize != 0) {
						num += 1;
					}
					int size = mValues.size() > pageSize ? pageSize : mValues
							.size();
					for (int i = 0; i < size; i++) {
						mShowValues.add(mValues.get(i));
					}

					mHandler.sendEmptyMessage(0);
				} else {
					Toast.makeText(CustomSearchActivity.this, "请稍后重试!",
							Toast.LENGTH_SHORT).show();
				}
				break;
		}

	}
}
