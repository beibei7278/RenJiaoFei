package com.yfd.appTest.Activity;
/**
 * 功能：个人中心页面
 */
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.PriceBeans;
import com.yfd.appTest.CustomView.CustomDialogTishi;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.MD5Util;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.softupdate.DownloadService;
import com.yfd.appTest.softupdate.ParsingXMLElements;
import com.yfd.appTest.widget.SharedPreference;

public class PerssionnalActivity extends BaseActivity implements
		OnClickListener {

	ImageView back;

	RelativeLayout toyjfk;
	RelativeLayout toset;
	RelativeLayout topro;
	RelativeLayout ppricelayout;
	RelativeLayout toupdate;
	RelativeLayout seemap;
	private TextView loginout, uanme, seemy;
	TextView price;
	private PriceBeans pricebeans;
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
										"为正常使用软件，请及时更新", Toast.LENGTH_SHORT)
										.show();
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
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {
				System.out.println(msg.obj.toString());
				pricebeans = (PriceBeans) Utils.jsonToBean(msg.obj.toString(),
						PriceBeans.class);
				if (pricebeans.getCode().equals("0")) {
					price.setText(pricebeans.getBalance());
				} else {
					// price.setText(pricebeans.getMessage());
				}
			} else {
				System.out.println("no_get");
			}

		};

	};
	TextView versionshow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_perfessional);
		BaseApplication.listacty.add(this);
		con = this;
		toyjfk = (RelativeLayout) findViewById(R.id.toyjfk);
		toset = (RelativeLayout) findViewById(R.id.toset);
		topro = (RelativeLayout) findViewById(R.id.topro);
		seemap = (RelativeLayout) findViewById(R.id.mapsee);
		back = (ImageView) findViewById(R.id.set_back);
		ppricelayout = (RelativeLayout) findViewById(R.id.p_ylayout);
		toupdate = (RelativeLayout) findViewById(R.id.tocheckupdate);
		price = (TextView) findViewById(R.id.p_user_yue);
		uanme = (TextView) findViewById(R.id.per_name);
		versionshow = (TextView) findViewById(R.id.version);
		seemy = (TextView) findViewById(R.id.see_my);

		loginout = (TextView) findViewById(R.id.set_exit);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		toyjfk.setOnClickListener(this);
		toset.setOnClickListener(this);
		topro.setOnClickListener(this);
		loginout.setOnClickListener(this);
		seemy.setOnClickListener(this);
		toupdate.setOnClickListener(this);
		seemap.setOnClickListener(this);
		if (BaseApplication.loginbeans != null) {
			uanme.setText(BaseApplication.loginbeans.getMsg().getUsName());
			if (BaseApplication.loginbeans.getMsg().getUsCredits() != null) {
				if (BaseApplication.loginbeans.getMsg().getUsCredits()
						.equals("7")) {
					seemap.setVisibility(View.VISIBLE);
				} else {
					seemap.setVisibility(View.GONE);
				}
			}
			// if(BaseApplication.loginbeans.getMsg().getUsName().equals("yfd02298")){
			//
			// seemap.setVisibility(View.VISIBLE);
			//
			// }else{
			//
			// seemap.setVisibility(View.GONE);
			// }

		}

		if (BaseApplication.loginbeans != null
				&& BaseApplication.loginbeans.getType() != null
				&& BaseApplication.loginbeans.getType().equals("0")) {

			ppricelayout.setVisibility(View.VISIBLE);
			toset.setVisibility(View.GONE);
		} else {
			ppricelayout.setVisibility(View.GONE);
		}

		try {
			versionshow.setText("version:" + getVersionName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PackageManager manager = PerssionnalActivity.this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(
					PerssionnalActivity.this.getPackageName(), 0);
			String appVersion = info.versionName;
			currentVersionCode = info.versionCode;
			System.out.println(currentVersionCode + " " + appVersion);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
		}

	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实�????
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名�????0代表是获取版本信�????
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tocheckupdate:
			showloading("");
			handMessage.sendEmptyMessage(5);
			break;

		case R.id.see_my:
			Intent intentmy = new Intent(PerssionnalActivity.this,
					MyInfoActivity.class);
			startActivity(intentmy);
			break;

		case R.id.set_exit:
			showloading("");
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < BaseApplication.listacty.size(); i++) {
						BaseApplication.listacty.get(i).finish();
					}
					Utils.cleanRegistInfo(PerssionnalActivity.this);
					SharedPreference.cleanUserInfo(PerssionnalActivity.this);
					BaseApplication.loginbeans=null;
					dimissloading();
				}
			}, 2000);
			break;

		case R.id.toyjfk:
			Intent intent = new Intent(PerssionnalActivity.this,
					YjfkActivity.class);
			startActivity(intent);

			break;

		case R.id.toset:
			Intent intentss = new Intent(PerssionnalActivity.this,
					setingActivity.class);
			startActivity(intentss);
			break;

		case R.id.topro:
			Intent intentpro = new Intent(PerssionnalActivity.this,
					problemActivity.class);
			startActivity(intentpro);
			break;

		case R.id.mapsee:
			Intent intentmap = new Intent(PerssionnalActivity.this,
					MapActivity.class);
			startActivity(intentmap);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		AnsyPost.Phkksave(
				BaseApplication.GET_PRICE
						+ "?terminalName="
						+ BaseApplication.loginbeans.getMsg().getUsName()
						+ "&signature="
						+ MD5Util.string2MD5("terminalName="
								+ BaseApplication.loginbeans.getMsg()
										.getUsName()), handler);

	}

	protected void startupdate() {
		// TODO Auto-generated method stub
		Intent it = new Intent(PerssionnalActivity.this, DownloadService.class);
		startService(it);
	}

	public int isNewVersion() {
		try {
			versionInfo = getXMLElements(serverUrl + xmlName);
			pastVersion = getPackageManager().getPackageInfo(getPackageName(),
					0).versionName;
			if (versionInfo != null) {
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

		URL url = new URL(BaseApplication.updateFile + xmlName);
		InputSource is = new InputSource(url.openStream());
		is.setEncoding("UTF-8");

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		ParsingXMLElements handler = new ParsingXMLElements();
		saxParser.parse(is, handler);
		return handler.getElement();
	}

}
