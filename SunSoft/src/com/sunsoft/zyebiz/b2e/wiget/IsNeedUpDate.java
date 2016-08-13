//package com.sunsoft.zyebiz.b2e.wiget;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import javax.net.ssl.HttpsURLConnection;
//import org.apache.http.Header;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.app.AlertDialog.Builder;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Matrix;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.bufengsoft.clothingstore.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.UpDate.BodyResult;
//import com.sunsoft.zyebiz.b2e.model.UpDate.VersionUpDate;
//import com.sunsoft.zyebiz.b2e.service.DownloadApkService;
//import com.sunsoft.zyebiz.b2e.service.DownloadApkService.DownloadBinder;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//
//public class IsNeedUpDate {
//	/** 设置版本号 */
//	private TextView tv_version;
//	private static String version;
//	private boolean isBinded = false;
//	private ProgressDialog proDia;
//	private static final int DOWN_OVER = 2;
//	private static final int DOWN_ERROR = 3;
//	private static final int SET_DOWNLOAD_MAX = 15;
//	private static final int REFRESS_DOWNLOAD_PROGRESS = 16;
//	private static final int STOP_DOWNLOAD_APK = 17;
//	private Long totalLenth;
//	private Long readLenth;
//	private boolean isDownloadOver = false;
//	private DownloadBinder binder;
//	private boolean isStopDownload = false;
//	private Matrix matrix = new Matrix();
//	/** 项目缓存地址 */
//	public static String APKCachePath = Environment
//			.getExternalStorageDirectory() + "/";
//	private static final String saveFileName = APKCachePath + "sssssssss.apk";
//	public static String downUrl;
//	
//	private Context mContext;
//	/**
//	 * 判断是否需要版本更新
//	 */
//	public static void statrDataSumber(final Context mContext) {
//		/**开始提交数据 */
//		try {
//			version = getVersionName();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		RequestParams params = new RequestParams();
//		params.put("versionCode", "v1.0");
//		params.put("type", "Android");
//		final Gson gson = new Gson();
//		AsyncHttpUtil.post(URLInterface.VERSION_UPDATE, params,
//				new AsyncHttpResponseHandler() {
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						if (statusCode == 200) {
//							String resultDate = new String(responseBody);
//							VersionUpDate versionUpdate = gson.fromJson(
//									resultDate, VersionUpDate.class);
//							BodyResult bodyResult = versionUpdate.getBody();
//
//							if (EmptyUtil.isNotEmpty(bodyResult)) {
//								// downUrl = bodyResult.getIsUpdate();
//								downUrl = "https://www.baidu.com";
//								System.out.println("downUrl=======" + downUrl);
//								if (EmptyUtil.isNotEmpty(downUrl)) {
//									isNeedUpdate(mContext);
//								}
//
//							}
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//					}
//				});
//
//	}
//	/**
//	 * 判断是否需要更新
//	 */
//
//	public static void isNeedUpdate(final Context context) {
//
//		UpDateDialog.Builder builder = new UpDateDialog.Builder(
//				UserMainActivity.mainActivity);
//		builder.setMessage("检测到新版本，请下载升级！");
//		builder.setTitle("版本更新");
//		builder.setPositiveButton(
//				UserMainActivity.mainActivity.getString(R.string.update_ok),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						 update(context);
//					}
//				});
//
//		builder.setNegativeButton(context.getString(R.string.update_cancle),
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//
//					}
//
//				});
//
//		builder.create().show();
//
//	}
//	/**
//	 * Handler接受下载
//	 */
//	private Handler handler = new Handler() {
//		// @SuppressLint("NewApi")
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case STOP_DOWNLOAD_APK:
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				proDia = null;
//				isStopDownload = false;
//				break;
//			case SET_DOWNLOAD_MAX:
//				isDownloadOver = false;
//				if (null == proDia) {
//					return;
//				}
//				int maxValue = (Integer) msg.obj;
//				proDia.setMax(maxValue);
//
//				proDia.setTitle("正在下载文件，总共  " + maxValue / 1024 + "kb");
//				break;
//			case REFRESS_DOWNLOAD_PROGRESS:
//				if (null == proDia) {
//					return;
//				}
//				isDownloadOver = false;
//				int currentProgress = (Integer) msg.obj;
//				proDia.setMessage("已下载  " + currentProgress / 1024 + "kb");
//
//				proDia.setProgress(currentProgress);
//				if (currentProgress < 100) {
//
//				}
//				break;
//			case DOWN_OVER:
//				isDownloadOver = true;
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				installApk();
//				break;
//			case DOWN_ERROR:
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	/**
//	 * 获得当前的程序版本号
//	 * 
//	 * @return
//	 * @throws Exception
//	 *             异常抛出
//	 */
//	private static  String getVersionName(Context mContext) throws Exception {
//		// 获取packagemanager的实例
//		PackageManager packageManager = mContext.getPackageManager();
//		// getPackageName()是你当前类的包名，0代表是获取版本信息
//		PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),
//				0);
//		System.out.println("------------------程序开始运行-------------------");
//		System.out.println("获取到的软件当前版本号是：" + packInfo.versionName);
//		return packInfo.versionName;
//
//	}
//
//	/**
//	 * 启动服务
//	 */
//	public static void update(Context mContext) {
//		Intent intentDownload = new Intent(context, DownloadApkService.class);
//		// startService(intentDownload);
//		mContext.bindService(intentDownload, conn, mContext.BIND_AUTO_CREATE);
//	}
//
//	@SuppressWarnings("unused")
//	private class DownloadFileRunnable implements Runnable {
//		public void run() {
//			try {
//				URL url = new URL(downUrl);
//				HttpURLConnection conn = (HttpURLConnection) url
//						.openConnection();
//				conn.setDoInput(true);
//				conn.setUseCaches(true);
//				conn.setConnectTimeout(2 * 1000);
//				conn.setRequestMethod("GET");
//				totalLenth = (long) conn.getContentLength();
//				Message msgTotalLength = new Message();
//				msgTotalLength.what = SET_DOWNLOAD_MAX;
//				msgTotalLength.obj = totalLenth.intValue();
//				handler.sendMessage(msgTotalLength);
//				File file = new File(APKCachePath);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				File fileApk = new File(saveFileName);
//				if (!fileApk.exists()) {
//					try {
//						fileApk.createNewFile();
//					} catch (Exception e) {
//						Toast.makeText(UserMainActivity.mainActivity, "没有存储设备，无法下载最新版本",
//								Toast.LENGTH_SHORT).show();
//					}
//				}
//				if (null != fileApk && fileApk.length() < totalLenth) {
//					fileApk.delete();
//					fileApk.createNewFile();
//				}
//				if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//					System.out.println("connect success");
//					InputStream in = conn.getInputStream();
//					FileOutputStream fileOut = new FileOutputStream(fileApk);
//					byte[] buffer = new byte[1024];
//					int length = -1;
//					byte[] data = null;
//					try {
//						while ((length = in.read(buffer)) != -1
//								&& !isStopDownload) {
//							fileOut.write(buffer, 0, length);
//							readLenth = fileApk.length();
//							Message msgProgress = new Message();
//							msgProgress.what = REFRESS_DOWNLOAD_PROGRESS;
//							msgProgress.obj = readLenth.intValue();
//							handler.sendMessage(msgProgress);
//						}
//						if (!isStopDownload) {
//
//							Message msgDownloadOK = new Message();
//							msgDownloadOK.what = DOWN_OVER;
//							handler.sendMessage(msgDownloadOK);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						if (null != in) {
//							in.close();
//						}
//						if (null != conn) {
//							conn.disconnect();
//						}
//						if (null != fileOut) {
//							fileOut.flush();
//							fileOut.close();
//						}
//						if (null != proDia) {
//							proDia.dismiss();
//						}
//					}
//				}
//
//			} catch (Exception e) {
//				Message msgError = new Message();
//				msgError.what = DOWN_ERROR;
//				handler.sendMessage(msgError);
//			}
//		}
//	}
//
//	/**
//	 * 回调
//	 */
//	private ICallbackResult callback = new ICallbackResult() {
//		@Override
//		public void OnBackResult(Object result) {
//			if ("finish".equals(result)) {
////				finish();
//				return;
//			}
//		}
//	};
//	/**
//	 * 启动服务
//	 */
//	ServiceConnection conn = new ServiceConnection() {
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			isBinded = false;
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			binder = (DownloadBinder) service;
//			System.out.println("服务启动!!!");
//			binder = (DownloadBinder) service;
//			System.out.println("服务启动!!!");
//			isBinded = true;
//			binder.addCallback(callback);
//			binder.start();
//
//		}
//
//	};
//
//	/**
//	 * 自动安装APK
//	 */
//	private void installApk() {
//		File apkfile = new File(saveFileName);
//		if (!apkfile.exists()) {
//			return;
//		}
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//				"application/vnd.android.package-archive");
//		UserMainActivity.mainActivity.startActivity(i);
//
//	}
//
//	/**
//	 * 判断网络连接是否已开true 已打开 false 未打开
//	 * 
//	 * @param context
//	 *            继承上下文
//	 * @return 返回状态
//	 */
//	public static boolean isConn(Context context) {
//		boolean bisConnFlag = false;
//		ConnectivityManager conManager = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo network = conManager.getActiveNetworkInfo();
//		if (network != null) {
//			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
//		}
//		return bisConnFlag;
//	}
//
//	/**
//	 * 打开设置网络界面
//	 * 
//	 * @param context
//	 *            继承上下文
//	 */
//	public static void setNetworkMethod(final Context context) {
//		AlertDialog.Builder builder = new Builder(context);
//		builder.setTitle("网络设置提示")
//				.setMessage("网络连接不可用,是否进行设置?")
//				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = null;
//						// 判断手机系统的版本 即API大于10 就是3.0或以上版本
//						if (android.os.Build.VERSION.SDK_INT > 10) {
//							intent = new Intent(
//									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//						} else {
//							intent = new Intent();
//							ComponentName component = new ComponentName(
//									"com.android.settings",
//									"com.android.settings.WirelessSettings");
//							intent.setComponent(component);
//							intent.setAction("android.intent.action.VIEW");
//						}
//						context.startActivity(intent);
//					}
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				}).show();
//	}
//
//}
