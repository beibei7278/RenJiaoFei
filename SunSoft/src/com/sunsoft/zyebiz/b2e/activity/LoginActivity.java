package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：登录页面
 * @author YinGuiChun
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.cycleviewpager.lib.CycleViewPager;
import com.sunsoft.zyebiz.b2e.cycleviewpager.lib.CycleViewPager.ImageCycleViewListener;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.ADInfo;
import com.sunsoft.zyebiz.b2e.model.Common.Common;
import com.sunsoft.zyebiz.b2e.model.Login.BodyResult;
import com.sunsoft.zyebiz.b2e.model.Login.CommonBean;
import com.sunsoft.zyebiz.b2e.model.Login.CommonButton;
import com.sunsoft.zyebiz.b2e.model.Login.LoginDate;
import com.sunsoft.zyebiz.b2e.model.Login.LoginResult;
import com.sunsoft.zyebiz.b2e.model.Login.SchoolResult;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.Dialog;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NLPullRefreshView;
import com.sunsoft.zyebiz.b2e.wiget.NLPullRefreshView.RefreshListener;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.ViewFactory;
import com.umeng.analytics.MobclickAgent;

@SuppressLint({ "NewApi", "ResourceAsColor", "HandlerLeak" })
public class LoginActivity extends Activity implements OnClickListener,
		RefreshListener {
	private TextView forgetPas, toRegist;
	private Button btLogin;
	// 登陆界面，输入学籍号和输入密码的EditText
	private EditText mUserName, mPassword;
	// 登陆界面的输入学籍号和登陆密码的整体的控件
	private RelativeLayout linUserName, linPassword;
	private String strUserName, strPassword, strXueJiHao, strUserRealName;
	private ImageView cleanUserName, cleanPassWord;
	private String[] urlList;
	private String[] imageUrls;
	private String strUrl;
	private String strPicUrl;
	// 装ImageView的集合，是轮播图中使用的
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	private NLPullRefreshView mRefreshableView;
	private Context mContext;
	private ADInfo info;
	private LoginResult userResult;
	private SchoolResult schoolResult;
	// 用于指导每一个Imageloader根据网络图片的状态（空白、下载错误、正在下载）显示对应的图片，是否将缓存加载到磁盘上，下载完后对图片进行怎么样的处理
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			mRefreshableView.finishRefresh();
			receivePicDate();
			Toast.makeText(mContext, getString(R.string.update_success),
					Toast.LENGTH_SHORT).show();
		};
	};
	private WebView mWebView = null;
	private String url = "";
	private long exitTime = 0;
	private String userType;

	private ImageView imgPic;
	// 防止一直点击登陆按钮，只有当clickNum是1
	private int clickNum = 1;
	String message;
	private TextView loginParent, loginStudent;
	// 登陆的是家长
	public static Boolean isParent;
	// 记录登陆的状态，是学生登陆还是家长登陆
	private String loginState;
	// 从服务器获取到的返回的数据
	private String message2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ECApplication.getInstance().addActivity(LoginActivity.this); // 将登陆的Activity加入到了一个集合中
		mContext = this;
		NetManager.isHaveNetWork(LoginActivity.this);
		receivePicDate();
		initView();
		initDate();

		showWebView();

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		forgetPas = (TextView) findViewById(R.id.forget_pas);
		btLogin = (Button) findViewById(R.id.bt_login);
		mUserName = (EditText) findViewById(R.id.edt_login_username);
		mPassword = (EditText) findViewById(R.id.edt_login_password);
		linUserName = (RelativeLayout) findViewById(R.id.lin_login_username);
		linPassword = (RelativeLayout) findViewById(R.id.lin_login_pas);
		cleanUserName = (ImageView) findViewById(R.id.img_username_clean);
		cleanPassWord = (ImageView) findViewById(R.id.img_password_clean);
		mRefreshableView = (NLPullRefreshView) findViewById(R.id.refresh_root);
		cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);
		toRegist = (TextView) findViewById(R.id.to_regist);
		imgPic = (ImageView) findViewById(R.id.login_img_moren_pic);
		loginParent = (TextView) findViewById(R.id.login_shenfen_parent);
		loginStudent = (TextView) findViewById(R.id.login_shenfen_student);

		forgetPas.setOnClickListener(this);
		btLogin.setOnClickListener(this);
		cleanUserName.setOnClickListener(this);
		cleanPassWord.setOnClickListener(this);
		mRefreshableView.setRefreshListener(this);
		toRegist.setOnClickListener(this);
		loginParent.setOnClickListener(this);
		loginStudent.setOnClickListener(this);

	}

	/**
	 * 进入的时候初始化数据
	 */
	private void initDate() {
		strUserName = mUserName.getText().toString();
		strPassword = mPassword.getText().toString();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this)); // ImageLoader的初始化
		options = new DisplayImageOptions.Builder().build();
		mPassword.setLongClickable(false);
		mPassword.setTextIsSelectable(false);
		loginStudent.setBackgroundResource(R.drawable.shape_shenfen_student);
		loginStudent.setTextColor(0xff515151);
		loginParent
				.setBackgroundResource(R.drawable.shape_shenfen_parent_click);
		loginParent.setTextColor(0xffffffff);
		mUserName.setHint(getString(R.string.login_username_hint_st));
		mPassword.setHint(getString(R.string.login_pas_hint_st));
		toRegist.setVisibility(View.GONE);
		loginState = "studentLogin"; // 默认的状态是学生登陆
		ECApplication.isFirstUpdate = true;
		mLinstenerUserName(); // 对输入用户名的EditText的焦点的设置监听
		mLinstenerPassWord(); // 对输入密码的EditText的焦点的设置监听

	}

	/**
	 * 对点击事件做处理
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forget_pas: // 忘记密码
			if (loginState.equals("studentLogin")) {
				Intent forgetPass = new Intent(this,
						UserForgetPasswordActivity.class);
				startActivity(forgetPass);
			} else {
				Intent intent = new Intent(this, RegistActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.to_regist: // 注册，只能是家长注册
			if (loginState.equals("studentLogin")) {
				Intent intent = new Intent(this, RegistActivity.class); // 家长注册
				startActivity(intent);
			} else {
				Intent forgetPass = new Intent(this,
						UserForgetPasswordActivity.class);
				startActivity(forgetPass);
			}

			break;
		case R.id.img_username_clean: // 清除输入的用户名
			mUserName.setText("");
			btLogin.setClickable(false);
			btLogin.setBackgroundResource(R.drawable.denglumoren);
			btLogin.setTextColor(0xffbebebe);
			break;
		case R.id.img_password_clean: // 清除输入的密码
			mPassword.setText("");
			btLogin.setClickable(false);
			btLogin.setBackgroundResource(R.drawable.denglumoren);
			btLogin.setTextColor(0xffbebebe);
			break;
		case R.id.bt_login: // 登陆
			if (NetManager.isWifi() || NetManager.isMoble()) {
				NetManager.isHaveNetWork(LoginActivity.this);
				if (clickNum == Constants.CONSTANT_ONE) {
					buttonLoginClick();
				} else {
					Toast.makeText(LoginActivity.this,
							getString(R.string.network_message_no),
							Toast.LENGTH_SHORT).show();
					System.out.println("clickNum:" + clickNum);
				}
				++clickNum;
			} else {
				Toast.makeText(LoginActivity.this,
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.login_shenfen_parent: // 家长身份
			mUserName.setText("");
			mPassword.setText("");
			loginStudent
					.setBackgroundResource(R.drawable.shape_shenfen_student);
			loginStudent.setTextColor(0xff515151);
			loginParent
					.setBackgroundResource(R.drawable.shape_shenfen_parent_click);
			loginParent.setTextColor(0xffffffff);
			mUserName.setHint(getString(R.string.login_username_hint_st));
			mPassword.setHint(getString(R.string.login_pas_hint_st));
			toRegist.setVisibility(View.GONE);
			loginState = "studentLogin";
			forgetPas.setText(getString(R.string.tv_do_forget_pas));

			break;
		case R.id.login_shenfen_student: // 学生身份
			mUserName.setText("");
			mPassword.setText("");
			loginParent.setBackgroundResource(R.drawable.shape_shenfen_parent);
			loginParent.setTextColor(0xff515151);
			loginStudent
					.setBackgroundResource(R.drawable.shape_shenfen_student_click);
			loginStudent.setTextColor(0xffffffff);
			mUserName.setHint(getString(R.string.login_username_hint_sp));
			mPassword.setHint(getString(R.string.login_pas_hint_sp));
			toRegist.setVisibility(View.VISIBLE);
			loginState = "parentLogin";
			toRegist.setText(getString(R.string.tv_do_forget_pas));
			forgetPas.setText(getString(R.string.tv_do_regist));

			break;

		default:
			break;
		}

	}

	/**
	 * 点击登陆
	 */
	private void buttonLoginClick() {
		strUserName = mUserName.getText().toString();
		strPassword = mPassword.getText().toString();
		if (EmptyUtil.isEmpty(strUserName) && EmptyUtil.isEmpty(strPassword)) {
			btLogin.setClickable(false);
		} else {
			btLogin.setClickable(true);
			if (strUserName.length() >= Constants.CONSTANT_ONE
					&& strUserName.length() <= Constants.CONSTANT_THIRTY_TWO) {
				checkLogin();
			} else {
				clickNum = Constants.CONSTANT_ONE;
				Dialog.showAlertDialog(LoginActivity.this,
						getString(R.string.dialog_login_toast));
			}
		}
	}

	@SuppressLint("NewApi")
	private void initialize() {

		if (EmptyUtil.isEmpty(imageUrls)) {
			System.out.println("imageUrls" + imageUrls);
			imgPic.setVisibility(View.VISIBLE);
		} else {
			imgPic.setVisibility(View.GONE);
			for (int i = Constants.CONSTANT_ZERO; i < imageUrls.length; i++) {
				info = new ADInfo();
				info.setUrl(imageUrls[i]);
				infos.add(info);
			}
			views.add(ViewFactory.getImageView(this, infos
					.get(infos.size() - 1).getUrl()));
			for (int i = Constants.CONSTANT_ZERO; i < infos.size(); i++) {
				views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
			}
			views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
			cycleViewPager.setCycle(true);
			cycleViewPager.setData(views, infos, mAdCycleViewListener);
			cycleViewPager.setWheel(true);
			cycleViewPager.setTime(Constants.CONSTANT_THREE_THOUSAND);
			cycleViewPager.setIndicatorCenter();
		}

	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				System.out.println("position" + position);

			}

		}

	};

	/**
	 * 清除用户名的时候，需要改变的登陆按钮背景色和焦点
	 */
	@SuppressLint("ResourceAsColor")
	private void cleanUserName() {
		if (mUserName.length() > Constants.CONSTANT_ZERO) {
			cleanUserName.setVisibility(View.VISIBLE);
		} else {
			cleanUserName.setVisibility(View.INVISIBLE);
			btLogin.setClickable(false);
			btLogin.setBackgroundResource(R.drawable.denglumoren);
			btLogin.setTextColor(0xffbebebe);
		}
	}

	/**
	 * 清除密码的时候，需要改变的登陆按钮背景色和焦点
	 */
	private void cleanPassword() {
		if (mPassword.length() > Constants.CONSTANT_ZERO) {
			cleanPassWord.setVisibility(View.VISIBLE);
		} else {
			cleanPassWord.setVisibility(View.INVISIBLE);
			btLogin.setClickable(false);
			btLogin.setBackgroundResource(R.drawable.denglumoren);
			btLogin.setTextColor(0xffbebebe);
		}
	}

	/**
	 * 对登陆按钮的焦点和颜色的改变
	 */
	private void isButtonClick() {
		if (mUserName.getText().length() > Constants.CONSTANT_ZERO
				&& mPassword.getText().length() > Constants.CONSTANT_ZERO) {
			btLogin.setClickable(true);
			btLogin.setBackgroundResource(R.drawable.denglu);
			btLogin.setTextColor(0xffFFFFFF);
		} else {
			btLogin.setClickable(false);
			btLogin.setBackgroundResource(R.drawable.denglumoren);
			btLogin.setTextColor(0xffbebebe);
		}
	}

	/**
	 * 请求轮播图的数据
	 */
	private void receivePicDate() {

		RequestParams params = new RequestParams();
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.LOGIN_PIC_VIDEO, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CommonBean commonBean = gson.fromJson(resultDate,
									CommonBean.class);
							List<LoginDate> body = commonBean.getBody();
							strUrl = "";
							strPicUrl = "";
							imageUrls = null;
							urlList = null;
							infos = new ArrayList<ADInfo>();
							info = new ADInfo();
							views = new ArrayList<ImageView>();
							if (EmptyUtil.isNotEmpty(body)) {
								for (int i = Constants.CONSTANT_ZERO; i < body
										.size(); i++) {
									String url = body.get(i).getAdLink();
									String picUrl = body.get(i).getAdCode();
									strUrl += url + ",";
									strPicUrl += picUrl + ",";
								}
								urlList = strUrl.split(",");
								imageUrls = strPicUrl.split(",");
								initialize();
							} else {
								System.out.println("body:" + body);
								initialize();
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
	 * 登陆之前对输入的账号和密码进行检测
	 */
	private void checkLogin() {

		RequestParams params = new RequestParams();
		params.put("userName", strUserName);
		params.put("password", strPassword);
		if (loginState.equals("parentLogin")) {
			params.put("userType", "01"); // 这里的userType分类的目的是：知道现在登陆的是学生还是家长
		} else {
			params.put("userType", "00"); // 00是学生，01是家长
		}

		AsyncHttpUtil.post(URLInterface.LOGIN_BUTTON, params,
				new AsyncHttpResponseHandler() {

					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CommonButton commonButton = gson.fromJson(
									resultDate, CommonButton.class);
							BodyResult bodyResult = commonButton.getBody();
							message2 = commonButton.getMessage();
							if (EmptyUtil.isNotEmpty(bodyResult)) {
								userResult = bodyResult.getUser();
								schoolResult = bodyResult.getSchool();
								userType = userResult.getUserType();
								isParent = userResult.getIsParent();
								System.out.println("isParent:" + isParent);
								judgeJoin(); // 这里将以前的方法进行了一个抽取
							} else {
								clickNum = Constants.CONSTANT_ONE;
								Dialog.okshowAlertDialog(LoginActivity.this,
										message2);
							}

						} else {
							clickNum = Constants.CONSTANT_ONE;
							Toast.makeText(
									LoginActivity.this,
									getString(R.string.un_connection_with_service),
									Toast.LENGTH_SHORT).show();
						}
					}

					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(LoginActivity.this,
								getString(R.string.un_connection_with_service),
								Toast.LENGTH_SHORT).show();

					}
				});
	}

	/**
	 * 防止学生和家长输入的错误 当输入的是家长角色而此时停留在学生登陆的界面的时候应该提示的是：输入的用户名错误
	 * 当输入的是学生角色而此时停留在家长登陆的界面的时候应该提示的是：输入的用户名错误
	 * 
	 */
	private void judgeJoin() {
		System.out.println("usreType:" + userType); // 这里是将这个登录的时候的数据传递给h5
		mWebView.loadUrl("javascript:FuncSetLocalStorage('userId','"
				+ userResult.getUserId() + "')");
		mWebView.loadUrl("javascript:FuncSetLocalStorage('userName','"
				+ userResult.getUserName() + "')");
		mWebView.loadUrl("javascript:FuncSetLocalStorage('token','"
				+ userResult.getToken() + "')");
		System.out.println("isParent:" + isParent);
		if (EmptyUtil.isNotEmpty(userType)) {
			if (userType.equals(Constants.LOGIN_STUDENT)) { // 00代表学生
				if (!isParent) { // 这里是对用户是否绑定了父母做的判定,没有绑定父母的需要绑定父母,并将这个信息传递过去
					Intent intent = new Intent(LoginActivity.this,
							RegistActivity.class);
					intent.putExtra("xuejihao", userResult.getUserName());
					intent.putExtra("userRealName",
							userResult.getUserRealName());
					startActivity(intent);
					return;
				} else {
					isGroupHaveDate();
				}

			} else {
				isGroupHaveDate();
				clickNum = Constants.CONSTANT_ONE;
			}

		} else {
			clickNum = Constants.CONSTANT_ONE;
		}
	}

	/**
	 * 保存用户登录信息
	 */
	private void saveUserInfo() {
		SharedPreferences pref = getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("USER_NAME", strUserName);
		editor.putString("USER_REALNAME", userResult.getUserRealName());
		editor.putString("PASSWORD", strPassword);
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

	/**
	 * 特殊字符过滤
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {

		String regEx = "\\s";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 对输入用户名的EditText的焦点的设置监听
	 */
	private void mLinstenerUserName() {
		mUserName
				.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							linUserName
									.setBackgroundResource(R.drawable.shape_login_username_click);
						} else {
							linUserName
									.setBackgroundResource(R.drawable.shape_login_username);
						}
					}
				});
		// 用户名输入文字的监听
		mUserName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				cleanUserName();
				isButtonClick();
			}

		});

	}

	/**
	 * 对输入密码的EditText的焦点的设置监听
	 */
	private void mLinstenerPassWord() {
		mPassword
				.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							linPassword
									.setBackgroundResource(R.drawable.shape_login_pas_click);
						} else {
							linPassword
									.setBackgroundResource(R.drawable.shape_login_pas);
						}
					}
				});
		mPassword.addTextChangedListener(new TextWatcher() {
			String mPastmp = ""; //
			String mPasdigits = " ";

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mPassword.setSelection(s.length()); // 对光标位置进行设置
				cleanPassword();
				isButtonClick();
				mPassword.setTransformationMethod(PasswordTransformationMethod // EditText的输入设置为隐藏
						.getInstance());
				mPassword.setSelection(mPassword.getText().toString().length()); // 对光标位置进行设置

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				mPastmp = s.toString();
				cleanPassword();
				isButtonClick();
			}

			@Override
			public void afterTextChanged(Editable s) {
				String mPasstr = s.toString();
				if (mPasstr.equals(mPastmp)) {
					return;
				}
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < mPasstr.length(); i++) {
					if (mPasdigits.indexOf(mPasstr.charAt(i)) < Constants.CONSTANT_ZERO) {
						sb.append(mPasstr.charAt(i));
					}
				}
				mPastmp = sb.toString();
				mPassword.setText(mPastmp);
				cleanPassword();
				isButtonClick();
			}

		});

	}

	public void onRefresh(NLPullRefreshView view) {
		handler.sendEmptyMessageDelayed(Constants.CONSTANT_ONE,
				Constants.CONSTANT_TWO_THOUSAND);

	}

	protected void onResume() {
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		clickNum = 1;
		MobclickAgent.onResume(this);
		super.onResume();
	}

	/**
	 * 初始化进入的时候登陆页面登陆加载WebView，此时的webview是隐藏的 初始化进来的时候userName和userId都没有
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showWebView() {
		try {
			mWebView = (WebView) findViewById(R.id.login_webview);
			mWebView.requestFocus(); // 这里获得焦点的目的
			mWebView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {

					return super.shouldOverrideUrlLoading(view, url);
				}

				public void onPageFinished(WebView view, String url) {
					if (IsContainString.containsString(url,
							URLInterface.LOGIN_BUTTON_WEBVIEW)) { // 这里是调用javascript，把登陆的一些参数传递给H5
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
			if (NetworkConnection.isNetworkAvailable(LoginActivity.this)) {
				webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
				// 默认加载方式，使用这种方式，会实现快速前进后退，在同一个标签打开几个网页后，关闭网络时，
				// 可以通过前进后退来切换已经访问过的数据，同时新建网页需要网络
			} else {
				webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
				// 这个方式不论如何都会从缓存中加载，除非缓存中的网页过期，出现的问题就是打开动态网页时，不能时时更新，
				// 会出现上次打开过的状态，除非清除缓存。
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
	 * 登陆，判断登陆的角色，判断登陆进入的界面
	 */
	private void toLogin() {
		System.out.println("userType:" + userType);
		Intent intent = new Intent();
		if (userType.equals(Constants.LOGIN_STUDENT)) {
			intent.putExtra(Constants.LOGIN_KEY, Constants.KEY_LOGIN_STUDENT);
			intent.setClass(LoginActivity.this, UserMainActivity.class);
		} else if (userType.equals(Constants.LOGIN_PARENT)) {
			intent.putExtra(Constants.LOGIN_KEY, Constants.KEY_LOGIN_PARENT);
			intent.setClass(LoginActivity.this, UserMainActivity.class);
		}
		if (UserMainActivity.isHaveDate) { // 统购商城中是否有数据，有数据就进入统购商城，没有数据就进入零售商城
			intent.putExtra("PaySuccess", "goIndex");
		} else {
			intent.putExtra("PaySuccess", "goRetail");
		}
		startActivity(intent);
		this.finish();

	}

	/**
	 * 判断统购商城和零售商城是否有数据
	 */
	private void isGroupHaveDate() {
		saveUserInfo();
		SharedPreference.getUserInfo(LoginActivity.this);
		System.out.println("token:" + SharedPreference.strUserToken);

		RequestParams params = new RequestParams();
		params.put("token", SharedPreference.strUserToken);
		final Gson gson = new Gson();
		System.out.println("url:" + URLInterface.IS_HAVE_DATE_FOR_GROUP
				+ "?token=" + SharedPreference.strUserToken);
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
						} else {
							clickNum = Constants.CONSTANT_ONE;
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > Constants.CONSTANT_TWO_THOUSAND) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.dialog_chengxu_exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				ECApplication.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
