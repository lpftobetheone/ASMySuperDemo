package com.lpf.mysuperdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.bean.StarProduct;
import com.lpf.mysuperdemo.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupf5 on 2015/8/19.
 */
public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<StarProduct> mStarProductList = new ArrayList<StarProduct>();

    public HorizontalRecyclerAdapter(Context context, List<StarProduct> list) {
        mContext = context;
        mStarProductList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    @Override
    public HorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_horizontal_recycler_view_item,parent,false);
        HorViewHolder viewHolder = new HorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorViewHolder holder, int position) {
        StarProduct bean = mStarProductList.get(position);
        ImageUtil.DisplayImage(bean.getPicurl(),holder.img);
        holder.tv.setText(bean.getProductname());

        setOnUpEvent(holder);
    }

    private void setOnUpEvent(final HorViewHolder holder) {
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStarProductList.size();
    }
}

class HorViewHolder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView tv;

    public HorViewHolder(View itemView) {
        super(itemView);

        img = (ImageView)itemView.findViewById(R.id.id_hor_recyclerview_img);
        tv = (TextView)itemView.findViewById(R.id.id_hor_recyclerview_tv);
    }
}
