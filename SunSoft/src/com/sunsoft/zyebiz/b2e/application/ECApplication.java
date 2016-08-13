/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.sunsoft.zyebiz.b2e.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.activity.HomeActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.common.URLInterface;

/**
 * Created by Jorstin on 2015/3/17.
 */
public class ECApplication extends Application {

	private static ECApplication instance;

	public static boolean isTuangou = true;
	public static boolean isHaveDateGroup = true;

	public static String URL_SCHOOL_STORE = URLInterface.GROUP_BUY_DATE;

	public static int countNum;
	public static boolean CommonUrl = true ;
	public static boolean isFirstUpdate = true;
	//存储Activity的集合
	private List<Activity> mList = new LinkedList<Activity>();
	/** 设置版本号 */
	private String version;

//	public ECApplication() {
//	}

	public synchronized static ECApplication getInstance() {
		if (instance == null) {
			instance = new ECApplication();
		}
		return instance;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		super.onCreate();
//		Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()); //捕获异常
		instance = this;
		configImageLoader();
		JPushInterface.setDebugMode(true); //极光推送模式
		JPushInterface.init(this); // 极光推送初始化
	}

	public void addActivity(Activity activity) {
		mList.add(activity);
	}
	private void configImageLoader() {
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.yifu) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.yifu) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.yifu) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}


	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
	
	public String getSpUrl(){
		SharedPreferences sp = getApplicationContext().getSharedPreferences("baseurl", MODE_PRIVATE);
		String baseUrl = sp.getString("baseurl", "");
		return baseUrl;
	}
	
	
	private class MyUncaughtExceptionHandler implements UncaughtExceptionHandler{
		//系统中由未捕获的异常的时候调用
		//Throwable : Error和Exception的父类
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			ex.printStackTrace();
			try {
				//将捕获到异常,保存到SD卡中
				System.out.println("捕获到了异常----------------");
//				Intent intent = new Intent(ECApplication.getInstance().getApplicationContext(),HomeActivity.class);
//				startActivity(intent);
				ex.printStackTrace(new PrintStream(new File("/mnt/sdcard/mjxygzylog.txt")));
				
			} catch (FileNotFoundException e) {
//				Intent intent = new Intent(ECApplication.getInstance().getApplicationContext(),HomeActivity.class);
//				startActivity(intent);
				e.printStackTrace();
			}
			//myPid() : 获取当前应用程序的进程id
			//自己把自己杀死
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}
