package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：注册页面
 * @author YinGuiChun
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.CheckNum.CheckNumber;
import com.sunsoft.zyebiz.b2e.model.Common.Common;
import com.sunsoft.zyebiz.b2e.model.Login.BodyResult;
import com.sunsoft.zyebiz.b2e.model.Login.CommonButton;
import com.sunsoft.zyebiz.b2e.model.Login.LoginResult;
import com.sunsoft.zyebiz.b2e.model.Login.SchoolResult;
import com.sunsoft.zyebiz.b2e.model.Regist.LoginBean;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.util.RegexUtil;
import com.sunsoft.zyebiz.b2e.wiget.AbstractSpinerAdapter.IOnItemSelectListener;
import com.sunsoft.zyebiz.b2e.wiget.CountDownButtonHelper.OnFinishListener;
import com.sunsoft.zyebiz.b2e.wiget.CountDownButtonHelper;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.SpinerPopWindow;
import com.umeng.analytics.MobclickAgent;

public class RegistActivity extends Activity implements OnClickListener,
		IOnItemSelectListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView regist_tishi;
	// 选择家长身份的时候弹出的框
	private SpinerPopWindow mSpinerPopWindow;
	private TextView bt_dropdown;
	private TextView edtParentShenFen;
	private TextView mBtnDropDown;
	// 家长身份的集合(父亲，母亲，其他)
	private List<String> nameList = new ArrayList<String>();
	private Button btnPassword, btnConfirmPassword;
	private TextView tvRegist, registParentUserName;
	private EditText edtPassword, edtConfirmPassword;
	private EditText registXueJi, registStudentName, registParentName,
			registParentPhone;
	private String msgArea;
	private String strUserRealName;
	private int beforeLength, afterLength;
	private TextView sendCheckNum;
	private EditText edtUserCode;
	private Boolean isClickCheckNum = false;
	private long timeout = 180000;
	private Handler mHandler = null;
	private Timer timer = null;
	private TimerTask timeTask;
	private String message;
	// 学生的信息，在登陆的界面输入的
	private LoginResult userResult;
	private SchoolResult schoolResult;
	private String userType;
	private WebView mWebView = null;
	// 是否显示密码,返回的true是显示，false是不显示
	private boolean isSeePassword1 = false;
	// 是否显示密码,返回的true是显示，false是不显示
	private boolean isSeePassword2 = false;
	// 注册按钮是否可以点击,根据两次返回的数据请求成功与否来判断，两次返回的都是true是一种情况，不可注册，其它为一种情况false，可以注册
	private boolean isCanClick;
	// 第一次返回的数据，请求成功为true,请求失败为false
	private boolean isCanClickFirst = true;
	// 第二次返回的数据,请求成功为true，请求失败为false
	private boolean isCanClickSecond = true;
	// 是否是进入这个页面并且第一次点击注册按钮，返回的是true表示是，返回的是false表示否
	private boolean isFirstClickRegisterButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		initView();
		initDate();
		showWebView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		regist_tishi = (TextView) findViewById(R.id.regist_tishi);
		edtParentShenFen = (TextView) findViewById(R.id.edt_regist_parent_shenfen);
		mBtnDropDown = (TextView) findViewById(R.id.bt_dropdown);
		btnPassword = (Button) findViewById(R.id.btn_visible);
		btnConfirmPassword = (Button) findViewById(R.id.btn_confirm_visible);
		edtPassword = (EditText) findViewById(R.id.regist_password);
		edtConfirmPassword = (EditText) findViewById(R.id.edt_change_confirm_password);
		tvRegist = (TextView) findViewById(R.id.regist_confirm);
		registXueJi = (EditText) findViewById(R.id.regist_xue_ji);
		registStudentName = (EditText) findViewById(R.id.regist_userName);
		registParentUserName = (TextView) findViewById(R.id.regist_parent_user_name);
		registParentName = (EditText) findViewById(R.id.regist_parent_name);
		registParentPhone = (EditText) findViewById(R.id.regist_parnet_phone);
		sendCheckNum = (TextView) findViewById(R.id.forget_check_num);
		edtUserCode = (EditText) findViewById(R.id.edt_forget_code);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		mBtnDropDown.setOnClickListener(this);
		tvRegist.setOnClickListener(this);
		edtParentShenFen.setOnClickListener(this);
		sendCheckNum.setOnClickListener(this);
	}

	/**
	 * 初始化数据，这里加上了判断，如果是从登陆界面过来的，就先让学生的学籍和学生的姓名直接显示
	 */
	@SuppressLint("ResourceAsColor")
	private void initDate() {
		isCanClick = false; /* false表示可以注册 */
		isFirstClickRegisterButton = true; /* true表示第一次点击注册按钮 */
		tvMainText.setText(this.getString(R.string.regist_title));
		regist_tishi.setText(Html.fromHtml("<font color=#FF2500>"
				+ this.getString(R.string.regist_tishi) + "</font>"
				+ this.getString(R.string.regist_tishi_message)));

		String[] names = getResources().getStringArray(R.array.regist_name);
		for (int i = Constants.CONSTANT_ZERO; i < names.length; i++) {
			nameList.add(names[i]);
		}

		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, Constants.CONSTANT_ZERO);
		mSpinerPopWindow.setItemListener(this);

		if (EmptyUtil.isNotEmpty(LoginActivity.isParent)) {
			if (!LoginActivity.isParent) {
				Bundle bundle = this.getIntent().getExtras();
				String xuejihao = bundle.getString("xuejihao");
				String userRealName = bundle.getString("userRealName");
				System.out.println("111xuejihao:" + xuejihao
						+ "------xueshengxingming:----" + userRealName);
				registXueJi.setText(xuejihao);
				registStudentName.setText(userRealName);
				registXueJi.setInputType(InputType.TYPE_NULL);
				registStudentName.setInputType(InputType.TYPE_NULL);

			} else {
				registXueJi.setInputType(InputType.TYPE_CLASS_TEXT);
				registStudentName.setInputType(InputType.TYPE_CLASS_TEXT);
			}
		} else {
			registXueJi.setInputType(InputType.TYPE_CLASS_TEXT);
			registStudentName.setInputType(InputType.TYPE_CLASS_TEXT);
		}

		edtPassword.setInputType(0x81); // 初始化进入的时候，EditText是密码状态。
		// 对点击是否可见密码做限制
		btnPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int length = edtPassword.getText().toString().length();
				if (isSeePassword1) {
					edtPassword.setInputType(0x90); // EditText变为非密码状态。
					btnPassword.setBackgroundResource(R.drawable.xiaoyanjing);
				} else {
					edtPassword.setInputType(0x81); // EditText变为密码状态。
					btnPassword
							.setBackgroundResource(R.drawable.xiaoyanjing_biyan);
				}
				isSeePassword1 = !isSeePassword1;
				Editable text1 = edtPassword.getText();
				Selection.setSelection(text1, text1.length());
			}
		});

		edtConfirmPassword.setInputType(0x81); // 初始化进入的时候，EditText是密码状态。
		// 这是确认输入密码时旁边设置的是否显示密码的按钮
		btnConfirmPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int length = edtConfirmPassword.getText().toString().length();
				if (isSeePassword2) {
					edtConfirmPassword.setInputType(0x90); // EditText变为非密码状态。
					btnConfirmPassword
							.setBackgroundResource(R.drawable.xiaoyanjing);
				} else {
					edtConfirmPassword.setInputType(0x81); // EditText变为密码状态。
					btnConfirmPassword
							.setBackgroundResource(R.drawable.xiaoyanjing_biyan);
				}
				isSeePassword2 = !isSeePassword2;
				Editable text2 = edtConfirmPassword.getText();
				Selection.setSelection(text2, text2.length());
			}
		});

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.CONSTANT_ONE:
					Toast.makeText(RegistActivity.this, "验证码失效，请重新获取",
							Toast.LENGTH_SHORT).show();
					isClickCheckNum = false;
					stopThread();
					break;

				default:
					break;
				}

			};
		};

		edtUserCode
				.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							if (EmptyUtil.isNotEmpty(registParentPhone
									.getText().toString())) {
								registParentUserName.setText(registParentPhone
										.getText().toString());
								if (EmptyUtil.isNotEmpty(message)) { // 给message加了一个非空判断
									if (!message.equals(edtUserCode.getText()
											.toString())) {
										Toast.makeText(RegistActivity.this,
												"请输入正确的验证码", Toast.LENGTH_SHORT)
												.show();
										return;
									}
								}
							}

						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.edt_regist_parent_shenfen:
		case R.id.bt_dropdown:
			showSpinWindow();
			break;
		case R.id.regist_confirm: /* 注册按钮 */
			if (isFirstClickRegisterButton) {
				// 直接走正常方法
				isFirstClickRegisterButton = false;
				isCanClick = false;
			} else {
				isCanClick = isCanClickFirst && isCanClickSecond;// 这里是false可以注册，是true不可注册
			}

			if (!isCanClick) { // 注册按钮就只能点击一次的标记
				int num = beforeRegisterButton();
				if (num == 1) {
					isCanRegeist();
				}
			}
			break;
		case R.id.send_yanzhengma: // 点击获取验证码才能得到验证码
		case R.id.forget_check_num:
			isClickCheckNum = true;
			stopThread();
			startThread();
			System.out.println("userphone:"
					+ registParentPhone.getText().toString());
			if (!RegexUtil.isMobileNumber(registParentPhone.getText()
					.toString())) {
				Toast.makeText(RegistActivity.this,
						getString(R.string.check_number_toast_phone),
						Toast.LENGTH_SHORT).show();
			} else {
				CountDownButtonHelper helper = new CountDownButtonHelper(
						sendCheckNum,
						this.getString(R.string.change_new_number_send), 60, 1);
				helper.setOnFinishListener(new OnFinishListener() {

					@Override
					public void finish() {
						sendCheckNum
								.setText(getString(R.string.change_new_number_send_again));
					}
				});
				helper.start();
				recCheckNum();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * WebView加载页面
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.regist_webview);
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
			if (NetworkConnection.isNetworkAvailable(RegistActivity.this)) {
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

	/**
	 * 选择列表
	 */
	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(edtParentShenFen.getWidth());
		mSpinerPopWindow.showAsDropDown(edtParentShenFen);
	}

	/**
	 * 选择身份信息
	 * 
	 * @param pos
	 *            点击选择的位置
	 */
	private void setIdentity(int pos) {
		if (pos >= Constants.CONSTANT_ZERO && pos <= nameList.size()) {
			String value = nameList.get(pos);
			edtParentShenFen.setText(value);
		}
	}

	/**
	 * 选择列表点击事件
	 */
	public void onItemClick(int pos) {
		setIdentity(pos);
		switch (pos) {
		case Constants.CONSTANT_ZERO:
			msgArea = Constants.CONSTANT_STRING_ONE;
			break;
		case Constants.CONSTANT_ONE:
			msgArea = Constants.CONSTANT_STRING_TWO;
			break;
		case Constants.CONSTANT_TWO:
			msgArea = Constants.CONSTANT_STRING_THREE;
			break;

		default:
			break;
		}
	}

	/**
	 * 验证所有的信息正确之后，再去请求网络，第二次去验证所有的代码
	 */
	private void sendRegistMessage() {
		RequestParams params = new RequestParams();
		params.put("parentName", registParentUserName.getText().toString());
		params.put("parentPhone", registParentPhone.getText().toString());
		params.put("userCode", edtUserCode.getText().toString());
		params.put("password", edtPassword.getText().toString());
		params.put("parentRealName", registParentName.getText().toString());
		params.put("ParentIdentity", msgArea);
		params.put("userName", registXueJi.getText().toString());

		AsyncHttpUtil.post(URLInterface.LOGIN_REGIST, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("家长resultDate:" + resultDate);

							CommonButton commonButton = gson.fromJson(
									resultDate, CommonButton.class);
							BodyResult bodyResult = commonButton.getBody();
							String title = commonButton.getTitle();
							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									userResult = bodyResult.getUser();
									schoolResult = bodyResult.getSchool();
									userType = userResult.getUserType();
									judgeJoin();
								} else {
									isCanClickSecond = false;
									Toast.makeText(RegistActivity.this,
											commonButton.getMessage(),
											Toast.LENGTH_SHORT).show();
								}

							} else {
								isCanClickSecond = false;
								Toast.makeText(RegistActivity.this,
										commonButton.getMessage(),
										Toast.LENGTH_SHORT).show();

							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						isCanClickSecond = false;
					}
				});
	}

	/**
	 * 添加注册前的验证 点击之前的注册信息的验证 返回的是1可以去请求网络 返回的是0不可以请求网络
	 */
	private int beforeRegisterButton() {
		String parentName = registParentName.getText().toString();
		int length = parentName.length();
		if (length <= 12) {
			return 1;
		} else {
			Toast.makeText(RegistActivity.this, "由1-12位汉字，字母，数字的字符组成",
					Toast.LENGTH_SHORT).show();
			registParentName.requestFocus();
			return 0;
		}
	}

	/**
	 * 点击注册之后去请求网络，第一次进行验证，验证
	 */
	private void isCanRegeist() {
		RequestParams params = new RequestParams();
		params.put("userName", registXueJi.getText().toString());

		AsyncHttpUtil.post(URLInterface.USER_MESSAGE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							LoginBean regist = gson.fromJson(resultDate,
									LoginBean.class);
							String title = regist.getTitle();
							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									strUserRealName = regist.getBody()
											.getUserRealName();
									allMessageRight();
								} else {
									isCanClickFirst = false;
									if (EmptyUtil.isEmpty(registXueJi.getText()
											.toString())) {
										Toast.makeText(RegistActivity.this,
												"学籍号不能为空", Toast.LENGTH_SHORT)
												.show();
									} else {
										Toast.makeText(
												RegistActivity.this,
												getString(R.string.regist_xueji),
												Toast.LENGTH_SHORT).show();
									}

								}

							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						isCanClickFirst = false;
					}
				});
	}

	/**
	 * 判断输入是否是汉字
	 * 
	 * @param c
	 *            输入字符
	 * @return 返回值
	 */
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测是否含有中文字符
	 * 
	 * @param name
	 *            字符串
	 * @return
	 */
	public boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = Constants.CONSTANT_ZERO; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * 验证所有的输入的信息是否正确
	 */
	private void allMessageRight() {
		/** 学生姓名是否为空检测 */
		if (EmptyUtil.isEmpty(registStudentName.getText().toString())) {
			Toast.makeText(RegistActivity.this, "学生姓名不能为空", Toast.LENGTH_SHORT)
					.show();
			registStudentName.requestFocus();
			registStudentName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}

		if (!strUserRealName.equals(registStudentName.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_name_error), Toast.LENGTH_SHORT)
					.show();
			registStudentName.requestFocus();
			registStudentName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}

		if (!RegexUtil.isMobileNumber(registParentPhone.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_phone_error), Toast.LENGTH_SHORT)
					.show();
			registParentPhone.requestFocus();
			registParentPhone.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}

		if (EmptyUtil.isEmpty(edtUserCode.getText().toString())) {
			Toast.makeText(RegistActivity.this, "验证码不能为空", Toast.LENGTH_SHORT)
					.show();
			isCanClickSecond = false;
			return;
		}
		if (EmptyUtil.isEmpty(message)) {
			Toast.makeText(RegistActivity.this, "请点击获取验证码", Toast.LENGTH_SHORT)
					.show();
			isCanClickSecond = false;
			return;
		}
		if (!isClickCheckNum) {
			Toast.makeText(RegistActivity.this, "验证码失效，请重新获取",
					Toast.LENGTH_SHORT).show();
			isCanClickSecond = false;
			return;
		}

		if (!message.equals(edtUserCode.getText().toString())) {
			Toast.makeText(RegistActivity.this, "验证码输入不正确，请重新输入",
					Toast.LENGTH_SHORT).show();
			isCanClickSecond = false;
			return;
		}

		/** 家长用户名是否为空检测 */
		if (EmptyUtil.isEmpty(registParentUserName.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_username_null),
					Toast.LENGTH_SHORT).show();
			registParentUserName.requestFocus();
			registParentUserName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		if (registParentUserName.getText().toString().length() < Constants.CONSTANT_TWO
				|| registParentUserName.getText().toString().length() > Constants.CONSTANT_SIXTEEN) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_username_length_error),
					Toast.LENGTH_SHORT).show();
			registParentUserName.requestFocus();
			registParentUserName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		if (!format(registParentUserName.getText().toString())) {
			Toast.makeText(RegistActivity.this, "用户名格式为英文,数字,汉字@.和下划线",
					Toast.LENGTH_SHORT).show();
			registParentUserName.requestFocus();
			registParentUserName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		/** 家长姓名是否为空检测 */
		if (EmptyUtil.isEmpty(registParentName.getText().toString())) {
			Toast.makeText(RegistActivity.this, "家长姓名不能为空", Toast.LENGTH_SHORT)
					.show();
			registParentName.requestFocus();
			registParentName.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}

		if (EmptyUtil.isEmpty(msgArea)) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_parent_identy_null),
					Toast.LENGTH_SHORT).show();
			isCanClickSecond = false;
			return;
		}
		if (EmptyUtil.isEmpty(edtPassword.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_password_null),
					Toast.LENGTH_SHORT).show();
			isCanClickSecond = false;
			return;
		}
		if (edtPassword.getText().toString().length() < Constants.CONSTANT_SIX
				|| edtPassword.getText().toString().length() > Constants.CONSTANT_SIXTEEN) {
			Toast.makeText(RegistActivity.this,
					"密码输入格式为:英文大小写字母、数字、特殊字符，长度为6-16位之内", Toast.LENGTH_SHORT)
					.show();
			edtPassword.requestFocus();
			edtPassword.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		if (edtPassword.getText().toString()
				.equals(registParentUserName.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_password_error),
					Toast.LENGTH_SHORT).show();
			edtPassword.requestFocus();
			edtPassword.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		if (EmptyUtil.isEmpty(edtConfirmPassword.getText().toString())) {
			Toast.makeText(RegistActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT)
					.show();
			edtConfirmPassword.requestFocus();
			edtConfirmPassword.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		if (!edtPassword.getText().toString()
				.equals(edtConfirmPassword.getText().toString())) {
			Toast.makeText(RegistActivity.this,
					getString(R.string.regist_password_check),
					Toast.LENGTH_SHORT).show();
			edtConfirmPassword.requestFocus();
			edtConfirmPassword.requestFocusFromTouch();
			isCanClickSecond = false;
			return;
		}
		sendRegistMessage();

	}

	/**
	 * 传递数据给H5页面
	 */
	private void judgeJoin() {
		mWebView.loadUrl("javascript:FuncSetLocalStorage('userId','"
				+ userResult.getUserId() + "')");
		mWebView.loadUrl("javascript:FuncSetLocalStorage('userName','"
				+ userResult.getUserName() + "')");
		mWebView.loadUrl("javascript:FuncSetLocalStorage('token','"
				+ userResult.getToken() + "')");
		if (EmptyUtil.isNotEmpty(userType)) {
			if (userType.equals(Constants.LOGIN_STUDENT)
					|| userType.equals(Constants.LOGIN_PARENT)) {

				isGroupHaveDate();
			}
		}

	}

	/**
	 * 判断团购页面是否有数据
	 */
	private void isGroupHaveDate() {
		saveUserInfo();
		SharedPreference.getUserInfo(RegistActivity.this);
		System.out.println("token:" + SharedPreference.strUserToken);
		RequestParams params = new RequestParams();
		params.put("token", SharedPreference.strUserToken);
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.IS_HAVE_DATE_FOR_GROUP, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
							Common common = gson.fromJson(resultDate,
									Common.class);
							String title = common.getTitle();
							System.out.println("title:" + title);
							if (title.equals(Constants.CONSTANT_STRING_ONE)) {
								UserMainActivity.isHaveDate = false;

							} else {
								UserMainActivity.isHaveDate = true;
							}
							toLogin();

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}

	/**
	 * 执行登录操作
	 */
	private void toLogin() {
		Toast.makeText(RegistActivity.this, getString(R.string.regist_success), // 注册成功
				Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent.putExtra(Constants.LOGIN_KEY, Constants.KEY_LOGIN_PARENT);
				intent.setClass(RegistActivity.this, UserMainActivity.class); // 进入的时候，团购有数据，进入团购，团购没有数据进入零售
				if (UserMainActivity.isHaveDate) { // 统购商城中是否有数据，有数据就进入统购商城，没有数据就进入零售商城
					intent.putExtra("PaySuccess", "goIndex");
				} else {
					intent.putExtra("PaySuccess", "goRetail");
				}
				startActivity(intent);
				finish();
			}
		}, Constants.CONSTANT_TWO_THOUSAND);

	}

	/**
	 * 得到验证码
	 */
	private void recCheckNum() {
		SharedPreference.getUserInfo(RegistActivity.this);
		RequestParams params = new RequestParams();
		System.out.println("token:" + SharedPreference.strUserToken);
		params.put("mobile", registParentPhone.getText().toString());
		params.put("type", "0");// 注册

		AsyncHttpUtil.post(URLInterface.MOBILE_SEND_MESSAGE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CheckNumber checkNum = gson.fromJson(resultDate,
									CheckNumber.class);
							message = checkNum.getMessage();
							System.out.println("message:" + message);
							System.out
									.println("---------------------------message:"
											+ message);
							// 加上一个判断，查询数据库得到数据，如果得到的数据是“手机号已注册”，就弹出提示框并且不会返回验证码
							if ("手机号已注册。".equals(message)
									&& EmptyUtil.isNotEmpty(message)) {
								Toast.makeText(RegistActivity.this, "手机号已注册。",
										Toast.LENGTH_SHORT).show();
								registParentPhone.setText(""); // 把手机号码清空
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
	 * 注册成功保存用户信息
	 */
	private void saveUserInfo() {
		SharedPreferences pref = getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("USER_NAME", userResult.getUserName());
		editor.putString("USER_REALNAME", userResult.getUserRealName());
		editor.putString("PASSWORD", edtPassword.getText().toString());
		editor.putString("USER_ID", userResult.getUserId());
		editor.putString("USER_PHONE", userResult.getMobilePhone());
		editor.putString("USER_GRADE", schoolResult.getGradeNo());
		editor.putString("USER_CLASS", schoolResult.getClassNo());
		editor.putString("USER_ICON", userResult.getIcon());
		editor.putString("USER_SEX", userResult.getSex());
		editor.putString("USER_SCHOOLNAME", schoolResult.getSchoolName());
		editor.putString("USER_SCHOOLID", schoolResult.getSchoolId());
		editor.putString("USER_TOKEN", userResult.getToken());
		editor.putString("USER_TYPE", userResult.getUserType());

		editor.commit();
	}

	private boolean format(String str) {
		beforeLength = str.length();
		String regEx = "[`~!#$%&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		String guolv = m.replaceAll("").trim();
		afterLength = guolv.length();
		System.out.println("beforlength:" + beforeLength + "--afterlength--"
				+ afterLength);
		if (beforeLength == afterLength) {
			return true;
		}
		return false;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onDestroy() {
		super.onDestroy();
		stopThread();
	}

	/**
	 * 停止所有线程
	 */
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

	/**
	 * 开始线程
	 */
	private void startThread() {
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
			timer.schedule(timeTask, timeout, 1);
		}
	}

}
