//package com.sunsoft.zyebiz.b2e.activity;
//
///**
// * 软件首页面
// * @author YinGuiChun
// */
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import javax.net.ssl.HttpsURLConnection;
//import org.apache.http.Header;
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Matrix;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.view.KeyEvent;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.application.ECApplication;
//import com.sunsoft.zyebiz.b2e.common.Constants;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolGongGaoManagerFragment;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolGongGaoManagerFragment.SchoolConsulListener;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolMyGardenFragment;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolMyGardenFragment.SchoolMyGardenlListener;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolOrderManagerFragment;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolOrderManagerFragment.SchoolOrderListener;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolSchoolManagerFragment;
//import com.sunsoft.zyebiz.b2e.fragment.SchoolSchoolManagerFragment.SchoolManagerListener;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.UpDate.BodyResult;
//import com.sunsoft.zyebiz.b2e.model.UpDate.VersionUpDate;
//import com.sunsoft.zyebiz.b2e.service.DownloadApkService;
//import com.sunsoft.zyebiz.b2e.service.DownloadApkService.DownloadBinder;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
//import com.sunsoft.zyebiz.b2e.wiget.ICallbackResult;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//import com.sunsoft.zyebiz.b2e.wiget.UpDateDialog;
//import com.umeng.analytics.MobclickAgent;
//
//@SuppressLint("HandlerLeak")
//public class TestSchoolMainActivity extends FragmentActivity implements
//		OnCheckedChangeListener, SchoolConsulListener, SchoolOrderListener,
//		SchoolManagerListener, SchoolMyGardenlListener {
//	private RadioGroup bottom_rg;
//	private RadioButton scGongGao, scOrder, schoolManager, scMyGarden;
//	public static TestSchoolMainActivity mainActivity;
//	public Boolean isTuangou = true;
//	private long exitTime = 0;
//	private String version;
//	private boolean isBinded = false;
//	private ProgressDialog proDia;
//	private static final int DOWN_OVER = 2;
//	private static final int DOWN_ERROR = 3;
//	private static final int SET_DOWNLOAD_MAX = 15;
//	private static final int REFRESS_DOWNLOAD_PROGRESS = 16;
//	private static final int STOP_DOWNLOAD_APK = 17;
//	private Long totalLenth;
//	private Long readLenth;
//	private boolean isDownloadOver = false;
//	private DownloadBinder binder;
//	private boolean isStopDownload = false;
//	private Matrix matrix = new Matrix();
//	public static String APKCachePath = Environment
//			.getExternalStorageDirectory() + "/";
//	private static final String saveFileName = APKCachePath + "sssssssss.apk";
//	public static String downUrl;
//	private String isUpdate;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_s_main);
//		statrDataSumber();
//		ECApplication.getInstance().addActivity(TestSchoolMainActivity.this);
//		mainActivity = this;
//
//		initView();
//		initDate();
//
//	}
//
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onResume(this);
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPause(this);
//	}
//
//	private void initView() {
//		bottom_rg = (RadioGroup) findViewById(R.id.bottom_rg);
//		scGongGao = (RadioButton) findViewById(R.id.school_gonggao_manager);
//		scOrder = (RadioButton) findViewById(R.id.school_order_manager);
//		schoolManager = (RadioButton) findViewById(R.id.school_school_manager);
//		scMyGarden = (RadioButton) findViewById(R.id.school_my_garden);
//
//		bottom_rg.setOnCheckedChangeListener(this);
//
//	}
//
//	private void initDate() {
//		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//				.beginTransaction();
//		fragmentTransaction.commit();
//		SharedPreference.getUserInfo(TestSchoolMainActivity.mainActivity);
//		changeFragment(new SchoolGongGaoManagerFragment());
//		scGongGao.setChecked(true);
//
//	}
//
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		if (scGongGao.getId() == checkedId) {
//			changeFragment(new SchoolGongGaoManagerFragment());
//
//		} else if (scOrder.getId() == checkedId) {
//			changeFragment(new SchoolOrderManagerFragment());
//
//		} else if (schoolManager.getId() == checkedId) {
//			changeFragment(new SchoolSchoolManagerFragment());
//
//		} else if (scMyGarden.getId() == checkedId) {
//			changeFragment(new SchoolMyGardenFragment());
//
//		}
//
//	}
//
//	public void goConsultation() {
//		scGongGao.setChecked(false);
//		scOrder.setChecked(false);
//		schoolManager.setChecked(false);
//		scMyGarden.setChecked(false);
//
//		changeFragment(new SchoolGongGaoManagerFragment());
//
//	}
//
//	private void changeFragment(Fragment targetFragment) {
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.content, targetFragment, "fragment")
//				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//				.addToBackStack(null).commit();
//	}
//
//	@SuppressLint("ShowToast")
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN) {
//
//			if ((System.currentTimeMillis() - exitTime) > Constants.CONSTANT_TWO_THOUSAND) {
//				Toast.makeText(TestSchoolMainActivity.mainActivity, getString(R.string.dialog_chengxu_exit),
//						Toast.LENGTH_SHORT).show();
//				exitTime = System.currentTimeMillis();
//			} else {
//				ECApplication.getInstance().exit();
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	public static void showAlertDialog(Context context, String string) {
//
//		CustomDialog.Builder builder = new CustomDialog.Builder(context);
//		builder.setMessage(string);
//		builder.setTitle(TestSchoolMainActivity.mainActivity
//				.getString(R.string.dialog_login_title));
//		builder.setPositiveButton(TestSchoolMainActivity.mainActivity
//				.getString(R.string.dialog_login_cancle),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//
//		builder.setNegativeButton(TestSchoolMainActivity.mainActivity
//				.getString(R.string.dialog_login_ok),
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						TestSchoolMainActivity.mainActivity.finish();
//
//					}
//				});
//
//		builder.create().show();
//
//	}
//
//	public void isNeedUpdate() {
//
//		UpDateDialog.Builder builder = new UpDateDialog.Builder(
//				TestSchoolMainActivity.mainActivity);
//		builder.setMessage(getString(R.string.update_title));
//		builder.setTitle(getString(R.string.update_message));
//		builder.setPositiveButton(
//				TestSchoolMainActivity.mainActivity.getString(R.string.update_ok),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						update();
//					}
//				});
//
//		builder.setNegativeButton(TestSchoolMainActivity.mainActivity
//				.getString(R.string.update_cancle),
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						if (isUpdate.equals(Constants.CONSTANT_STRING_ONE)) {
//							dialog.dismiss();
//							ECApplication.getInstance().exit();
//						} else if (isUpdate.equals(Constants.CONSTANT_STRING_ZERO)) {
//							dialog.dismiss();
//						}
//
//					}
//
//				});
//
//		builder.create().show();
//
//	}
//
//	public void statrDataSumber() {
//		try {
//			version = getVersionName();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		RequestParams params = new RequestParams();
//		params.put("versionCode", "1.0");
//		params.put("type", "02");
//		final Gson gson = new Gson();
//		AsyncHttpUtil.post(URLInterface.VERSION_UPDATE, params,
//				new AsyncHttpResponseHandler() {
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
//							String resultDate = new String(responseBody);
//							System.out.println("resultDate:" + resultDate);
//							VersionUpDate versionUpdate = gson.fromJson(
//									resultDate, VersionUpDate.class);
//							BodyResult bodyResult = versionUpdate.getBody();
//
//							if (EmptyUtil.isNotEmpty(bodyResult)) {
//								String url = bodyResult.getUrl();
//								String versionCode = bodyResult
//										.getVersionCode();
//								isUpdate = bodyResult.getIsUpdate();// 1是强制更新
//								if (EmptyUtil.isNotEmpty(url)) {
//									downUrl = url;
//									System.out.println("downUrl======="
//											+ downUrl);
//									if (!version.equals(versionCode)) {
//										isNeedUpdate();
//									}
//
//								}
//
//							}
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//					}
//				});
//
//	}
//
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case STOP_DOWNLOAD_APK:
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				proDia = null;
//				isStopDownload = false;
//				break;
//			case SET_DOWNLOAD_MAX:
//				isDownloadOver = false;
//				if (null == proDia) {
//					return;
//				}
//				int maxValue = (Integer) msg.obj;
//				proDia.setMax(maxValue);
//
//				proDia.setTitle("正在下载文件，总共  " + maxValue / 1024 + "kb");
//				break;
//			case REFRESS_DOWNLOAD_PROGRESS:
//				if (null == proDia) {
//					return;
//				}
//				isDownloadOver = false;
//				int currentProgress = (Integer) msg.obj;
//				proDia.setMessage("已下载  " + currentProgress / 1024 + "kb");
//
//				proDia.setProgress(currentProgress);
//				if (currentProgress < 100) {
//
//				}
//				break;
//			case DOWN_OVER:
//				isDownloadOver = true;
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				installApk();
//				break;
//			case DOWN_ERROR:
//				if (null != proDia) {
//					proDia.dismiss();
//				}
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	private String getVersionName() throws Exception {
//		PackageManager packageManager = getPackageManager();
//		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
//				0);
//		return packInfo.versionName;
//
//	}
//
//	public void update() {
//		Intent intentDownload = new Intent(TestSchoolMainActivity.mainActivity,
//				DownloadApkService.class);
//		bindService(intentDownload, conn,
//				TestSchoolMainActivity.mainActivity.BIND_AUTO_CREATE);
//	}
//
//	@SuppressWarnings("unused")
//	private class DownloadFileRunnable implements Runnable {
//		public void run() {
//			try {
//				URL url = new URL(downUrl);
//				HttpURLConnection conn = (HttpURLConnection) url
//						.openConnection();
//				conn.setDoInput(true);
//				conn.setUseCaches(true);
//				conn.setConnectTimeout(2 * 1000);
//				conn.setRequestMethod("GET");
//				totalLenth = (long) conn.getContentLength();
//				Message msgTotalLength = new Message();
//				msgTotalLength.what = SET_DOWNLOAD_MAX;
//				msgTotalLength.obj = totalLenth.intValue();
//				handler.sendMessage(msgTotalLength);
//				File file = new File(APKCachePath);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				File fileApk = new File(saveFileName);
//				if (!fileApk.exists()) {
//					try {
//						fileApk.createNewFile();
//					} catch (Exception e) {
//						Toast.makeText(TestSchoolMainActivity.mainActivity,
//								getString(R.string.no_sd_no_upload), Toast.LENGTH_SHORT).show();
//					}
//				}
//				if (null != fileApk && fileApk.length() < totalLenth) {
//					fileApk.delete();
//					fileApk.createNewFile();
//				}
//				if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//					System.out.println("connect success");
//					InputStream in = conn.getInputStream();
//					FileOutputStream fileOut = new FileOutputStream(fileApk);
//					byte[] buffer = new byte[1024];
//					int length = -1;
//					byte[] data = null;
//					try {
//						while ((length = in.read(buffer)) != -1
//								&& !isStopDownload) {
//							fileOut.write(buffer, 0, length);
//							readLenth = fileApk.length();
//							Message msgProgress = new Message();
//							msgProgress.what = REFRESS_DOWNLOAD_PROGRESS;
//							msgProgress.obj = readLenth.intValue();
//							handler.sendMessage(msgProgress);
//						}
//						if (!isStopDownload) {
//
//							Message msgDownloadOK = new Message();
//							msgDownloadOK.what = DOWN_OVER;
//							handler.sendMessage(msgDownloadOK);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						if (null != in) {
//							in.close();
//						}
//						if (null != conn) {
//							conn.disconnect();
//						}
//						if (null != fileOut) {
//							fileOut.flush();
//							fileOut.close();
//						}
//						if (null != proDia) {
//							proDia.dismiss();
//						}
//					}
//				}
//
//			} catch (Exception e) {
//				Message msgError = new Message();
//				msgError.what = DOWN_ERROR;
//				handler.sendMessage(msgError);
//			}
//		}
//	}
//
//	private ICallbackResult callback = new ICallbackResult() {
//		@Override
//		public void OnBackResult(Object result) {
//			if ("finish".equals(result)) {
//				finish();
//				return;
//			}
//		}
//	};
//	ServiceConnection conn = new ServiceConnection() {
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			isBinded = false;
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			binder = (DownloadBinder) service;
//			binder = (DownloadBinder) service;
//			isBinded = true;
//			binder.addCallback(callback);
//			binder.start();
//
//		}
//
//	};
//
//	private void installApk() {
//		File apkfile = new File(saveFileName);
//		if (!apkfile.exists()) {
//			return;
//		}
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//				"application/vnd.android.package-archive");
//		TestSchoolMainActivity.mainActivity.startActivity(i);
//
//	}
//
//	public static boolean isConn(Context context) {
//		boolean bisConnFlag = false;
//		ConnectivityManager conManager = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo network = conManager.getActiveNetworkInfo();
//		if (network != null) {
//			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
//		}
//		return bisConnFlag;
//	}
//
//}
