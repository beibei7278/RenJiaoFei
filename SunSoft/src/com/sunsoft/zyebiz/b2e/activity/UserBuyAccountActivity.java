package com.sunsoft.zyebiz.b2e.activity;

/**
 * 结算中心页面
 * @author YinGuiChun
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.util.PayResult;
import com.sunsoft.zyebiz.b2e.util.SignUtils;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("SimpleDateFormat")
public class UserBuyAccountActivity extends Activity implements OnClickListener {
	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleBackTop;
	private WebView mWebView = null;
	private String strRecId;
	private String type;
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
					Toast.makeText(UserBuyAccountActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(UserBuyAccountActivity.this,
							UserBuySuccessActivity.class);
					startActivity(intent);

				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(UserBuyAccountActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(UserBuyAccountActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						Intent payFail = new Intent();
						payFail.setClass(UserBuyAccountActivity.this,
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
		setContentView(R.layout.activity_buy_account);
		ECApplication.getInstance().addActivity(this);
		NetManager.isHaveNetWork(UserBuyAccountActivity.this);
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

	/**
	 * 初始化数据
	 */
	private void initDate() {
		tvMainText.setText(this.getString(R.string.buy_account_title_main));
		Bundle bundle = this.getIntent().getExtras();
		if (EmptyUtil.isNotEmpty(bundle)) {
			strRecId = bundle.getString("recId");
		}
		type = "account";

	}

	/**
	 * 初始化控件
	 */
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
			mWebView = (WebView) findViewById(R.id.group_buy_shang_pin_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					System.out.println("BuyAccountActivity_url:" + url);
					if (NetManager.isWifi() || NetManager.isMoble()) {
						if (IsContainString.containsString(url,
								URLInterface.USER_SHOU_HUO_ADDRESS)) {
							if (IsContainString.containsString(url,
									"backParam=edit")) {
								type = "editsave";
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set));

								view.loadUrl(url);
							} else if (IsContainString.containsString(url,
									"backParam=add")) {
								type = "addsave";
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set));
								System.out.println("type=addsave");
								view.loadUrl(url);
							} else {
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set));
								type = "addressList";
								view.loadUrl(url);
							}

						} else if (IsContainString.containsString(url,
								"type=add")) {
							type = "add";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_add));
							view.loadUrl(url);

						} else if (IsContainString.containsString(url,
								"type=edit")) {
							type = "edit";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_edit));
							System.out.println("type=edit");
							view.loadUrl(url);

						} else if (IsContainString.containsString(url,
								URLInterface.ACCOUNT_CENTER_ADDRESS)) {
							type = "addressList";
							tvMainText
									.setText(getString(R.string.buy_account_title_main));
							view.loadUrl(url);
						} else {
							type = "account";
							view.loadUrl(url);
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
				@Override
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
			if (NetworkConnection
					.isNetworkAvailable(UserBuyAccountActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				Toast.makeText(getApplication(),
						this.getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
			mWebView.loadUrl(URLInterface.ACCOUNT_CENTER + "?userId="
					+ SharedPreference.strUserId + "&&recId=" + strRecId
					+ "&&userName=" + SharedPreference.strUserName + "&&token="
					+ SharedPreference.strUserToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			if (type.equals("addsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_newadd));
				type = "add";
			} else if (type.equals("editsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_edit));
				type = "edit";
			} else if (type.equals("edit") || type.equals("add")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_set));
				type = "addressList";
			} else if (type.equals("addressList")) {

				mWebView.goBack();
				tvMainText.setText(getString(R.string.buy_account_title_main));
				type = "account";
			} else if (type.equals("account")) {
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

	/**
	 * 接受H5页面传递的参数
	 * 
	 */
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

	/**
	 * 保存订单信息
	 */
	private void saveOrderMessage() {
		SharedPreferences pref = UserBuyAccountActivity.this
				.getSharedPreferences("orderMessage", Context.MODE_PRIVATE);
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
		SharedPreference.getOrderMessage(UserMainActivity.mainActivity);
		if (TextUtils.isEmpty(confirm_verficationcode)
				|| TextUtils.isEmpty(confirm_vircardnoin)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(UserMainActivity.mainActivity)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									UserMainActivity.mainActivity.finish();
								}
							}).show();
			return;
		}

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
				PayTask alipay = new PayTask(UserMainActivity.mainActivity);
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
		PayTask payTask = new PayTask(UserMainActivity.mainActivity);
		String version = payTask.getVersion();
		Toast.makeText(UserMainActivity.mainActivity, version,
				Toast.LENGTH_SHORT).show();
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
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		System.out.println("商户订单号：" + key);
		return key;
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

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (type.equals("addsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_newadd));
				type = "add";
				return true;
			}
			if (type.equals("editsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_edit));
				type = "edit";
				return true;
			}
			if (type.equals("edit") || type.equals("add")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_set));
				type = "addressList";
				return true;
			}
			if (type.equals("addressList")) {
				mWebView.goBack();
				tvMainText.setText(getString(R.string.buy_account_title_main));
				type = "account";
				return true;
			}
			if (type.equals("account")) {
				finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
