package com.sunsoft.zyebiz.b2e.wiget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {
	public static String strUserName;
	public static String strUserRealName;
	public static String strPassword;
	public static String strUserId;
	public static String strUserPhone;
	public static String strUserGrade;
	public static String strUserClass;
	public static String strUserIcon;
	public static String strUserSex;
	public static String strSchoolName;
	public static String strSchoolID;
	public static String strUserToken;
	public static String strUserType;
	public static Boolean isHaveDate;
	
	public static String strOrderId;
	public static String strOrderGoodsDetail;
	public static String strOrderPrice;
	public static String strOrderTitleName;
	public static String strOrderVircardnoin;
	public static String strOrderVerficationcode;
	public static String strOrderMerchantid;
	public static String strOrderUrl;
	public static String strPayMent;
	
	

	/**
	 * 获得用户信息
	 * 
	 */
	public static void getUserInfo(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		strUserName = pref.getString("USER_NAME", null);
		strUserRealName = pref.getString("USER_REALNAME", null);
		strPassword = pref.getString("PASSWORD", null);
		strUserId = pref.getString("USER_ID", null);
		strUserPhone = pref.getString("USER_PHONE", null);
		strUserGrade = pref.getString("USER_GRADE", null);
		strUserClass = pref.getString("USER_CLASS", null);
		strUserIcon = pref.getString("USER_ICON", null);
		strUserSex = pref.getString("USER_SEX", null);
		strSchoolName = pref.getString("USER_SCHOOLNAME", null);
		strSchoolID = pref.getString("USER_SCHOOLID", null);
		strUserToken = pref.getString("USER_TOKEN", null);
		strUserType = pref.getString("USER_TYPE", null);
		isHaveDate = pref.getBoolean("HAVE_DATE", false);
	}

	/**
	 * 清除用户名和密码
	 */
	public static void cleanUserInfo(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	/**
	 * 获得订单信息
	 * 
	 */
	public static void getOrderMessage(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("orderMessage",
				Context.MODE_PRIVATE);
		strOrderId = pref.getString("CONFIRM_ORDERID", null);
		strOrderGoodsDetail= pref.getString("CONFIRM_GOODSDETAIL", null);
		strOrderPrice= pref.getString("CONFIRM_PRICE", null);
		strOrderTitleName= pref.getString("CONFIRM_TITLENAME", null);
		strOrderVircardnoin= pref.getString("CONFIRM_VIRCARDNOIN", null);
		strOrderVerficationcode= pref.getString("CONFIRM_VERFICATIONCODE", null);
		strOrderMerchantid= pref.getString("CONFIRM_MERCHANTID", null);
		strOrderUrl= pref.getString("CONFIRM_URL", null);
		strPayMent = pref.getString("CONFIRM_PAY_MENT", null);
		
	}

	/**
	 * 清除订单信息
	 */
	public static void cleanOrderMessage(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("orderMessage",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 获得用户信息
	 * 
	 */
	public static void isHaveDate(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("isHaveDate",
				0);
		isHaveDate = pref.getBoolean("HAVE_DATE", false);
	}

	/**
	 * 清除用户名和密码
	 */
	public static void cleanisHaveDate(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("isHaveDate",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

}
