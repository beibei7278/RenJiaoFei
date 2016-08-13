package com.yfd.appTest.Activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.CustomView.CustomDialogTishi;
import com.yfd.appTest.softupdate.DownloadService;
import com.yfd.appTest.softupdate.ParsingXMLElements;

public class setingActivity extends BaseActivity {
	
	ImageView back;
	TextView cphone,cpsd,update;
	private String pastVersion;
	private String serverUrl = BaseApplication.updateFile; 
	private String xmlName = BaseApplication.xmlName;
	private Map<String, String> versionInfo;
	private int currentVersionCode;
	public enum versionInfoField {
		filename, filetype, version, description
	}
	private Handler handMessage = new Handler() {
		public void handleMessage(Message msg) {
			
			dimissloading();
			
			switch (msg.what) {

			case 0:
				CustomDialogTishi.Builder builder = new CustomDialogTishi.Builder(
						con);
				builder.setMessage(BaseApplication.versioninfo);
				builder.setTitle("更新提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								startupdate();
								dialog.dismiss();
							}
						});

				builder.setNegativeButton("稍后再说",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(getApplicationContext(),
										"为正常使用软件，请及时更及时更新",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						});

				builder.create().show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "已是最新版本",
						Toast.LENGTH_SHORT).show();
				break;

			case -1:
				Toast.makeText(getApplicationContext(), "未检测到更新",
						Toast.LENGTH_SHORT).show();
				break;

			case 5:

				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (isNewVersion() == 0) {

							handMessage.sendEmptyMessage(0);

						}
						if (isNewVersion() == 1) {

							 handMessage.sendEmptyMessage(1);

						}
						if (isNewVersion() == -1) {

							 handMessage.sendEmptyMessage(-1);

						}
					}
				}.start();

				break;

			}

			// handler.sendEmptyMessage(2);
		}
	};
	Context con;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		con=this;
		back=(ImageView)findViewById(R.id.setttt_back);
		
		cphone=(TextView)findViewById(R.id.cphone);
		cpsd=(TextView)findViewById(R.id.cpsd);
		update=(TextView)findViewById(R.id.checkupdate);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		cphone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(setingActivity.this, changephoneActivity.class);
				startActivity(intent);
			}
		});
		
         cpsd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(setingActivity.this, changepsdActivity.class);
				startActivity(intent);
			}
		});
         
         update.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				Toast.makeText(getApplicationContext(), "已是最新版本",
						Toast.LENGTH_SHORT).show();
 				showloading("");
 				//handMessage.sendEmptyMessage(5);
 			}
 		});
        PackageManager manager = setingActivity.this.getPackageManager();
	try {
		PackageInfo info = manager.getPackageInfo(
				setingActivity.this.getPackageName(), 0);
		String appVersion = info.versionName; // �汾��
		currentVersionCode = info.versionCode; // �汾��
		System.out.println(currentVersionCode + " " + appVersion);
	} catch (NameNotFoundException e) {
		// TODO Auto-generated catch blockd
		e.printStackTrace();
	} 
	}
	
	protected void startupdate() {
		// TODO Auto-generated method stub

		Intent it = new Intent(setingActivity.this, DownloadService.class);
		startService(it);
		// bindService(it, conn, Context.BIND_AUTO_CREATE);

	}
	
	public int isNewVersion() {
		try {

			versionInfo = getXMLElements(serverUrl + xmlName);
			pastVersion = getPackageManager().getPackageInfo(getPackageName(),
					0).versionName;
			if(versionInfo!=null){
				BaseApplication.versioninfo = versionInfo.get("description");
			}
			return null == versionInfo
					|| null == versionInfo.get("version")
					|| null == pastVersion
					|| pastVersion.equals(versionInfo
							.get(versionInfoField.version.toString())) ? 1 : 0;

		} catch (NameNotFoundException e) {

		
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
	
		} catch (Exception e) {
			e.printStackTrace();
		
		}

		return -1;
	}
	public Map<String, String> getVersionInfo() {
		return versionInfo;
	}

	public Map<String, String> getXMLElements(String resourceUrl)
			throws MalformedURLException, IOException, Exception {
		// ��ȡXML
		URL url = new URL(BaseApplication.updateFile + xmlName);
		InputSource is = new InputSource(url.openStream());
		is.setEncoding("UTF-8");

		// ����XML
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser(); // ����������
		ParsingXMLElements handler = new ParsingXMLElements();
		saxParser.parse(is, handler);
		return handler.getElement();
	}
}
