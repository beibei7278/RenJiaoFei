//package com.sunsoft.zyebiz.b2e.wiget;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.os.Handler;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.model.ADInfo;
//
///**
// * 广告图片自动轮播控件</br>
// * 
// * <pre>
// *   集合ViewPager和指示器的一个轮播控件，主要用于�?��常见的广告图片轮播，具有自动轮播和手动轮播功�?
// *   使用：只�?��xml文件中使用{@code <com.minking.imagecycleview.ImageCycleView/>} �? *   然后在页面中调用  {@link #setImageResources(ArrayList, ImageCycleViewListener) }即可!
// *   
// *   另外提供{@link #startImageCycle() } \ {@link #pushImageCycle() }两种方法，用于在Activity不可见之时节省资源；
// *   因为自动轮播�?��进行控制，有利于内存管理
// * </pre>
// * 
// */
//public class ImageCycleView extends LinearLayout {
//
//	/**
//	 * ������
//	 */
//	private Context mContext;
//
//	/**
//	 * ͼƬ�ֲ���ͼ
//	 */
//	private CyclePager mBannerPager = null;
//
//	/**
//	 * ��ͼƬ��ͼ������
//	 */
//	private ImageCycleAdapter mAdvAdapter;
//
//	/**
//	 * ͼƬ�ֲ�ָʾ��ؼ�
//	 */
//	private ViewGroup mGroup;
//
//	/**
//	 * ͼƬ�ֲ�ָʾ��-��ͼ
//	 */
//	private ImageView mImageView = null;
//
//	/**
//	 * ��ͼƬָʾ��-��ͼ�б�
//	 */
//	private ImageView[] mImageViews = null;
//
//	/**
//	 * ͼƬ���ǰͼƬ�±�
//	 */
//	private int mImageIndex = 1;
//
//	/**
//	 * �ֻ��ܶ�
//	 */
//	private float mScale;
//
//	/**
//	 * @param context
//	 */
//	public ImageCycleView(Context context) {
//		super(context);
//	}
//
//	/**
//	 * @param context
//	 * @param attrs
//	 */
//	public ImageCycleView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		mContext = context;
//		mScale = context.getResources().getDisplayMetrics().density;
//		LayoutInflater.from(context).inflate(R.layout.view_banner_content, this);
//		mBannerPager = (CyclePager) findViewById(R.id.pager_banner);
//		mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
//		mBannerPager.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//					case MotionEvent.ACTION_UP:
//						// ��ʼͼƬ��
//						startImageTimerTask();
//						break;
//					default:
//						// ֹͣͼƬ��
//						stopImageTimerTask();
//						break;
//				}
//				return false;
//			}
//		});
//		// ��ͼƬ����ָʾ����ͼ
//		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
//	}
//
//	/**
//	 * װ��ͼƬ���
//	 * 
//	 * @param imageUrlList
//	 * @param imageCycleViewListener
//	 */
//	public void setImageResources(ArrayList<ADInfo> infoList, ImageCycleViewListener imageCycleViewListener) {
//		// �����������ͼ
//		mGroup.removeAllViews();
//		// ͼƬ�����
//		final int imageCount = infoList.size();
//		mImageViews = new ImageView[imageCount];
//		for (int i = 0; i < imageCount; i++) {
//			mImageView = new ImageView(mContext);
//			int imageParams = (int) (mScale * 20 + 0.5f);// XP��DPת������Ӧ��ͬ�ֱ���
//			int imagePadding = (int) (mScale * 5 + 0.5f);
//			LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			layout.setMargins(3, 0, 3, 0);
//			mImageView.setLayoutParams(layout);
//			//mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
//			mImageViews[i] = mImageView;
//			if (i == 0) {
//				mImageViews[i].setBackgroundResource(R.drawable.icon_point_pre);
//			} else {
//				mImageViews[i].setBackgroundResource(R.drawable.icon_point);
//			}
//			mGroup.addView(mImageViews[i]);
//		}
//		mAdvAdapter = new ImageCycleAdapter(mContext, infoList, imageCycleViewListener);
//		mBannerPager.setAdapter(mAdvAdapter);
//		startImageTimerTask();
//	}
//
//	/**
//	 * ��ʼ�ֲ�(�ֶ������Զ��ֲ���񣬱�����Դ����)
//	 */
//	public void startImageCycle() {
//		startImageTimerTask();
//	}
//
//	/**
//	 * ��ͣ�ֲ��������ڽ�ʡ��Դ
//	 */
//	public void pushImageCycle() {
//		stopImageTimerTask();
//	}
//
//	/**
//	 * ��ʼͼƬ������
//	 */
//	private void startImageTimerTask() {
//		stopImageTimerTask();
//		// ͼƬÿ3���һ��
//		mHandler.postDelayed(mImageTimerTask, 3000);
//	}
//
//	/**
//	 * ֹͣͼƬ������
//	 */
//	private void stopImageTimerTask() {
//		mHandler.removeCallbacks(mImageTimerTask);
//	}
//
//	private Handler mHandler = new Handler();
//
//	/**
//	 * ͼƬ�Զ��ֲ�Task
//	 */
//	private Runnable mImageTimerTask = new Runnable() {
//
//		@Override
//		public void run() {
//			if (mImageViews != null) {
//				// �±����ͼƬ�б?��˵���ѹ�����һ��ͼƬ,�����±�
//				if ((++mImageIndex) == mImageViews.length + 1) {
//					mImageIndex = 1;
//				}
//				mBannerPager.setCurrentItem(mImageIndex);
//			}
//		}
//	};
//
//	/**
//	 * �ֲ�ͼƬ״̬������
//	 * 
//	 * @author minking
//	 */
//	private final class GuidePageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageScrollStateChanged(int state) {
//			if (state == ViewPager.SCROLL_STATE_IDLE)
//				startImageTimerTask(); // ��ʼ�´μ�ʱ
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//		}
//
//		@Override
//		public void onPageSelected(int index) {
//			
//			if (index == 0 || index == mImageViews.length + 1) {
//				return;
//			}
//			// ����ͼƬ��ָʾ���
//			mImageIndex = index;
//			index -= 1;
//			mImageViews[index].setBackgroundResource(R.drawable.icon_point_pre);
//			for (int i = 0; i < mImageViews.length; i++) {
//				if (index != i) {
//					mImageViews[i].setBackgroundResource(R.drawable.icon_point);
//				}
//			}
//
//		}
//
//	}
//
//	private class ImageCycleAdapter extends PagerAdapter {
//
//		/**
//		 * ͼƬ��ͼ�����б�
//		 */
//		private ArrayList<ImageView> mImageViewCacheList;
//
//		/**
//		 * ͼƬ��Դ�б�
//		 */
//		private ArrayList<ADInfo> mAdList = new ArrayList<ADInfo>();
//
//		/**
//		 * ���ͼƬ��������
//		 */
//		private ImageCycleViewListener mImageCycleViewListener;
//
//		private Context mContext;
//
//		public ImageCycleAdapter(Context context, ArrayList<ADInfo> adList, ImageCycleViewListener imageCycleViewListener) {
//			mContext = context;
//			mAdList = adList;
//			mImageCycleViewListener = imageCycleViewListener;
//			mImageViewCacheList = new ArrayList<ImageView>();
//		}
//
//		@Override
//		public int getCount() {
//			return mAdList.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object obj) {
//			return view == obj;
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, final int position) {
//			String imageUrl = mAdList.get(position).getUrl();
//			ImageView imageView = null;
//			if (mImageViewCacheList.isEmpty()) {
//				imageView = new ImageView(mContext);
//				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//				
//			} else {
//				imageView = mImageViewCacheList.remove(0);
//			}
//			// ����ͼƬ������
//			imageView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					mImageCycleViewListener.onImageClick(mAdList.get(position),position, v);
//				}
//			});
//			imageView.setTag(imageUrl);
//			container.addView(imageView);
//			mImageCycleViewListener.displayImage(imageUrl, imageView);
//			return imageView;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			ImageView view = (ImageView) object;
//			container.removeView(view);
//			mImageViewCacheList.add(view);
//		}
//
//	}
//
//	/**
//	 * �ֲ��ؼ��ļ����¼�
//	 * 
//	 * @author minking
//	 */
//	public static interface ImageCycleViewListener {
//
//		/**
//		 * ����ͼƬ��Դ
//		 * 
//		 * @param imageURL
//		 * @param imageView
//		 */
//		public void displayImage(String imageURL, ImageView imageView);
//
//		/**
//		 * ����ͼƬ�¼�
//		 * 
//		 * @param position
//		 * @param imageView
//		 */
//		public void onImageClick(ADInfo info, int postion, View imageView);
//	}
//
//}
