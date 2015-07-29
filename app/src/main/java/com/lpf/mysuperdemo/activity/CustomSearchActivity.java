package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.adapter.SearchAdapter;
import com.lpf.mysuperdemo.views.SearchFlowLayout;


public class CustomSearchActivity extends Activity implements OnClickListener{

	private LinearLayout mLinearLayoutBack;
	private LinearLayout mLinearLayoutSearch;

	private EditText mEtSearch;
	private SearchFlowLayout mSearchFlowLayout;// 热门搜索标签
	private List<String> mValues = new ArrayList<String>(); // 热门搜索标签内容
	private ListView mListView;

	private List<String> mListData = new ArrayList<String>();
	private SearchAdapter mListAdapter;
	
	private LayoutInflater mInflater;
	private View mHeaderView;
	private View mFooterView;

	Set<String> set = new HashSet<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mLinearLayoutBack = (LinearLayout) findViewById(R.id.id_search_back);
		mLinearLayoutSearch = (LinearLayout) findViewById(R.id.id_search_text);
		mEtSearch = (EditText) findViewById(R.id.et_search);
		mSearchFlowLayout = (SearchFlowLayout) findViewById(R.id.search_hot_search);
		mListView = (ListView) findViewById(R.id.search_historylist);
		
		mLinearLayoutBack.setOnClickListener(this);
		mLinearLayoutSearch.setOnClickListener(this);
		
		mInflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//从SharedPreferences中获取搜索历史记录
		getFromSharedPreference();

		//热门搜索
		initHotSearch();

		//历史搜索
		initHistorySearch();

	}

	/**
	 * 热门搜索标签
	 */
	private void initHotSearch() {
		// TODO Auto-generated method stub
		mValues = setHotSearchTags();
		LayoutInflater mInflater = LayoutInflater.from(this);
		for (int i = 0; i < mValues.size(); i++) {
			final TextView mHotText = (TextView) mInflater.inflate(
					R.layout.search_textview, mSearchFlowLayout, false);
			mHotText.setText(mValues.get(i));
			mHotText.setTextSize(12);
			mSearchFlowLayout.addView(mHotText);
			mHotText.setOnClickListener(new View.OnClickListener() {
				// 点击热门标签进行搜索
				@Override
				public void onClick(View v) {
					addToSharedPreference(mHotText.getText().toString());
					showList();
					goToSearchResultActivity();
				}
			});
		}

	}
	
	/**
	 * 
	 * @Description:
	 */
	private List<String> setHotSearchTags() {
		// TODO Auto-generated method stub
		mValues.add("测试1");
		mValues.add("测试1测试1");
		mValues.add("测试2");
		mValues.add("测试2测试2");
		mValues.add("测试33333333");
		return mValues;
	}

	/**
	 * 历史搜索列表
	 */
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void initHistorySearch() {
		mListAdapter = new SearchAdapter(CustomSearchActivity.this,mListData);
		
		mHeaderView = mInflater.inflate(R.layout.activity_search_list_header, null);
		mFooterView = mInflater.inflate(R.layout.activity_search_list_footer, null);
		mListView.addHeaderView(mHeaderView);
		mListView.addFooterView(mFooterView);
		Button mListClear = (Button) mFooterView.findViewById(R.id.search_listClear);
		
		mListClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//清空SharedPreferences
				clearSharedPreference();

				// 对搜索列表进行清除
				mListData.clear();
				mListAdapter.notifyDataSetChanged();
				// 是否显示历史搜索列表
				showList();
			}
		});
		//要在addFooterView之后调用
		mListView.setAdapter(mListAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				addToSharedPreference((String)mListView.getItemAtPosition(position));
				goToSearchResultActivity();
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
			if (TextUtils.isEmpty(mEtSearch.getText())) {
				Toast.makeText(CustomSearchActivity.this, R.string.search_is_null,
						Toast.LENGTH_SHORT).show();
			} else {
				//搜索内容不为空，将其添加到SharedPreferences中，同时更新ListView列表
				addToSharedPreference(mEtSearch.getText().toString());
				showList();
				goToSearchResultActivity();
			}
			break;
		case R.id.id_search_back:
			this.finish();
			break;
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

		/*if(set.contains(keyValue)){
			set.remove(keyValue);
		}
		set.add(keyValue);
		editor.putStringSet("searchKey", set);*/
		
		//判断搜索记录中是否已经存在该搜索内容
		String searchStr = getSp.getString("history", "");
		String[] searchArrays = searchStr.split(",");
		int startPosition = 0;
		int endPosition = 0;
		for(int i =0; i<searchArrays.length; i++){
			if(searchArrays[i].equals(keyValue)){
				//得到重复字符串的终点位置
				endPosition = startPosition + keyValue.length()+1; 	
				searchStr = searchStr.substring(0,startPosition)+searchStr.substring(endPosition);
				//从搜索历史列表中移除重复搜索关键字
				mListData.remove(keyValue);
				break;
			}
			startPosition += searchArrays[i].length()+1;
		}
		
		/*if(searchStr.length() > 0){
			if(searchStr.contains(keyValue)){
				searchStr.replace(keyValue+",", "");
				mListData.remove(keyValue);
			}
		}*/
		StringBuilder sb = new StringBuilder(searchStr);
		sb.append(keyValue+",");
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
			/*Set<String> keyValues = getSp.getStringSet("searchKey", null);
			// 如果存在搜索记录
			if (keyValues != null) {
				Iterator<String> it = keyValues.iterator();
				while (it.hasNext()) {
					String keyValue = it.next();
					set.add(keyValue);
					mListData.add(0, keyValue);
				}
			}*/
			
			String searchStr = getSp.getString("history", "");
			String[] searchArrays = searchStr.split(",");
			mListData.clear();
			
			for(int i =0; i<searchArrays.length;i++)
			{
				//如果不为空，则加入到List中
				if(!searchArrays[i].equals("")){
					mListData.add(0,searchArrays[i]);
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
	
	private void goToSearchResultActivity(){
		Intent intent = new Intent(CustomSearchActivity.this,CustomSearchResultActivity.class);
		startActivity(intent);
	}

}
