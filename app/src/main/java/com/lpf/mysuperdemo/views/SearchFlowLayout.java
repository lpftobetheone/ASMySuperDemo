package com.lpf.mysuperdemo.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SearchFlowLayout extends ViewGroup {

	private static final String TAG = "SearchFlowLayout";

	public SearchFlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public SearchFlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SearchFlowLayout(Context context) {
		this(context, null);
	}

	// 负责测量子控件测量模式和大小 依据所有子控件设置自己的宽和高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 获得它的父容器为它设置的测量模式和大小
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		Log.e(TAG, sizeWidth + "," + sizeHeight);

		// 如果是wrapContent
		int width = 0;
		int height = 0;

		// 记录每一行的宽度，width不断获取最大宽度
		int lineWidth = 0;
		// 记录每一行的高度，height不断累加
		int lineHeight = 0;

		int cCount = getChildCount();

		// 遍历每个子元素
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			// 测量每一个child的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// 得到child的lp
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 得到当前子控件实际占据的宽度和高度
			int h = child.getMeasuredWidth();
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;

			// 如果加入当前child，则超出最大宽度后将最大宽度给width,开启新行
			if (lineWidth + childWidth > sizeWidth - getPaddingLeft()
					- getPaddingRight()) {
				width = Math.max(lineWidth, childWidth);// 记录最大的宽度
				lineWidth = childWidth; // 重新开始新行，开始记录
				// 叠加当前高度
				height += lineHeight;
				// 开启记录下一行的高度
				lineHeight = childHeight;
			} else {
				// 累加lineWidth/lineHeight
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}

			// 如果是最后一个，则将当前记录的最大宽度和当前lineWidth比较
			if (i == cCount - 1) {
				width = Math.max(width, lineWidth);
				height += lineHeight;
			}
		}

		// 用来保存测量得到的width和height
		setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
				: width + getPaddingLeft() + getPaddingRight(),
				(modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height
						+ getPaddingTop() + getPaddingBottom()));
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	// 存储所有的view，按行记录
	private List<List<View>> mAllViews = new ArrayList<List<View>>();

	// 记录每一行的最大高度
	private List<Integer> mLineHeight = new ArrayList<Integer>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		mAllViews.clear();
		mLineHeight.clear();

		int width = getWidth();

		int lineWidth = 0;
		int lineHeight = 0;

		// 存储每一行的childView
		List<View> lineViews = new ArrayList<View>();
		int cCount = getChildCount();
		// 遍历所有的孩子
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			// 如果需要换行
			if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width
					- getPaddingLeft() - getPaddingRight()) {
				// 记录这一行的所有view以及最大高度
				mLineHeight.add(lineHeight);
				// 将当前行的childView保存，然后开启新的ArrayList保存下一行
				mAllViews.add(lineViews);
				lineWidth = 0;
				lineViews = new ArrayList<View>();
			}
			// 不需要换行
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
					+ lp.bottomMargin);
			lineViews.add(child);
		}
		// 记录最后一行
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);

		int left = getPaddingLeft();
		int top = getPaddingTop();
		// 得到总行数
		int lineNums = mAllViews.size();
		for (int i = 0; i < lineNums; i++) {
			// 每一行的所有views
			lineViews = mAllViews.get(i);
			// 当前行的高度
			lineHeight = mLineHeight.get(i);

			Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
			Log.e(TAG, "第" + i + "行， ：" + lineHeight);

			// 遍历当前行所有view
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child
						.getLayoutParams();

				// 计算childView的left,top,right,bottom
				int lc = left + lp.leftMargin;
				int tc = top + lp.topMargin;
				int rc = lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();

				child.layout(lc, tc, rc, bc);

				Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
						+ rc + " , b = " + bc);

				left += child.getMeasuredWidth() + lp.rightMargin
						+ lp.leftMargin;

			}
			left = getPaddingLeft();
			top += lineHeight;
		}

	}

}
