//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：学校用户登录公告管理页面
// * @author YGC
// */
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
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
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.application.ECApplication;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.interfaces.MyOnClickListener;
//import com.sunsoft.zyebiz.b2e.model.FragmentBiaoShi;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//
//public class TestConsultationFragment extends Fragment implements OnClickListener {
//	/** 标题 */
//	private TextView tv_main_text, tv_title_back;
//	private ImageView img_title_back;
//	private RelativeLayout rel_shopping;
//
//	/** 校服馆 零售馆 直通车 */
//	private TextView school_uniform, retail_holl, straight_train;
//
//	private LinearLayout lin_fragment_consultation;
//
//	private MyOnClickListener listener;
//
////	OnConsultationListener mCallback;
//
//	private WebView mWebView = null;
//	private Context mContext;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View mBaseView = inflater.inflate(R.layout.fragment_school_consultation, null);
////		NetManager.isHaveNetWork(MainActivity.mainActivity);
//		initView(mBaseView);
//		initDate();
////		showWebView();
//
//		return mBaseView;
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
//	private void showWebView() { // webView
//		try {
//
//			mWebView.requestFocus();
//			mWebView.setWebViewClient(new WebViewClient() {
//
//				// 点击页面中的链接会调用这个方法
//				@Override
//				public boolean shouldOverrideUrlLoading(WebView view, String url) {
//					// TODO Auto-generated method stub
//					// 跳转到另外的activity
//					if(NetManager.isWifi()||NetManager.isMoble()){
//						if (containsString(url, URLInterface.GROUP_BUY_DATE)) {
//							System.out.println("bbbbbbbbbb");
////							mCallback.goSchoolStore();
//							// biaoshi.setIsTuanGou(true);
//							ECApplication.isTuangou = true;
//							ECApplication.URL_SCHOOL_STORE = URLInterface.GROUP_BUY_DATE+"&&userId="
//							+ SharedPreference.strUserId+"&&schoolId="+SharedPreference.strSchoolID+"&&userName="+SharedPreference.strUserName+"&&token="+SharedPreference.strUserToken;
//
//						} else if (containsString(url,
//								URLInterface.SELL_RETAIL_BUY_DATE)) {
////							mCallback.goSchoolStore();
//							// biaoshi.setIsTuanGou(false);
//							ECApplication.isTuangou = false;
//							ECApplication.URL_SCHOOL_STORE = URLInterface.SELL_RETAIL_BUY_DATE+"&&userId="
//							+ SharedPreference.strUserId+"&&schoolId="+SharedPreference.strSchoolID+"&&userName="+SharedPreference.strUserName+"&&token="+SharedPreference.strUserToken;
//						}
//						view.loadUrl(url);
//					}else{
//						Toast.makeText(UserMainActivity.mainActivity, "似乎互联网已断开连接", Toast.LENGTH_SHORT).show();
//					}
//					
//
//					// return super.shouldOverrideUrlLoading(view, url);
//					return true;
//				}
//
//			});
//
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				@Override
//				public void onProgressChanged(WebView view, int progress) {
//					UserMainActivity.mainActivity.setTitle("Loading...");
//					UserMainActivity.mainActivity.setProgress(progress * 100); // Make
//					// Return the app name after finish loading
//					if (progress == 100) {
//						UserMainActivity.mainActivity.setTitle(R.string.app_name);
//					}
//
//				}
//			});
//
//			mWebView.setOnKeyListener(new View.OnKeyListener() { // webview can
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
//			mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
//			mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示
//			 //开启storage
//		    webSettings.setDomStorageEnabled(true);
//			webSettings.setDefaultTextEncodingName("utf-8");
//			// 判断网络有LOAD_DEFAULT无LOAD_CACHE_ELSE_NETWORK
//			if (NetworkConnection.isNetworkAvailable(UserMainActivity.mainActivity)) {
//				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			} else {
//				Toast.makeText(UserMainActivity.mainActivity, "似乎互联网已断开连接",
//						Toast.LENGTH_SHORT);
//				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//				// webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//			}
//			mWebView.loadUrl(URLInterface.HOME_PAGE + "?userId="
//					+ SharedPreference.strUserId+"&&userName="+SharedPreference.strUserName+"&&token="+SharedPreference.strUserToken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void initDate() {
//		// TODO Auto-generated method stub
//		tv_main_text.setText("公告管理");
//
//	}
//
//	private void initView(View rootView) {
//		// TODO Auto-generated method stub
//		tv_main_text = (TextView) rootView.findViewById(R.id.title_main);
//		mWebView = (WebView) rootView.findViewById(R.id.webview);
//
//	}
//
//	/**
//	 * 首页内的点击事件
//	 * 
//	 * @param v
//	 */
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//
////		case R.id.retail_holl:
////			System.out.println("零售馆");
////			mCallback.onArticleSelected();
////			break;
////		case R.id.straight_train:
////			System.out.println("直通车");
////			mCallback.onArticleSelected();
////			break;
//
//		default:
//			break;
//		}
//
//	}
//
////	/**
////	 * 回调接口:从首页接入学校商城页
////	 * 
////	 */
////	public interface OnConsultationListener {
////		public void goSchoolStore();
////	}
////
////	/**
////	 * 执行回调方法
////	 */
////	@Override
////	public void onAttach(Activity activity) {
////
////		try {
////			mCallback = (OnConsultationListener) activity;
////		} catch (ClassCastException e) {
////			throw new ClassCastException(activity.toString()
////					+ " must implement OnHeadlineSelectedListener");
////		}
////
////		super.onAttach(activity);
////	}
//
//}
