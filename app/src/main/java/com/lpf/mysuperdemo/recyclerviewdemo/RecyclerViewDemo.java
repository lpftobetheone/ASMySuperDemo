package com.lpf.mysuperdemo.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemo extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView_hor;
    private List<String> mDatas;
    private BaseViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_demo);

        initDatas();
        initViews();
    }

    private void initDatas() {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("Come on, Item "+ i);
        }

    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerView);
        mRecyclerView_hor = (RecyclerView)findViewById(R.id.id_recyclerView_horizontal);

        mAdapter = new BaseViewAdapter(this, mDatas);

        mAdapter.setOnItemClickListener(new BaseViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showShort(RecyclerViewDemo.this, "点击item" + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                ToastUtil.showShort(RecyclerViewDemo.this, "长时间点击item" + position);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager lp = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(lp);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //水平滚动列表
        mRecyclerView_hor.setAdapter(mAdapter);
        mRecyclerView_hor.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView_hor.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView_hor.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                mAdapter.addData(1);
                break;
            case R.id.action_delete:
                mAdapter.deleteData(1);
                break;
            case R.id.action_grid:
                RecyclerView.LayoutManager lp  = new GridLayoutManager(this,3);
                mRecyclerView.setLayoutManager(lp);
                break;
            case R.id.action_horizontal:
                RecyclerView.LayoutManager hlp = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL,false);
                mRecyclerView.setLayoutManager(hlp);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
