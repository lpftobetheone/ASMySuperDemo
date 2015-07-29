/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.interfaces.INetObserver;
import com.lpf.mysuperdemo.listviewloadmore.OrderDetail;
import com.lpf.mysuperdemo.listviewloadmore.OrderDetailAdapter;
import com.lpf.mysuperdemo.util.NetworkUtil;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-7-13
 * @Version:1.1.0
 */
public class CustomListViewLoadMoreActivity extends Activity implements
		INetObserver {

	private TextView mOrderDetailText;
	private LinearLayout mLinearLayoutFooter;

	List<OrderDetail> orderDetaillist = new ArrayList<OrderDetail>();
	List<OrderDetail> orderDetaillist1 = new ArrayList<OrderDetail>();

	private ListView mListView;
	private OrderDetailAdapter adapter;

	private int selectedPosition = 0;// 设置列表页面显示的位置

	private int pageNo = 1;
	private int pageSize = 10;
	private boolean isDone = false;

	// 接受数据，更新ListView数据适配器
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mOrderDetailText.setVisibility(View.GONE);
				setOrderDetailData(selectedPosition);
				mLinearLayoutFooter.setVisibility(View.GONE);
				break;
			case 1:
				// 提示没有数据
				mOrderDetailText.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails);

		for (int i = 0; i < 10; i++) {
			OrderDetail bean = new OrderDetail();
			bean.setAward("10");
			bean.setOrderNo("12312312312:" + i);
			bean.setPayTime("2013-0404");
			bean.setStatus("已收货");
			orderDetaillist1.add(bean);
		}

		initViews();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mOrderDetailText = (TextView) this
				.findViewById(R.id.id_order_detail_text);
		mListView = (ListView) this.findViewById(R.id.id_order_detail_list);

		mLinearLayoutFooter = (LinearLayout) findViewById(R.id.ll_main_progress);
		adapter = new OrderDetailAdapter(
				CustomListViewLoadMoreActivity.this, orderDetaillist);
		mListView.setAdapter(adapter);

		if (NetworkUtil.isNetworkConnected(CustomListViewLoadMoreActivity.this)) {
			// 从服务器获取json数据
			updateNetState(true);
		} else {
			// analysisReponseData(PreferencesUtil.getString(
			// CustomListViewLoadMoreActivity.this, "c2corderlist"), 1000);
		}

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到底部
					int lastPosition = mListView.getLastVisiblePosition();
					int count = mListView.getCount() - 1;
					if (lastPosition == count) {
						// Log.v("c2c", "滑到底部");

						if (!isDone) {
							mLinearLayoutFooter.setVisibility(View.VISIBLE);
							// Log.v("c2c", "滑到底部1");
							pageNo++;
							// 保存记录列表上次显示的位置
							selectedPosition = mListView
									.getLastVisiblePosition();
							// Log.v("c2c",
							// "selectedPosition1:"+selectedPosition);
							getOrderDetailListData(pageNo, pageSize);

						} else {
							// Log.v("c2c",
							// "selectedPosition2:"+selectedPosition);
							selectedPosition = mListView
									.getLastVisiblePosition();
						}

					}
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	boolean isShow = true;

	@Override
	public void updateNetState(boolean connected) {
		// TODO Auto-generated method stub
		if (connected) {
			if (isShow) {
				isShow = false;
				// 第一次访问，访问第一页，10条数据
				getOrderDetailListData(pageNo, pageSize);
			}
		} else {
			isShow = true;
		}
	}

	/**
	 * 
	 * @Description:
	 */
	protected void setOrderDetailData(int selectedPosition) {
		adapter.notifyDataSetChanged();
		mListView.setSelection(selectedPosition);
	}

	// 获取下一页数据
	public void getOrderDetailListData(int pageNo, int pageSize) {

		// 测试数据
		if (pageNo * pageSize <= 30) {
			orderDetaillist.addAll(orderDetaillist1);
//			mHandler.sendEmptyMessage(0);
			//因为数据太快了，此处延迟一秒
			mHandler.sendEmptyMessageDelayed(0, 1000);
		} else {
			isDone = true; // 已经加载完毕
			mLinearLayoutFooter.setVisibility(View.GONE);
			setOrderDetailData(selectedPosition);
		}
	}
}
