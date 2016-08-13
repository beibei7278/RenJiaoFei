package com.yfd.appTest.softupdate;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.yfd.appTest.softupdate.DownloadService.DownloadBinder;


public class NotificationUpdate{
	private DownloadBinder binder;
	private boolean isBinded;
	// 锟斤拷取锟斤拷锟斤拷锟斤拷url锟斤拷直锟接革拷锟狡革拷MapApp,锟斤拷锟斤拷锟饺拷直锟斤拷锟�
	private String downloadUrl;
	//
	private boolean isDestroy = true;

    Context con;
	/** Called when the activity is first created. */
    String tv_progress;
	public NotificationUpdate(Context con){
this.con=con;

		//app = (MyApp)con.getApplicationContext();
		// btn_update = (Button) findViewById(R.id.update);
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				binder.cancel();
//				binder.cancelNotification();
//				finish();
//			}
//		});
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadBinder) service;

			// 锟斤拷始锟斤拷锟斤拷
			isBinded = true;
			//binder.addCallback(callback);
			binder.start();

		}
	};


	protected void onResume() {
		// TODO Auto-generated method stub
	
			Intent it = new Intent(con, DownloadService.class);
			con.startService(it);
			con.bindService(it, conn, Context.BIND_AUTO_CREATE);
		
	}


	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub

		
			Intent it = new Intent(con, DownloadService.class);
			con.startService(it);
			con.bindService(it, conn, Context.BIND_AUTO_CREATE);
	
	}

	
	protected void onStart() {
		// TODO Auto-generated method stub
		

	}


	protected void onPause() {
		// TODO Auto-generated method stub
		
		System.out.println(" notification  onPause");
	}


	protected void onStop() {
		// TODO Auto-generated method stub

		isDestroy = false;
		System.out.println(" notification  onStop");
	}


	protected void onDestroy() {
	
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			con.unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			System.out.println(" onDestroy  stopservice");
			Intent it = new Intent(con, DownloadService.class);
			con.stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				((Activity)con).finish();
				return;
			}
			
			int i = (Integer) result;

			mHandler.sendEmptyMessage(i);
		}

	};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			tv_progress="正在下载: " + msg.what + "%";
		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}
}
