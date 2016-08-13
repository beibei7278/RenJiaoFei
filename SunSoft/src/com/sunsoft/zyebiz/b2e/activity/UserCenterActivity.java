package com.sunsoft.zyebiz.b2e.activity;

/**
 * 结算中心
 * @author YinGuiChun
 */
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.umeng.analytics.MobclickAgent;
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

public class UserCenterActivity extends Activity implements OnClickListener {
	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleBackTop;
	private WebView mWebView = null;
	private String url = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changjia_xq);
		ECApplication.getInstance().addActivity(this);
		NetManager.isHaveNetWork(UserCenterActivity.this);
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
	 * 初始化数据
	 */
	private void initDate() {
		tvMainText.setText(this.getString(R.string.buy_account_title_main));
		Bundle bundle = this.getIntent().getExtras();
		if (EmptyUtil.isNotEmpty(bundle)) {
			url = bundle.getString("url");
		} else {
			url = URLInterface.GROUP_BUY_DATE;
		}

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
			mWebView = (WebView) findViewById(R.id.group_buy_factory_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (IsContainString.containsString(url,
							URLInterface.GROUP_BUY_SUB)) {
						Intent goRetail = new Intent();
						goRetail.setClass(UserCenterActivity.this,
								UserBuySuccessActivity.class);
						view.loadUrl(null);
						startActivity(goRetail);
						UserCenterActivity.this.finish();
						UserMainActivity.mainActivity.finish();
					}

					return super.shouldOverrideUrlLoading(view, url);
				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				@Override
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
			if (NetworkConnection.isNetworkAvailable(UserCenterActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(),
						this.getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			mWebView.loadUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_title_back:
			this.finish();
			break;

		default:
			break;
		}

	}
}
