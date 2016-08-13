package com.sunsoft.zyebiz.b2e.activity;
//package com.sunsoft.zyebiz.b2e.activity;
//
///**
// * 通知管理页
// * @author YinGuiChun
// */
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//import com.umeng.analytics.MobclickAgent;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class TongZhijActivity extends Activity implements
//		OnClickListener {
//	/** 标题 */
//	private TextView tvMainText;
//	private TextView tvTitleBack;
//	private ImageView imgTitleBack;
//	private WebView mWebView = null;
//	private String url = "";
////	private RelativeLayout relNoNetWork;
////	private TextView mLoadAgain;
////	WebSettings webSettings;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_gonggao_xq);
//		NetManager.isHaveNetWork(TongZhijActivity.this);
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
//		tvMainText.setText(getString(R.string.tong_zhi_manager));
//		Bundle bundle = this.getIntent().getExtras();
//		if (EmptyUtil.isNotEmpty(bundle)) {
//			url = bundle.getString("url");
//		} else {
//			url = URLInterface.TONGZHI_MANAGER;
//		}
////		relNoNetWork.setVisibility(View.GONE);
////		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//
//	}
//
//	private void initView() {
//		tvMainText = (TextView) findViewById(R.id.title_main);
//		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
//		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
////		relNoNetWork = (RelativeLayout)findViewById(R.id.rel_network_no);
////		mLoadAgain = (TextView) findViewById(R.id.bt_load_again);
////		mLoadAgain.setOnClickListener(this);
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
//
//				public boolean shouldOverrideUrlLoading(WebView view, String url) {
//					if (NetManager.isWifi() || NetManager.isMoble()) {
////						relNoNetWork.setVisibility(View.GONE);
////						mWebView.setVisibility(View.VISIBLE);
//						if (IsContainString.containsString(url,
//								URLInterface.GONGGAO_XQ)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(TongZhijActivity.this,
//									TestGongGaoXQEActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							startActivity(goRetail);
//						} else {
//							view.loadUrl(url);
//						}
//
//					} else {
////						mWebView.setVisibility(View.GONE);
////						relNoNetWork.setVisibility(View.VISIBLE);
//						Toast.makeText(TongZhijActivity.this,
//								getString(R.string.network_message_no), Toast.LENGTH_SHORT).show();
//					}
//
//					// return super.shouldOverrideUrlLoading(view, url);
//					return true;
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
//			isHaveNetWork();
//			
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_title_back:
//		case R.id.img_title_back:
//			this.finish();
//
//			break;
//		case R.id.bt_load_again:
//			isHaveNetWork();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//	private void isHaveNetWork() {
//		
//		if (NetworkConnection.isNetworkAvailable(TongZhijActivity.this)) {
//			mWebView.setVisibility(View.VISIBLE);
////			relNoNetWork.setVisibility(View.GONE);
////			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			mWebView.loadUrl(url);
//		} else {
//			Toast.makeText(getApplication(),
//					getString(R.string.network_message_no),
//					Toast.LENGTH_SHORT).show();
////			mWebView.setVisibility(View.GONE);
////			relNoNetWork.setVisibility(View.VISIBLE);
//		}
//	}
//
//}
