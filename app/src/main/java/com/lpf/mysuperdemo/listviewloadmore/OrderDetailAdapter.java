package com.lpf.mysuperdemo.listviewloadmore;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

public class OrderDetailAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<OrderDetail> list; 
	
	private LayoutInflater mInflater;

	public OrderDetailAdapter(Context context, List<OrderDetail> list) {
		this.mContext = context;
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		 ViewHolder holder = null;
         if (convertView == null) {
             convertView = mInflater.inflate(R.layout.activity_orderdetail_item, null);
             holder = new ViewHolder();
             holder.orderDetail = (TextView)convertView.findViewById(R.id.id_order_time);
             holder.orderNumber = (TextView)convertView.findViewById(R.id.id_order_number);
             holder.profile = (TextView)convertView.findViewById(R.id.id_profile);
             holder.status = (TextView)convertView.findViewById(R.id.id_status);
             convertView.setTag(holder);
         } else {
             holder = (ViewHolder)convertView.getTag();
         }
         holder.orderDetail.setText(list.get(position).getPayTime());
         holder.orderNumber.setText("订单号:"+list.get(position).getOrderNo());
         holder.profile.setText("¥"+list.get(position).getAward());
         String status = list.get(position).getStatus().trim();
         if(status.equals("已发货")){
        	 holder.status.setTextColor(Color.RED);
         }else{
        	 holder.status.setTextColor(Color.BLACK);
         }
         holder.status.setText(list.get(position).getStatus());
         return convertView;
	}
	
	private class ViewHolder{
		private TextView orderDetail;
		private TextView orderNumber;
		private TextView profile;
		private TextView status;
	}

}
