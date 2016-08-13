package com.sunsoft.zyebiz.b2e.cycleviewpager.lib;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.model.ADInfo;


/**
 *  实现可循环，可轮播
 */
@SuppressLint("NewApi")
public class CycleViewPager extends Fragment implements OnPageChangeListener {
	
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	private ImageView[] indicators;
	private FrameLayout viewPagerFragmentLayout;
	private LinearLayout indicatorLayout; 
	private BaseViewPager viewPager;
	private BaseViewPager parentViewPager;
	private ViewPagerAdapter adapter;
	private CycleViewPagerHandler handler;
	private int time = Constants.CONSTANT_FIVE_THOUSAND; 
	private int currentPosition = 0; 
	private boolean isScrolling = false; 
	private boolean isCycle = true; 
	private boolean isWheel = true; 
	
	private long releaseTime = 0; 
	private int WHEEL = 100; 
	private int WHEEL_WAIT = 101; 
	private ImageCycleViewListener mImageCycleViewListener;
	private List<ADInfo> infos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.view_cycle_viewpager_contet, null);

		viewPager = (BaseViewPager) view.findViewById(R.id.viewPager);
		indicatorLayout = (LinearLayout) view
				.findViewById(R.id.layout_viewpager_indicator);
		
		viewPagerFragmentLayout = (FrameLayout) view
				.findViewById(R.id.layout_viewager_content);
//		if(imageViews.size() == 0){
//			indicatorLayout.setBackgroundResource(R.drawable.login_carousel);
//	}
//		System.out.println("imageViews.size():"+imageViews.size());
		

		handler = new CycleViewPagerHandler(getActivity()) {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == WHEEL && imageViews.size() != 0) {
					indicatorLayout.setBackground(null);
					if (!isScrolling) {
						int max = imageViews.size() + 1;
						int position = (currentPosition + 1) % imageViews.size();
						viewPager.setCurrentItem(position, true);
						if (position == max) { 
							viewPager.setCurrentItem(1, false);
						}
					}

					releaseTime = System.currentTimeMillis();
					handler.removeCallbacks(runnable);
					handler.postDelayed(runnable, Constants.CONSTANT_FIVE_THOUSAND);
					return;
				}

				if (msg.what == WHEEL_WAIT && imageViews.size() != 0) {
					indicatorLayout.setBackground(null);
					handler.removeCallbacks(runnable);
					handler.postDelayed(runnable, Constants.CONSTANT_FIVE_THOUSAND);
				}
			}
		};

		return view;
	}

	public void setData(List<ImageView> views, List<ADInfo> list, ImageCycleViewListener listener) {
		setData(views, list, listener, 0);
	}

	public void setData(List<ImageView> views, List<ADInfo> list, ImageCycleViewListener listener, int showPosition) {
		mImageCycleViewListener = listener;
		infos = list;
		this.imageViews.clear();

		if (views.size() == 0) {
			viewPager.setVisibility(View.GONE);
			viewPagerFragmentLayout.setVisibility(View.VISIBLE);
			indicatorLayout.setBackgroundResource(R.drawable.login_carousel);
//			viewPagerFragmentLayout.setBackgroundResource(R.drawable.login_carousel);
			return;
		}

		for (ImageView item : views) {
			this.imageViews.add(item);
		}

		int ivSize = views.size();

		
		indicators = new ImageView[ivSize];
		if (isCycle)
			indicators = new ImageView[ivSize - 2];
		indicatorLayout.removeAllViews();
		for (int i = 0; i < indicators.length; i++) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.view_cycle_viewpager_indicator, null);
			indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
			
			indicatorLayout.addView(view);
		}

		adapter = new ViewPagerAdapter();

		setIndicator(0);

		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(this);
		viewPager.setAdapter(adapter);
		if (showPosition < 0 || showPosition >= views.size())
			showPosition = 0;
		if (isCycle) {
			showPosition = showPosition + 1;
		}
		viewPager.setCurrentItem(showPosition);

	}

	public void setIndicatorCenter() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		indicatorLayout.setLayoutParams(params);
	}

	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	public boolean isCycle() {
		return isCycle;
	}

	public void setWheel(boolean isWheel) {
		this.isWheel = isWheel;
		isCycle = true;
		if (isWheel) {
			handler.postDelayed(runnable, Constants.CONSTANT_FIVE_THOUSAND);
		}
	}

	public boolean isWheel() {
		return isWheel;
	}

	final Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (getActivity() != null && !getActivity().isFinishing()
					&& isWheel) {
				long now = System.currentTimeMillis();
				if (now - releaseTime > Constants.CONSTANT_FIVE_THOUSAND /*- 500*/) {
					handler.sendEmptyMessage(WHEEL);
				} else {
					handler.sendEmptyMessage(WHEEL_WAIT);
				}
			}else{
				System.out.println("getActivity==null");
			}
		}
	};

	public void releaseHeight() {
		getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
		refreshData();
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void refreshData() {
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	public void hide() {
		viewPagerFragmentLayout.setVisibility(View.GONE);
	}

	public BaseViewPager getViewPager() {
		return viewPager;
	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			ImageView v = imageViews.get(position);
			if (mImageCycleViewListener != null) {
				v.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mImageCycleViewListener.onImageClick(infos.get(currentPosition - 1), currentPosition, v);
					}
				});
			}
			container.addView(v);
			return v;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (arg0 == 1) { 
			isScrolling = true;
			return;
		} else if (arg0 == 0) { 
			if (parentViewPager != null)
				parentViewPager.setScrollable(true);

			releaseTime = System.currentTimeMillis();

			viewPager.setCurrentItem(currentPosition, false);
			
		}
		isScrolling = false;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		int max = imageViews.size() - 1;
		int position = arg0;
		currentPosition = arg0;
		if (isCycle) {
			if (arg0 == 0) {
				currentPosition = max - 1;
			} else if (arg0 == max) {
				currentPosition = 1;
			}
			position = currentPosition - 1;
		}
		setIndicator(position);
	}

	public void setScrollable(boolean enable) {
		viewPager.setScrollable(enable);
	}

	public int getCurrentPostion() {
		return currentPosition;
	}

	private void setIndicator(int selectedPosition) {
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(R.drawable.icon_point);
		}
		if (indicators.length > selectedPosition)
			indicators[selectedPosition]
					.setBackgroundResource(R.drawable.icon_point_pre);
	}

	public void disableParentViewPagerTouchEvent(BaseViewPager parentViewPager) {
		if (parentViewPager != null)
			parentViewPager.setScrollable(false);
	}

	
	public static interface ImageCycleViewListener {

		public void onImageClick(ADInfo info, int postion, View imageView);
	}
}