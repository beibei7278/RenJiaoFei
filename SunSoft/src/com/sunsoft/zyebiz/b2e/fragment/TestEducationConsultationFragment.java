package com.sunsoft.zyebiz.b2e.fragment;
//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：教育局用户登录通知管理页面
// * @author YGC
// */
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.TestGongGaoXQEActivity;
//import com.sunsoft.zyebiz.b2e.activity.TongZhijActivity;
//import com.sunsoft.zyebiz.b2e.activity.TestEducationMainActivity;
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
//public class EducationConsultationFragment extends Fragment implements
//		OnClickListener {
//	private TextView tv_main_text;
//	private WebView mWebView = null;
//	private RelativeLayout relNoNetWork;
//	private TextView mLoadAgain;
//	WebSettings webSettings;
//	public Dialog mLoading;
//	private Handler mHandler = null;
//	private Timer timer = null;
//	TimerTask timeTask;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View mBaseView = inflater.inflate(
//				R.layout.fragment_school_consultation, null);
//		NetManager.isHaveNetWork(TestEducationMainActivity.mainActivity);
//		mLoading = DialogUtils
//				.createLoadingDialog(TestEducationMainActivity.mainActivity);
//		initView(mBaseView);
//		initDate();
//		showWebView();
//
//		return mBaseView;
//	}
//
//	private void initDate() {
//		tv_main_text.setText(getString(R.string.app_name));
//		relNoNetWork.setVisibility(View.GONE);
//		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//
//		mHandler = new Handler() {
//			public void handleMessage(Message msg) {
//				if (msg.what == Constants.CONSTANT_ONE) {
//					mLoading.dismiss();
//					Toast.makeText(TestEducationMainActivity.mainActivity,
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
//		relNoNetWork = (RelativeLayout) rootView
//				.findViewById(R.id.rel_network_no);
//		mLoadAgain = (TextView) rootView.findViewById(R.id.bt_load_again);
//		mLoadAgain.setOnClickListener(this);
//
//	}
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
//	@SuppressLint("SetJavaScriptEnabled")
//	private void showWebView() {
//		try {
//
//			mWebView.requestFocus();
//			mWebView.setWebViewClient(new WebViewClient() {
//
//				public boolean shouldOverrideUrlLoading(WebView view, String url) {
//					if (NetManager.isWifi() || NetManager.isMoble()) {
//						relNoNetWork.setVisibility(View.GONE);
//						mWebView.setVisibility(View.VISIBLE);
//						if (IsContainString.containsString(url,
//								URLInterface.GONGGAO_XQ)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									TestGongGaoXQEActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else if (IsContainString.containsString(url,
//								URLInterface.TONGZHI_MANAGER)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									TongZhijActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else {
//							view.loadUrl(url);
//						}
//
//					} else {
//						mWebView.setVisibility(View.GONE);
//						relNoNetWork.setVisibility(View.VISIBLE);
//						Toast.makeText(TestEducationMainActivity.mainActivity,
//								getString(R.string.network_message_no),
//								Toast.LENGTH_SHORT).show();
//					}
//
//					// return super.shouldOverrideUrlLoading(view, url);
//					return true;
//				}
//
//				public void onPageStarted(WebView view, String url,
//						Bitmap favicon) {
//					// TODO Auto-generated method stub
//					super.onPageStarted(view, url, favicon);
//					mLoading.show();
//					startThread();
//				}
//
//				public void onPageFinished(WebView view, String url) {
//					super.onPageFinished(view, url);
//					mLoading.dismiss();
//					stopThread();
//				}
//
//			});
//
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				@Override
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
//				@Override
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
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.bt_load_again:
//			isHaveNetWork();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	private void isHaveNetWork() {
//		if (NetworkConnection
//				.isNetworkAvailable(TestEducationMainActivity.mainActivity)) {
//			mWebView.setVisibility(View.VISIBLE);
//			relNoNetWork.setVisibility(View.GONE);
//			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			mWebView.loadUrl(URLInterface.TONGZHI_ORDER + "?userId="
//					+ SharedPreference.strUserId + "&&userName="
//					+ SharedPreference.strUserName + "&&token="
//					+ SharedPreference.strUserToken);
//		} else {
//			Toast.makeText(TestEducationMainActivity.mainActivity,
//					getString(R.string.network_message_no), Toast.LENGTH_SHORT)
//					.show();
//			mWebView.setVisibility(View.GONE);
//			relNoNetWork.setVisibility(View.VISIBLE);
//		}
//	}
//
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
