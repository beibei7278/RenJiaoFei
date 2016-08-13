//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：学校用户订单管理页面
// * @author YGC
// */
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.UserCenterActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserBuySuccessActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserBuyFactoryActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyGongGaoActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyShangPinActivity;
//import com.sunsoft.zyebiz.b2e.activity.LoginActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.activity.RetailXQActivity;
//import com.sunsoft.zyebiz.b2e.application.ECApplication;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//
//public class TestManagerFragment extends Fragment implements OnClickListener {
//	/** 标题 */
//	private TextView tv_main_text;
//	private TextView tvTitleBack;
//	private ImageView imgTitleBack;
//	private WebView mWebView = null;
//	private String goBack;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method
//		View mBaseView = inflater.inflate(R.layout.school_manager, null);
////		NetManager.isHaveNetWork(MainActivity.mainActivity);
//		initView(mBaseView);
//		initDate();
////
////		showWebView();
//		return mBaseView;
//	}
//
//	private void initDate() {
//		// TODO Auto-generated method stub
//		tv_main_text.setText("学校管理");
//		imgTitleBack.setVisibility(View.GONE);
//
//	}
//
//	private void initView(View rootView) {
//		// TODO Auto-generated method stub
//		tv_main_text = (TextView) rootView.findViewById(R.id.title_main);
//		mWebView = (WebView) rootView.findViewById(R.id.webview);
//		tvTitleBack = (TextView) rootView.findViewById(R.id.tv_title_back);
//		imgTitleBack = (ImageView) rootView.findViewById(R.id.img_title_back);
//
//		imgTitleBack.setOnClickListener(this);
//		tvTitleBack.setOnClickListener(this);
//		tv_main_text.setOnClickListener(this);
//
//	}
//
//	@SuppressLint("SetJavaScriptEnabled")
//	@JavascriptInterface
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
//					if(NetManager.isWifi()||NetManager.isMoble()){
//						if (IsContainString.containsString(url,
//								URLInterface.SELL_RETAIL_DETAIL)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(), RetailXQActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else if (IsContainString.containsString(url,
//								URLInterface.GROUP_BUY_GOODS_XIANG_QING)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									GroupBuyShangPinActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else if (IsContainString.containsString(url,
//								URLInterface.GROUP_BUY_XIANG_XI)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									GroupBuyGongGaoActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else if (IsContainString.containsString(url,
//								URLInterface.GROUP_BUY_FACTORY_MESSAGE)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									UserBuyFactoryActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//						} else if (IsContainString.containsString(url,
//								URLInterface.GROUP_BUY_SUB)) {
//							Intent goRetail = new Intent();
//							goRetail.setClass(getActivity(),
//									UserBuySuccessActivity.class);
//							goRetail.putExtra("url", url);
//							view.loadUrl(null);
//							getActivity().startActivity(goRetail);
//							UserMainActivity.mainActivity.finish();
//						}else {
//							view.loadUrl(url);
//						}
//
//					}else{
//						Toast.makeText(UserMainActivity.mainActivity, "似乎互联网已断开连接", Toast.LENGTH_SHORT).show();
//					}
//					
//					// return super.shouldOverrideUrlLoading(view, url);
//					return true;
//				}
//
//				public void onPageFinished(WebView view, String url) {
//					// TODO Auto-generated method stub
//					// super.onPageFinished(view, url);
//					if(NetManager.isWifi()||NetManager.isMoble()){
//						if (IsContainString.containsString(url,
//								URLInterface.GROUP_BUY_PAYMENT)) {
//							imgTitleBack.setVisibility(View.VISIBLE);
//							tv_main_text.setText("结算中心");
//							goBack = "finish";
//						}
//					}else{
//						Toast.makeText(UserMainActivity.mainActivity, "似乎互联网已断开连接", Toast.LENGTH_SHORT).show();
//					}
//					
//				}
//
//			});
//
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				@Override
//				public void onProgressChanged(WebView view, int progress) {
//
//				}
////				public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {    
////		            quotaUpdater.updateQuota(spaceNeeded * 2);    
////		        }         
//
////				public boolean onJsAlert(WebView view, String url,
////						String message, JsResult result) {
////					// TODO Auto-generated method stub
////					return super.onJsAlert(view, url, message, result);
////				}
//			});
//
////			mWebView.setOnKeyListener(new View.OnKeyListener() { // webview can
////				@Override
////				public boolean onKey(View v, int keyCode, KeyEvent event) {
////					if (keyCode == KeyEvent.KEYCODE_BACK
////							&& mWebView.canGoBack()) {
////						mWebView.goBack();
////						return true;
////					}
////					return false;
////				}
////			});
//
//			WebSettings webSettings = mWebView.getSettings();
//			webSettings.setJavaScriptEnabled(true);
//			webSettings.setUseWideViewPort(true);
//			webSettings.setLoadWithOverviewMode(true);
//			mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
//			mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示
//			 //开启storage
//		    webSettings.setDomStorageEnabled(true);
//		  //设置可以访问文件 
//		    webSettings.setAllowFileAccess(true); 
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
//
//			
//			if(ECApplication.isTuangou){
//				mWebView.loadUrl(URLInterface.GROUP_BUY_DATE +
//						"&&userId="
//						+ SharedPreference.strUserId+"&&schoolId="+SharedPreference.strSchoolID+"&&userName="+SharedPreference.strUserName+"&&token="+SharedPreference.strUserToken);
//			}else{
//				mWebView.loadUrl(URLInterface.SELL_RETAIL_BUY_DATE +
//						"&&userId="
//						+ SharedPreference.strUserId+"&&schoolId="+SharedPreference.strSchoolID+"&&userName="+SharedPreference.strUserName+"&&token="+SharedPreference.strUserToken);
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.tv_title_back:
//		case R.id.img_title_back:
//			mWebView.goBack();
//			imgTitleBack.setVisibility(View.GONE);
//			tv_main_text.setText("学校商城");
//			break;
//		case R.id.title_main:
//			System.out.println("11111");
//			mWebView.loadUrl("javascript:FuncBackTop();");
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//
//}
