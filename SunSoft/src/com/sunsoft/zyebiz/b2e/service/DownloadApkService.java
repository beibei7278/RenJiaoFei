package com.sunsoft.zyebiz.b2e.service;

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.activity.HomeActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.ICallbackResult;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

public class DownloadApkService extends Service {
	private final int SUCCESS_DOWN_LOAD = 12;
	private final int CANCEL_DOWNLOAD = 11;
	private final int DOWNLOAD_ERROR = 14;
	private final int SET_PROGRESS = 13;

	private static final int NOTIFY_ID = 0;
	public static int progress;
	public static int rate;
	private NotificationManager mNotificationManager;
	private boolean canceled;

	private String apkUrl = "";
	private String ACTION_CANCEL_DOWNLOAD_APK = "action_cancel_download_apk";
	private String ACTION_PAUSE_DOWNLOAD_APK = "action_pause_download_apk";

	private static final String savePath = "/sdcard/wcs/";
	private static final String saveFileName = savePath + "wcs.apk";
	private ICallbackResult callback;
	private DownloadBinder binder;
	private boolean serviceIsDestroy = false;
	private Context mContext = this;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SUCCESS_DOWN_LOAD:
				mNotificationManager.cancel(NOTIFY_ID);
				SharedPreference.cleanUserInfo(mContext);
				installApk();
				break;
			case CANCEL_DOWNLOAD:
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case DOWNLOAD_ERROR:
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case SET_PROGRESS:
				rate = msg.arg1;
				if (rate < 100) {
					RemoteViews contentview = mNotification.contentView;
					contentview.setTextViewText(R.id.tv_progress, rate + "%");
					contentview.setTextColor(R.id.tv_progress, 0xff000000);
					contentview.setProgressBar(R.id.progressbar, 100, rate,
							false);

				} else {
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;
					Intent intent = new Intent(mContext, HomeActivity.class);
					// 告知已完成
					intent.putExtra("completed", "yes");
					// 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
					PendingIntent contentIntent = PendingIntent.getActivity(
							mContext, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					mNotification.setLatestEventInfo(mContext, "下载完成",
							"文件已下载完毕", contentIntent);
					serviceIsDestroy = true;

					stopSelf();// 停掉服务自身
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {

		super.onRebind(intent);
		System.out.println("downloadservice onRebind");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("userType:" + SharedPreference.strUserType);
		if(EmptyUtil.isEmpty(SharedPreference.strUserType)){
			apkUrl = HomeActivity.downUrl;
		}else{
			if (SharedPreference.strUserType.equals("00")
					|| SharedPreference.strUserType.equals("01")) {
				apkUrl = UserMainActivity.downUrl;
				if(EmptyUtil.isEmpty(apkUrl)){
					SharedPreferences spApkUrl = getSharedPreferences("set_apk_url",MODE_PRIVATE);
					apkUrl = spApkUrl.getString("downUrl", "");
				}
			}
		}
		

		binder = new DownloadBinder();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_CANCEL_DOWNLOAD_APK);
		registerReceiver(onclickCancelListener, filter);

	}

	public class DownloadBinder extends Binder {
		public void start() {
			if (downLoadThread == null || !downLoadThread.isAlive()) {
				progress = 0;
				setUpNotification();
				new Thread() {
					public void run() {
						startDownload();
					};
				}.start();
			}
		}

		public void cancel() {
			canceled = true;
		}

		public int getProgress() {
			return progress;
		}

		public boolean isCanceled() {
			return canceled;
		}

		public boolean serviceIsDestroy() {
			return serviceIsDestroy;
		}

		public void cancelNotification() {
			mHandler.sendEmptyMessage(CANCEL_DOWNLOAD);
		}

		public void addCallback(ICallbackResult callback) {
			DownloadApkService.this.callback = callback;
		}
	}

	private void startDownload() {
		canceled = false;
		downloadApk();
	}

	BroadcastReceiver onclickCancelListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_CANCEL_DOWNLOAD_APK)) {
				mNotificationManager.cancel(NOTIFY_ID);
				binder.cancel();
				binder.cancelNotification();
				if (binder != null && binder.isCanceled()) {
					stopSelf();
				}
				callback.OnBackResult("cancel");
			}
		}
	};
	BroadcastReceiver onClickPauseLisener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_PAUSE_DOWNLOAD_APK)) {
				callback.OnBackResult("cancel");
			}
		}
	};

	Notification mNotification;

	private void setUpNotification() {
		int icon = R.drawable.logo;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.download_notification_layout);
		contentView.setTextColor(R.id.name, 0xff000000);
		contentView.setTextViewText(R.id.name, "正在下载...");
		// 指定个性化视图
		mNotification.contentView = contentView;
		Intent btnCancelIntent = new Intent(ACTION_CANCEL_DOWNLOAD_APK);
		PendingIntent pendButtonIntent = PendingIntent.getBroadcast(this, 0,
				btnCancelIntent, 0);
		contentView.setOnClickPendingIntent(R.id.ivDelete, pendButtonIntent);
		Intent intent = new Intent(this, HomeActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 指定内容意图
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	private Thread downLoadThread;

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable1);
		downLoadThread.start();
	}

	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
		callback.OnBackResult("finish");

	}

	private int lastRate = 0;
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int totalLength = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}

				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);

				if (null != ApkFile && ApkFile.length() < totalLength) {
					ApkFile.delete();
					ApkFile.createNewFile();
				}

				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / totalLength) * 100);
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
						if (callback != null)
							callback.OnBackResult(progress);
					}
					if (numread <= 0) {
						mHandler.sendEmptyMessage(SUCCESS_DOWN_LOAD);
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	private Runnable mdownApkRunnable1 = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.setUseCaches(true);
				conn.setConnectTimeout(2 * 1000);
				conn.setRequestMethod("GET");
				long totalLenth = (long) conn.getContentLength();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				File fileApk = new File(saveFileName);
				if (!fileApk.exists()) {
					try {
						fileApk.createNewFile();
					} catch (Exception e) {
					}
				}
				if (null != fileApk && fileApk.length() < totalLenth) {
					fileApk.delete();
					fileApk.createNewFile();
				}
				if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
					return;
				}

				InputStream in = conn.getInputStream();
				FileOutputStream fileOut = new FileOutputStream(fileApk);
				byte[] buffer = new byte[1024];
				int length = -1;
				int currentCount = 0;
				try {
					while ((length = in.read(buffer)) != -1 && !canceled) {
						progress = (int) (((float) fileApk.length() / totalLenth) * 100);
						Message msg = mHandler.obtainMessage();
						msg.what = SET_PROGRESS;
						msg.arg1 = progress;
						if (progress >= lastRate + 1) {
							mHandler.sendMessage(msg);
							lastRate = progress;
							if (callback != null)
								callback.OnBackResult(progress);
						}
						fileOut.write(buffer, 0, length);
					}
					if (canceled) {
						Message msgDownloadCancel = new Message();
						msgDownloadCancel.what = CANCEL_DOWNLOAD;
						mHandler.sendMessage(msgDownloadCancel);
						return;
					}
					mHandler.sendEmptyMessage(SUCCESS_DOWN_LOAD);
					canceled = true;
				} catch (Exception e) {
					e.printStackTrace();
					Message msgError = new Message();
					msgError.what = DOWNLOAD_ERROR;
					mHandler.sendMessage(msgError);
				} finally {
					if (null != in) {
						in.close();
					}
					if (null != conn) {
						conn.disconnect();
					}
					if (null != fileOut) {
						fileOut.flush();
						fileOut.close();
					}
				}

			} catch (Exception e) {
				Message msgError = new Message();
				msgError.what = DOWNLOAD_ERROR;
				mHandler.sendMessage(msgError);
			}
		}
	};
}
