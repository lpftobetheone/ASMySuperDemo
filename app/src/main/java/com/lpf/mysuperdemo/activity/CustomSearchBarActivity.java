package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomSearchBarActivity extends Activity implements View.OnClickListener{

    private RelativeLayout mLayout_multiple;
    private RelativeLayout mLayout_show_more;
    private FrameLayout mLayout_product;
    private Button mBtn_multiple;
    private Button mBtn_sale;
    private Button mBtn_price;
    private Button mBtn_selector;

    private ImageView mImg_mulitple;
    private Boolean isUp = true;

    private ListView mList_mulitple;
    private ListView mList_product;

    private ArrayList<Map<String,String>> multiple_list = new ArrayList<Map<String,String>>();
    private ArrayList<Map<String,String>> product_list = new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search_bar);

        initDatas();

        initViews();

        initListeners();
    }

    private void initDatas() {

        String[] names = new String[]{"综合","新品","评论数"};
        for (int i = 0; i < names.length; i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("name",names[i]);
            multiple_list.add(map);
        }

        String[] names2 = new String[]{"商品1","商品2","商品3","商品1","商品2","商品3"};
        for (int i = 0; i < names2.length; i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("name",names2[i]);
            product_list.add(map);
        }

    }

    private void initViews() {
        mLayout_multiple = (RelativeLayout) CustomSearchBarActivity.this.findViewById(R.id.id_layout_mulitple);
        mImg_mulitple = (ImageView)CustomSearchBarActivity.this.findViewById(R.id.id_img_multiple);
        mLayout_show_more = (RelativeLayout)CustomSearchBarActivity.this.findViewById(R.id.id_show_more);
        mList_mulitple = (ListView)CustomSearchBarActivity.this.findViewById(R.id.id_multiple_list);

        mBtn_multiple = (Button)CustomSearchBarActivity.this.findViewById(R.id.id_btn_mulitple);
        mList_mulitple.setAdapter(new SimpleAdapter(this,multiple_list,R.layout.layout_search_multiple_listview_item,new String[]{"name"},new int[]{R.id.id_list_item}));

        mList_product= (ListView)CustomSearchBarActivity.this.findViewById(R.id.id_product_list);
        mList_product.setAdapter(new SimpleAdapter(this, product_list, R.layout.layout_search_multiple_listview_item, new String[]{"name"}, new int[]{R.id.id_list_item}));

        mLayout_product = (FrameLayout)CustomSearchBarActivity.this.findViewById(R.id.id_frame_product);
    }
    private void initListeners() {
        mLayout_multiple.setOnClickListener(this);
        mBtn_multiple.setOnClickListener(this);
        mLayout_product.setOnClickListener(this);

        mList_mulitple.setOnClickListener(this);

//        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                L.v("tap","singleTap");
//                mImg_mulitple.setImageResource(R.drawable.arrows_up);
//                isUp = true;
//                mLayout_show_more.setVisibility(View.GONE);
//                mLayout_product.setForeground(null);
//                return super.onSingleTapUp(e);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_layout_mulitple:
            case R.id.id_btn_mulitple:
                if(isUp) {
                    mImg_mulitple.setImageResource(R.drawable.arrows_down);
                    isUp = false;
                    mLayout_show_more.setVisibility(View.VISIBLE);
                    Resources res = getBaseContext().getResources();
                    Drawable d = res.getDrawable(R.drawable.mask);
                    mLayout_product.setForeground(d);
                }else{
                    mImg_mulitple.setImageResource(R.drawable.arrows_up);
                    isUp = true;
                    mLayout_show_more.setVisibility(View.GONE);
                    mLayout_product.setForeground(null);
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:
               ToastUtil.showShort(this,"single tap");
               mImg_mulitple.setImageResource(R.drawable.arrows_up);
               isUp = true;
               mLayout_show_more.setVisibility(View.GONE);
               mLayout_product.setForeground(null);
               break;
       }
        return super.onTouchEvent(event);
    }
}
