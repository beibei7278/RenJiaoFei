package com.sunsoft.zyebiz.b2e.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
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
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.Register.Register;
import com.sunsoft.zyebiz.b2e.model.UpDate.BodyResult;
import com.sunsoft.zyebiz.b2e.model.UpDate.VersionUpDate;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService.DownloadBinder;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.DateCleanManager;
import com.sunsoft.zyebiz.b2e.wiget.ICallbackResult;
import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.UpDateDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的设置界面
 * 
 * @author YinGuiChun
 */
public class UserMySettingActivity extends FragmentActivity implements
		OnClickListener {
	// 消息推送
	private RelativeLayout setMessage;
	// 清除缓存
	private RelativeLayout clearCache;
	// 检查最新版本
	private RelativeLayout versionRelative;
	// 退出程序
	private Button exitButton;
	// 标题
	private TextView titleTv;
	private static WebView mWebView = null;
	// 版本
	private TextView versionTv;
	// 设置版本号
	private String version;
	// 0可更新可不更新，1强制更新，不更新退出
	private String isUpdate;
	public static String downUrl;
	private Long totalLenth;
	private boolean isBinded = false;
	private ProgressDialog proDia;
	private static final int DOWN_OVER = 2;
	private static final int DOWN_ERROR = 3;
	private static final int SET_DOWNLOAD_MAX = 15;
	private static final int REFRESS_DOWNLOAD_PROGRESS = 16;
	private static final int STOP_DOWNLOAD_APK = 17;
	private Long readLenth;
	private boolean isDownloadOver = false;
	private DownloadBinder binder;
	private boolean isStopDownload = false;
	private Matrix matrix = new Matrix();
	public static String APKCachePath = Environment
			.getExternalStorageDirectory() + "/";
	private static final String saveFileName = APKCachePath + "sssssssss.apk";

	// 拿到动态的BaseUrl
	// public static String spUrl = ECApplication.getInstance().getSpUrl();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_my_guard_setting);
		// setContentView(R.layout.activity_my_garden_setting);
		ECApplication.getInstance().addActivity(UserMySettingActivity.this);
		initView();
		// initData();
		getVersion();
		showWebView();
	}

	private void initView() {
		setMessage = (RelativeLayout) findViewById(R.id.my_setting_message_rl);
		clearCache = (RelativeLayout) findViewById(R.id.my_setting_clear_cache);
		versionRelative = (RelativeLayout) findViewById(R.id.my_setting_version_rl);
		exitButton = (Button) findViewById(R.id.bt_tuichu);
		titleTv = (TextView) findViewById(R.id.title_main);
		versionTv = (TextView) findViewById(R.id.my_setting_version);
		backIv = (ImageView) findViewById(R.id.img_title_back);
		titleTv.setText("设置");
		setMessage.setOnClickListener(this);
		clearCache.setOnClickListener(this);
		versionRelative.setOnClickListener(this);
		exitButton.setOnClickListener(this);
		backIv.setOnClickListener(this);
	}

	/**
	 * 获取版本信息
	 */
	private void getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			if (EmptyUtil.isNotEmpty(version)) {
				versionTv.setHint(version);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

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
	 * 点击事件做出处理
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.my_setting_message_rl: // 消息推送设置界面
			toSetMessageActivity();
			break;
		case R.id.my_setting_clear_cache: // 清除缓存
			clearCache();
			break;
		case R.id.my_setting_version_rl: // 更新到最新的版本
			setNewVersion();
			break;
		case R.id.bt_tuichu: // 退出程序按钮
			exitAccount();
			break;
		case R.id.img_title_back: // 返回键
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 退出当前的账号
	 */
	private void exitAccount() {
		if (!NetManager.isWifi() && !NetManager.isMoble()) {
			Toast.makeText(UserMySettingActivity.this,
					getString(R.string.network_message_no), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		showTuiChu(UserMySettingActivity.this,
				getString(R.string.dialog_login_exit_user));
	}

	/**
	 * 设置版本号,当前的版本如果不是最新的版本点击就下载，当前的版本是最新的版本点击就弹出提示
	 */
	private void setNewVersion() {
		statrDataSumber();
	}

	/**
	 * 清除缓存
	 */
	private void clearCache() {
		try {
			String strCache = DateCleanManager
					.getTotalCacheSize(UserMySettingActivity.this);
			if (strCache.equals("0.0Byte")) {
				Toast.makeText(UserMySettingActivity.this,
						getString(R.string.dialog_login_cache_clean),
						Toast.LENGTH_SHORT).show();
			} else {
				showAlertDialog(
						UserMySettingActivity.this,
						getString(R.string.dialog_login_cache)
								+ DateCleanManager
										.getTotalCacheSize(UserMySettingActivity.this));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消息推送设置界面
	 */
	private void toSetMessageActivity() {
		// SetMessageActivity setMessageActivity = new SetMessageActivity();
		// Intent intent = new
		// Intent(ActivityMySetting2.this,SetMessageActivity.class);
		// startActivity(intent);

		Intent setMessageActivity = new Intent();
		setMessageActivity.setClass(UserMySettingActivity.this,
				SetMessageActivity.class);
		startActivity(setMessageActivity);
	}

	/**
	 * 缓存清理
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
	 * 显示退出框
	 * 
	 * @param context
	 *            继承上下文
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

	/**
	 * 退出App
	 * 
	 * @param context
	 *            继承上下文
	 */
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
							String title = "";
							if (EmptyUtil.isNotEmpty(shopcart)) {
								title = shopcart.getTitle();
							}

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
	 * WebVeiw加载页面
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
			if (NetworkConnection
					.isNetworkAvailable(UserMySettingActivity.this)) {
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
	 * 软件的版本更新
	 */
	public void statrDataSumber() {
		try {
			version = getVersionName();
			System.out.println("verson:" + version);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------版本更新");
		RequestParams params = new RequestParams();
		params.put("versionCode", version);
		params.put("type", "10");
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.VERSION_UPDATE, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						System.out.println("--------------成功");
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
							VersionUpDate versionUpdate = gson.fromJson(
									resultDate, VersionUpDate.class);
							BodyResult bodyResult = versionUpdate.getBody();
							String title = versionUpdate.getTitle();
							if (EmptyUtil.isNotEmpty(title)) { // 版本更新的标示是title,0需要更新，1不需要更新
								if ("0".equals(title)) {
									String url = bodyResult.getUrl();
									downUrl = url;
									SharedPreferences spApkUrl = getSharedPreferences(
											"set_apk_url", MODE_PRIVATE);
									spApkUrl.edit()
											.putString("downUrl", downUrl)
											.commit();
									isNeedUpdate();
								} else {
									Toast.makeText(
											UserMainActivity.mainActivity,
											"已是最新版本", Toast.LENGTH_SHORT)
											.show();
								}
							}
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(UserMySettingActivity.this,
								R.string.network_message_no, Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	/**
	 * 得到版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getVersionName() throws Exception {
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;

	}

	/**
	 * 版本更新弹出框，根据isUpdate来完成相应的操作
	 */
	public void isNeedUpdate() {

		UpDateDialog.Builder builder = new UpDateDialog.Builder(
				UserMySettingActivity.this);
		builder.setMessage(getString(R.string.update_title));
		builder.setTitle(getString(R.string.update_message));
		builder.setPositiveButton(
				UserMainActivity.mainActivity.getString(R.string.update_ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						update();
					}
				});

		builder.setNegativeButton(
				UserMainActivity.mainActivity.getString(R.string.update_cancle),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				});

		builder.create().show();

	}

	/**
	 * 下载新版本
	 */
	public void update() {
		Intent intentDownload = new Intent(UserMySettingActivity.this,
				DownloadApkService.class);
		bindService(intentDownload, conn,
				UserMySettingActivity.this.BIND_AUTO_CREATE);
	}

	@SuppressWarnings("unused")
	private class DownloadFileRunnable implements Runnable {
		public void run() {
			try {
				URL url = new URL(downUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.setUseCaches(true);
				conn.setConnectTimeout(2 * 1000);
				conn.setRequestMethod("GET");
				totalLenth = (long) conn.getContentLength();
				Message msgTotalLength = new Message();
				msgTotalLength.what = SET_DOWNLOAD_MAX;
				msgTotalLength.obj = totalLenth.intValue();
				handler.sendMessage(msgTotalLength);
				File file = new File(APKCachePath);
				if (!file.exists()) {
					file.mkdir();
				}
				File fileApk = new File(saveFileName);
				if (!fileApk.exists()) {
					try {
						fileApk.createNewFile();
					} catch (Exception e) {
						Toast.makeText(UserMySettingActivity.this,
								"没有存储设备，无法下载最新版本", Toast.LENGTH_SHORT).show();
					}
				}
				if (null != fileApk && fileApk.length() < totalLenth) {
					fileApk.delete();
					fileApk.createNewFile();
				}
				if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
					System.out.println("connect success");
					InputStream in = conn.getInputStream();
					FileOutputStream fileOut = new FileOutputStream(fileApk);
					byte[] buffer = new byte[1024];
					int length = -1;
					byte[] data = null;
					try {
						while ((length = in.read(buffer)) != -1
								&& !isStopDownload) {
							fileOut.write(buffer, 0, length);
							readLenth = fileApk.length();
							Message msgProgress = new Message();
							msgProgress.what = REFRESS_DOWNLOAD_PROGRESS;
							msgProgress.obj = readLenth.intValue();
							handler.sendMessage(msgProgress);
						}
						if (!isStopDownload) {

							Message msgDownloadOK = new Message();
							msgDownloadOK.what = DOWN_OVER;
							handler.sendMessage(msgDownloadOK);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (null != in) {
							in.close();
						}
						if (null != conn) {
							conn.disconnect();
						}
						if (null != fileOut) {
							fileOut.flush();
							fileOut.close();
						}
						if (null != proDia) {
							proDia.dismiss();
						}
					}
				}

			} catch (Exception e) {
				Message msgError = new Message();
				msgError.what = DOWN_ERROR;
				handler.sendMessage(msgError);
			}
		}
	}

	/**
	 * 接口回调
	 */
	private ICallbackResult callback = new ICallbackResult() {
		@Override
		public void OnBackResult(Object result) {
			if ("finish".equals(result)) {
				finish();
				return;
			}
		}
	};
	ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (DownloadBinder) service;
			binder = (DownloadBinder) service;
			isBinded = true;
			binder.addCallback(callback);
			binder.start();

		}

	};

	/**
	 * 自动更新
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		UserMySettingActivity.this.startActivity(i);

	}

	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
		}
		return bisConnFlag;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case STOP_DOWNLOAD_APK:
				if (null != proDia) {
					proDia.dismiss();
				}
				proDia = null;
				isStopDownload = false;
				break;
			case SET_DOWNLOAD_MAX:
				isDownloadOver = false;
				if (null == proDia) {
					return;
				}
				int maxValue = (Integer) msg.obj;
				proDia.setMax(maxValue);

				proDia.setTitle("正在下载文件，总共  " + maxValue / 1024 + "kb");
				break;
			case REFRESS_DOWNLOAD_PROGRESS:
				if (null == proDia) {
					return;
				}
				isDownloadOver = false;
				int currentProgress = (Integer) msg.obj;
				proDia.setMessage("已下载  " + currentProgress / 1024 + "kb");

				proDia.setProgress(currentProgress);
				if (currentProgress < 100) {

				}
				break;
			case DOWN_OVER:
				isDownloadOver = true;
				if (null != proDia) {
					proDia.dismiss();
				}
				installApk();
				break;
			case DOWN_ERROR:
				if (null != proDia) {
					proDia.dismiss();
				}
				break;
			default:
				break;
			}
		};
	};
	private ImageView backIv;

}
