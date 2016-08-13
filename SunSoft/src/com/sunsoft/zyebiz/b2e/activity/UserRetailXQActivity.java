package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：零售详情页面
 * @author YinGuiChun
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.alipay.sdk.app.PayTask;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.util.PayResult;
import com.sunsoft.zyebiz.b2e.util.SignUtils;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserRetailXQActivity extends Activity implements OnClickListener {
	private TextView tvMainText;
	private TextView tvTitleBack;
	private TextView tvTitleBackTop;
	private ImageView imgTitleBack;
	private RelativeLayout relTitleRight;
	private WebView mWebView = null;
	private WebSettings webSettings;
	private String goodsId, strurl, type;
	private String confirm_orderId, confirm_goodsDetail, confirm_price,
			confirm_titleName, confirm_vircardnoin, confirm_verficationcode,
			confirm_merchantid, confirm_url;
	private String payMent;
	private String[] DateJi;
	/** 商户收款账号 */
	public static String SELLER = "";

	private static final int SDK_PAY_FLAG = 1;
	// 立即购买按钮在一个页面中只能点击一次，否则会出现多次提交订单，true表示可提交订单，false表示不可提交订单
	private static Boolean ONLY_ONE_CLICK = true;
	private int showToast = 1;
	private RelativeLayout relNoNetWork;
	private TextView mLoadAgain;
	private Handler mHandlerZhiFu = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证
				 */
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				/** 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档 */
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(UserRetailXQActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(UserRetailXQActivity.this,
							UserBuySuccessActivity.class);
					startActivity(intent);

				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(UserRetailXQActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(UserRetailXQActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						Intent payFail = new Intent();
						payFail.setClass(UserRetailXQActivity.this,
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
		setContentView(R.layout.activity_retail_xq);
		initView();
		initDate();

		showWebView();

	}

	public void onResume() {
		super.onResume();
		ONLY_ONE_CLICK = true;
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 商品详情页面，顶部标题栏的处理
	 */
	private void initDate() {
		tvMainText.setText(getString(R.string.retail_xq_title));
		imgTitleBack.setVisibility(View.VISIBLE);
		relNoNetWork.setVisibility(View.GONE);
		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		type = "retail";
		Bundle bundle = this.getIntent().getExtras();
		if (EmptyUtil.isNotEmpty(bundle)) {
			strurl = bundle.getString("url");
			goodsId = bundle.getString("goodsId");
		}

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		tvTitleBackTop = (TextView) findViewById(R.id.title_main_back_top);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		relTitleRight = (RelativeLayout) findViewById(R.id.my_garden_setting);
		relNoNetWork = (RelativeLayout) findViewById(R.id.rel_network_no);
		mLoadAgain = (TextView) findViewById(R.id.bt_load_again);
		mLoadAgain.setOnClickListener(this);
		imgTitleBack.setOnClickListener(this);
		relTitleRight.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		tvTitleBackTop.setOnClickListener(this);

	}

	/**
	 * WebView加载
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.group_buy_retail_webview);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					System.out.println("url:" + url);
					if (NetManager.isWifi() || NetManager.isMoble()) {
						if (IsContainString.containsString(url, "type=SUCCESS")) {
							view.loadUrl(null);

						} else if (IsContainString.containsString(url,
								"type=add")) {
							type = "add";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_add)); // 增加收货地址
							System.out.println("type=add");
							view.loadUrl(url);

						} else if (IsContainString.containsString(url,
								"type=edit")) {
							type = "edit";
							tvMainText
									.setText(getString(R.string.address_choose_title_main_edit)); // 编辑收货地址
							System.out.println("type=edit");
							view.loadUrl(url);
						} else if (IsContainString.containsString(url,
								URLInterface.USER_SHOU_HUO_ADDRESS)) {
							if (IsContainString.containsString(url,
									"backParam=edit")) {
								type = "editsave";
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set)); // 收货地址
								System.out.println("type=editsave");
								view.loadUrl(url);
							} else if (IsContainString.containsString(url,
									"backParam=add")) {
								type = "addsave";
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set)); // 收货地址
								System.out.println("type=addsave");
								view.loadUrl(url);
							} else {
								tvMainText
										.setText(getString(R.string.address_choose_title_main_set)); // 收货地址
								type = "addressList";
								view.loadUrl(url);
							}
						} else {
							type = "retail";
							view.loadUrl(url);
						}

					} else {
						Toast.makeText(UserRetailXQActivity.this,
								getString(R.string.network_message_no),
								Toast.LENGTH_SHORT).show();
						System.out.println("111");
					}
					return true;
				}

				/** 零售 立即购买按钮 */
				public void onPageFinished(WebView view, String url) {

					if (IsContainString.containsString(url,
							URLInterface.GROUP_BUY_RETAIL_PAYMENT)) {
						if (NetManager.isWifi() || NetManager.isMoble()) {
							relNoNetWork.setVisibility(View.GONE);
							mWebView.setVisibility(View.VISIBLE);
							imgTitleBack.setVisibility(View.VISIBLE);
							tvMainText
									.setText(getString(R.string.buy_account_title_main)); // 结算中心
							relTitleRight.setVisibility(View.GONE);
							type = "account";
							mWebView.loadUrl("javascript:retailtoorder();");
						} else {
							mWebView.setVisibility(View.GONE);
							relNoNetWork.setVisibility(View.VISIBLE);
							Toast.makeText(UserRetailXQActivity.this,
									getString(R.string.network_message_no),
									Toast.LENGTH_SHORT).show();
						}

					}

				}

			});

			mWebView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {

				}
			});

			webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.setVerticalScrollBarEnabled(false);
			webSettings.setDomStorageEnabled(true);
			mWebView.addJavascriptInterface(new DemoJavaScriptInterface(),
					"demo");
			webSettings.setDefaultTextEncodingName("utf-8");
			isHaveNetWork();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调用H5获取数据
	 */
	final class DemoJavaScriptInterface {
		DemoJavaScriptInterface() {
		}

		@JavascriptInterface
		public void clickOnAndroid(String result) {
			if (ONLY_ONE_CLICK) { // 只能提交一次订单
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
				ONLY_ONE_CLICK = false;
				System.out.println("confirm_id:" + confirm_orderId);
				saveOrderMessage();
			}

		}
	}

	/**
	 * 保存订单信息
	 */
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
		// Intent payFail = new Intent();
		// payFail.setClass(UserMainActivity.mainActivity,
		// UserBuyFailActivity.class);
		// startActivity(payFail);
		if (payMent.equals("alipay") || payMent.equals("gopay")) {
			pay();
		} else {
			System.out.println("支付方式payMent：" + payMent);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			if (type.equals("edit") || type.equals("add")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_set)); // 收货地址
			} else if (type.equals("addsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_newadd)); // 新增收货地址
				type = "add";
			} else if (type.equals("editsave")) {
				mWebView.goBack();
				tvMainText
						.setText(getString(R.string.address_choose_title_main_edit)); // 编辑收货地址
				type = "edit";
			} else if (type.equals("addressList")) {
				mWebView.goBack();
				tvMainText.setText(getString(R.string.buy_account_title_main)); // 结算中心
			} else if (type.equals("account")) {
				mWebView.loadUrl("javascript:FuncBacktoRetailDetail()");
				tvMainText
						.setText(getString(R.string.group_buy_shangpin_title_main)); // 商品详情
				relTitleRight.setVisibility(View.VISIBLE);
			} else if (type.equals("retail")) {

				finish();
			}
			System.out.println("type:" + type);
			break;
		case R.id.my_garden_setting: // 我的智园
			Intent goIndex = new Intent();
			goIndex.setClass(UserRetailXQActivity.this, UserMainActivity.class);
			goIndex.putExtra(Constants.LOGIN_KEY, "ShopCart");
			startActivity(goIndex);
			this.finish();
			break;
		case R.id.title_main_back_top: // 点击返回顶端
			mWebView.loadUrl("javascript:FuncBackTop()");
			break;
		case R.id.bt_load_again:
			isHaveNetWork();
			break;

		default:
			break;
		}

	}

	/**
	 * 判断是否有网络
	 */
	@SuppressLint("ShowToast")
	private void isHaveNetWork() {
		if (NetworkConnection.isNetworkAvailable(UserRetailXQActivity.this)) {
			mWebView.setVisibility(View.VISIBLE);
			relNoNetWork.setVisibility(View.GONE);
			webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			if (EmptyUtil.isEmpty(goodsId)) {
				mWebView.loadUrl(strurl);
			} else {
				mWebView.loadUrl(URLInterface.SELL_RETAIL_DETAIL + "?userId="
						+ SharedPreference.strUserId + "&&" + "goodsId="
						+ goodsId + "&&token=" + SharedPreference.strUserToken);
			}
		} else {
			Toast.makeText(UserRetailXQActivity.this,
					getString(R.string.network_message_no), Toast.LENGTH_SHORT)
					.show();
			mWebView.setVisibility(View.GONE);
			relNoNetWork.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {
		SharedPreference.getOrderMessage(UserRetailXQActivity.this);
		if (TextUtils.isEmpty(confirm_verficationcode)
				|| TextUtils.isEmpty(confirm_vircardnoin)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(UserRetailXQActivity.this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									UserRetailXQActivity.this.finish();
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
				PayTask alipay = new PayTask(UserRetailXQActivity.this);
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
		PayTask payTask = new PayTask(UserRetailXQActivity.this);
		String version = payTask.getVersion();
		Toast.makeText(UserRetailXQActivity.this, version, Toast.LENGTH_SHORT)
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

	/**
	 * 实体键返回事件
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
				mWebView.loadUrl("javascript:FuncBacktoRetailDetail()");
				tvMainText
						.setText(getString(R.string.group_buy_shangpin_title_main));
				relTitleRight.setVisibility(View.VISIBLE);
				type = "retail";
				return true;
			}
			if (type.equals("retail")) {

				finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
