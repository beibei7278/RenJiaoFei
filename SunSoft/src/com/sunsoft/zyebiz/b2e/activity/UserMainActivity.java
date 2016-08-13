package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：进入软件首页面
 * 这里学生和家长登陆进入的时候，统购商城有数据进入统购，统购商城没有数据进入零售商城
 * @author YGC
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.fragment.UserGroupFragment;
import com.sunsoft.zyebiz.b2e.fragment.UserMyGardenFragment;
import com.sunsoft.zyebiz.b2e.fragment.UserSchoolStoreFragment;
import com.sunsoft.zyebiz.b2e.fragment.UserShopCartFragment;
import com.sunsoft.zyebiz.b2e.fragment.UserShopCartFragment.OnShopCartListener;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.UpDate.BodyResult;
import com.sunsoft.zyebiz.b2e.model.UpDate.VersionUpDate;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService;
import com.sunsoft.zyebiz.b2e.service.DownloadApkService.DownloadBinder;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.ICallbackResult;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.UpDateDialog;
import com.umeng.analytics.MobclickAgent;

public class UserMainActivity extends FragmentActivity implements
		OnCheckedChangeListener,
		/* OnShopingStoreListener, */OnShopCartListener {
	/** 底部菜单按钮 */
	private RadioGroup bottom_rg;
	// 底部的四个button按钮，统购商城，学校商城，购物车，我的智园
	private RadioButton consultation, groupStore, shoping, myGarden;
	public static UserMainActivity mainActivity;
	private Boolean teacherLogin = false;
	public static Boolean isHaveDate = true;
	// 接受登陆界面传递过来的登陆的角色
	private String goSetting = null;
	private long exitTime = 0;

	/** 设置版本号 */
	private String version;
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
	/** 0可更新可不更新，1强制更新，不更新退出 */
	private String isUpdate;

	// fragment的切换
	private android.support.v4.app.Fragment mTempFragmentA;
	private UserSchoolStoreFragment userSchoolStoreFragment;
	private UserGroupFragment userGroupFragment;
	private Fragment mTempFragment;
	private boolean isFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ECApplication.getInstance().addActivity(UserMainActivity.this);
		mainActivity = this;

		initView();
		initDate();

		recBundFromActivity();
		// initFragment();

	}

	public void initFragment() {
		userSchoolStoreFragment = new UserSchoolStoreFragment();
		userGroupFragment = new UserGroupFragment();
		mTempFragment = userGroupFragment;
		getSupportFragmentManager().beginTransaction()
				.add(R.id.content, userGroupFragment).commit();
	}

	/**
	 * MobclickAgent友盟统计使用到的一个类，在Resume和Pause过程中调用
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		bottom_rg = (RadioGroup) findViewById(R.id.bottom_rg);
		consultation = (RadioButton) findViewById(R.id.consultation);
		groupStore = (RadioButton) findViewById(R.id.groupstore);
		shoping = (RadioButton) findViewById(R.id.shoping);
		myGarden = (RadioButton) findViewById(R.id.mygarden);
		bottom_rg.setOnCheckedChangeListener(this);

	}

	/**
	 * 初始化进入的时候，获取用户的信息
	 */
	private void initDate() {
		// FragmentTransaction fragmentTransaction = getSupportFragmentManager()
		// .beginTransaction();
		// fragmentTransaction.commit();
		SharedPreference.getUserInfo(UserMainActivity.mainActivity); // 获得用户信息
		// isConsultOrSchoolStore();

	}

	/**
	 * 判断是否进入统购页面
	 */
	private void isConsultOrSchoolStore() {
		if (UserMainActivity.isHaveDate) { // 有数据进入，统购页面
			changeFragment(new UserGroupFragment());
			consultation.setChecked(true);
		} else {
			changeFragment(new UserSchoolStoreFragment()); // 没有数据，进入学校商城
			groupStore.setChecked(true);
		}

	}

	/**
	 * 接受Activity的传值，根据参数判断接入哪个页面，这里直接进入统购的页面（因为不是第一次登陆了就直接进入统购界面）
	 */
	private void recBundFromActivity() {
		initFragment();
		Bundle bundle = this.getIntent().getExtras();
		if (EmptyUtil.isNotEmpty(bundle)) {
			goSetting = bundle.getString(Constants.LOGIN_KEY);
			if (goSetting != null) {
				if (goSetting.equals(Constants.LOGIN_KEY)) {
					onSetting(); // 进入我的智园
				} else if (goSetting.equals("goRetail")) {
					goSchoolStore(); // 进入学校商城，零售商城

				} else if (goSetting.equals("ShopCart")) {
					goShopCart(); // 进入购物车
				} else if (goSetting.equals("goIndex")) {
					goConsultation(); // 进入首页,统购商城

				} else if (goSetting.equals("TeacherLogin")) {
					teacherLogin = true;
				} else {
					System.out.println("goSetting equals dont know");
				}
			} else {
				System.out.println("goSetting:" + goSetting);
			}

		} else {
			System.out.println("bundle:" + bundle);
		}

	}

	/**
	 * 当RadioGroup的组件改变时，会调用此方法
	 * 
	 * @param group
	 * @param checkedId
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (consultation.getId() == checkedId) {
			changeFragment(userGroupFragment);
			// switchFragment(userGroupFragment);
		} else if (groupStore.getId() == checkedId) {
			if (ECApplication.isFirstUpdate) {
				statrDataSumber(); // 软件的版本更新
			}
			changeFragment(userSchoolStoreFragment);
			// switchFragment(userSchoolStoreFragment);
		} else if (shoping.getId() == checkedId) {
			changeFragment(new UserShopCartFragment());
		} else if (myGarden.getId() == checkedId) {
			changeFragment(new UserMyGardenFragment());

		}

	}

	/**
	 * 改变Fragment的界面
	 * 
	 * @param targetFragment
	 */
	private void changeFragment(Fragment fragment) {
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.content, targetFragment, "fragment")
		// .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		// .addToBackStack(null).commit();

		if (fragment != mTempFragment) {
			if (!fragment.isAdded()) {
				getSupportFragmentManager().beginTransaction()
						.hide(mTempFragment).add(R.id.content, fragment)
						.commit();
			} else {
				getSupportFragmentManager().beginTransaction()
						.hide(mTempFragment).show(fragment).commit();
			}
			mTempFragment = fragment;
		}

	}

	/**
	 * 使用hide和show方法切换Fragment
	 * 
	 * @param fragment
	 *            需要切换的fragment
	 */
	// private void switchFragment(Fragment fragment) {
	// if (fragment != mTempFragmentA) {
	// if (!fragment.isAdded()) {
	// getSupportFragmentManager().beginTransaction().hide(mTempFragmentA)
	// .add(R.id.content, fragment).commit();
	// } else {
	// getSupportFragmentManager().beginTransaction().hide(mTempFragmentA)
	// .show(fragment).commit();
	// }
	// mTempFragmentA = fragment;
	// }
	// }

	/**
	 * 零售商城
	 */
	public void goSchoolStore() {
		consultation.setChecked(false);
		groupStore.setChecked(true);
		shoping.setChecked(false);
		myGarden.setChecked(false);
		changeFragment(new UserSchoolStoreFragment());

	}

	/**
	 * 进入首页，统购商城
	 */
	public void goConsultation() {
		System.out.println("20160413");
		consultation.setChecked(true);
		groupStore.setChecked(false);
		shoping.setChecked(false);
		myGarden.setChecked(false);

	}

	/**
	 * 进入我的智园
	 */
	public void onSetting() {
		consultation.setChecked(false);
		groupStore.setChecked(false);
		shoping.setChecked(false);
		myGarden.setChecked(true);
		changeFragment(new UserMyGardenFragment());

	}

	/**
	 * 进入购物车
	 */
	public void goShopCart() {
		consultation.setChecked(false);
		groupStore.setChecked(false);
		shoping.setChecked(true);
		myGarden.setChecked(false);
		changeFragment(new UserShopCartFragment());

	}

	/**
	 * 重写onKeyDown()方法
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@SuppressLint("ShowToast")
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > Constants.CONSTANT_TWO_THOUSAND) {
				Toast.makeText(UserMainActivity.mainActivity,
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

	/**
	 * 显示退出提示框
	 * 
	 * @param context
	 *            继承上下文
	 * @param string
	 *            提示信息
	 */
	public static void showAlertDialog(Context context, String string) {

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(string);
		builder.setTitle(UserMainActivity.mainActivity
				.getString(R.string.dialog_login_title));
		builder.setPositiveButton(UserMainActivity.mainActivity
				.getString(R.string.dialog_login_cancle),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(UserMainActivity.mainActivity
				.getString(R.string.dialog_login_ok),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						UserMainActivity.mainActivity.finish();

					}
				});

		builder.create().show();

	}

	/**
	 * 版本更新弹出框，根据isUpdate来完成相应的操作
	 */
	public void isNeedUpdate() {

		UpDateDialog.Builder builder = new UpDateDialog.Builder(
				UserMainActivity.mainActivity);
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
						if (isUpdate.equals(Constants.CONSTANT_STRING_ONE)) {
							dialog.dismiss();
							ECApplication.getInstance().exit();
						} else if (isUpdate
								.equals(Constants.CONSTANT_STRING_ZERO)) {
							dialog.dismiss();
						}

					}

				});

		builder.create().show();

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
		RequestParams params = new RequestParams();
		params.put("versionCode", version);
		params.put("type", "02");
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.VERSION_UPDATE, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
							VersionUpDate versionUpdate = gson.fromJson(
									resultDate, VersionUpDate.class);
							BodyResult bodyResult = versionUpdate.getBody();

							if (EmptyUtil.isNotEmpty(bodyResult)) {
								String url = bodyResult.getUrl();
								String versionCode = bodyResult
										.getVersionCode();
								isUpdate = bodyResult.getIsUpdate();// 1是强制更新
								if (EmptyUtil.isNotEmpty(url)) {
									downUrl = url;
									System.out.println("downUrl======="
											+ downUrl);
									if (!version.equals(versionCode)) {
										isNeedUpdate();
									}

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
	 * 下载新版本
	 */
	public void update() {
		Intent intentDownload = new Intent(UserMainActivity.mainActivity,
				DownloadApkService.class);
		bindService(intentDownload, conn,
				UserMainActivity.mainActivity.BIND_AUTO_CREATE);
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
						Toast.makeText(UserMainActivity.mainActivity,
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		goSetting = null;
	}

}
