package com.yfd.appTest.widget;

/**
 * 功能：保存数据
 * @author YinGuiChun
 * @date 2016-07-14
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {
	/** 需要上传的图片个数 */
	public static String picNumber;
	/**是否必须升级，0表示不是必须升级*/
	public static String must_upgrade = "0";
	/***/
	public static boolean is_puhao = true;

	/**
	 * 获得登陆用户信息
	 */
	public static void getUserInfo(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		picNumber = pref.getString("PIC_NUMBER", null);
		must_upgrade = pref.getString("MUST_UPGRADE",null);

	}

	/**
	 * 清除登陆用户信息
	 */
	public static void cleanUserInfo(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

}
