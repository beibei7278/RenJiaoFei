package com.yfd.appTest.Activity;

import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.com.senter.helper.ConsantHelper;
import cn.com.senter.helper.ShareReferenceSaver;
import cn.com.senter.model.IdentityCardZ;
import cn.com.senter.sdkdefault.helper.Error;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Utils.BlueReaderHelper;

public class Testbulue extends Activity {
Button butn;

//----蓝牙功能有关的变量----
private final static String SERVER_KEY1 = "CN.COM.SENTER.SERVER_KEY1";
private final static String PORT_KEY1 = "CN.COM.SENTER.PORT_KEY1";
private final static String BLUE_ADDRESSKEY = "CN.COM.SENTER.BLUEADDRESS";
private final static String KEYNM = "CN.COM.SENTER.KEY";
 private static final int REQUEST_CONNECT_DEVICE = 1;
private BluetoothAdapter mBluetoothAdapter = null;			///蓝牙适配器
private BlueReaderHelper mBlueReaderHelper;
private int iselectNowtype = 0;
private String Blueaddress = null;

private PowerManager.WakeLock wakeLock = null;
String et_mmsnumber = "";
String et_imsinumber = "";
private String server_address = "senter-online.cn";
private int server_port = 10002;
private ImageView photoView;

Context con;

private MyHandler uiHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.testblue);
		con=this;
		
		//蓝牙读卡    
	     uiHandler = new MyHandler(this);
	   	 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		 if (mBluetoothAdapter == null) {
	            Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	     }
		 mBlueReaderHelper = new BlueReaderHelper(con, uiHandler);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		 Blueaddress = ShareReferenceSaver.getData(this, BLUE_ADDRESSKEY);
		 initShareReference();
		
		butn=(Button)findViewById(R.id.saosao);
	
		
		
		
		butn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				readCardBlueTooth();
			}
		});
	}

	
	
	class MyHandler extends Handler {
		private Testbulue activity;

		MyHandler(Testbulue activity) {
			this.activity = activity;
		}

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case ConsantHelper.READ_CARD_SUCCESS:
				Toast.makeText(activity, "sus。", 1000).show();
				break;

			case ConsantHelper.SERVER_CANNOT_CONNECT:			
			Toast.makeText(activity, "服务器连接失败! 请检查网络。", 1000).show();
				break;

			case ConsantHelper.READ_CARD_FAILED:
				Toast.makeText(activity, "无法读取信息请重试!", 1000).show();
				break;

			case ConsantHelper.READ_CARD_WARNING:
				 String str = (String)msg.obj;

                 if(str.indexOf("card")>-1){                    
                 	Toast.makeText(activity, "读卡失败: 卡片丢失,或读取错误!", 1000).show();
                 }else{
                     String[] datas = str.split(":");
                     Toast.makeText(activity, "网络超时 错误码: "+ Integer.toHexString(new Integer(datas[1])), 1000).show();
                     
                 }
				//.setText("请移动卡片在合适位置!");
		
				break;

			case ConsantHelper.READ_CARD_PROGRESS:

                int progress_value = (Integer) msg.obj;    
                //Log.e("main", String.format("progress_value = %d", progress_value));
				//activity.name.setText("正在读卡......,进度：" + progress_value + "%");
				
                
				
				break;

			case ConsantHelper.READ_CARD_START:
				Toast.makeText(activity, "开始读卡......", 1000).show();
				break;
			case Error.ERR_CONNECT_SUCCESS:
				String devname = (String) msg.obj;
				Toast.makeText(activity, devname+"连接成功!", 1000).show();
				//mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" + String.format("%d", failecount));
				break;
			case Error.ERR_CONNECT_FAILD:
				String devname1 = (String) msg.obj;
				Toast.makeText(activity, devname1+"连接失败!", 1000).show();
				//mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" + String.format("%d", failecount));
				break;
			case Error.ERR_CLOSE_SUCCESS:
				
				Toast.makeText(activity, (String) msg.obj+"断开连接成功", 1000).show();
				break;
			case Error.ERR_CLOSE_FAILD:
				
				Toast.makeText(activity, (String) msg.obj+"断开连接失败", 1000).show();
				break;
			case Error.RC_SUCCESS:
				String devname12 = (String) msg.obj;
				Toast.makeText(activity, devname12+"连接成功!", 1000).show();
				break;

			}
		}

	}
	
	/**
	 * 蓝牙读卡方式
	 */
	private class BlueReadTask extends AsyncTask<Void, Void, String> {

	

		@Override
		protected void onPostExecute(String strCardInfo) {		
			
			butn.setEnabled(true);
			
			if (TextUtils.isEmpty(strCardInfo)) {
				uiHandler.sendEmptyMessage(ConsantHelper.READ_CARD_FAILED);
				
				return;
			}
			
			if (strCardInfo.length() <=2){
				readCardFailed(strCardInfo);
				return;
			}

			ObjectMapper objectMapper = new ObjectMapper();
			IdentityCardZ mIdentityCardZ = new IdentityCardZ();

			try {
				mIdentityCardZ = (IdentityCardZ) objectMapper.readValue(
						strCardInfo, IdentityCardZ.class);
			} catch (Exception e) {
				e.printStackTrace();
				 Log.e(ConsantHelper.STAGE_LOG, "mIdentityCardZ failed");
				
				return;
			}
			
			readCardSuccess(mIdentityCardZ);

			
			super.onPostExecute(strCardInfo);			 
			
		}

		@Override
		protected String doInBackground(Void... params) {
			
			
			String 	strCardInfo = mBlueReaderHelper.read();		
		
			return strCardInfo;
		}		
	};	

	/**
	 * 蓝牙读卡方式
	 */
	protected void readCardBlueTooth()
	{				
		
		if ( Blueaddress == null){
			Toast.makeText(this, "请选择蓝牙设备，再读卡1!", Toast.LENGTH_LONG).show();
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return;
		}
		
		if ( Blueaddress.length() <= 0){
			Toast.makeText(this, "请选择蓝牙设备，再读卡2!", Toast.LENGTH_LONG).show();
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return;
		}		

		if (mBlueReaderHelper.openBlueConnect(Blueaddress) == true){		
			
			butn.setEnabled(false);
			new BlueReadTask().executeOnExecutor(Executors.newCachedThreadPool());
			
		}else{
			Log.e("","close ok");
			Toast.makeText(this, "请确认蓝牙已经开启，再读卡!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void readCardSuccess(IdentityCardZ identityCard) {

		if (identityCard != null) {
			
			
			Toast.makeText(con, identityCard.name+identityCard.sex+identityCard.ethnicity+identityCard.birth+identityCard.cardNo+identityCard.address, 1000).show();
			
		}
		
		Toast.makeText(con, "读卡成功", 1000).show();
		Log.e(ConsantHelper.STAGE_LOG, "读卡成功!");
//		totalcount++;
//		mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" + String.format("%d", failecount));

	}
	
	private void readCardFailed(String strcardinfo){
		int bret = Integer.parseInt(strcardinfo);
		switch (bret){
		case -1:
		
			Toast.makeText(con,"服务器连接失败!", 1000).show();
			break;
		case 1:
			
			Toast.makeText(con,"读卡失败!", 1000).show();
			break;
		case 2:
		
			Toast.makeText(con,"读卡失败!", 1000).show();
			break;
		case 3:
		
			Toast.makeText(con,"网络超时!", 1000).show();
			break;
		case 4:
			Toast.makeText(con,"读卡失败!", 1000).show();
			break;
		case -2:
			Toast.makeText(con,"读卡失败!", 1000).show();
			break;	
		case 5:
			Toast.makeText(con,"照片解码失败!", 1000).show();
			break;			
		}

	}
	
	private void initShareReference() {
		
//		if ( this.server_address.length() <= 0){
//			if (ShareReferenceSaver.getData(this, SERVER_KEY1).trim().length() <= 0) {
//				 this.server_address = "senter-online.cn";
//			} else {
//				this.server_address = ShareReferenceSaver.getData(this, SERVER_KEY1);
//			}
//			if (ShareReferenceSaver.getData(this, PORT_KEY1).trim().length() <= 0) {
//				this.server_port = 10002;
//			} else {
//				this.server_port = Integer.valueOf(ShareReferenceSaver.getData(this, PORT_KEY1));				
//			}
//		}		
//		
//		mNFCReaderHelper.setServerAddress(this.server_address);
//		mNFCReaderHelper.setServerPort(this.server_port);
//
//		mOTGReaderHelper.setServerAddress(this.server_address);
//		mOTGReaderHelper.setServerPort(this.server_port);
		
		//----实例化help类---
		mBlueReaderHelper.setServerAddress(server_address);
		mBlueReaderHelper.setServerPort(server_port);		

	}
	
}
