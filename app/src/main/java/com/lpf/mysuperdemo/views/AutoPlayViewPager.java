package com.lpf.mysuperdemo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.activity.CommonWebClientActivity;
import com.lpf.mysuperdemo.bean.ScrollPicture;
import com.lpf.mysuperdemo.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片轮播模块
 * @author liupf5
 *
 */
@SuppressLint("ClickableViewAccessibility")
public class AutoPlayViewPager extends FrameLayout {

	private List<ScrollPicture> mScrollPictureList;
	private List<ImageView> mImageViewsList;
	private List<View> mDotViewsList;
	private ViewPager mViewPager;
	private int currentItem = 0;

	private Context mContext;
	/**
	 * 请求更新显示的View。
	 */
	protected static final int MSG_UPDATE_IMAGE = 1;
	/**
	 * 请求暂停轮播。
	 */
	protected static final int MSG_KEEP_SILENT = 2;
	/**
	 * 请求恢复轮播。
	 */
	protected static final int MSG_BREAK_SILENT = 3;
	/**
	 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
	 * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
	 */
	protected static final int MSG_PAGE_CHANGED = 4;

	// 轮播间隔时间
	protected static final long MSG_DELAY = 3000;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
				handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				mViewPager.setCurrentItem(currentItem);
				// 准备下次播放
				handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				// 只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				break;
			}
		}
	};

	public AutoPlayViewPager(Context context) {
		super(context, null);

	}

	public AutoPlayViewPager(Context context, AttributeSet attrs) {
		super(context, attrs, 0);

	}

	public AutoPlayViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		mImageViewsList = new ArrayList<ImageView>();
		mDotViewsList = new ArrayList<View>();
		mScrollPictureList = new ArrayList<ScrollPicture>();

	}

	private void initUI(Context context) {

		LayoutInflater.from(mContext).inflate(R.layout.autoviewpager_layout, this,
				true);
		LinearLayout mDotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		mDotLayout.removeAllViews();

		mViewPager = (ViewPager) findViewById(R.id.id_imageviewPager);
		mViewPager.setFocusable(true);

		mViewPager.setAdapter(new MyPagerAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());

		if (mScrollPictureList != null && mScrollPictureList.size() > 0) {

			mImageViewsList.clear();
			// 热点个数与图片个数相等
			for (int i = 0; i < mScrollPictureList.size(); i++) {
				ScrollPicture mScrollPicture = mScrollPictureList.get(i);
				ImageView view = new ImageView(mContext);
				view.setTag(mScrollPicture.getPicurl());
				view.setScaleType(ScaleType.FIT_XY);
				view.setOnClickListener(new MyOnClickListener(mScrollPicture));
				mImageViewsList.add(view);

				ImageView dotView = new ImageView(mContext);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.leftMargin = 4;
				params.rightMargin = 4;
				mDotLayout.addView(dotView, params);
				Log.e("logtest", "add dot");
				mDotViewsList.add(dotView);
			}
			for (int i = 0; i < mDotViewsList.size(); i++) {
				if (i == 0) {
					mDotViewsList.get(i).setBackgroundResource(
							R.drawable.dot_focus);
				} else {
					mDotViewsList.get(i).setBackgroundResource(
							R.drawable.dot_blur);
				}
			}

			mViewPager.setCurrentItem(mImageViewsList.size() * 100);// 默认在中间，使用户看不到边界
			// 开始轮播效果
			handler.sendEmptyMessageDelayed(MSG_BREAK_SILENT, MSG_DELAY);
		} else {
			// 没有网络显示一张默认图片
			ImageView view = new ImageView(mContext);
			view.setBackgroundResource(R.drawable.default_img1);
			view.setScaleType(ScaleType.FIT_XY);
			mViewPager.setCurrentItem(0);
		}
	}

	private class MyOnClickListener implements OnClickListener {
		ScrollPicture scrollPicture;

		public MyOnClickListener(ScrollPicture scrollPicture) {
			this.scrollPicture = scrollPicture;
		}

		@Override
		public void onClick(View arg0) {
			String linkUrl = scrollPicture.getLinkurl();
			Intent intent = new Intent(mContext, CommonWebClientActivity.class);
			intent.putExtra("detailUrl", linkUrl);
			mContext.startActivity(intent);

		}

	}

	// 填充ViewPager的页面适配器
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// position = Math.abs(position % mImageViewsList.size());
			ImageView view = null;
			if (mScrollPictureList != null && mScrollPictureList.size() > 0 && mImageViewsList!=null && mImageViewsList.size()>0) {
				position %= mImageViewsList.size();
				if (position < 0) {
					position = mImageViewsList.size() + position;
				}
				view = mImageViewsList.get(position);
				if (view.getTag() != null) {
					ImageUtil.DisplayImage(view.getTag() + "", view);
				}
			} else {
				// 没有网络添加一张图片
				view = new ImageView(mContext);
				view.setBackgroundResource(R.drawable.default_img1);
			}
			// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
			ViewParent vp = view.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(view);
			}
			container.addView(view);
			return view;

		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				handler.sendEmptyMessage(MSG_KEEP_SILENT);
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int pos) {
			handler.sendMessage(Message.obtain(handler, MSG_PAGE_CHANGED, pos,
					0));
			for (int i = 0; i < mDotViewsList.size(); i++) {
				if (i == pos % mImageViewsList.size()) {
					((View) mDotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_focus);
				} else {
					((View) mDotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_blur);
				}
			}
		}
	}

	// 判断前后两次传递过来的数据是否相同，相同不进行任何操作
	public boolean compare(List<ScrollPicture> a, List<ScrollPicture> b) {
		if (a == null || b == null || a.size() == 0 || b.size() == 0
				|| a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置ViewPager数据
	 * 
	 * @param mContext
	 * @param scrollPictureList
	 *            图片数据
	 */
	public void setViewPagerData(Context mContext,
			List<ScrollPicture> scrollPictureList) {
		if (scrollPictureList != null) {

			if (compare(mScrollPictureList, scrollPictureList)) {
				Log.e("logtest", "equal,not change");
			} else {
				Log.e("logtest",
						"scrollPictureList.size()" + scrollPictureList.size());
				Log.e("logtest", "change");
				// stopPlay();

				initData();
				this.mContext = mContext;

				if (mDotViewsList != null) {
					for (View view : mDotViewsList) {
						view.setBackgroundResource(R.drawable.dot_blur);
					}
					mDotViewsList.clear();
				}

				mScrollPictureList.clear();
				if (scrollPictureList.size() > 0) {
					mScrollPictureList.addAll(scrollPictureList);
				}
				mImageViewsList.clear();

				initUI(mContext);
			}
		}
	}
}
