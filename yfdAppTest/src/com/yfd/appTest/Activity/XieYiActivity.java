package com.yfd.appTest.Activity;
/**
 * 功能：协议页面
 * @author YinGuiChun
 * @date 2016-07-30
 */
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.widget.NetworkConnection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class XieYiActivity extends BaseActivity implements OnClickListener {

	ImageView back;
	private WebView mWebView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xieyi);

		initView();
		showWebView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.setttt_back);
		back.setOnClickListener(this);

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.about_zhi_yuan_webview);
			mWebView.requestFocus();
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			webSettings.setDefaultTextEncodingName("utf-8");
			if (NetworkConnection.isNetworkAvailable(XieYiActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(), "网络已断开连接", Toast.LENGTH_SHORT)
						.show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return super.shouldOverrideUrlLoading(view, url);
				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {

				}
			});

			mWebView.loadUrl(BaseApplication.XIEYI_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setttt_back:
			this.finish();
			break;

		default:
			break;
		}

	}

}
