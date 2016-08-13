//package com.sunsoft.zyebiz.b2e.activity;
//
///**
// * 人资料页面
// * @author YinGuiChun
// */
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import org.apache.http.Header;
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.application.ECApplication;
//import com.sunsoft.zyebiz.b2e.common.Constants;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.Register.Register;
//import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.CircularImage;
//import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
//import com.sunsoft.zyebiz.b2e.wiget.IsContainString;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetworkConnection;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//import com.umeng.analytics.MobclickAgent;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class TestMyMessageActivity extends Activity implements
//		OnClickListener {
//	private TextView tvMainText;
//	private TextView tvTitleBack;
//	private ImageView imgTitleBack;
//	private CircularImage myinfo_img;
//	private String imagepath;
//	private Bitmap bitmap;
//	private TextView personName, personPhone, personUserName;
//	private RelativeLayout changePhoneNumber;
//	private DisplayImageOptions options;
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//	private Button btnTuiChu;
//	private static WebView mWebView = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_message);
//		NetManager.isHaveNetWork(TestMyMessageActivity.this);
//		ECApplication.getInstance().addActivity(TestMyMessageActivity.this);
//
//		initView();
//		initDate();
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
//	private void initDate() {
//		tvMainText.setText(getString(R.string.my_person_message));
//		imageLoader.init(ImageLoaderConfiguration
//				.createDefault(TestMyMessageActivity.this));
//		options = new DisplayImageOptions.Builder().build();
//		imagepath = SharedPreference.strUserIcon;
//		imageLoader.displayImage(imagepath, myinfo_img, options);
//		personName.setText(SharedPreference.strUserRealName);
//		personUserName.setText(SharedPreference.strUserName);
//		personPhone.setText(SharedPreference.strUserPhone);
//
//	}
//
//	private void initView() {
//		tvMainText = (TextView) findViewById(R.id.title_main);
//		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
//		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
//
//		myinfo_img = (CircularImage) findViewById(R.id.my_garden_touxiang);
//		changePhoneNumber = (RelativeLayout) findViewById(R.id.rel_person_change_phone);
//		personName = (TextView) findViewById(R.id.person_message_name);
//		personPhone = (TextView) findViewById(R.id.person_message_phone);
//		personUserName = (TextView) findViewById(R.id.person_message_userName);
//		btnTuiChu = (Button) findViewById(R.id.bt_tuichu);
//
//		imgTitleBack.setOnClickListener(this);
//		tvTitleBack.setOnClickListener(this);
//		myinfo_img.setOnClickListener(this);
//		changePhoneNumber.setOnClickListener(this);
//		btnTuiChu.setOnClickListener(this);
//
//	}
//
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_title_back:
//		case R.id.img_title_back:
//			this.finish();
//			break;
//		case R.id.my_garden_touxiang:
//			// ChoosePicUtil.showCustomAlertDialog(this);
//			break;
//		case R.id.rel_person_change_phone:
//			Intent changePhone = new Intent();
//			changePhone.setClass(TestMyMessageActivity.this,
//					PhoneActivity.class);
//			startActivityForResult(changePhone, 1002);
//			break;
//		case R.id.bt_tuichu:
//			if (!NetManager.isWifi() && !NetManager.isMoble()) {
//				Toast.makeText(TestMyMessageActivity.this, getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT).show();
//				return;
//			} 
//
//			showTuiChu(TestMyMessageActivity.this,
//					getString(R.string.dialog_login_exit_user));
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	public static void showTuiChu(final Context context, String string) {
//
//		CustomDialog.Builder builder = new CustomDialog.Builder(context);
//		builder.setMessage(string);
//		builder.setTitle(context.getString(R.string.dialog_login_title));
//		builder.setPositiveButton(
//				context.getString(R.string.dialog_login_cancle),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//
//		builder.setNegativeButton(context.getString(R.string.dialog_login_ok),
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						SharedPreference.cleanUserInfo(context);
//						// ExitFromApp(context);
//						Intent intent = new Intent();
//						intent.setClass(context, LoginActivity.class);
//						context.startActivity(intent);
//						TestSchoolMainActivity.mainActivity.finish();
//
//					}
//
//				});
//
//		builder.create().show();
//
//	}
//
//	private static void ExitFromApp(final Context context) {
//		RequestParams params = new RequestParams();
//		params.put("token", SharedPreference.strUserToken);
//
//		AsyncHttpUtil.post(URLInterface.ZHIYUAN_SET_EXIT_LOGIN, params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						Gson gson = new Gson();
//						if (statusCode == 200) {
//							String resultDate = new String(responseBody);
//							Register shopcart = gson.fromJson(resultDate,
//									Register.class);
//							String title = shopcart.getTitle();
//							System.out.println("title:" + title);
//
//							if (EmptyUtil.isNotEmpty(title)) {
//								if (title.equals("0")) {
//									System.out
//											.println("javascript:FuncClearLocalStorage");
//									mWebView.loadUrl("javascript:FuncClearLocalStorage('userId')");
//								}
//
//							}
//
//						}
//					}
//
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//
//					}
//				});
//	}
//
//	@SuppressLint("SimpleDateFormat")
//	private void cameraCamera(Intent data) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String name = formatter.format(System.currentTimeMillis()) + ".png";
//		Bundle bundle = data.getExtras();
//		Bitmap bitmap = (Bitmap) bundle.get("data");
//		FileOutputStream b = null;
//		String path = Environment.getExternalStorageDirectory().getPath();
//		File file = new File(path + "/myImage/");
//		if (!file.exists() && !file.isDirectory())
//			file.mkdirs();
//		String fileName = file.getPath() + "/" + name;
//		try {
//			b = new FileOutputStream(fileName);
//			bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (b == null)
//					return;
//				b.flush();
//				b.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		imagepath = fileName;
//		myinfo_img.setImageBitmap(bitmap);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == Constants.SYS_INTENT_REQUEST
//				&& resultCode == RESULT_OK && data != null) {
//			try {
//				Uri uri = data.getData();
//				Cursor cursor = getContentResolver().query(uri, null, null,
//						null, null);
//				String imageFilePath;
//				if (cursor != null) {
//					cursor.moveToFirst();
//					imageFilePath = cursor.getString(1);
//					FileInputStream fis = new FileInputStream(imageFilePath);
//					bitmap = BitmapFactory.decodeStream(fis);
//
//					imagepath = imageFilePath;
//					myinfo_img.setImageBitmap(bitmap);
//
//					cursor.close();
//					fis.close();
//
//				} else {
//					imageFilePath = uri.getPath();
//					FileInputStream fis = new FileInputStream(imageFilePath);
//					bitmap = BitmapFactory.decodeStream(fis);
//
//					imagepath = imageFilePath;
//					myinfo_img.setImageBitmap(bitmap);
//
//					fis.close();
//
//				}
//
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		} else if (requestCode == Constants.CAMERA_INTENT_REQUEST
//				&& resultCode == RESULT_OK && data != null) {
//			cameraCamera(data);
//		} else if (requestCode == 1002) {
//			Bundle userPhone = data.getExtras();
//			if (userPhone != null) {
//				personPhone.setText(userPhone.getString("userPhone"));
//			} else {
//				personPhone.setText(SharedPreference.strUserPhone);
//			}
//		} else if (1 == requestCode && RESULT_OK != resultCode) {
//			finish();
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//
//	}
//
//}
