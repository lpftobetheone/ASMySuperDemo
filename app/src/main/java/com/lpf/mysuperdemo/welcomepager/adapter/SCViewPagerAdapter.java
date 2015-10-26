package com.lpf.mysuperdemo.welcomepager.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lpf.mysuperdemo.R;

import java.util.ArrayList;

/**
 * Created by liupf5 on 2015/10/23.
 */
public class SCViewPagerAdapter extends FragmentStatePagerAdapter{

    private ArrayList<SCViewPagerFragment> mFragmentList;

    private int mNumberOfPage = 0;
    private int mBackgroundColor;


    public SCViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
    }

    public void setNumberOfPage(int numberOfPage) {
        mNumberOfPage = numberOfPage;
    }

    public void setFragmentBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    @Override
    public Fragment getItem(int position) {

        SCViewPagerFragment fragment = null;
        if(mFragmentList.size() -1 >= position ){
            fragment = mFragmentList.get(position);
        }
        if(fragment == null){
            fragment = new SCViewPagerFragment();
            fragment.setBackground(mBackgroundColor);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumberOfPage;
    }

    public static class SCViewPagerFragment extends Fragment{
        private int color;
        public SCViewPagerFragment(){
            this.color = android.R.color.holo_green_light;
        }

        public void setBackground(int inColor){
            this.color = inColor;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            LinearLayout view = new LinearLayout(getActivity());
//
//            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
//            view.setOrientation(LinearLayout.VERTICAL);
//            view.setBackgroundColor(android.R.color.darker_gray);

            inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_welcome,container,false);
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }
    }
}
