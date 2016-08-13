package com.sunsoft.zyebiz.b2e.wiget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.sunsoft.zyebiz.b2e.R;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.test_view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
	/**
	 * ����Ĭ�ϱ���ͼƬ
	 * @param context
	 * @return
	 */
	public static ImageView setBackPic(Context context) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.test_view_banner, null);
		imageView.setBackgroundResource(R.drawable.login_carousel);
		return imageView;
	}
}
