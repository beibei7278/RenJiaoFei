package com.sunsoft.zyebiz.b2e.activity;

/**
 * 退货换货页面
 * @author YinGuiChun
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserMyOrderTuiHuoActivity extends Activity implements
		OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private TextView tvTitleBackTop;
	private ImageView imgTitleBack;
	private WebView mWebView = null;
	private Bundle bundlee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		NetManager.isHaveNetWork(UserMyOrderTuiHuoActivity.this);
		initView();
		initDate();

		showWebView();
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		tvMainText.setText(getString(R.string.my_order_title));
		bundlee = this.getIntent().getExtras();

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
	 * WebView加载页面
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.my_order_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					System.out.println("url:" + url);
					System.out.println("URLInterface.SELL_RETAIL_DETAIL:"
							+ URLInterface.SELL_RETAIL_DETAIL);
					if (NetManager.isWifi() || NetManager.isMoble()) {
						if (IsContainString.containsString(url,
								"retail/retailDetail.shtml")) { // 这里进行了两种处理方式，一种是使用WebView来加载，一种是跳转到新的Activity来加载
							Intent goRetail = new Intent();
							goRetail.setClass(UserMyOrderTuiHuoActivity.this,
									UserRetailXQActivity.class); // 跳转到新的页面来进行加载的
							goRetail.putExtra("url", url);
							view.loadUrl(null);
							startActivity(goRetail);
						} else {
							view.loadUrl(url); // 在本页面加载
						}

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
					.isNetworkAvailable(UserMyOrderTuiHuoActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(),
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			if (EmptyUtil.isNotEmpty(bundlee)) {
				String type = bundlee.getString("type");
				String userId = bundlee.getString("userId");
				mWebView.loadUrl(URLInterface.MY_ORDER_TUIHUO + "?type=" + type
						+ "&&" + "userId=" + SharedPreference.strUserId
						+ "&&token=" + SharedPreference.strUserToken);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_title_back:
			if (mWebView.canGoBack()) {
				mWebView.goBack();
			} else {
				finish();
			}
			break;
		case R.id.title_main_back_top:
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;

		default:
			break;
		}

	}

}
