package com.sunsoft.zyebiz.b2e.fragment;

/**
 * 零售商城
 * 功能：普通用户登陆之学校商城页面
 * @author YGC
 */


import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.activity.UserRetailXQActivity;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.wiget.DialogUtils;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserSchoolStoreFragment extends Fragment implements
		OnClickListener {
	private TextView tv_main_text;
	private TextView tvTitleBack;
	private TextView tvTitleBackTop;
	private ImageView imgTitleBack;
	private WebView mWebView = null;
	private String goBack;
	private RelativeLayout relNoNetWork;
	private TextView mLoadAgain;
	private WebSettings webSettings;
    public Dialog mLoading;
    private Handler mHandler = null;
	private Timer timer = null;
	TimerTask timeTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("进入零售商城中--------------------");
		View mBaseView = inflater.inflate(R.layout.fragment_school_store,
				null);
		mLoading = DialogUtils.createLoadingDialog(UserMainActivity.mainActivity);
		NetManager.isHaveNetWork(UserMainActivity.mainActivity);
		initView(mBaseView);
		initDate();
		System.out.println("进入零售商城的展示webview---------------------");
		showWebView();
		return mBaseView;
	}
	

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@SuppressLint({ "ShowToast", "HandlerLeak" })
	private void initDate() {
		tv_main_text.setText(getString(R.string.school_store));
		imgTitleBack.setVisibility(View.GONE);
		relNoNetWork.setVisibility(View.GONE);
		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		 mHandler = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.what == Constants.CONSTANT_ONE) {
						mLoading.dismiss();
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT);
						mWebView.setVisibility(View.GONE);
						relNoNetWork.setVisibility(View.VISIBLE);
					}
				};
			};



	}

	private void initView(View rootView) {
		tv_main_text = (TextView) rootView.findViewById(R.id.title_main);
		mWebView = (WebView) rootView.findViewById(R.id.webview);
		tvTitleBack = (TextView) rootView.findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) rootView.findViewById(R.id.img_title_back);
		relNoNetWork = (RelativeLayout) rootView
				.findViewById(R.id.rel_network_no);
		mLoadAgain = (TextView) rootView.findViewById(R.id.bt_load_again);
		tvTitleBackTop = (TextView)rootView.findViewById(R.id.title_main_back_top);
		
		mLoadAgain.setOnClickListener(this);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		tv_main_text.setOnClickListener(this);
		tvTitleBackTop.setOnClickListener(this);

	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface", "NewApi" })
	private void showWebView() {
		try {
			mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //加速硬件
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) { //在webview加载URL的时候可以截获这个动作
					if (isWifi() || isMoble()) {
						System.out.println("userSchool进入wifi-----------------shouldOverrideUrlLoading--");
//						relNoNetWork.setVisibility(View.GONE);
//						mWebView.setVisibility(View.VISIBLE);
						if (IsContainString.containsString(url,
								URLInterface.SELL_RETAIL_DETAIL)) {
							Intent goRetail = new Intent();
							goRetail.setClass(getActivity(),
									UserRetailXQActivity.class);  //零售商城的商品详情页面
							System.out.println("url----------零售商城中------"+url);
							goRetail.putExtra("url", url);  //这里传递的这个url有什么用
							view.loadUrl(null);
							getActivity().startActivity(goRetail);
						}  else if (IsContainString.containsString(url,
								"checkOrder.shtml")) {
							mWebView.loadUrl("javascript:wave()");

						} else {
							view.loadUrl(url);
						}

					} else {
//						mWebView.setVisibility(View.GONE);
//						relNoNetWork.setVisibility(View.VISIBLE);
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
					}

					return true; //在WebView浏览器中执行
				}

				// 开始加载网页时要做的工作
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					// TODO Auto-generated method stub
					super.onPageStarted(view, url, favicon);
					stopThread();
					mLoading.show();
					startThread();
					
				}

				
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					mLoading.dismiss();
					stopThread();
				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {
					if(progress>=100){
						mLoading.dismiss();
					}

				}
			});

			webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			webSettings.setAllowFileAccess(true);
			mWebView.getSettings().setSupportZoom(true);
			mWebView.requestFocus();
			mWebView.getSettings().setBuiltInZoomControls(true);

			webSettings.setDefaultTextEncodingName("utf-8");
			isHaveNetWork();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			mWebView.goBack();
			imgTitleBack.setVisibility(View.GONE);
			tv_main_text.setText(getString(R.string.school_store));
			break;
		case R.id.title_main:
			mWebView.loadUrl("javascript:FuncBackTop();");
			break;
		case R.id.bt_load_again:
//			relNoNetWork.setVisibility(View.GONE);
			isHaveNetWork();
			break;
		case R.id.title_main_back_top:
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;

		default:
			break;
		}

	}

	@SuppressLint("ShowToast")
	private void isHaveNetWork() {
		if (NetworkConnection.isNetworkAvailable(UserMainActivity.mainActivity)) {
			mWebView.setVisibility(View.VISIBLE);
			relNoNetWork.setVisibility(View.GONE);
			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			mWebView.loadUrl(URLInterface.SELL_RETAIL_BUY_DATE
						+ "?userId=" + SharedPreference.strUserId
						+ "&&schoolId=" + SharedPreference.strSchoolID
						+ "&&userName=" + SharedPreference.strUserName
						+ "&&token=" + SharedPreference.strUserToken);
			 
		} else {
			mWebView.setVisibility(View.GONE);
			relNoNetWork.setVisibility(View.VISIBLE);
			Toast.makeText(UserMainActivity.mainActivity,
					getString(R.string.network_message_no), Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopThread();
	}
	private void stopThread() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}

		if (timeTask != null) {
			timeTask.cancel();
			timeTask = null;
		}

	}
	
	private void startThread(){
		if (timer == null) {
			timer = new Timer();
		}
		if (timeTask == null) {
			timeTask = new TimerTask() {
				public void run() {
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
					timer.cancel();
					timer.purge();
				}
			};
		}
		if (timer != null && timeTask != null) {
			timer.schedule(timeTask, Constants.TIME_OUT, 1);  //超时时间是15秒，在15秒之后，第一次启动这个线程，然后是1秒后继续
		}
	}
	
	
	
	
	/**
	 * 判断WiFi是否连接
	 * @param mContext
	 * @return
	 */
	public static boolean isWifi() {  
	    ConnectivityManager connectivityManager = (ConnectivityManager) UserMainActivity.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    System.out.println("调用了本地的wifi----------------------------");
	    if(UserMainActivity.mainActivity == null){
	    	System.out.println("在我的wifi中UserMainActivity.mainActivity是空--------------------------------");
	    }
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
	        return true;  
	    }  
	    return false;  
	}  
	/**
	 * 判断手机流量是否连接
	 * @param mContext
	 * @return
	 */
	public static boolean isMoble() {  
		System.out.println("来到isMoble这里----------------------------");
	    ConnectivityManager connectivityManager = (ConnectivityManager) UserMainActivity.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    System.out.println("isMoble这里报空了----------------------------");
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
	        return true;  
	    }  
	    return false;  
	}  

}
