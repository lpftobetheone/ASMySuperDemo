package com.lpf.mysuperdemo.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.lpf.mysuperdemo.R;

/**
 * 右侧菜单的内容Fragment
 * @author liupf5
 *
 */
public class LeftMenuFragment extends Fragment {

	private View mView;
	private Context mContext;
	private ListView listview_right_category;
	private LeftMenuAdapter mAdapter;
	private String[] category_name;
	private String[] category_title;
	private Integer[] category_img;
	private List<ItemCategoryModel> mLists;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(null == mView){
			mView = inflater.inflate(R.layout.left_category, container,false);
			initView();
			initValidata();
			bindData();
			initListener();
		}
		return mView;
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview_right_category = (ListView)mView.findViewById(R.id.listview_right_category);
		
	}

	private void initValidata() {
		// TODO Auto-generated method stub
		mContext = mView.getContext();
		//测试数据
		category_name = mContext.getResources().getStringArray(R.array.category_name);
		category_title = mContext.getResources().getStringArray(R.array.category_title);
		category_img = new Integer[]{
				R.drawable.biz_navigation_tab_local_news,
				R.drawable.biz_navigation_tab_ties,
				R.drawable.biz_navigation_tab_pics,
				R.drawable.biz_navigation_tab_ugc,
				R.drawable.biz_navigation_tab_voted,
				R.drawable.biz_navigation_tab_micro
		};
		mLists = new ArrayList<ItemCategoryModel>();
		//构造要显示的服务类别对象集合
		for(int i =0; i<category_img.length; i++){
			mLists.add(new ItemCategoryModel(category_img[i],category_name[i],category_title[i]));
		}
		//初始化适配器
		mAdapter = new LeftMenuAdapter(mContext, mLists);
	}

	/**
	 * 绑定数据
	 */
	private void bindData() {
		// TODO Auto-generated method stub
		listview_right_category.setAdapter(mAdapter);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		listview_right_category.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	/**
	 * 菜单点击事件
	 * @author liupf5
	 *
	 */
	class MyOnItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "选择了"+category_name[arg2], Toast.LENGTH_SHORT).show();
//			if (arg2 == 3) {
//				Intent intent = new Intent(mContext,SlideViewDemoActivity.class);
//				startActivity(intent);
//			}
		}
		
	}
}
