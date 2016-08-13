package com.yfd.appTest.Activity;

/**
 * 功能：靓号开卡页面
 * @author YinGuiChun
 * @date 2016-07-14
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.senter.helper.ConsantHelper;
import cn.com.senter.helper.ShareReferenceSaver;
import cn.com.senter.model.IdentityCardZ;
import cn.com.senter.sdkdefault.helper.Error;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.Czbackbeans;
import com.yfd.appTest.Beans.FormFile;
import com.yfd.appTest.Beans.SaoMiaobckBeans;
import com.yfd.appTest.CustomView.SelectPicPopupWindow;
import com.yfd.appTest.Utils.BlueReaderHelper;
import com.yfd.appTest.Utils.CheckUtil;
import com.yfd.appTest.Utils.EmptyUtil;
import com.yfd.appTest.Utils.UploadFileTask;
import com.yfd.appTest.Utils.UploadMore;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.widget.SharedPreference;

public class PhkkActivity extends BaseActivity implements OnClickListener {

	// ----蓝牙功能有关的变量----
	private final static String SERVER_KEY1 = "CN.COM.SENTER.SERVER_KEY1";
	private final static String PORT_KEY1 = "CN.COM.SENTER.PORT_KEY1";
	private final static String BLUE_ADDRESSKEY = "CN.COM.SENTER.BLUEADDRESS";
	private final static String KEYNM = "CN.COM.SENTER.KEY";
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private BluetoothAdapter mBluetoothAdapter = null; // /蓝牙适配器
	private BlueReaderHelper mBlueReaderHelper;
	private int iselectNowtype = 0;
	private String Blueaddress = null;
	private boolean bSelServer = false;
	private boolean isbule;
	private boolean isNFC;
	private boolean openblueok;
	private PowerManager.WakeLock wakeLock = null;
	String et_mmsnumber = "";
	String et_imsinumber = "";
	private String server_address = "senter-online.cn";
	private int server_port = 10002;
	private ImageView photoView;

	// 图片上传相关
	String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
	String PREFIX = "--", LINE_END = "\r\n";
	String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	private ImageView phkk_back;
	ImageView sfz, sff, sfap, saomiao;
	SelectPicPopupWindow menuWindow;
	String path1, path2, path3, path4;
	EditText name, sim, phone, sfnum, adress, puk;
	private Button yys, save;
	Activity acty;
	FormFile[] formfile;
	Map<String, String> params;
	private SaoMiaobckBeans loginbeansmsg;
	Czbackbeans czbackbeans;
	TextView title;
	String k;
	List<String> listyys = new ArrayList<String>();
	LinearLayout puklay;
	ImageView saomiaosim;
	Button sbsaomiao;
	Context con;

	public static Handler uiHandler;
	TextView blueshow;
	FormFile file2, file3, file4;
	private Uri mUri;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if (msg.obj != null) {

				Toast.makeText(getApplicationContext(), msg.toString(), 1000)
						.show();

			}

		};

	};
	private Handler handlersave = new Handler() {

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if (msg.obj != null) {

				loginbeansmsg = (SaoMiaobckBeans) Utils.jsonToBean(
						msg.obj.toString(), SaoMiaobckBeans.class);
				Toast.makeText(getApplicationContext(), loginbeansmsg.getMsg(),
						1000).show();
				if (loginbeansmsg.getState().equals("1")) {
					Intent intent = new Intent(PhkkActivity.this,
							PhkkActivityto.class);
					// Intent intenget = getIntent();
					// k = intenget.getStringExtra("kk");
					// if (k.equals("l")) {
					// intent.putExtra("kk", "l");
					// } else {
					// intent.putExtra("kk", "p");
					// }
					// intent.putExtra("kk", "2");

					startActivity(intent);
					finish();

				}
				System.out.println(msg.obj.toString());

			}

		};

	};

	private Handler handlers = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if (msg.obj != null) {
				loginbeansmsg = (SaoMiaobckBeans) Utils.jsonToBean(
						msg.obj.toString(), SaoMiaobckBeans.class);
				if (Utils.isEmpty(loginbeansmsg.getMsg())) {

					Toast.makeText(getApplicationContext(), "识别失败，请重试~", 1000)
							.show();
				} else {
					name.setText(loginbeansmsg.getMsg().split("<br><br>")[1]);
					sfnum.setText(loginbeansmsg.getMsg().split("<br><br>")[0]);
					adress.setText(loginbeansmsg.getMsg().split("<br><br>")[8]);
				}

			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phkk);
		con = this;
		// 蓝牙读卡
		uiHandler = new MyHandler(this);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		isbule = false;
		openblueok = false;
		mBlueReaderHelper = new BlueReaderHelper(con, uiHandler);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Blueaddress = ShareReferenceSaver.getData(this, BLUE_ADDRESSKEY);
		initShareReference();
		// 初始化ui
		listyys.add("远特");
		listyys.add("国美");
		listyys.add("迪信通");
		listyys.add("蜗牛");
		listyys.add("186联通");

		phkk_back = (ImageView) findViewById(R.id.phkk_back);
		title = (TextView) findViewById(R.id.kk_title);
		puklay = (LinearLayout) findViewById(R.id.puk_lay);
		saomiaosim = (ImageView) findViewById(R.id.saomiao_sim);
		sbsaomiao = (Button) findViewById(R.id.sbsaomiao);
		blueshow = (TextView) findViewById(R.id.blueshow);
		photoView = (ImageView) findViewById(R.id.photoimage);

		// Intent inten = getIntent();
		// k = inten.getStringExtra("kk");
		//
		// if (k.equals("l")) {
		// title.setText("靓号开卡");
		// } else {
		// title.setText("普号开卡");
		// }
		if (!SharedPreference.is_puhao) {
			title.setText("靓号开卡");
		} else {
			title.setText("普号开卡");
		}

		sfz = (ImageView) findViewById(R.id.sf_zheng);
		sff = (ImageView) findViewById(R.id.sf_fan);
		sfap = (ImageView) findViewById(R.id.sf_pas);
		phone = (EditText) findViewById(R.id.phkk_phone);
		sim = (EditText) findViewById(R.id.phkk_sim);
		name = (EditText) findViewById(R.id.phkk_name);
		sfnum = (EditText) findViewById(R.id.phkk_sfnum);
		adress = (EditText) findViewById(R.id.phkk_adress);
		puk = (EditText) findViewById(R.id.phkk_puk);
		yys = (Button) findViewById(R.id.phkk_choseyys);
		save = (Button) findViewById(R.id.phkk_save);
		saomiao = (ImageView) findViewById(R.id.saomiao);
		saomiao.setOnClickListener(this);
		sbsaomiao.setOnClickListener(this);
		sfz.setOnClickListener(this);
		sff.setOnClickListener(this);
		sfap.setOnClickListener(this);
		yys.setOnClickListener(this);
		yys.setOnClickListener(this);
		save.setOnClickListener(this);
		saomiaosim.setOnClickListener(this);

		phkk_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		yys.setText(listyys.get(0));

		yys.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (yys.getText().toString().equals("国美")) {
					puklay.setVisibility(View.VISIBLE);
				} else {
					puklay.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		SharedPreference.getUserInfo(PhkkActivity.this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {

		case R.id.sbsaomiao:

			blueshow.setText("正在连接设备...");
			isbule = true;
			iselectNowtype = 3;

			blueshow.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					readCardBlueTooth();
				}
			}, 1000);

			break;

		case R.id.saomiao_sim:

			Intent saomaintent = new Intent(PhkkActivity.this,
					MipcaActivityCapture.class);
			startActivity(saomaintent);

			break;

		case R.id.phkk_choseyys:
			initPopWindow(listyys, yys);
			break;

		case R.id.phkk_save:
			BaseApplication.isMainPic=true;

			save();
			break;

		case R.id.sf_zheng:
			BaseApplication.FromImg = 2;
			menuWindow = new SelectPicPopupWindow(PhkkActivity.this,
					itemsOnClick);
			// 显示窗口
			menuWindow.showAtLocation(
					PhkkActivity.this.findViewById(R.id.sf_zheng),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		case R.id.sf_fan:
			BaseApplication.FromImg = 3;
			menuWindow = new SelectPicPopupWindow(PhkkActivity.this,
					itemsOnClick);
			// 显示窗口
			menuWindow.showAtLocation(
					PhkkActivity.this.findViewById(R.id.sf_fan), Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		case R.id.sf_pas:
			BaseApplication.FromImg = 4;
			menuWindow = new SelectPicPopupWindow(PhkkActivity.this,
					itemsOnClick);
			// 显示窗口
			menuWindow.showAtLocation(
					PhkkActivity.this.findViewById(R.id.sf_pas), Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			break;
		case R.id.saomiao:

			// AnsyPost.Saomiao(BaseApplication.SAOMIAO_URL+"?ocra1=http://image.jdol.com.cn/MemberCert/img/201203/efcc182e732343a79b747aff388e74db.jpg",
			// handler);

			BaseApplication.FromImg = 1;
			menuWindow = new SelectPicPopupWindow(PhkkActivity.this,
					itemsOnClick);
			menuWindow.showAtLocation(
					PhkkActivity.this.findViewById(R.id.saomiao),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			// 显示窗口

			break;
		default:
			break;
		}

	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo:

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				if (BaseApplication.FromImg == 1) {
					File appDir = new File("/sdcard/myImage/");
					if (!appDir.exists()) {
						appDir.mkdir();
					}
					mUri = Uri.fromFile(new File("/sdcard/myImage/", "kengDiePic"+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
					intent.putExtra(
							android.provider.MediaStore.EXTRA_OUTPUT, mUri);
					startActivityForResult(intent, 5);
				}
				if (BaseApplication.FromImg == 2) {
					Uri imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, 6);
				}
				if (BaseApplication.FromImg == 3) {
					Uri imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, 7);
				}
				if (BaseApplication.FromImg == 4) {
					Uri imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "image.jpg"));
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, 8);
				}

				break;
			case R.id.btn_pick_photo:
				// takePhoto = false;
				// /** 创建一个Intent对象 */
				// Intent picSelect = new Intent();
				// /** 设置Intent动作 */
				// picSelect.setAction(Intent.ACTION_PICK);
				// /** 设置Intent类型，设置为图片类型，这样就可以识别是启动的哪个Activity */
				// picSelect.setType("image/*");
				// /** 启动Activity */
				// startActivityForResult(picSelect, 1);

				startActivityForResult(new Intent(PhkkActivity.this,
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

		case 101:
			if (resultCode == 101) {
				Blueaddress = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				if (!Blueaddress
						.matches("([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])")) {

					Toast.makeText(
							con,
							"address:" + Blueaddress + " is wrong, length = "
									+ Blueaddress.length(), 1000).show();
					// tv_info.setText("address:" + Blueaddress +
					// " is wrong, length = " + Blueaddress.length());
					return;
				}

				ShareReferenceSaver.saveData(PhkkActivity.this,
						BLUE_ADDRESSKEY, Blueaddress);

			}
			break;

		case 1:

			if (Utils.isNetworkAvailable((Activity) con)) {

				showloading("");
				path1 = data.getExtras().getString("path1");
				System.out.println("path1:" + path1);
				// Toast.makeText(getApplicationContext(), cpath1, 1000).show();

				saomiao.setImageBitmap(Utils.getLoacalBitmap(path1)); // 设置Bitmap

				UploadFileTask uploadFileTask = new UploadFileTask(this,
						handlers);
				uploadFileTask.execute(path1);
			} else {

				Toast.makeText(getApplicationContext(), "网络连接异常，请重试~", 1000)
						.show();
			}

			break;

		case 2:
			path2 = data.getExtras().getString("path1");
			System.out.println("bendi_path2:" + path2);
			// Toast.makeText(getApplicationContext(), cpath1, 1000).show();

			sfz.setImageBitmap(Utils.getLoacalBitmap(path2)); // 设置Bitmap

			break;

		case 3:
			path3 = data.getExtras().getString("path1");
			// Toast.makeText(getApplicationContext(), cpath1, 1000).show();

			sff.setImageBitmap(Utils.getLoacalBitmap(path3)); // 设置Bitmap

			break;
		case 4:

			path4 = data.getExtras().getString("path1");
			// Toast.makeText(getApplicationContext(), cpath1, 1000).show();

			sfap.setImageBitmap(Utils.getLoacalBitmap(path4)); // 设置Bitmap

			break;
		case Activity.RESULT_OK:
			if (BaseApplication.FromImg == 1) {
				if (Utils.isNetworkAvailable((Activity) con)) {
					showloading("");
					path1 = setImage(saomiao, data);

					UploadFileTask uploadFileTasks = new UploadFileTask(
							(Activity) con, handlers);
					uploadFileTasks.execute(path1);
				} else {

					Toast.makeText(getApplicationContext(), "网络连接异常，请重试~", 1000)
							.show();
				}
			}
			if (BaseApplication.FromImg == 2) {

				path2 = setImage(sfz, data);
				System.out.println("paizhao_path2:" + path2);

			}
			if (BaseApplication.FromImg == 3) {

				path3 = setImage(sff, data);

			}

			if (BaseApplication.FromImg == 4) {

				path4 = setImage(sfap, data);

			}
			break;

		default:
			break;
		}

	}

	public void save() {

		if (Utils.isEmpty(phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "手机号 不能为空", 1000).show();
			return;
		}
		if (!CheckUtil.isMobileNumber(phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "请输入正确手机号", 1000).show();
			return;
		}
		if (Utils.isEmpty(sim.getText().toString())) {
			Toast.makeText(getApplicationContext(), "sim卡号不能为空", 1000).show();
			return;
		}

		// if (sim.getText().toString().length() != 19) {
		// Toast.makeText(getApplicationContext(), "请输入正确卡号信息", 1000).show();
		// return;
		// }

		if (Utils.isEmpty(yys.getText().toString())) {
			Toast.makeText(getApplicationContext(), "运营商未选择", 1000).show();
			return;
		}
		if (Utils.isEmpty(puk.getText().toString())
				&& yys.getText().toString().equals("国美")) {
			Toast.makeText(getApplicationContext(), "PUK未填写", 1000).show();
			return;
		}
		if (Utils.isEmpty(name.getText().toString())) {
			Toast.makeText(getApplicationContext(), "姓名不能为空", 1000).show();
			return;
		}
		if (Utils.isEmpty(sfnum.getText().toString())) {
			Toast.makeText(getApplicationContext(), "身份证号不能为空", 1000).show();
			return;
		}
		if (sfnum.getText().toString().length() != 18) {
			Toast.makeText(getApplicationContext(), "请输入正确身份证号码", 1000).show();
			return;
		}
		if (Utils.isEmpty(adress.getText().toString())) {
			Toast.makeText(getApplicationContext(), "地址不能为空", 1000).show();
			return;
		}

		if (SharedPreference.picNumber == null) {
			SharedPreference.picNumber = "3";
		}

		/** 照片数量是三的情况 */
		if (SharedPreference.picNumber != null
				&& SharedPreference.picNumber.endsWith("3")) {
			if (Utils.isEmpty(path2)) {
				Toast.makeText(getApplicationContext(), "请添加身份证正面照片", 1000)
						.show();
				return;
			}
			if (Utils.isEmpty(path3)) {
				Toast.makeText(getApplicationContext(), "请添加身份证反面照片", 1000)
						.show();
				return;
			}
			if (Utils.isEmpty(path4)) {
				Toast.makeText(getApplicationContext(), "请添加手持身份证照片", 1000)
						.show();
				return;
			}

		}

		/** 照片数量是二的情况 */
		if (SharedPreference.picNumber != null
				&& SharedPreference.picNumber.endsWith("2")) {
			if (Utils.isEmpty(path2)) {
				Toast.makeText(getApplicationContext(), "请添加身份证正面照片", 1000)
						.show();
				return;
			}
			if (Utils.isEmpty(path3)) {
				Toast.makeText(getApplicationContext(), "请添加身份证反面照片", 1000)
						.show();
				return;
			}

		}
		/** 照片数量是一的情况 */
		if (SharedPreference.picNumber != null
				&& SharedPreference.picNumber.endsWith("1")) {
			if (Utils.isEmpty(path2)) {
				Toast.makeText(getApplicationContext(), "请添加身份证正面照片", 1000)
						.show();
				return;
			}

		}
		/** 照片数量是零的情况不需要考虑 */

		formfile = new FormFile[3];

		if (EmptyUtil.isNotEmpty(path2)) {
			file2 = new FormFile(Utils.getlocalSysDatetime(), new File(path2),
					"miImgA1", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		}
		if (EmptyUtil.isNotEmpty(path3)) {
			file3 = new FormFile(Utils.getlocalSysDatetime(), new File(path3),
					"miImgA2", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		}

		if (EmptyUtil.isNotEmpty(path4)) {
			file4 = new FormFile(Utils.getlocalSysDatetime(), new File(path4),
					"miImgA3", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		}

		formfile[0] = file2;
		formfile[1] = file3;
		formfile[2] = file4;

		params = new HashMap<String, String>();
		params.put("miPhone", phone.getText().toString());
		params.put("miSim", sim.getText().toString());
		params.put("carrierOp", yys.getText().toString());
		params.put("miUseName", name.getText().toString());
		params.put("miUseScard", sfnum.getText().toString());
		params.put("miUseAddr", adress.getText().toString());
		params.put("miPuk", puk.getText().toString());
		showloading("");

		Runnable networkTask = new Runnable() {

			@Override
			public void run() {
				// TODO
				// 在这里进行 http request.网络请求相关操作
				try {

					UploadMore.post(BaseApplication.Phkk_URL, params, formfile,
							handlersave);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		if (Utils.isNetworkAvailable((Activity) con)) {
			new Thread(networkTask).start();
		} else {
			Toast.makeText(getApplicationContext(), "网络连接异常，请重试~", 1000).show();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (BaseApplication.Saoma != null) {

			sim.setText(BaseApplication.Saoma);
			BaseApplication.Saoma = null;
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

		
		
		if (BaseApplication.FromImg == 1) {
			// 获取相机返回的数据，并转换为Bitmap图片格式
//			Bundle bundle = data.getExtras();
//			bitmap = (Bitmap) bundle.get("data");
			
			ContentResolver cr = this.getContentResolver();
			try {
				if (bitmap != null)// 如果不释放的话，不断取图片，将会内存不够
					bitmap.recycle();
				bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(mUri));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		} else {
			bitmap = BitmapFactory.decodeFile(Environment
					.getExternalStorageDirectory() + "/image.jpg");
		}

		try {
			b = new FileOutputStream(fileName);
			if (BaseApplication.FromImg == 1) {

				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);// 把数据写入文件

			} else {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, b);// 把数据写入文件

			}

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

	class MyHandler extends Handler {
		private PhkkActivity activity;

		MyHandler(PhkkActivity activity) {
			this.activity = activity;
		}

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case ConsantHelper.READ_CARD_SUCCESS:
				blueshow.setText("读卡成功");
				break;

			case ConsantHelper.SERVER_CANNOT_CONNECT:
				blueshow.setText("服务器连接失败! 请检查网络。");
				break;

			case ConsantHelper.READ_CARD_FAILED:
				blueshow.setText("无法读取信息请重试!");
				break;

			case ConsantHelper.READ_CARD_WARNING:
				String str = (String) msg.obj;

				if (str.indexOf("card") > -1) {
					blueshow.setText("读卡失败: 卡片丢失,或读取错误!");
				} else {
					String[] datas = str.split(":");
					blueshow.setText("网络超时 错误码: "
							+ Integer.toHexString(new Integer(datas[1])));
				}
				// .setText("请移动卡片在合适位置!");

				break;

			case ConsantHelper.READ_CARD_PROGRESS:

				int progress_value = (Integer) msg.obj;
				// Log.e("main", String.format("progress_value = %d",
				// progress_value));
				blueshow.setText("正在读卡......,进度：" + progress_value + "%");

				break;

			case ConsantHelper.READ_CARD_START:

				blueshow.setText("开始读卡,请将卡片放到设备识别区...");

				break;
			case Error.ERR_CONNECT_SUCCESS:
				String devname = (String) msg.obj;

				blueshow.setText("设备：" + devname + "连接成功!请确认蓝牙已经开启，再读卡! ");

				// mtv_info1.setText("成功:" + String.format("%d", totalcount) +
				// " 失败:" + String.format("%d", failecount));
				break;
			case Error.ERR_CONNECT_FAILD:
				String devname1 = (String) msg.obj;
				blueshow.setText("设备：" + devname1 + "连接成功!请确认蓝牙已经开启，再读卡!");
				// mtv_info1.setText("成功:" + String.format("%d", totalcount) +
				// " 失败:" + String.format("%d", failecount));
				break;
			case Error.ERR_CLOSE_SUCCESS:
				blueshow.setText((String) msg.obj + "断开连接成功");
				break;
			case Error.ERR_CLOSE_FAILD:
				blueshow.setText((String) msg.obj + "断开连接失败");
				break;
			case Error.RC_SUCCESS:
				String devname12 = (String) msg.obj;
				blueshow.setText("设备：" + devname12 + "连接成功!");
				break;

			}
		}

	}

	/**
	 * 蓝牙读卡方式
	 */
	private class BlueReadTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPostExecute(String strCardInfo) {

			sbsaomiao.setEnabled(true);

			if (TextUtils.isEmpty(strCardInfo)) {
				uiHandler.sendEmptyMessage(ConsantHelper.READ_CARD_FAILED);

				return;
			}

			if (strCardInfo.length() <= 2) {
				readCardFailed(strCardInfo);
				return;
			}

			ObjectMapper objectMapper = new ObjectMapper();
			IdentityCardZ mIdentityCardZ = new IdentityCardZ();

			try {
				mIdentityCardZ = (IdentityCardZ) objectMapper.readValue(
						strCardInfo, IdentityCardZ.class);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(ConsantHelper.STAGE_LOG, "mIdentityCardZ failed");

				return;
			}

			readCardSuccess(mIdentityCardZ);

			super.onPostExecute(strCardInfo);

		}

		@Override
		protected String doInBackground(Void... params) {

			String strCardInfo = mBlueReaderHelper.read();

			return strCardInfo;
		}
	};

	/**
	 * 蓝牙读卡方式
	 */
	protected void readCardBlueTooth() {

		if (Blueaddress == null) {
			blueshow.setText("请选择蓝牙设备，再读卡!");
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return;
		}

		if (Blueaddress.length() <= 0) {
			blueshow.setText("请选择蓝牙设备，再读卡!");
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return;
		}

		if (mBlueReaderHelper.openBlueConnect(Blueaddress) == true) {

			sbsaomiao.setEnabled(false);
			new BlueReadTask().executeOnExecutor(Executors
					.newCachedThreadPool());

		} else {
			Log.e("", "close ok");
			blueshow.setText("请确认蓝牙或设备已经开启，再读卡!");
		}
	}

	private void readCardSuccess(IdentityCardZ identityCard) {

		if (identityCard != null) {
			name.setText(identityCard.name);
			sfnum.setText(identityCard.cardNo);
			adress.setText(identityCard.address);
		}

		blueshow.setText("读卡成功");
		Log.e(ConsantHelper.STAGE_LOG, "读卡成功!");
		// totalcount++;
		// mtv_info1.setText("成功:" + String.format("%d", totalcount) + " 失败:" +
		// String.format("%d", failecount));

	}

	private void readCardFailed(String strcardinfo) {
		int bret = Integer.parseInt(strcardinfo);
		switch (bret) {
		case -1:

			blueshow.setText("服务器连接失败!");
			break;
		case 1:

			blueshow.setText("读卡失败!");
			break;
		case 2:

			blueshow.setText("读卡失败!");
			break;
		case 3:

			Toast.makeText(con, "网络超时!", 1000).show();
			blueshow.setText("服务器连接失败!");
			break;
		case 4:
			blueshow.setText("读卡失败!");
			break;
		case -2:

			blueshow.setText("读卡失败!");
			break;
		case 5:

			blueshow.setText("照片解码失败!");
			break;
		}

	}

	private void initShareReference() {

		// if ( this.server_address.length() <= 0){
		// if (ShareReferenceSaver.getData(this, SERVER_KEY1).trim().length() <=
		// 0) {
		// this.server_address = "senter-online.cn";
		// } else {
		// this.server_address = ShareReferenceSaver.getData(this, SERVER_KEY1);
		// }
		// if (ShareReferenceSaver.getData(this, PORT_KEY1).trim().length() <=
		// 0) {
		// this.server_port = 10002;
		// } else {
		// this.server_port = Integer.valueOf(ShareReferenceSaver.getData(this,
		// PORT_KEY1));
		// }
		// }
		//
		// mNFCReaderHelper.setServerAddress(this.server_address);
		// mNFCReaderHelper.setServerPort(this.server_port);
		//
		// mOTGReaderHelper.setServerAddress(this.server_address);
		// mOTGReaderHelper.setServerPort(this.server_port);

		// ----实例化help类---
		mBlueReaderHelper.setServerAddress(server_address);
		mBlueReaderHelper.setServerPort(server_port);

	}

}
