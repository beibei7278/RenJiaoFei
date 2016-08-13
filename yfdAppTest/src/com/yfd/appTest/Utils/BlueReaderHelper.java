package com.yfd.appTest.Utils;

import android.content.Context;
import android.os.Handler;
import cn.com.senter.mediator.BluetoothReader;

public class BlueReaderHelper 
{
	private Context context;
	
	private BluetoothReader bluecardreader;
	
	public BlueReaderHelper(Context context,Handler handler)
	{
		this.context=context;
		bluecardreader = new BluetoothReader(handler, context);
	}	
	
	public String read()
	{       
		return bluecardreader.readCard_Sync();
	}
	
	public boolean openBlueConnect(String address)
	{
		return bluecardreader.registerBlueCard(address);
	}
	
	
	
	public void setServerAddress(String server_address) {
		bluecardreader.setServerAddress(server_address);
	}

	public void setServerPort(int server_port) {
		bluecardreader.setServerPort(server_port);
	} 
	

}
