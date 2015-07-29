package com.lpf.mysuperdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.bean.StarProduct;
import com.lpf.mysuperdemo.bean.StarProductList;
import com.lpf.mysuperdemo.util.ImageUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HorizontalListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private List<StarProduct> listItems = new ArrayList<StarProduct>();
	private StarProductList mGetStarProduct = new StarProductList();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	public HorizontalListViewAdapter(Context con,
			StarProductList mGetStarProduct) {
		mInflater = LayoutInflater.from(con);
		this.mGetStarProduct = mGetStarProduct;
		this.mContext = con;
		if(mGetStarProduct!=null)
		{
			this.listItems = mGetStarProduct.getStarproductlist();
		}
		
	}

	@Override
	public int getCount() {
		int size = listItems.size();
		for(int i =0; i<5-size; i++){
			listItems.add(new StarProduct());
		}
		//默认显示5条数据
		return listItems.size() > 0 ? listItems.size() : 5;
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.horizontallistview_item,
					null);
			holder.mStarProductImage = (ImageView) convertView.findViewById(R.id.id_star_pic);
			holder.mStarProductName = (TextView) convertView
					.findViewById(R.id.id_star_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (listItems.size() > 0) {
			StarProduct bean = listItems.get(position);
			ImageUtil.DisplayImage(bean.getPicurl(), holder.mStarProductImage);
			holder.mStarProductName.setText(bean.getProductname());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView mStarProductName;
		ImageView mStarProductImage;
	}
}