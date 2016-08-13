package com.yfd.appTest.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mining.app.zxing.decoding.Util;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.FormFile;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.RegistBeans;
import com.yfd.appTest.Beans.SaoMiaobckBeans;
import com.yfd.appTest.CustomView.SelectPicPopupWindow;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.CheckUtil;
import com.yfd.appTest.Utils.EmptyUtil;
import com.yfd.appTest.Utils.UploadMore;
import com.yfd.appTest.Utils.Utils;

public class RegestActivity extends BaseActivity implements OnClickListener {

	ImageView back;
	TextView regestbtn;
	EditText uname, name, adress, psd1, psd2, input_id_card, input_user_phone;
	private LoginBeansStr lbeans;
	private ImageView sf_pas;
	SelectPicPopupWindow menuWindow;
	private String path2;
	FormFile[] formfile = null;
	Map<String, String> params;
	FormFile file2=null;

	private RegistBeans registBeans;
	private TextView tvRegistTitle;

	// 图片上传相关
	String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
	String PREFIX = "--", LINE_END = "\r\n";
	String CONTENT_TYPE = "multipart/form-data"; // 内容类型

	private String usId;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			dimissloading();

			if (msg.obj != null) {

				lbeans = (LoginBeansStr) Utils.jsonToBean(msg.obj.toString(),
						LoginBeansStr.class);

				Toast.makeText(getApplicationContext(), lbeans.getMsg(), 1000)
						.show();

			} else {

				Toast.makeText(getApplicationContext(), "请求失败，请重试~", 1000)
						.show();

			}

		};

	};

	private Handler handlersave = new Handler() {

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if (msg.obj != null) {
				registBeans = (RegistBeans) Utils.jsonToBean(
						msg.obj.toString(), RegistBeans.class);
				Toast.makeText(getApplicationContext(), registBeans.getMsg(),
						1000).show();
				if (registBeans.getState() == 1) {
					finish();

				}

			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regest);
		back = (ImageView) findViewById(R.id.rege_back);
		regestbtn = (TextView) findViewById(R.id.regestbtn);
		uname = (EditText) findViewById(R.id.regest_usname);
		name = (EditText) findViewById(R.id.regest_name);
		adress = (EditText) findViewById(R.id.regest_adress);
		psd1 = (EditText) findViewById(R.id.regest_psd);
		psd2 = (EditText) findViewById(R.id.regest_rpasd);
		input_id_card = (EditText) findViewById(R.id.input_id_card);
		input_user_phone = (EditText) findViewById(R.id.input_user_phone);
		sf_pas = (ImageView) findViewById(R.id.sf_pas);
		tvRegistTitle = (TextView) findViewById(R.id.tv_regist_title);
		regestbtn.setOnClickListener(this);
		back.setOnClickListener(this);
		sf_pas.setOnClickListener(this);
		initDate();
	}

	private void initDate() {
		tvRegistTitle.setText("用户注册");
		regestbtn.setText("注册");
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rege_back:
			finish();
			break;
		case R.id.regestbtn:
			regestAction();
			break;
		case R.id.sf_pas:
			BaseApplication.FromImg = 2;
			menuWindow = new SelectPicPopupWindow(RegestActivity.this,
					itemsOnClick);
			// 显示窗口
			menuWindow.showAtLocation(
					RegestActivity.this.findViewById(R.id.sf_pas),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		default:
			break;
		}
	}

	public void regestAction() {

		if (Utils.isEmpty(uname.getText().toString())) {

			Toast.makeText(getApplicationContext(), "输入用户名", 1000).show();
			return;

		}
		if (Utils.isEmpty(name.getText().toString())) {

			Toast.makeText(getApplicationContext(), "输入姓名", 1000).show();
			return;

		}
		// if (Utils.isEmpty(input_id_card.getText().toString())) {
		//
		// Toast.makeText(getApplicationContext(), "输入身份证号", 1000).show();
		// return;
		//
		// }
		if (!CheckUtil.isMobileNumber(input_user_phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "请输入正确手机号", 1000).show();
			return;
		}

		if (Utils.isEmpty(adress.getText().toString())) {

			Toast.makeText(getApplicationContext(), "输入地址", 1000).show();
			return;

		}
		if (Utils.isEmpty(psd1.getText().toString())) {

			Toast.makeText(getApplicationContext(), "输入密码", 1000).show();
			return;

		}
		if (Utils.isEmpty(psd2.getText().toString())) {

			Toast.makeText(getApplicationContext(), "请再次输入密码", 1000).show();
			return;

		}

		if (!psd1.getText().toString().equals(psd2.getText().toString())) {

			Toast.makeText(getApplicationContext(), "两次密码输入不一致，请检查~", 1000)
					.show();
			return;

		}
		formfile = new FormFile[1];
		if (EmptyUtil.isNotEmpty(path2)) {
			file2 = new FormFile(Utils.getlocalSysDatetime(), new File(path2),
					"miImgA1", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		} 
		formfile[0] = file2;

		params = new HashMap<String, String>();
		// if (EmptyUtil.isNotEmpty(Utils.getUname(RegestActivity.this))) {
		// params.put("usId", Utils.getUsId(RegestActivity.this));
		// params.put("usIsUpdate", "0");// 表示更新数据
		// } else {
		params.put("usId", String.valueOf(new String(new SimpleDateFormat(
				"ddHHmmss").format(new Date()))));
		params.put("usIsUpdate", "1");// 表示插入数据
		// }

		params.put("usName", uname.getText().toString());
		if (EmptyUtil.isNotEmpty(input_id_card.getText().toString())) {
			params.put("miUseScard", input_id_card.getText().toString());
		} else {
			params.put("miUseScard", "");
		}

		params.put("usPass", psd1.getText().toString());
		params.put("usCredits", "2");
		params.put("userName", name.getText().toString());
		params.put("usAddr", adress.getText().toString());
		params.put("usMobile", input_user_phone.getText().toString());

		Runnable networkTask = new Runnable() {

			@Override
			public void run() {
				try {

					UploadMore.post(BaseApplication.REGIST_DATA_URL, params,
							formfile, handlersave);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		// showloading("");
		if (Utils.isNetworkAvailable(RegestActivity.this)) {
			new Thread(networkTask).start();
		} else {
			Toast.makeText(getApplicationContext(), "网络连接异常，请重试~", 1000).show();
		}

		showloading("");
		//
		// AnsyPost.Login(BaseApplication.REGEST_URL + "?usName="
		// + uname.getText().toString() + "&idcard="
		// + input_id_card.getText().toString() + "&usPass="
		// + psd1.getText().toString() + "&usCredits=2" + "&userName="
		// + name.getText().toString() + "&usAddr="
		// + adress.getText().toString(), handler);
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (BaseApplication.FromImg == 2) {
					Uri imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, 6);
				}

				break;
			case R.id.btn_pick_photo:
				startActivityForResult(new Intent(RegestActivity.this,
						ImagechoseActivity.class), 1);
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {

		switch (resultCode) {
		case 2:
			path2 = data.getExtras().getString("path1");
			System.out.println("bendi_path2:" + path2);
			sf_pas.setImageBitmap(Utils.getLoacalBitmap(path2)); // 设置Bitmap
			break;

		case Activity.RESULT_OK:
			if (BaseApplication.FromImg == 2) {
				path2 = setImage(sf_pas, data);
				System.out.println("paizhao_path2:" + path2);
			}

			break;

		default:
			break;
		}

	}

	public String setImage(ImageView imgview, Intent data) {

		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Log.i("TestFile", "SD card is not avaiable/writeable right now.");
			return null;
		}
		String name = new DateFormat().format("yyyyMMdd_hhmmss",
				Calendar.getInstance(Locale.CHINA))
				+ ".jpg";
		Bitmap bitmap = null;
		FileOutputStream b = null;
		// 为什么不能直接保存在系统相册位置呢
		File file = new File("/sdcard/myImage/");
		if (file != null) {
			file.mkdirs();// 创建文件夹
		}
		String fileName = "/sdcard/myImage/" + name;
		// 将保存在本地的图片取出并缩小后显示在界面上

		bitmap = BitmapFactory.decodeFile(Environment
				.getExternalStorageDirectory() + "/image.jpg");

		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, b);// 把数据写入文件

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imgview.setImageBitmap(Utils.getLoacalBitmap(fileName));// 将图片显示在ImageView里

		return fileName;

	}

}
