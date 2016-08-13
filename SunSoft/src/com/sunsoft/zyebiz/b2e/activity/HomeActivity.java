package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：闪现页
 * @author YinGuiChun
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpClient;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.model.ChangeIp.ChangeIpBodyBean;
import com.sunsoft.zyebiz.b2e.model.ChangeIp.ObjBean;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService.DownloadBinder;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.util.ExampleUtil;
import com.sunsoft.zyebiz.b2e.util.SPUtils;
import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.ICallbackResult;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.OkCustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class HomeActivity extends Activity {
	// 判断是否是第一次进入
	private Boolean first;
	private static Context context;
	private NetManager netManager;
	private Animation animation;
	private View view;
	private String START;
	private int progressInt = 0;
	private ProgressBar pb;
	/** 设置版本号 */
	private String version;
	// 极光推送
	public static boolean isForeground = false;
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.sunsoft.zyebiz.b2e.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	// 请求的网络地址
	public String bodyUrl;
	public String BASE_URL = "http://www.ygzykj.cn:8080/"; // 演示环境
	private String msgCode;
	public ArrayList changeIpBodyList;
	public ArrayList objList;
	public ObjBean objBean;
	public ChangeIpBodyBean changeIpBodyBean;

	private static SharedPreferences spUrl;

	private boolean isBinded = false;
	private ProgressDialog proDia;
	private static final int DOWN_OVER = 2;
	private static final int DOWN_ERROR = 3;
	private static final int SET_DOWNLOAD_MAX = 15;
	private static final int REFRESS_DOWNLOAD_PROGRESS = 16;
	private static final int STOP_DOWNLOAD_APK = 17;
	private Long totalLenth;
	private Long readLenth;
	private boolean isDownloadOver = false;
	private DownloadBinder binder;
	private boolean isStopDownload = false;
	private Matrix matrix = new Matrix();
	public static String APKCachePath = Environment
			.getExternalStorageDirectory() + "/";
	private static final String saveFileName = APKCachePath + "sssssssss.apk";
	public static String downUrl;
	// 是否强制更新标记
	public static String UPDATE_MSGCODE;
	// 更换的服务器的url
	public static String UPDATE_SERVER_URL;
	// 下载apk的URL
	public static String UPDATE_APK_URL;
	private long exitTime = 0;

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

	Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			msg.what = 0;
			progressInt = progressInt + 2;
			pb.setProgress(progressInt);
			handler2.removeMessages(0);

			if (progressInt <= 96) {
				if (progressInt == 40) {
					handler2.sendEmptyMessageDelayed(0, 100);
				} else if (progressInt == 80) {
					handler2.sendEmptyMessageDelayed(0, 50);
				} else {
					handler2.sendEmptyMessageDelayed(0, 15);
				}
			} else if (msg.what == 1) {
				pb.setProgress(100);
			}

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getUrl();
		view = View.inflate(this, R.layout.activity_home, null);
		setContentView(view);
		registerMessageReceiver(); // 注册广播
		context = this;
		netManager = new NetManager(context);
		SharedPreference.getUserInfo(HomeActivity.this);
		System.out.println("SharedPreference.strUserType:"
				+ SharedPreference.strUserType);
		pb = (ProgressBar) findViewById(R.id.pb_progressbar);

		handler2.sendEmptyMessageDelayed(0, 10);

	}

	/**
	 * 注册广播信息接受者
	 */
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	protected void onResume() {
		isForeground = true; // 极光推送
		MobclickAgent.onResume(this);
		super.onResume();
	}

	public void onPause() {
		isForeground = false; // 极光推送
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 主要是极光推送使用的
	 */
	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

	/**
	 * 根据是否是第一次进入应用来判断该进入哪个页面
	 */
	private void into() {

		first = (Boolean) SPUtils.get(context, "First", true);
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		handler.sendEmptyMessageDelayed(1, 10);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation arg0) {
			}

			public void onAnimationRepeat(Animation arg0) {
			}

			public void onAnimationEnd(Animation arg0) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {

						Intent intent;
						if (first) { // 第一次进入
							intent = new Intent(HomeActivity.this,
									CommonWelcomeActivity.class);
						} else {

							intent = new Intent();

							if (EmptyUtil.isEmpty(SharedPreference.strUserType)) { // 不是第一次进入，读取sp,得到登陆用户的类型
								intent.setClass(HomeActivity.this,
										LoginActivity.class);

							} else {
								ECApplication.isFirstUpdate = false;
								intent.setClass(HomeActivity.this,
										UserMainActivity.class);
								intent.putExtra("PaySuccess", "goIndex"); // 在UserMainActivity中接受到这边的信息，直接进入统购
								// }
							}
						}
						startActivity(intent);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						HomeActivity.this.finish();
					}
				}, Constants.CONSTANT_ONE_HUNDRED_AND_ONE); // 启动页面的延时
			}
		});

	}

	/**
	 * 弹出退出提示框
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

					}

				});

		builder.create().show();

	}

	/**
	 * 极光推送接受者
	 */
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
			}
		}
	}

	/**
	 * 获取网络的地址
	 */
	public void getUrl() {
		NetManager.isHaveNetWork(HomeActivity.this);
		try {
			version = getVersionName();
			System.out.println("verson:" + version);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BASE_URL = BASE_URL + "sunsoft-app/version/checkVersionip.json?"
				+ "versionCode=" + version + "&type=" + "10" + "&pageNo=1"
				+ "&pageSize=10"; /* +"&pageNo=1"+"&pageSize=10" */
		System.out.println("BASE_URL:" + BASE_URL);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(BASE_URL, new AsyncHttpResponseHandler() {

			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
					String resultDate = new String(responseBody);
					System.out.println("resultDate------------" + resultDate);
					if (EmptyUtil.isNotEmpty(resultDate)) {
						try {
							JSONObject jsonObject = new JSONObject(resultDate);
							System.out.println("jsonObject:" + jsonObject);
							if (jsonObject.has("body")) {
								JSONObject jsonObject2 = jsonObject
										.getJSONObject("body");
								UPDATE_MSGCODE = jsonObject2
										.getString("msgCode");
								if (jsonObject2.has("obj")) {
									JSONObject jsonObject3 = jsonObject2
											.getJSONObject("obj");
									UPDATE_SERVER_URL = jsonObject3
											.getString("serverUrl");
									UPDATE_APK_URL = jsonObject3
											.getString("url");
									URLInterface urlInterface = new URLInterface(
											UPDATE_SERVER_URL);
									downUrl = UPDATE_APK_URL;
								}
							}

							if (EmptyUtil.isNotEmpty(UPDATE_MSGCODE)) {
								if ("1".equals(UPDATE_MSGCODE)) {
									mustUpdateAlertDialog(HomeActivity.this,
											"马上升级！马上升级！马上升级！重要的事情说三遍。升级吧，少年！");
								} else {
									into();
								}

							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// 输出错误信息
				error.printStackTrace();
			}
		});

	}

	/**
	 * 获取版本号
	 * 
	 * @return 版本号
	 * @throws Exception
	 */
	private String getVersionName() throws Exception {
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;

	}

	/**
	 * Dialog提示框,启动页的强制更新
	 * 
	 * @param string
	 *            提示信息
	 */
	public void mustUpdateAlertDialog(Context context, String string) {

		OkCustomDialog.Builder builder = new OkCustomDialog.Builder(context);

		builder.setMessage("马上升级！马上升级！马上升级！重要的事情说三遍。升级吧，少年！");
		builder.setTitle("提示");

		builder.setNegativeButton("确定",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						update();
					}
				});

		OkCustomDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();

	}

	/**
	 * 下载新版本
	 */
	public void update() {
		Intent intentDownload = new Intent(HomeActivity.this,
				DownloadApkService.class);
		bindService(intentDownload, conn, HomeActivity.BIND_AUTO_CREATE);
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
						Toast.makeText(HomeActivity.this, "没有存储设备，无法下载最新版本",
								Toast.LENGTH_SHORT).show();
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

	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		UserMainActivity.mainActivity.startActivity(i);

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

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > Constants.CONSTANT_TWO_THOUSAND) {
				Toast.makeText(HomeActivity.this,
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
