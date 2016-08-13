//package com.sunsoft.zyebiz.b2e.activity;
//
///**
// * 待支付二级页面
// * @author YinGuiChun
// */
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.umeng.analytics.MobclickAgent;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class TestOrderDaiZhiFuActivity extends Activity implements
//		OnClickListener {
//	/** 标题 */
//	private TextView tvMainText;
//	private TextView tvTitleBack;
//	private ImageView imgTitleBack;
//	private WebView mWebView = null;
//	private String url = "";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_gonggao_xq);
//		NetManager.isHaveNetWork(TestOrderDaiZhiFuActivity.this);
//		initView();
//		initDate();
//
//		showWebView();
//
//	}
//
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);
//	}
//
//	private void initDate() {
//		tvMainText.setText(getString(R.string.dai_zhifu_order));
//		Bundle bundle = this.getIntent().getExtras();
//		if (EmptyUtil.isNotEmpty(bundle)) {
//			url = bundle.getString("url");
//		} else {
//			url = URLInterface.SCHOOL_LOGIN_ORDER_MANAGER_DAI_ZHIFU;
//		}
//
//	}
//
//	private void initView() {
//		tvMainText = (TextView) findViewById(R.id.title_main);
//		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
//		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
//
//		imgTitleBack.setOnClickListener(this);
//		tvTitleBack.setOnClickListener(this);
//
//	}
//
//	@SuppressLint("SetJavaScriptEnabled")
//	private void showWebView() {
//		try {
//			mWebView = (WebView) findViewById(R.id.group_buy_gong_gao_webview);
//			mWebView.requestFocus();
//			mWebView.setWebViewClient(new WebViewClient() {
//				public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//					return super.shouldOverrideUrlLoading(view, url);
//				}
//
//			});
//
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				public void onProgressChanged(WebView view, int progress) {
//
//				}
//			});
//
//			mWebView.setOnKeyListener(new View.OnKeyListener() { // webview can
//				public boolean onKey(View v, int keyCode, KeyEvent event) {
//					if (keyCode == KeyEvent.KEYCODE_BACK
//							&& mWebView.canGoBack()) {
//						mWebView.goBack();
//						return true;
//					}
//					return false;
//				}
//			});
//
//			WebSettings webSettings = mWebView.getSettings();
//			webSettings.setJavaScriptEnabled(true);
//			webSettings.setUseWideViewPort(true);
//			webSettings.setLoadWithOverviewMode(true);
//			mWebView.setHorizontalScrollBarEnabled(false);
//			mWebView.setVerticalScrollBarEnabled(false);
//			webSettings.setDomStorageEnabled(true);
//			webSettings.setDefaultTextEncodingName("utf-8");
//			if (NetworkConnection
//					.isNetworkAvailable(TestOrderDaiZhiFuActivity.this)) {
//				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			} else {
//				Toast.makeText(getApplication(),
//						getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT).show();
//				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//			}
//			mWebView.loadUrl(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_title_back:
//			this.finish();
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//}
