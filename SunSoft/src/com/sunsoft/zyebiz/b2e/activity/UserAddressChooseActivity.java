package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：地址选择页面
 * @author YinGuiChun
 */

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserAddressChooseActivity extends Activity implements
		OnClickListener {
	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleBackTop;
	private WebView mWebView = null;
	private String type;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_choose);
		NetManager.isHaveNetWork(UserAddressChooseActivity.this);
		initView();
		initDate();

		showWebView();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		tvTitleBackTop = (TextView) findViewById(R.id.title_main_back_top);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		tvTitleBackTop.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		tvMainText.setText(getString(R.string.address_choose_title_main_set));
	}

	/**
	 * WebView加载页面
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.group_buy_shang_pin_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (NetManager.isWifi() || NetManager.isMoble()) {
						if (IsContainString.containsString(url, "type=add")) {
							type = "add";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_add));
							System.out.println("type=add");
						} else if (IsContainString.containsString(url,
								"type=edit")) {
							type = "edit";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_edit));
							System.out.println("type=edit");
						} else {
							type = null;
							tvMainText
									.setText(getString(R.string.address_choose_title_main_set));
							System.out.println("type=null");
						}
						mWebView.loadUrl(url);

					} else {
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
					}
					return true;
				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {

				}
			});

			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			webSettings.setDefaultTextEncodingName("utf-8");
			if (NetworkConnection
					.isNetworkAvailable(UserAddressChooseActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(),
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			mWebView.loadUrl(URLInterface.USER_SHOU_HUO_ADDRESS + "?userId="
					+ SharedPreference.strUserId + "&&schoolId="
					+ SharedPreference.strSchoolID + "&&userName="
					+ SharedPreference.strUserName + "&&token="
					+ SharedPreference.strUserToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			if (EmptyUtil.isNotEmpty(type)) {
				type = null;
				tvMainText
						.setText(getString(R.string.address_choose_title_main_set));
				mWebView.loadUrl(URLInterface.USER_SHOU_HUO_ADDRESS
						+ "?userId=" + SharedPreference.strUserId
						+ "&&userName=" + SharedPreference.strUserName
						+ "&&token=" + SharedPreference.strUserToken);
			} else {
				this.finish();
			}
			break;
		case R.id.title_main_back_top:
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;

		default:
			break;
		}

	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// if (keyCode == KeyEvent.KEYCODE_BACK
	// && event.getAction() == KeyEvent.ACTION_DOWN) {
	// // if (EmptyUtil.isNotEmpty(type)) {
	// // type = null;
	// // tvMainText
	// // .setText(getString(R.string.address_choose_title_main_set));
	// // mWebView.loadUrl(URLInterface.USER_SHOU_HUO_ADDRESS
	// // + "?userId=" + SharedPreference.strUserId
	// // + "&&userName=" + SharedPreference.strUserName
	// // + "&&token=" + SharedPreference.strUserToken);
	// // } else {
	// // this.finish();
	// // }
	// if(mWebView.canGoBack()){
	// mWebView.goBack();
	// }
	// }
	// return super.onKeyDown(keyCode, event);
	// }

}
