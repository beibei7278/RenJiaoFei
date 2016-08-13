package com.sunsoft.zyebiz.b2e.fragment;

/**
 * 功能：统购页面，统购商城
 * @author YinGuiChun
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.activity.HomeActivity;
import com.sunsoft.zyebiz.b2e.activity.UserBuyFailActivity;
import com.sunsoft.zyebiz.b2e.activity.UserBuySuccessActivity;
import com.sunsoft.zyebiz.b2e.activity.UserGroupBuyGongGaoActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.PayResult;
import com.sunsoft.zyebiz.b2e.util.SignUtils;
import com.sunsoft.zyebiz.b2e.wiget.DialogUtils;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserGroupFragment extends Fragment implements OnClickListener {
	/** 标题 */
	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleBackTop;
	private WebView mWebView = null;
	private RelativeLayout relNoNetWork;
	private TextView mLoadAgain;
	private WebSettings webSettings;
	/** 菊花转 */
	private Dialog mLoading;
	/** 定时器 */
	private Handler mHandler = null;     
	
	private Timer timer = null;
	private TimerTask timeTask;
	/** 支付宝获得参数 */
	private String confirmOrderId, confirmGoodsDetail, confirmPrice,
			confirmTitleName, confirmVircardnoin, confirmVerficationcode,
			confirmMerchantid, confirmUrl;
	private String payMent;
	private String[] DateJi;
	/** 商户收款账号 */
	private String mSeller = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mBaseView = inflater.inflate(R.layout.fragment_group, null);
		System.out.println("UserGroupFragment");
		NetManager.isHaveNetWork(UserMainActivity.mainActivity);
		initView(mBaseView);
		initDate();
		showWebView();

		return mBaseView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ECApplication.getInstance().exit();
	}
	
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(Constants.STRING_YOU_MENG_TONG_JI);
	}

	private void initView(View rootView) {
		mWebView = (WebView) rootView.findViewById(R.id.webview);
		tvMainText = (TextView) rootView.findViewById(R.id.title_main);
		tvTitleBack = (TextView) rootView.findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) rootView.findViewById(R.id.img_title_back);
		mLoadAgain = (TextView) rootView.findViewById(R.id.bt_load_again);
		relNoNetWork = (RelativeLayout) rootView
				.findViewById(R.id.rel_network_no);
		tvTitleBackTop = (TextView) rootView
				.findViewById(R.id.title_main_back_top);

		mLoadAgain.setOnClickListener(this);
		imgTitleBack.setOnClickListener(this);
		tvTitleBackTop.setOnClickListener(this);

	}

	@SuppressLint({ "ShowToast", "HandlerLeak" })
	private void initDate() {
		relNoNetWork.setVisibility(View.GONE);
		imgTitleBack.setVisibility(View.GONE);
		tvMainText.setText(getString(R.string.consulution));
		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mLoading = DialogUtils
				.createLoadingDialog(UserMainActivity.mainActivity);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			mWebView.goBack();
			imgTitleBack.setVisibility(View.GONE);
			tvMainText.setText(getString(R.string.consulution));
			break;
		case R.id.bt_load_again:
			isHaveNetWork();
			break;
		case R.id.title_main_back_top:
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;

		default:
			break;
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (NetManager.isWifi() || NetManager.isMoble()) {
						System.out.println("userGroup进入wifi-----------------shouldOverrideUrlLoading--");
						if (IsContainString.containsString(url,
								URLInterface.GROUP_BUY_XIANG_XI)) {
							Intent goRetail = new Intent();
							goRetail.setClass(getActivity(),
									UserGroupBuyGongGaoActivity.class);
							goRetail.putExtra("url", url);
							view.loadUrl(null);
							getActivity().startActivity(goRetail);
						} else {
							view.loadUrl(url);
						}

					} else {
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
					}
					return true;
				}

				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					mLoading.show();
					startThread();
				}

				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					mLoading.dismiss();
					stopThread();
					
					if (NetManager.isWifi() || NetManager.isMoble()) {
						System.out.println("userGroup进入wifi-----------------onPageFinished--");
						relNoNetWork.setVisibility(View.GONE);
						mWebView.setVisibility(View.VISIBLE);
						if (IsContainString.containsString(url,
								URLInterface.GROUP_BUY_PAYMENT)) {
							tvMainText
									.setText(getString(R.string.buy_account_title_main));
							imgTitleBack.setVisibility(View.VISIBLE);
						}
					} else {
						mWebView.setVisibility(View.GONE);
						relNoNetWork.setVisibility(View.VISIBLE);
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
					}
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
			mWebView.addJavascriptInterface(new DemoJavaScriptInterface(),
					"demo");
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			webSettings.setDefaultTextEncodingName("utf-8");
			isHaveNetWork();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final class DemoJavaScriptInterface {
		DemoJavaScriptInterface() {

		}

		@JavascriptInterface
		public void clickOnAndroid(String result) {
			String zhifuDate = result;
			System.out.println("zhifuDate:" + zhifuDate);
			DateJi = zhifuDate.split(";");
			payMent = DateJi[DateJi.length - Constants.CONSTANT_ONE];
			System.out.println("payMent:" + payMent);
			if (payMent.equals("alipay")) {
				confirmOrderId = DateJi[Constants.CONSTANT_ZERO];
				confirmGoodsDetail = DateJi[Constants.CONSTANT_ONE];
				confirmPrice = DateJi[Constants.CONSTANT_TWO];
				confirmTitleName = DateJi[Constants.CONSTANT_THREE];
				/** rsa_private */
				confirmVircardnoin = DateJi[Constants.CONSTANT_FOUT];
				/** partner */
				confirmVerficationcode = DateJi[Constants.CONSTANT_FIVE];
				/** ras_public */
				confirmMerchantid = DateJi[Constants.CONSTANT_SIX];
				confirmUrl = DateJi[Constants.CONSTANT_SEVEN];
				/** seller */
				mSeller = DateJi[Constants.CONSTANT_EIGHT];
				payMent = DateJi[Constants.CONSTANT_NINE];
				System.out.println("confirm_id:" + confirmOrderId);
				saveOrderMessage();
			} else {
				System.out.println("payMent:" + payMent);
			}

		}
	}

	private void saveOrderMessage() {
		SharedPreferences pref = UserMainActivity.mainActivity
				.getSharedPreferences("orderMessage", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("CONFIRM_ORDERID", confirmOrderId);
		editor.putString("CONFIRM_GOODSDETAIL", confirmGoodsDetail);
		editor.putString("CONFIRM_PRICE", confirmPrice);
		editor.putString("CONFIRM_TITLENAME", confirmTitleName);
		editor.putString("CONFIRM_VIRCARDNOIN", confirmVircardnoin);
		editor.putString("CONFIRM_VERFICATIONCODE", confirmVerficationcode);
		editor.putString("CONFIRM_MERCHANTID", confirmMerchantid);
		editor.putString("CONFIRM_URL", confirmUrl);
		editor.putString("CONFIRM_PAY_MENT", payMent);
		editor.commit();
		pay();

	}

	/**
	 * 调用支付宝的SDK
	 */
	public void pay() {
		SharedPreference.getOrderMessage(UserMainActivity.mainActivity);
		String orderInfo = getOrderInfo(SharedPreference.strOrderId,
				SharedPreference.strOrderGoodsDetail,
				SharedPreference.strOrderPrice);
		String sign = sign(orderInfo);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(UserMainActivity.mainActivity);
				/** 调用支付接口，获取支付结果 */
				String result = alipay.pay(payInfo, true);
				Message msg = new Message();
				msg.what = Constants.CONSTANT_ONE;
				msg.obj = result;
				mHandlerZhiFu.sendMessage(msg);
			}
		};
		/** 必须异步调用 */
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 获得SDK版本号
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(UserMainActivity.mainActivity);
		String version = payTask.getVersion();
		Toast.makeText(UserMainActivity.mainActivity, version,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 创建订单信息
	 */
	private String getOrderInfo(String subject, String body, String price) {
		/** 签约合作者身份ID */
		String orderInfo = "partner=" + "\"" + confirmVerficationcode + "\"";
		/** 签约卖家支付宝账号 */
		orderInfo += "&seller_id=" + "\"" + mSeller + "\"";
		/** 商户网站唯一订单号 */
		orderInfo += "&out_trade_no=" + "\"" + SharedPreference.strOrderId
				+ "\"";
		/** 商品名称支付宝订单显示 的是订单号 */
		orderInfo += "&subject=" + "\"" + subject + "\"";
		/** 商品详情 */
		orderInfo += "&body=" + "\"" + body + "\"";
		/** 商品金额 */
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		/** 服务器异步通知页面路径 */
		orderInfo += "&notify_url=" + "\"" + SharedPreference.strOrderUrl
				+ "\"";
		/** 服务接口名称， 固定值 */
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		/** 支付类型， 固定值 */
		orderInfo += "&payment_type=\"1\"";
		/** 参数编码， 固定值 */
		orderInfo += "&_input_charset=\"utf-8\"";
		/**
		 * 设置未付款交易的超时时间 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		 * m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点，如1.5h，可转换为90m。
		 * */
		orderInfo += "&it_b_pay=\"30m\"";
		/** 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空 */
		orderInfo += "&return_url=\"m.alipay.com\"";
		return orderInfo;
	}

	/**
	 * 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, confirmVircardnoin);
	}

	/**
	 * 获取签名方式
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	@SuppressLint("ShowToast")
	private void isHaveNetWork() {
		if (NetworkConnection.isNetworkAvailable(UserMainActivity.mainActivity)) {
			mWebView.setVisibility(View.VISIBLE);
			relNoNetWork.setVisibility(View.GONE);
			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			mWebView.loadUrl(URLInterface.GROUP_BUY_DATE + "?userName="
					+ SharedPreference.strUserName + "&&token="
					+ SharedPreference.strUserToken);

		} else {
			Toast.makeText(UserMainActivity.mainActivity,
					getString(R.string.network_message_no), Toast.LENGTH_SHORT);
			mWebView.setVisibility(View.GONE);
			relNoNetWork.setVisibility(View.VISIBLE);
		}
	}

	public static boolean containsString(String src, String dest) {
		boolean flag = false;
		if (src.contains(dest)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 定时器启动
	 */
	private void startThread() {
		if (timer == null) {
			timer = new Timer();
		}
		if (timeTask == null) {
			timeTask = new TimerTask() {
				public void run() {
					Message msg = new Message();
					msg.what = Constants.CONSTANT_ONE;
					mHandler.sendMessage(msg);
					timer.cancel();
					timer.purge();
				}
			};
		}
		if (timer != null && timeTask != null) {
			timer.schedule(timeTask, Constants.TIME_OUT, Constants.CONSTANT_ONE);
		}
	}

	/**
	 * 停止定时器
	 */
	@SuppressLint("ShowToast")
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

	@SuppressLint("HandlerLeak")
	private Handler mHandlerZhiFu = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.CONSTANT_ONE: {
				PayResult payResult = new PayResult((String) msg.obj);
				/** 同步返回需要验证的信息 */
				String resultInfo = payResult.getResult();
				System.out.println("resultInfo:" + resultInfo);
				String resultStatus = payResult.getResultStatus();
				System.out.println("resultSatatus:" + resultStatus);
				/** 判断resultStatus 为9000则代表支付成功 */
				if (TextUtils.equals(resultStatus,
						Constants.STRING_NINE_THOUSAND)) {
					Toast.makeText(UserMainActivity.mainActivity,
							getString(R.string.pay_success), Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(UserMainActivity.mainActivity,
							UserBuySuccessActivity.class);
					startActivity(intent);

				} else {
					/** 8000代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认 */
					if (TextUtils.equals(resultStatus,
							Constants.STRING_EIGHT_THOUSAND)) {
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.pay_on_confirm),
								Toast.LENGTH_SHORT).show();

					} else {
						/** 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误 */
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.pay_fail),
								Toast.LENGTH_SHORT).show();
						Intent payFail = new Intent();
						payFail.setClass(UserMainActivity.mainActivity,
								UserBuyFailActivity.class);
						startActivity(payFail);

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(Constants.STRING_YOU_MENG_TONG_JI);
	}

	public void onDestroy() {
		super.onDestroy();
		stopThread();
	}

}
