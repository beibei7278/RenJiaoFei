package com.sunsoft.zyebiz.b2e.activity;

/**
 * 个人资料页面
 * @author YinGuiChun
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
import com.sunsoft.zyebiz.b2e.wiget.CircularImage;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserMyMessageActivity extends Activity implements OnClickListener {
	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private CircularImage myinfo_img;
	private String imagepath;
	private Bitmap bitmap;
	private TextView personName, personPhone, personUserName;
	private RelativeLayout zhangHuManager, changePhoneNumber, changePassword;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_message);

		initView();
		initDate();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initDate() {
		tvMainText.setText(getString(R.string.my_person_message));
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(UserMyMessageActivity.this));
		options = new DisplayImageOptions.Builder().build();
		imagepath = SharedPreference.strUserIcon;
		imageLoader.displayImage(imagepath, myinfo_img, options);
		personName.setText(SharedPreference.strUserRealName);
		personUserName.setText(SharedPreference.strUserName);
		personPhone.setText(SharedPreference.strUserPhone);

	}

	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		myinfo_img = (CircularImage) findViewById(R.id.my_garden_touxiang);
		zhangHuManager = (RelativeLayout) findViewById(R.id.rel_person_zhanghu_message);
		changePhoneNumber = (RelativeLayout) findViewById(R.id.rel_person_change_phone);
		changePassword = (RelativeLayout) findViewById(R.id.rel_person_change_password);
		personName = (TextView) findViewById(R.id.person_message_name);
		personPhone = (TextView) findViewById(R.id.person_message_phone);
		personUserName = (TextView) findViewById(R.id.person_message_userName);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		myinfo_img.setOnClickListener(this);
		zhangHuManager.setOnClickListener(this);
		changePhoneNumber.setOnClickListener(this);
		changePassword.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.my_garden_touxiang:
			// ChoosePicUtil.showCustomAlertDialog(this);
			break;
		case R.id.rel_person_zhanghu_message:
			Intent changeZhangHu = new Intent();
			changeZhangHu.setClass(UserMyMessageActivity.this,
					UserStudentMessageActivity.class);
			startActivity(changeZhangHu);
			break;
		case R.id.rel_person_change_phone:
			Intent changePhone = new Intent();
			changePhone.setClass(UserMyMessageActivity.this,
					UserPhoneActivity.class);
			startActivityForResult(changePhone, 1002);
			break;
		case R.id.rel_person_change_password:
			Intent changePassword = new Intent();
			changePassword.setClass(UserMyMessageActivity.this,
					UserPasswordActivity.class);
			startActivity(changePassword);

			break;

		default:
			break;
		}

	}

	private void cameraCamera(Intent data) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = formatter.format(System.currentTimeMillis()) + ".png";
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");
		FileOutputStream b = null;
		String path = Environment.getExternalStorageDirectory().getPath();
		File file = new File(path + "/myImage/");
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		String fileName = file.getPath() + "/" + name;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (b == null)
					return;
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imagepath = fileName;
		myinfo_img.setImageBitmap(bitmap);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.SYS_INTENT_REQUEST
				&& resultCode == RESULT_OK && data != null) {
			try {
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,
						null, null);
				String imageFilePath;
				if (cursor != null) {
					cursor.moveToFirst();
					imageFilePath = cursor.getString(1);
					FileInputStream fis = new FileInputStream(imageFilePath);
					bitmap = BitmapFactory.decodeStream(fis);

					imagepath = imageFilePath;
					myinfo_img.setImageBitmap(bitmap);

					cursor.close();
					fis.close();

				} else {
					imageFilePath = uri.getPath();
					FileInputStream fis = new FileInputStream(imageFilePath);
					bitmap = BitmapFactory.decodeStream(fis);

					imagepath = imageFilePath;
					myinfo_img.setImageBitmap(bitmap);

					fis.close();

				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (requestCode == Constants.CAMERA_INTENT_REQUEST
				&& resultCode == RESULT_OK && data != null) {
			cameraCamera(data);
		} else if (requestCode == 1002) {
			Bundle userPhone = data.getExtras();
			if (userPhone != null) {
				personPhone.setText(userPhone.getString("userPhone"));
			} else {
				personPhone.setText(SharedPreference.strUserPhone);
			}
		} else if (1 == requestCode && RESULT_OK != resultCode) {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

}
