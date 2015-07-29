/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-7-1
 * @Version:1.1.0
 */
public class PartnerListViewAdpater extends BaseAdapter {

	private Context context;
	private String[] beans;

	private int selectedIndex;

	class ViewHolder {

		TextView tvName;
		RadioButton rb_state;
	}

	public PartnerListViewAdpater(Context context, String[] beans) {
		this.beans = beans;
		this.context = context;
	}

	@Override
	public int getCount() {
		
		return beans.length;
	}

	@Override
	public Object getItem(int position) {
		
		return beans[position];
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	public void setSelectedIndex(int position) {
		this.selectedIndex = position;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		// 页面
		ViewHolder holder;
		String bean = beans[position];
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.activity_partner_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView
					.findViewById(R.id.id_partner_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(bean);
		holder.rb_state = (RadioButton) convertView.findViewById(R.id.id_rb);
		if (selectedIndex == position) {
			holder.rb_state.setChecked(true);
		} else {
			holder.rb_state.setChecked(false);
		}
		return convertView;
	}
}