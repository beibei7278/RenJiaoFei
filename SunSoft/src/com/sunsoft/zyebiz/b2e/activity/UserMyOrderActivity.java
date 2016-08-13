package com.sunsoft.zyebiz.b2e.activity;

/**
 * 我的订单
 * @author YinGuiChun
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.util.PayResult;
import com.sunsoft.zyebiz.b2e.util.SignUtils;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserMyOrderActivity extends Activity implements OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleBackTop;
	private WebView mWebView = null;
	private Bundle bundle;
	private String confirm_orderId, confirm_goodsDetail, confirm_price,
			confirm_titleName, confirm_vircardnoin, confirm_verficationcode,
			confirm_merchantid, confirm_url;
	private String payMent;
	private String[] DateJi;
	// 商户收款账号
	public static String SELLER = "";

	private static final int SDK_PAY_FLAG = 1;

	private Handler mHandlerZhiFu = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(UserMyOrderActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(UserMyOrderActivity.this,
							UserBuySuccessActivity.class);
					startActivity(intent);

				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(UserMyOrderActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(UserMyOrderActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						Intent payFail = new Intent();
						payFail.setClass(UserMyOrderActivity.this,
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		NetManager.isHaveNetWork(UserMyOrderActivity.this);
		initView();
		initDate();

		showWebView();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initDate() {
		tvMainText.setText(getString(R.string.my_order_title));
		bundle = this.getIntent().getExtras();

	}

	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		tvTitleBackTop = (TextView) findViewById(R.id.title_main_back_top);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		tvTitleBackTop.setOnClickListener(this);

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.my_order_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					System.out.println("url:" + url);
					
					if (NetManager.isWifi() || NetManager.isMoble()) {
						if (IsContainString.containsString(url,
								URLInterface.SELL_RETAIL_DETAIL)) { // 这里进行了两种处理方式，一种是使用WebView来加载，一种是跳转到新的Activity来加载
							Intent goRetail = new Intent();
							goRetail.setClass(UserMyOrderActivity.this,
									UserRetailXQActivity.class); // 跳转到新的页面来进行加载的
							goRetail.putExtra("url", url);
							view.loadUrl(null);
							startActivity(goRetail);
						} else {
							view.loadUrl(url); // 在本页面加载
						}

					} else {
						Toast.makeText(UserMainActivity.mainActivity,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
					}
					return true;
				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {

				}
			});

			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			mWebView.addJavascriptInterface(new DemoJavaScriptInterface(),
					"demo");
			webSettings.setDefaultTextEncodingName("utf-8");
			if (NetworkConnection.isNetworkAvailable(UserMyOrderActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(),
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			if (EmptyUtil.isNotEmpty(bundle)) {
				String type = bundle.getString("type");
				System.out.println("type:" + type);
				mWebView.loadUrl(URLInterface.MY_ORDER_ALL + "?type=" + type
						+ "&&" + "userId=" + SharedPreference.strUserId
						+ "&&userName=" + SharedPreference.strUserName
						+ "&&token=" + SharedPreference.strUserToken);
				System.out.println("order:" + URLInterface.MY_ORDER_ALL
						+ "?type=" + type + "&&" + "userId="
						+ SharedPreference.strUserId + "&&userName="
						+ SharedPreference.strUserName + "&&token="
						+ SharedPreference.strUserToken);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			if (mWebView.canGoBack()) {
				mWebView.goBack();
			} else {
				finish();
			}

			break;
		case R.id.title_main_back_top:
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;

		default:
			break;
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
			payMent = DateJi[DateJi.length - 1];
			System.out.println("payMent:" + payMent);
			if (payMent.equals("alipay")) {
				confirm_orderId = DateJi[0];
				confirm_goodsDetail = DateJi[1];
				confirm_price = DateJi[2];
				confirm_titleName = DateJi[3];
				confirm_vircardnoin = DateJi[4];// rsa_private
				confirm_verficationcode = DateJi[5];// partner
				confirm_merchantid = DateJi[6];// ras_public
				confirm_url = DateJi[7];
				SELLER = DateJi[8]; // seller
				payMent = DateJi[9];
			} else if (payMent.equals("gopay")) {
				confirm_orderId = DateJi[0];
				confirm_goodsDetail = DateJi[1];
				confirm_price = DateJi[2];
				confirm_titleName = DateJi[3];
				confirm_vircardnoin = DateJi[4];
				confirm_verficationcode = DateJi[5];
				confirm_merchantid = DateJi[6];
				confirm_url = DateJi[7];
				payMent = DateJi[8];
			}
			System.out.println("confirm_id:" + confirm_orderId);
			saveOrderMessage();

		}
	}

	private void saveOrderMessage() {
		SharedPreferences pref = getSharedPreferences("orderMessage",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("CONFIRM_ORDERID", confirm_orderId);
		editor.putString("CONFIRM_GOODSDETAIL", confirm_goodsDetail);
		editor.putString("CONFIRM_PRICE", confirm_price);
		editor.putString("CONFIRM_TITLENAME", confirm_titleName);
		editor.putString("CONFIRM_VIRCARDNOIN", confirm_vircardnoin);
		editor.putString("CONFIRM_VERFICATIONCODE", confirm_verficationcode);
		editor.putString("CONFIRM_MERCHANTID", confirm_merchantid);
		editor.putString("CONFIRM_URL", confirm_url);
		editor.commit();
		if (payMent.equals("alipay") || payMent.equals("gopay")) {
			pay();
		} else {
			System.out.println("支付方式payMent：" + payMent);
		}
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {
		SharedPreference.getOrderMessage(UserMyOrderActivity.this);
		System.out.println("strOrderId:" + SharedPreference.strOrderId);
		System.out.println("url:" + SharedPreference.strOrderUrl);
		String orderInfo = getOrderInfo(SharedPreference.strOrderId,
				SharedPreference.strOrderGoodsDetail,
				SharedPreference.strOrderPrice);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(UserMyOrderActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandlerZhiFu.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(UserMyOrderActivity.this);
		String version = payTask.getVersion();
		Toast.makeText(UserMyOrderActivity.this, version, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + confirm_verficationcode + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		// orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		// orderInfo += "&out_trade_no=" + "\"" + tradeNo + "\"";
		orderInfo += "&out_trade_no=" + "\"" + SharedPreference.strOrderId
				+ "\"";

		// 商品名称支付宝订单显示 的是订单号
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		// orderInfo += "&notify_url=" + "\"" +
		// "http://notify.msp.hk/notify.htm"
		// + "\"";
		orderInfo += "&notify_url=" + "\"" + SharedPreference.strOrderUrl
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		System.out.println("orderINfo:" + orderInfo);

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, confirm_vircardnoin);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	protected void onDestroy() {
		super.onDestroy();
	}

}
