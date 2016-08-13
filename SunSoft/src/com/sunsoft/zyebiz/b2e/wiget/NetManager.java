package com.sunsoft.zyebiz.b2e.wiget;

/**
 * 判断网络是否连接
 */
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.application.ECApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetManager {

	static Context context;

	public NetManager(Context context) {
		this.context = context;
	}

	// 判断网络是否可用的方法
	public static boolean isOpenNetwork() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (connectivity != null) {
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null)
//				for (int i = 0; i < info.length; i++)
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//		}
		final android.net.NetworkInfo wifi = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final android.net.NetworkInfo mobile = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (wifi.isAvailable() || mobile.isAvailable()){ // getState()方法是查询是否连接了数据网络
			return true;
		}
			
		return false;
	}

	// 判断WIFI网络是否可用的方法
	public boolean isOpenWifi() {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	public static void isHaveNetWork(Context context) {
		System.out.println("来到isHaveNetWork这里----------------------------");
		ConnectivityManager connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		System.out.println("isHaveNetWork报空了----------------------------");
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null) {
			Toast.makeText(context, "似乎互联网已断开连接", Toast.LENGTH_SHORT).show();
		} else {
			System.out.println("有网络");
		}
	}

	/**
	 * 判断WiFi和数据流量是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkConnection(Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (wifi.isAvailable() || mobile.isAvailable()) // getState()方法是查询是否连接了数据网络
			return true;
		else
			return false;
	}
	/**
	 * 判断WiFi是否连接
	 * @param mContext
	 * @return
	 */
	public static boolean isWifi() {  
		System.out.println("来到isWifi这里----------------------------");
		if(context == null){
			System.out.println("context是空----------------------------");
//			context = ECApplication.getInstance().getApplicationContext();
		}
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    System.out.println("isWifi这里报空了----------------------------");
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
	        return true;  
	    }  
	    return false;  
	}  
	/**
	 * 判断手机流量是否连接
	 * @param mContext
	 * @return
	 */
	public static boolean isMoble() {  
		System.out.println("来到isMoble这里----------------------------");
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    System.out.println("isMoble这里报空了----------------------------");
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
	        return true;  
	    }  
	    return false;  
	}  

}
