package com.sunsoft.zyebiz.b2e.fragment;
//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：学校用户登录之首页一级页面
// * @author YGC
// */
//
//import java.util.Timer;
//import java.util.TimerTask;
//import android.graphics.Bitmap; 
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.SchoolGongGaoXQActivity;
//import com.sunsoft.zyebiz.b2e.activity.SchoolMainActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserCheckNewNumberActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.common.Constants;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.wiget.DialogUtils;
//import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//import com.umeng.analytics.MobclickAgent;
//
//public class SchoolConsulFragment extends Fragment {
//	private TextView tv_main_text;
//	private WebView mWebView = null;
//	public Dialog mLoading;
//	private Handler mHandler = null;
//	private Timer timer = null;
//	TimerTask timeTask;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View mBaseView = inflater.inflate(
//				R.layout.school_fragment_school_consultation, null);
//		NetManager.isHaveNetWork(SchoolMainActivity.mainActivity);
//		mLoading = DialogUtils
//				.createLoadingDialog(SchoolMainActivity.mainActivity);
//		initView(mBaseView);
//		initDate();
//		showWebView();
//
//		return mBaseView;
//	}
//
//
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onPageStart("MainScreen");
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPageEnd("MainScreen");
//	}
//
//	public static boolean containsString(String src, String dest) {
//		boolean flag = false;
//		if (src.contains(dest)) {
//			flag = true;
//		}
//		return flag;
//	}
//
//	@SuppressLint({ "SetJavaScriptEnabled", "ShowToast" })
//	private void showWebView() {
//		try {
//
//			mWebView.requestFocus();
//			mWebView.setWebViewClient(new WebViewClient() {
//
//				public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//					if (NetManager.isWifi() || NetManager.isMoble()) {
//						if (IsContainString
//								.containsString(
//										url,
//										URLInterface.SCHOOL_LOGIN_GONGGAO_MANAGER_CONTENT)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									SchoolGongGaoXQActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else {
//							view.loadUrl(url);
//						}
//
//					} else {
//						Toast.makeText(SchoolMainActivity.mainActivity,
//								getString(R.string.network_message_no),
//								Toast.LENGTH_SHORT).show();
//					}
//					return true;
//				}
//
//				public void onPageStarted(WebView view, String url,
//						Bitmap favicon) {
//					// TODO Auto-generated method stub
//					super.onPageStarted(view, url, favicon);
//					timer = new Timer();
//					TimerTask tt = new TimerTask() {
//						public void run() {
//							if (mWebView.getProgress() < 100) {
//								Message msg = new Message();
//								msg.what = 1;
//								mHandler.sendMessage(msg);
//								timer.cancel();
//								timer.purge();
//							}
//						}
//					};
//					timer.schedule(tt, timeout, 1);
//				}
//
//				public void onPageFinished(WebView view, String url) {
//					super.onPageFinished(view, url);
//					timer.cancel();
//					timer.purge();
//				}
//
//			});
//
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				public void onProgressChanged(WebView view, int progress) {
//					if (progress >= 100) {
//						mLoading.dismiss();
//					} else {
//						mLoading.show();
//					}
//
//				}
//			});
//
//			mWebView.setOnKeyListener(new View.OnKeyListener() {
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
//					.isNetworkAvailable(SchoolMainActivity.mainActivity)) {
//				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			} else {
//				Toast.makeText(SchoolMainActivity.mainActivity,
//						getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT);
//				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//			}
//			mWebView.loadUrl(URLInterface.SCHOOL_LOGIN_CONSUL + "?userId="
//					+ SharedPreference.strUserId + "&&userName="
//					+ SharedPreference.strUserName + "&&schoolId="
//					+ SharedPreference.strSchoolID + "&&token="
//					+ SharedPreference.strUserToken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressLint("ShowToast")
//	private void initDate() {
//		tv_main_text.setText(getString(R.string.app_name));
//		mHandler = new Handler() {
//			public void handleMessage(Message msg) {
//				if (msg.what == Constants.CONSTANT_ONE) {
//					mLoading.dismiss();
//					Toast.makeText(SchoolMainActivity.mainActivity,
//							getString(R.string.network_message_no),
//							Toast.LENGTH_SHORT);
//					mWebView.setVisibility(View.GONE);
//					relNoNetWork.setVisibility(View.VISIBLE);
//				}
//			};
//		};
//
//	}
//
//	private void initView(View rootView) {
//		tv_main_text = (TextView) rootView.findViewById(R.id.title_main);
//		mWebView = (WebView) rootView.findViewById(R.id.webview);
//
//	}
//	private void stopThread() {
//		if (timer != null) {
//			timer.cancel();
//			timer = null;
//		}
//
//		if (timeTask != null) {
//			timeTask.cancel();
//			timeTask = null;
//		}
//
//	}
//
//	private void startThread() {
//		if (timer == null) {
//			timer = new Timer();
//		}
//		if (timeTask == null) {
//			timeTask = new TimerTask() {
//				public void run() {
//					Message msg = new Message();
//					msg.what = 1;
//					mHandler.sendMessage(msg);
//					timer.cancel();
//					timer.purge();
//				}
//			};
//		}
//		if (timer != null && timeTask != null) {
//			timer.schedule(timeTask, Constants.TIME_OUT, 1);
//		}
//	}
//
//}
