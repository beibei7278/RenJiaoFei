//package com.sunsoft.zyebiz.b2e.activity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebSettings.PluginState;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import com.sunsoft.zyebiz.b2e.R;
//import com.umeng.analytics.MobclickAgent;
//
//public class TestVideoPlayActivity extends Activity {
//	private WebView wv;
//	private String SerUrl;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_play_video);
//
//		Bundle bundle = this.getIntent().getExtras();
//		SerUrl = bundle.getString("SerUrl");
//		System.out.println("SerUrl:" + SerUrl);
//
//		init();
//	}
//
//	@SuppressWarnings("deprecation")
//	private void init() {
//		wv = (WebView) findViewById(R.id.webview);
//		WebSettings settings = wv.getSettings();
//		settings.setJavaScriptEnabled(true);
//		settings.setJavaScriptEnabled(true);
//		settings.setPluginState(PluginState.ON);
//
//		settings.setJavaScriptCanOpenWindowsAutomatically(true);
//		settings.setAllowFileAccess(true);
//		settings.setDefaultTextEncodingName("UTF-8");
//		settings.setLoadWithOverviewMode(true);
//		settings.setUseWideViewPort(true);
//		wv.setVisibility(View.VISIBLE);
//		wv.loadUrl(SerUrl);
//		wv.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//		});
//	}
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);
//		}
//		public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);
//		}
//
//}
