package com.lpf.mysuperdemo.slidingmenu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

/**
 * 右侧菜单适配器
 * @author liupf5
 *
 */
public class LeftMenuAdapter extends BaseAdapter {

	private Context mContext;
	private List<ItemCategoryModel> mLists;
	private LayoutInflater mLayoutInflater;
	
	
	public LeftMenuAdapter(Context pContext, List<ItemCategoryModel> pLists) {
		this.mContext = pContext;
		this.mLists = pLists;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLists!=null ? mLists.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			convertView = mLayoutInflater.inflate(R.layout.left_category_item, null);
			holder.right_item_img = (ImageView)convertView.findViewById(R.id.right_item_img);
			holder.right_item_name = (TextView)convertView.findViewById(R.id.right_item_name);
			holder.right_item_title = (TextView)convertView.findViewById(R.id.right_item_title);
			convertView.setTag(holder);
		}else{
			holder = (Holder)convertView.getTag();
		}
		holder.right_item_img.setImageResource(mLists.get(position).getId());
		holder.right_item_name.setText(mLists.get(position).getName());
		holder.right_item_title.setText(mLists.get(position).getTitle());
		return convertView;
	}
	
	/**
	 * 内部类
	 * @author liupf5
	 *
	 */
	private static class Holder{
		ImageView right_item_img;
		TextView right_item_name;
		TextView right_item_title;
	}

}
