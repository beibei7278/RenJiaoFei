package com.yfd.appTest.softupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.setingActivity;
import com.yfd.appTest.softupdate.NotificationUpdate.ICallbackResult;

public class DownloadService extends Service {
	private static final int NOTIFY_ID = 0;
	private int progress;
	private NotificationManager mNotificationManager;
	private boolean canceled;
	// 鏉╂柨娲栭惃鍕暔鐟佸懎瀵榰rl
	private String apkUrl = BaseApplication.updateUrl;
	// private String apkUrl = MyApp.downloadApkUrl;
	/* 娑撳娴囬崠鍛暔鐟佸懓鐭惧锟�? */
	private static final String savePath = BaseApplication.updateszvelocal;

	private static final String saveFileName = BaseApplication.Savefilename;
	private ICallbackResult callback;

	private boolean serviceIsDestroy = false;

	private Context mContext = this;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				BaseApplication.getInstance().setDownload(false);
		
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				BaseApplication.getInstance().setDownload(false);
			
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 1:
				int rate = msg.arg1;
				BaseApplication.getInstance().setDownload(true);
				if (rate < 100) {
					RemoteViews contentview = mNotification.contentView;
					contentview.setTextViewText(R.id.tv_progress,"正在下载: "+ rate + "%");
					contentview.setProgressBar(R.id.progressbar, 100, rate, false);
				} else {
					System.out.println("下载完成!");
			
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;
					Intent intent = new Intent(mContext, setingActivity.class);
		
					intent.putExtra("completed", "yes");
		
					PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					mNotification.setLatestEventInfo(mContext, "任缴费", "正在下载", contentIntent);
					//
					serviceIsDestroy = true;
					stopSelf();
					
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			}
		}
	};

	//
	 @Override
	 public int onStartCommand(Intent intent, int flags, int startId) {
	 // TODO Auto-generated method stub
	 return START_STICKY;
	 }

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("downloadservice ondestroy");
		
		BaseApplication.getInstance().setDownload(false);
		
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("downloadservice onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub

		super.onRebind(intent);
		System.out.println("downloadservice onRebind");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//binder = new DownloadBinder();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
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
			mHandler.sendEmptyMessage(2);
		}

		public void addCallback(ICallbackResult callback) {
			DownloadService.this.callback = callback;
		}
	}

	private void startDownload() {
		// TODO Auto-generated method stub
		canceled = false;
		downloadApk();
	}

	//
	Notification mNotification;

	private void setUpNotification() {
		int icon = R.drawable.logo;
		CharSequence tickerText = "任缴费开始下载...";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.download_notification_layout);
		contentView.setTextViewText(R.id.name, "任缴费");
		
		mNotification.contentView = contentView;

		Intent intent = new Intent(this, setingActivity.class);
		
		 intent.setAction(Intent.ACTION_MAIN);
		 intent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	//
	/**
	 * 娑撳娴嘺pk
	 * 
	 * @param url
	 */
	private Thread downLoadThread;

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 鐎瑰顥奱pk
	 * 
	 * @param url
	 */
	private void installApk() {
		BaseApplication.ifupdate=false;
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
		callback.OnBackResult("finish");

	}
	
	private int lastRate = 0;
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				if(conn!=null){
					conn.connect();
				}
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 閺囧瓨鏌婃潻娑樺�?
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
						// 娑撳娴囩�瑰本鍨氶柅姘辩叀鐎瑰顥�?
						mHandler.sendEmptyMessage(0);
						// 娑撳娴囩�瑰奔绨￠敍�?�慳ncelled娑旂喕顩︾拋鍓х枂
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// 閻愮懓鍤�?崣鏍ㄧХ鐏忓崬浠犲顫瑓鏉烇�?.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

}
