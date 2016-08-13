//package com.sunsoft.zyebiz.b2e.wiget;
//
//import android.app.Application;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//
///**
// * MyApplication
// * 
// * @author minking
// */
//public class MyApplication extends Application {
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		// ��ʼ��ImageLoader
//		@SuppressWarnings("deprecation")
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // ����ͼƬ�����ڼ���ʾ��ͼƬ
//				.showImageForEmptyUri(R.drawable.icon_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
//				.showImageOnFail(R.drawable.icon_error) // ����ͼƬ���ػ�������з��������ʾ��ͼƬ
//				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
//				.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
//				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
//				.build(); // �������ù��DisplayImageOption����
//
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
//				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//		ImageLoader.getInstance().init(config);
//	}
//
//}
