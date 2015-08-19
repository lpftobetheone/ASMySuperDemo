package com.lpf.mysuperdemo.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;

import java.util.List;

/**
 * Created by liupf5 on 2015/8/18.
 */
public class BaseViewAdapter extends RecyclerView.Adapter<MyViewHolder>{

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<String> mDatas;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public BaseViewAdapter( Context context , List<String> datas) {
        mDatas = datas;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_recyclerview_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        setUpItemEvent(holder);
    }

    public void addData(int pos){
        mDatas.add(pos, "new data");
        notifyItemInserted(pos);
    }

    public void deleteData(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    protected void setUpItemEvent(final MyViewHolder myViewHolder){
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

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
class MyViewHolder extends ViewHolder{
    TextView tv;
    public MyViewHolder(View itemView){
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.id_recyclerView_tv);
    }
}


