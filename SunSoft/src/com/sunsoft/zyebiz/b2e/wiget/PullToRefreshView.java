//package com.sunsoft.zyebiz.b2e.wiget;
//
//
//
//import com.sunsoft.zyebiz.b2e.R;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//
//public class PullToRefreshView extends LinearLayout {
//
//	/**
//	 * last y
//	 */
//	private int mLastMotionY;
//	/**
//	 * lock
//	 */
//	private boolean mLock;
//	/**
//	 * header view
//	 */
//	private View mHeaderView;
//	/**
//	 * footer view
//	 */
//	private View mFooterView;
//	/**
//	 * 
//	 */
//	protected ViewGroup mAdapterView;
//	private ScrollView mScrollView;
//	private int mHeaderViewHeight;
//	private int mFooterViewHeight;
//	private ImageView mHeaderImageView;
//	private ImageView mFooterImageView;
//	private TextView mHeaderTextView;
//	private TextView mFooterTextView;
//	private TextView mHeaderUpdateTextView;
//	private ProgressBar mHeaderProgressBar;
//	private ProgressBar mFooterProgressBar;
//	private LayoutInflater mInflater;
//	private int mHeaderState;
//	private int mFooterState;
//	private int mPullState;
//	/**
//	 * ��Ϊ���µļ�ͷ,�ı��ͷ����
//	 */
//	private RotateAnimation mFlipAnimation;
//	/**
//	 * ��Ϊ����ļ�ͷ,��ת
//	 */
//	private RotateAnimation mReverseFlipAnimation;
//	
//	private OnFooterRefreshListener mOnFooterRefreshListener;
//	private OnHeaderRefreshListener mOnHeaderRefreshListener;
//	private int flipPx = 8;
//	private boolean noRefresh;
//	private boolean noAddMore;
//	private int footViewText = -1;
//	private int preListHeight;
//	public PullToRefreshView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init();
//	}
//
//	public PullToRefreshView(Context context) {
//		super(context);
//		init();
//	}
//
//	private void init() {
//		this.mFlipAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1,
//				0.5F);
//		this.mFlipAnimation.setInterpolator(new LinearInterpolator());
//		this.mFlipAnimation.setDuration(250L);
//		this.mFlipAnimation.setFillAfter(true);
//		this.mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, 1,
//				0.5F, 1, 0.5F);
//		this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
//		this.mReverseFlipAnimation.setDuration(250L);
//		this.mReverseFlipAnimation.setFillAfter(true);
//
//		this.mInflater = LayoutInflater.from(getContext());
//
//		addHeaderView();
//	}
//
//	private void addHeaderView() {
//		this.mHeaderView = this.mInflater.inflate(R.layout.refresh_header,
//				this, false);
//
//		this.mHeaderImageView = ((ImageView) this.mHeaderView
//				.findViewById(R.id.pull_to_refresh_image));
//		this.mHeaderTextView = ((TextView) this.mHeaderView
//				.findViewById(R.id.pull_to_refresh_text));
//		this.mHeaderUpdateTextView = ((TextView) this.mHeaderView
//				.findViewById(R.id.pull_to_refresh_updated_at));
//		this.mHeaderProgressBar = ((ProgressBar) this.mHeaderView
//				.findViewById(R.id.pull_to_refresh_progress));
//
//		measureView(this.mHeaderView);
//		this.mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,
//				this.mHeaderViewHeight);
//
//		params.topMargin = (-this.mHeaderViewHeight);
//
//		addView(this.mHeaderView, params);
//	}
//
//	private void addFooterView() {
//		this.mFooterView = this.mInflater.inflate(R.layout.refresh_footer,
//				this, false);
//		this.mFooterImageView = ((ImageView) this.mFooterView
//				.findViewById(R.id.pull_to_load_image));
//		this.mFooterTextView = ((TextView) this.mFooterView
//				.findViewById(R.id.pull_to_load_text));
//		this.mFooterProgressBar = ((ProgressBar) this.mFooterView
//				.findViewById(R.id.pull_to_load_progress));
//
//		measureView(this.mFooterView);
//		this.mFooterViewHeight = this.mFooterView.getMeasuredHeight();
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,
//				this.mFooterViewHeight);
//
//		addView(this.mFooterView, params);
//	}
//
//	protected void onFinishInflate() {
//		super.onFinishInflate();
//
//		addFooterView();
//		initContentAdapterView();
//	}
//
//	private void initContentAdapterView() {
//		int count = getChildCount();
//		if (count < 3) {
//			throw new IllegalArgumentException(
//					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
//		}
//		View view = null;
//		for (int i = 0; i < count - 1; i++) {
//			view = getChildAt(i);
//			if ((view instanceof ViewGroup)) {
//				this.mAdapterView = ((ViewGroup) view);
//			}
//			if ((view instanceof ScrollView)) {
//				this.mScrollView = ((ScrollView) view);
//			}
//		}
//		if ((this.mAdapterView == null) && (this.mScrollView == null)) {
//			throw new IllegalArgumentException(
//					"must contain a AdapterView or ScrollView in this layout!");
//		}
//	}
//
//	private void measureView(View child) {
//		ViewGroup.LayoutParams p = child.getLayoutParams();
//		if (p == null) {
//			p = new ViewGroup.LayoutParams(-1, -2);
//		}
//		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
//		int lpHeight = p.height;
//		int childHeightSpec;
//		if (lpHeight > 0) {
//			childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
//					1073741824);
//		} else {
//			childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
//		}
//		child.measure(childWidthSpec, childHeightSpec);
//	}
//
//	public boolean onInterceptTouchEvent(MotionEvent e) {
//		int y = (int) e.getRawY();
//		switch (e.getAction()) {
//			case 0 :
//				this.mLastMotionY = y;
//				break;
//			case 2 :
//				int deltaY = y - this.mLastMotionY;
//				if (isRefreshViewScroll(deltaY)) {
//					return true;
//				}
//				break;
//		}
//		return false;
//	}
//
//	public boolean onTouchEvent(MotionEvent event) {
//		if (this.mLock) {
//			return true;
//		}
//		int y = (int) event.getRawY();
//		switch (event.getAction()) {
//			case 0 :
//				break;
//			case 2 :
//				int deltaY = y - this.mLastMotionY;
//				if (this.mPullState == 1) {
//					if (!this.noRefresh) {
//						headerPrepareToRefresh(deltaY);
//					}
//				} else if (this.mPullState == 0) {
//					if (!this.noAddMore) {
//						footerPrepareToRefresh(deltaY);
//					}
//				}
//				this.mLastMotionY = y;
//				break;
//			case 1 :
//			case 3 :
//				int topMargin = getHeaderTopMargin();
//				if (this.mPullState == 1) {
//					if (topMargin >= 0) {
//						headerRefreshing();
//					} else {
//						setHeaderTopMargin(-this.mHeaderViewHeight);
//					}
//				} else if (this.mPullState == 0) {
//					if (Math.abs(topMargin) >= this.mHeaderViewHeight
//							+ this.mFooterViewHeight) {
//						footerRefreshing();
//					} else {
//						setHeaderTopMargin(-this.mHeaderViewHeight);
//					}
//				}
//				break;
//		}
//		return super.onTouchEvent(event);
//	}
//
//	protected int getFirstPostion() {
//		return ((AdapterView) this.mAdapterView).getFirstVisiblePosition();
//	}
//
//	protected int getLastPostion() {
//		return ((AdapterView) this.mAdapterView).getLastVisiblePosition();
//	}
//
//	protected int getCount() {
//		return ((AdapterView) this.mAdapterView).getCount();
//	}
//
//	private boolean isRefreshViewScroll(int deltaY) {
//		if ((this.mHeaderState == 4) || (this.mFooterState == 4)) {
//			return false;
//		}
//		if (this.mAdapterView != null) {
//			if (deltaY > this.flipPx) {
//				View child = this.mAdapterView.getChildAt(0);
//				if (child == null) {
//					return false;
//				}
//				if ((getFirstPostion() == 0) && (child.getTop() == 0)) {
//					this.mPullState = 1;
//
//					return true;
//				}
//				int top = child.getTop();
//				int padding = this.mAdapterView.getPaddingTop();
//				if ((getFirstPostion() == 0) && (Math.abs(top - padding) <= 8)) {
//					this.mPullState = 1;
//					return true;
//				}
//			} else if (deltaY < -this.flipPx) {
//				View lastChild = this.mAdapterView.getChildAt(this.mAdapterView
//						.getChildCount() - 1);
//				if (lastChild == null) {
//					return false;
//				}
//				if ((lastChild.getBottom() <= getHeight())
//						&& (getLastPostion() == getCount() - 1)) {
//					this.mPullState = 0;
//					return true;
//				}
//			}
//		}
//		if (this.mScrollView != null) {
//			View child = this.mScrollView.getChildAt(0);
//			if ((deltaY > this.flipPx) && (this.mScrollView.getScrollY() == 0)) {
//				this.mPullState = 1;
//				return true;
//			}
//			if ((deltaY < -this.flipPx)
//					&& (child.getMeasuredHeight() <= getHeight()
//							+ this.mScrollView.getScrollY())) {
//				this.mPullState = 0;
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private void headerPrepareToRefresh(int deltaY) {
//		int newTopMargin = changingHeaderViewTopMargin(deltaY);
//		if ((newTopMargin >= 0) && (this.mHeaderState != 3)) {
//			this.mHeaderTextView
//					.setText(R.string.pull_to_refresh_release_label);
//			this.mHeaderUpdateTextView.setVisibility(0);
//			this.mHeaderImageView.clearAnimation();
//			this.mHeaderImageView.startAnimation(this.mFlipAnimation);
//			this.mHeaderState = 3;
//		} else if ((newTopMargin < 0)
//				&& (newTopMargin > -this.mHeaderViewHeight)) {
//			this.mHeaderImageView.clearAnimation();
//			this.mHeaderImageView.startAnimation(this.mFlipAnimation);
//
//			this.mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
//			this.mHeaderState = 2;
//		}
//	}
//
//	private void footerPrepareToRefresh(int deltaY) {
//		int newTopMargin = changingHeaderViewTopMargin(deltaY);
//		if ((Math.abs(newTopMargin) >= this.mHeaderViewHeight
//				+ this.mFooterViewHeight)
//				&& (this.mFooterState != 3)) {
//			this.mFooterTextView
//					.setText(R.string.pull_to_refresh_footer_release_label);
//			this.mFooterImageView.clearAnimation();
//			this.mFooterImageView.startAnimation(this.mFlipAnimation);
//			this.mFooterState = 3;
//		} else if (Math.abs(newTopMargin) < this.mHeaderViewHeight
//				+ this.mFooterViewHeight) {
//			this.mFooterImageView.clearAnimation();
//			this.mFooterImageView.startAnimation(this.mFlipAnimation);
//			if (this.footViewText != -1) {
//				this.mFooterTextView.setText(this.footViewText);
//			} else {
//				this.mFooterTextView
//						.setText(R.string.pull_to_refresh_footer_pull_label);
//			}
//			this.mFooterState = 2;
//		}
//	}
//
//	private int changingHeaderViewTopMargin(int deltaY) {
//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mHeaderView
//				.getLayoutParams();
//		float newTopMargin = params.topMargin + deltaY * 0.3F;
//		if ((deltaY > 0) && (this.mPullState == 0)
//				&& (Math.abs(params.topMargin) <= this.mHeaderViewHeight)) {
//			return params.topMargin;
//		}
//		if ((deltaY < 0) && (this.mPullState == 1)
//				&& (Math.abs(params.topMargin) >= this.mHeaderViewHeight)) {
//			return params.topMargin;
//		}
//		params.topMargin = ((int) newTopMargin);
//		this.mHeaderView.setLayoutParams(params);
//		invalidate();
//		return params.topMargin;
//	}
//
//	public void headerRefreshing() {
//		this.mHeaderState = 4;
//		setHeaderTopMargin(0);
//
//		this.mHeaderImageView.setVisibility(8);
//		this.mHeaderImageView.clearAnimation();
//		this.mHeaderImageView.setImageDrawable(null);
//		this.mHeaderProgressBar.setVisibility(0);
//		this.mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
//		if (this.mOnHeaderRefreshListener != null) {
//			this.mOnHeaderRefreshListener.onHeaderRefresh(this);
//		}
//	}
//
//	private void footerRefreshing() {
//		this.mFooterState = 4;
//
//		int top = this.mHeaderViewHeight;
//		if (this.mAdapterView != null) {
//			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mAdapterView
//					.getLayoutParams();
//			if (this.mAdapterView.getMeasuredHeight() > 0) {
//				this.preListHeight = this.mAdapterView.getMeasuredHeight();
//				params.height = (this.mAdapterView.getMeasuredHeight() - this.mFooterViewHeight);
//			}
//		} else {
//			top = this.mHeaderViewHeight + this.mFooterViewHeight;
//		}
//		setHeaderTopMargin(-top);
//		this.mFooterImageView.setVisibility(8);
//		this.mFooterImageView.clearAnimation();
//		this.mFooterImageView.setImageDrawable(null);
//		this.mFooterProgressBar.setVisibility(0);
//		this.mFooterTextView
//				.setText(R.string.pull_to_refresh_footer_refreshing_label);
//		if (this.mOnFooterRefreshListener != null) {
//			this.mOnFooterRefreshListener.onFooterRefresh(this);
//		}
//	}
//
//	private void setHeaderTopMargin(int topMargin) {
//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mHeaderView
//				.getLayoutParams();
//		params.topMargin = topMargin;
//		this.mHeaderView.setLayoutParams(params);
//		invalidate();
//	}
//
//	public void onHeaderRefreshComplete() {
//		setHeaderTopMargin(-this.mHeaderViewHeight);
//		this.mHeaderImageView.setVisibility(0);
//		this.mHeaderImageView
//				.setImageResource(R.drawable.ic_pulltorefresh_arrow);
//		this.mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
//		this.mHeaderProgressBar.setVisibility(8);
//
//		this.mHeaderState = 2;
//	}
//
//	public void onHeaderRefreshComplete(String lastUpdated) {
//		setLastUpdated(lastUpdated);
//		onHeaderRefreshComplete();
//	}
//
//	public void onFooterRefreshComplete() {
//		setHeaderTopMargin(-this.mHeaderViewHeight);
//		if (this.mAdapterView != null) {
//			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mAdapterView
//					.getLayoutParams();
//			if ((this.mAdapterView.getMeasuredHeight() > 0)
//					&& (this.preListHeight > 0)) {
//				params.height = this.preListHeight;
//			}
//		}
//		this.mFooterImageView.setVisibility(0);
//		this.mFooterImageView
//				.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
//		this.mFooterTextView
//				.setText(R.string.pull_to_refresh_footer_pull_label);
//		this.mFooterProgressBar.setVisibility(8);
//
//		this.mFooterState = 2;
//	}
//
//	public void setLastUpdated(String lastUpdated) {
//		if (lastUpdated != null) {
//			this.mHeaderUpdateTextView.setVisibility(0);
//			this.mHeaderUpdateTextView.setText(lastUpdated);
//		} else {
//			this.mHeaderUpdateTextView.setVisibility(8);
//		}
//	}
//
//	private int getHeaderTopMargin() {
//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.mHeaderView
//				.getLayoutParams();
//		return params.topMargin;
//	}
//	/**
//	 * set headerRefreshListener
//	 * 
//	 * @param headerRefreshListener
//	 */
//	public void setOnHeaderRefreshListener(
//			OnHeaderRefreshListener headerRefreshListener) {
//		this.mOnHeaderRefreshListener = headerRefreshListener;
//	}
//
//	public void setOnFooterRefreshListener(
//			OnFooterRefreshListener footerRefreshListener) {
//		this.mOnFooterRefreshListener = footerRefreshListener;
//	}
//
//	public void setFootviewText(int text) {
//		this.footViewText = text;
//	}
//
//	public void setHeadviewText(int text) {
//	}
//
//	public boolean isNoRefresh() {
//		return this.noRefresh;
//	}
//
//	public void setNoRefresh(boolean noRefresh) {
//		this.noRefresh = noRefresh;
//	}
//
//	public boolean isNoAddMore() {
//		return this.noAddMore;
//	}
//
//	public void setNoAddMore(boolean noAddMore) {
//		this.noAddMore = noAddMore;
//		setFootViewVisible(!noAddMore);
//	}
//
//	public void setFootViewVisible(boolean b) {
//		if (b) {
//			this.mFooterView.setVisibility(0);
//		} else {
//			this.mFooterView.setVisibility(8);
//		}
//	}
//	/**
//	 * Interface definition for a callback to be invoked when list/grid footer
//	 * view should be refreshed.
//	 */
//	public static abstract interface OnFooterRefreshListener {
//		public abstract void onFooterRefresh(
//				PullToRefreshView paramPullToRefreshView);
//	}
//	/**
//	 * Interface definition for a callback to be invoked when list/grid header
//	 * view should be refreshed.
//	 */
//	public static abstract interface OnHeaderRefreshListener {
//		public abstract void onHeaderRefresh(
//				PullToRefreshView paramPullToRefreshView);
//	}
//}
