package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.adapter.HorizontalRecyclerAdapter;
import com.lpf.mysuperdemo.bean.StarProduct;
import com.lpf.mysuperdemo.bean.StarProductList;
import com.lpf.mysuperdemo.constants.Global;
import com.lpf.mysuperdemo.recyclerviewdemo.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyclerViewActivity extends Activity {

    private StarProductList mGetStarProductList = new StarProductList();
    private List<StarProduct> mStarProductList = new ArrayList<StarProduct>();

    String picUrl[] = Global.mHorizontalListViewUrl;

    private RecyclerView mRecyclerView;
    private HorizontalRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_recycler_view);

        initViews();
        initDatas();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView)this.findViewById(R.id.id_hor_recyclerview);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initDatas() {
        for (int i = 0; i < 5; i++) {
            StarProduct mProduct = new StarProduct();
            mProduct.setPicurl(picUrl[i]);
            mProduct.setProductname("name" + i);
            mProduct.setDetailurl("http://www.baidu.com");
            mStarProductList.add(mProduct);
        }
        mGetStarProductList.setStarproductlist(mStarProductList);

        mAdapter = new HorizontalRecyclerAdapter(this, mStarProductList);
        mAdapter.setOnItemClickListener(new HorizontalRecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HorizontalRecyclerViewActivity.this,CommonWebClientActivity.class);
                intent.putExtra("detailUrl",mStarProductList.get(position).getDetailurl());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
