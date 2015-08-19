package com.lpf.mysuperdemo.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

import java.util.List;

/**
 * Created by liupf5 on 2015/8/18.
 */
public class HorizontalScrollViewAdaper extends RecyclerView.Adapter<HorizontalViewHolder>{

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<String> mDatas;

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public HorizontalScrollViewAdaper( Context context , List<String> datas) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.activity_recyclerview_item,parent,false);
        HorizontalViewHolder holder = new HorizontalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        setUpItemEvent(holder);
    }

    protected void setUpItemEvent(final HorizontalViewHolder myViewHolder){
        if(mOnItemClickListener!=null){

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //布局上的位置,当调用notifyItemInsert后布局中的item位置仍然会保留之前的数值
                    int layoutPosition= myViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(myViewHolder.itemView,layoutPosition);
                }
            });
            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    int layoutPostion = myViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(myViewHolder.itemView, layoutPostion);
                    return false;
                }
            });
        }
    }


}

final class HorizontalViewHolder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView tv;

    public HorizontalViewHolder(View itemView) {
        super(itemView);

        img = (ImageView)itemView.findViewById(R.id.id_recyclerView_img);
        tv = (TextView)itemView.findViewById(R.id.id_recyclerView_tv);
    }
}
