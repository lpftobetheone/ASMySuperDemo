package com.lpf.mysuperdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.fragments.Fragment1;
import com.lpf.mysuperdemo.fragments.Fragment2;
import com.lpf.mysuperdemo.fragments.Fragment3;
import com.lpf.mysuperdemo.fragments.Fragment4;

public class FrameRadioGroupActivity extends FragmentActivity implements OnCheckedChangeListener{

	private static final int MENU_HOME = 0;
	private static final int MENU_CLASSIFY = 1;
	private static final int MENU_SHOPCAR = 2;
	private static final int MENU_PERSONAL = 3;
	
	private RadioGroup menu_group;
	private RadioButton menu_home, menu_classify, menu_shopcar, menu_personal;
	
	private Fragment1 mFragment1;
	private Fragment2 mFragment2;
	private Fragment3 mFragment3;
	private Fragment4 mFragment4;
	
	private Fragment mCurrentFragment;
	private int mCurrentPage = 0;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = getApplicationContext();
		setContentView(R.layout.activity_bottom_radiobutton);
		initViews();
		selectFirstPage();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		menu_group = (RadioGroup) findViewById(R.id.menu_group);
		menu_group.setOnCheckedChangeListener(this);

		menu_home = (RadioButton) findViewById(R.id.menu_home);
		menu_classify = (RadioButton) findViewById(R.id.menu_classify);
		menu_shopcar = (RadioButton) findViewById(R.id.menu_shopcar);
		menu_personal = (RadioButton) findViewById(R.id.menu_personal);

		mFragment1 = new Fragment1();
		mFragment2 = new Fragment2();
		mFragment3 = new Fragment3();
		mFragment4 = new Fragment4();
	}
	
	private void selectFirstPage() {
		// TODO Auto-generated method stub
		menu_home.setChecked(true);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.menu_home:
			switchContentNew(MENU_HOME);
			break;
		case R.id.menu_classify:
			switchContentNew(MENU_CLASSIFY);
			break;
		case R.id.menu_shopcar:
			switchContentNew(MENU_SHOPCAR);
			break;
		case R.id.menu_personal:
			switchContentNew(MENU_PERSONAL);
			break;
		default:
			break;
		}
	}

	private void switchContentNew(int page) {
		// TODO Auto-generated method stub
		switch (page) {
		case MENU_HOME:
			switchContent(mFragment1);
			break;
		case MENU_CLASSIFY:
			switchContent(mFragment2);
			break;
		case MENU_SHOPCAR:
			switchContent(mFragment3);
			break;
		case MENU_PERSONAL:
			switchContent(mFragment4);
			break;
		}
	}

	private void switchContent(Fragment to) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if(mCurrentFragment != null && mCurrentFragment != to){
			if(!to.isAdded()){
				//当前Fragment未被加载过
				transaction.hide(mCurrentFragment).add(R.id.container, to);
				transaction.commit();
			}else{
				transaction.hide(mCurrentFragment).show(to);
				transaction.commit();
			}
		}else if(null == mCurrentFragment){
			transaction.replace(R.id.container, to);
			transaction.commit();
		}
		mCurrentFragment = to;
	}
	
	
}
