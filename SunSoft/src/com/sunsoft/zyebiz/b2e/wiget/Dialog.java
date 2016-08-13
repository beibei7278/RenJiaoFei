package com.sunsoft.zyebiz.b2e.wiget;

import com.sunsoft.zyebiz.b2e.application.ECApplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

public class Dialog{
	/**
	 * Dialog提示框
	 * 
	 * @param string
	 *            提示信息
	 */
	public static void showAlertDialog(Context context,String string) {

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle("提示");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}
	public static void DialogExit(Context context,String string) {

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle("提示");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
//						System.exit(0);
						ECApplication.getInstance().exit();
					}
				});

		builder.create().show();

	}
	
	/**
	 * Dialog提示框
	 * 
	 * @param string
	 *            提示信息
	 */
	public static void okshowAlertDialog(Context context,String string) {

		OkCustomDialog.Builder builder = new OkCustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle("提示");
//		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				// 设置你的操作事项
//			}
//		});

		builder.setNegativeButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}
	
	
}
