//package com.sunsoft.zyebiz.b2e.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//
//public class ApkUtils {
//
//	/**
//	 * 检查手机上是否安装了指定的软件
//	 * 
//	 * @param context
//	 * @param packageName
//	 *            ：应用包名
//	 * @return
//	 */
//	public static boolean isAvilible(Context context, String packageName) {
//		// 获取packagemanager
//		final PackageManager packageManager = context.getPackageManager();
//		// 获取所有已安装程序的包信息
//		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
//		// 用于存储所有已安装程序的包名
//		List<String> packageNames = new ArrayList<String>();
//		// 从pinfo中将包名字逐一取出，压入pName list中
//		if (packageInfos != null) {
//			for (int i = 0; i < packageInfos.size(); i++) {
//				String packName = packageInfos.get(i).packageName;
//				packageNames.add(packName);
//			}
//		}
//		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
//		return packageNames.contains(packageName);
//	}
//
//	/**
//	 * 检查手机上是否安装了指定的软件
//	 * 
//	 * @param context
//	 * @param applicationInfo
//	 *            ：应用名
//	 * @return
//	 */
//	public static boolean isInstalled(Context context, String applicationInfo) {
//		PackageManager pManager = context.getPackageManager();
//		List<PackageInfo> packageInfo = SystemUtils.getAllApps(context);
//		if (packageInfo != null) {
//			for (int i = 0; i < packageInfo.size(); i++) {
//				PackageInfo pinfo = packageInfo.get(i);
//				// 应用名
//				String appName = pManager.getApplicationLabel(
//						pinfo.applicationInfo).toString();
//				if (appName.equals(applicationInfo)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//}
