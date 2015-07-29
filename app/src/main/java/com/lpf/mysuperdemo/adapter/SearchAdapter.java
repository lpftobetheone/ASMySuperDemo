package com.lpf.mysuperdemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

public class SearchAdapter extends BaseAdapter {
	
	private List<String> list; 
	
	private LayoutInflater mInflater;

	public SearchAdapter(Context context, List<String> list) {
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return list.size()+1;
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
//	@Override
//	public int getItemViewType(int position) {
//		// TODO Auto-generated method stub
//		if (position == 0) {
//			return 0;
//		} else {
//			return 1;
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.BaseAdapter#getViewTypeCount()
//	 */
//	@Override
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 2;
//	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
//		if(position == 0){
//			convertView = mInflater.inflate(R.layout.activity_search_list_header, null);
//			return convertView;
//		}else{
//			ViewHolder holder = null;
//	         if (convertView == null) {
//	             convertView = mInflater.inflate(R.layout.activity_search_item, null);
//	             holder = new ViewHolder();
//	             holder.searchHistory = (TextView)convertView.findViewById(R.id.id_search_history);
//	             convertView.setTag(holder);
//	         } else {
//	             holder = (ViewHolder)convertView.getTag();
//	         }
//	         holder.searchHistory.setText(list.get(position-1));
//	         return convertView;
//		}
		
		ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_search_item, null);
            holder = new ViewHolder();
            holder.searchHistory = (TextView)convertView.findViewById(R.id.id_search_history);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.searchHistory.setText(list.get(position));
        return convertView;
		 
	}
	
	private class ViewHolder{
		private TextView searchHistory;
	}

}
