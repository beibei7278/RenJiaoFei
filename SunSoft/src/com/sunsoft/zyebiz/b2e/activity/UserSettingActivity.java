package com.sunsoft.zyebiz.b2e.activity;

/**
 * 智园设置页面
 * @author YinGuiChun
 */
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.Address.AddressBean;
import com.sunsoft.zyebiz.b2e.model.Register.Register;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.DateCleanManager;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("SetJavaScriptEnabled")
public class UserSettingActivity extends Activity implements OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvAddressReceive;
	private RelativeLayout userManager, shouHuoAddress, cleanCache;
	private Button btnTuiChu;
	private TextView userInfo;
	private static WebView mWebView = null;
	private Button payZhiFu;
	private int payNum = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_garden_setting);
		ECApplication.getInstance().addActivity(UserSettingActivity.this);
		initView();
		initDate();
		receiveAddress();

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
		tvMainText.setText(getString(R.string.my_setting_title_main));
		userInfo.setText(SharedPreference.strUserRealName);

		// switchButton.setOnChangeListener(new OnChangeListener() {
		// public void onChange(SwitchButton sb, boolean state) {
		// Toast.makeText(UserMyGardenSettingActivity.this, state ? "关" : "开",
		// Toast.LENGTH_SHORT).show();
		// }
		// });

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		userManager = (RelativeLayout) findViewById(R.id.rel_setting_user_manager);
		shouHuoAddress = (RelativeLayout) findViewById(R.id.rel_setting_receive_address);
		// receiveTongZhi = (RelativeLayout)
		// findViewById(R.id.rel_setting_receive_tong_zhi);
		cleanCache = (RelativeLayout) findViewById(R.id.rel_setting_clean_cache);
		userInfo = (TextView) findViewById(R.id.my_setting_user_manager);
		btnTuiChu = (Button) findViewById(R.id.bt_tuichu);
		// switchButton = (SwitchButton) findViewById(R.id.slideSwitch);
		tvAddressReceive = (TextView) findViewById(R.id.my_garden_set_address_moren);
		payZhiFu = (Button) findViewById(R.id.pay_zhifu);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		userManager.setOnClickListener(this);
		shouHuoAddress.setOnClickListener(this);
		// receiveTongZhi.setOnClickListener(this);
		cleanCache.setOnClickListener(this);
		btnTuiChu.setOnClickListener(this);
		payZhiFu.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.rel_setting_user_manager:
			Intent goPersonMessage = new Intent();
			goPersonMessage.setClass(UserSettingActivity.this,
					UserMyMessageActivity.class);
			startActivity(goPersonMessage);

			break;
		case R.id.rel_setting_receive_address:
			Intent goChangeAddress = new Intent();
			goChangeAddress.setClass(UserSettingActivity.this,
					UserAddressChooseActivity.class);
			startActivity(goChangeAddress);
			break;
		case R.id.rel_setting_clean_cache:

			try {
				String strCache = DateCleanManager
						.getTotalCacheSize(UserSettingActivity.this);
				if (strCache.equals("0.0Byte")) {
					Toast.makeText(UserSettingActivity.this,
							getString(R.string.dialog_login_cache_clean),
							Toast.LENGTH_SHORT).show();
				} else {
					showAlertDialog(
							UserSettingActivity.this,
							getString(R.string.dialog_login_cache)
									+ DateCleanManager
											.getTotalCacheSize(UserSettingActivity.this));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case R.id.bt_tuichu:
			System.out.println("payNum:" + payNum);
			if (payNum == 5) {
				payZhiFu.setVisibility(View.VISIBLE);
			}
			if (!NetManager.isWifi() && !NetManager.isMoble()) {
				Toast.makeText(UserSettingActivity.this,
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				return;
			}

			showTuiChu(UserSettingActivity.this,
					getString(R.string.dialog_login_exit_user));
			++payNum;
			break;
		case R.id.pay_zhifu:
			Intent intent = new Intent();
			intent.setClass(this, PayDemoActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	/**
	 * 接受地址
	 */
	private void receiveAddress() {
		RequestParams params = new RequestParams();
		params.put("userId", SharedPreference.strUserId);
		params.put("token", SharedPreference.strUserToken);

		AsyncHttpUtil.post(URLInterface.USER_ADDRESS_MOREN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							AddressBean addressBean = gson.fromJson(resultDate,
									AddressBean.class);
							String bodyresult = addressBean.getBody();
							if (EmptyUtil.isNotEmpty(bodyresult)) {
								tvAddressReceive.setText(bodyresult);
							} else {
								tvAddressReceive.setText("");
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});
	}

	/**
	 * 清除缓存
	 * 
	 * @param context
	 *            继承上下文
	 * @param string
	 *            提示信息
	 */
	public static void showAlertDialog(final Context context, String string) {

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle(context.getString(R.string.dialog_login_title));
		builder.setPositiveButton(
				context.getString(R.string.dialog_login_cancle),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(context.getString(R.string.dialog_login_ok),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						DateCleanManager.clearAllCache(context);
						try {
							String strCache = DateCleanManager
									.getTotalCacheSize(UserMainActivity.mainActivity);
							if (strCache.equals("0.0Byte")) {
								Toast.makeText(UserMainActivity.mainActivity,
										"清除完成", Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

		builder.create().show();

	}

	/**
	 * 退出信息提示
	 * 
	 * @param context
	 *            上下文
	 * @param string
	 *            提示信息
	 */
	public static void showTuiChu(final Context context, String string) {

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle(context.getString(R.string.dialog_login_title));
		builder.setPositiveButton(
				context.getString(R.string.dialog_login_cancle),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(context.getString(R.string.dialog_login_ok),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						SharedPreference.cleanUserInfo(context);
						ExitFromApp(context);
						Intent intent = new Intent();
						intent.setClass(context, LoginActivity.class);
						context.startActivity(intent);

					}

				});

		builder.create().show();

	}

	private static void ExitFromApp(final Context context) {
		RequestParams params = new RequestParams();
		params.put("token", SharedPreference.strUserToken);

		AsyncHttpUtil.post(URLInterface.ZHIYUAN_SET_EXIT_LOGIN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							Register shopcart = gson.fromJson(resultDate,
									Register.class);
							String title = shopcart.getTitle();

							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									System.out
											.println("javascript:FuncClearLocalStorage");
									mWebView.loadUrl("javascript:FuncClearLocalStorage('userId')");
								}

							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});
	}

	/**
	 * 加载WebView
	 */
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.login_exit);
			mWebView.requestFocus();
			mWebView.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {

					return super.shouldOverrideUrlLoading(view, url);
				}

				public void onPageFinished(WebView view, String url) {
					if (IsContainString.containsString(url,
							URLInterface.LOGIN_BUTTON_WEBVIEW)) {
						mWebView.loadUrl("javascript:FuncClearLocalStorage('token':"
								+ SharedPreference.strUserToken
								+ ",'userId':"
								+ SharedPreference.strUserId
								+ ",'userName':"
								+ SharedPreference.strUserName + ");");
					}

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
			webSettings.setDefaultTextEncodingName("utf-8");
			if (NetworkConnection.isNetworkAvailable(UserSettingActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
			} else {
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}

			mWebView.loadUrl(URLInterface.LOGIN_BUTTON_WEBVIEW + "?userId="
					+ SharedPreference.strUserId + "&&userName="
					+ SharedPreference.strUserName + "&&token="
					+ SharedPreference.strUserToken);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
