package com.yfd.appTest.Activity;

/**
 * 功能：主程序入口页面
 * @author YinGuiChun
 * @date 2016-07-15
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager.ImageCycleViewListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.stevenhu.android.phone.bean.ADInfo;
import com.stevenhu.android.phone.utils.ViewFactory;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.PriceBeans;
import com.yfd.appTest.CustomView.CustomDialogTishi;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.EmptyUtil;
import com.yfd.appTest.Utils.MD5Util;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.softupdate.DownloadService;
import com.yfd.appTest.softupdate.ParsingXMLElements;
import com.yfd.appTest.widget.SharedPreference;

public class MainActivity extends BaseActivity implements OnClickListener {
	private long exitTime = 0;
	Button seachbtn;
	TextView price;
	RelativeLayout phoneNumkk;
	RelativeLayout perssionnal;
	RelativeLayout phoneNumlhkk;
	RelativeLayout search;
	RelativeLayout down;
	RelativeLayout hfcz;
	private RelativeLayout llCZ;
	private RelativeLayout more;
	ImageView hd;
	private RelativeLayout shouhou;
	RelativeLayout mainbottom;
	View view1, view2;
	LinearLayout maint;
	PriceBeans pricebeans;
	Context con;
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;

	private String[] imageUrls = {
			"http://222.73.22.122:8083/hyfdsta/banner/banner_img1.png",
			"http://222.73.22.122:8083/hyfdsta/banner/banner_img2.png",
			"http://222.73.22.122:8083/hyfdsta/banner/banner_img3.png" };
	private String[] webUrls = {
			"http://222.73.22.122:8083/hyfdsta/banner/banner_action1.html",
			"http://222.73.22.122:8083/hyfdsta/banner/banner_action2.html",
			"http://222.73.22.122:8083/hyfdsta/banner/banner_action3.html" };

	private String pastVersion;
	private String serverUrl = BaseApplication.updateFile;
	private String xmlName = BaseApplication.xmlName;
	private Map<String, String> versionInfo;
	private int currentVersionCode;
	/** 获得需要上传照片数量 */
	private String pic_number = "1";
	/** 是否必须升级，0表示不是必须升级 */
	private String must_upgrade = "0";

	public enum versionInfoField {
		filename, filetype, version, description
	}

	private Handler handMessage = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				CustomDialogTishi.Builder builder = new CustomDialogTishi.Builder(
						con);
				builder.setMessage(BaseApplication.versioninfo);
				builder.setTitle("更新提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								startupdate();
								dialog.dismiss();
							}
						});

				builder.setNegativeButton("稍后再说",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(getApplicationContext(),
										"为正常使用软件，请及时更新", Toast.LENGTH_SHORT)
										.show();
								if (SharedPreference.must_upgrade != null
										&& SharedPreference.must_upgrade
												.equals("0")) {
									dialog.dismiss();
								}

							}
						});

				builder.create().show();
				break;
			// case 1:
			// // Toast.makeText(getApplicationContext(), "已是�????新版�????",
			// // Toast.LENGTH_SHORT).show();
			// break;
			//
			// case -1:
			// // Toast.makeText(getApplicationContext(), "未检测到更新",
			// // Toast.LENGTH_SHORT).show();
			// break;
			//
			// case 5:
			//
			// new Thread() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			//
			// if (isNewVersion() == 0) {
			//
			// handMessage.sendEmptyMessage(0);
			//
			// }
			// if (isNewVersion() == 1) {
			//
			// handMessage.sendEmptyMessage(1);
			//
			// }
			// if (isNewVersion() == -1) {
			//
			// handMessage.sendEmptyMessage(-1);
			//
			// }
			// }
			// }.start();
			//
			// break;

			}

			// handler.sendEmptyMessage(2);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {
				System.out.println(msg.obj.toString());
				pricebeans = (PriceBeans) Utils.jsonToBean(msg.obj.toString(),
						PriceBeans.class);
				if (pricebeans.getCode().equals("0")) {
					price.setText("我的     余额:" + pricebeans.getBalance());
				} else {
					price.setText(pricebeans.getMessage());
				}

			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseApplication.listacty.add(this);
		setContentView(R.layout.activity_main);

		con = this;
		configImageLoader();
		maint = (LinearLayout) findViewById(R.id.main_kkl);
		view1 = (View) findViewById(R.id.leftv1);
		view2 = (View) findViewById(R.id.leftv2);
		perssionnal = (RelativeLayout) findViewById(R.id.main_toperssion);
		phoneNumkk = (RelativeLayout) findViewById(R.id.main_menu_kk);
		phoneNumlhkk = (RelativeLayout) findViewById(R.id.main_menu_lhkk);
		search = (RelativeLayout) findViewById(R.id.main_search);
		hfcz = (RelativeLayout) findViewById(R.id.main_hfcz);
		llCZ = (RelativeLayout) findViewById(R.id.main_LLcz);
		down = (RelativeLayout) findViewById(R.id.down);
		more = (RelativeLayout) findViewById(R.id.main_more);
		hd = (ImageView) findViewById(R.id.main_tohd);
		shouhou = (RelativeLayout) findViewById(R.id.main_shouhou);
		price = (TextView) findViewById(R.id.main_lastprice);
		mainbottom = (RelativeLayout) findViewById(R.id.main_bottom);

		if (BaseApplication.loginbeans.getType().equals("0")) {

			// mainbottom.setVisibility(View.VISIBLE);
			phoneNumkk.setVisibility(View.GONE);
			phoneNumlhkk.setVisibility(View.GONE);
			view1.setVisibility(View.INVISIBLE);
			view2.setVisibility(View.INVISIBLE);
			maint.setVisibility(View.GONE);
			shouhou.setVisibility(View.GONE);
			hfcz.setVisibility(View.VISIBLE);
			llCZ.setVisibility(View.VISIBLE);

		} 
//		else {
			// hfcz.setVisibility(View.GONE);
			// llCZ.setVisibility(View.GONE);
			// //mainbottom.setVisibility(View.INVISIBLE);
			// phoneNumkk.setVisibility(View.VISIBLE);
			// phoneNumlhkk.setVisibility(View.VISIBLE);
			// view1.setVisibility(View.GONE);
			// view2.setVisibility(View.GONE);
			// maint.setVisibility(View.VISIBLE);
			// shouhou.setVisibility(View.VISIBLE);
//		}
		initialize();
		seachbtn = (Button) findViewById(R.id.main_s);
		down.setOnClickListener(this);
		seachbtn.setOnClickListener(this);
		perssionnal.setOnClickListener(this);
		phoneNumkk.setOnClickListener(this);
		phoneNumlhkk.setOnClickListener(this);
		search.setOnClickListener(this);
		hfcz.setOnClickListener(this);
		llCZ.setOnClickListener(this);
		more.setOnClickListener(this);
		hd.setOnClickListener(this);
		shouhou.setOnClickListener(this);

		if (BaseApplication.loginbeans != null
				&& BaseApplication.loginbeans.getMsg() != null
				&& EmptyUtil.isEmpty(BaseApplication.loginbeans.getMsg()
						.getUsMobile())) {
			Intent intent = new Intent(MainActivity.this,
					AddPhoneActivity.class);
			startActivity(intent);

		}

		PackageManager manager = MainActivity.this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(
					MainActivity.this.getPackageName(), 0);
			String appVersion = info.versionName;
			currentVersionCode = info.versionCode;
			System.out.println(currentVersionCode + " " + appVersion);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
		}

		// handMessage.sendEmptyMessage(5);

		new Thread() {
			@Override
			public void run() {
				// 这里写入子线程需要做的工作
				if (isNewVersion() == 0) {
					handMessage.sendEmptyMessage(0);
				}
				SharedPreference.getUserInfo(MainActivity.this);
				System.out.println("pic_num:" + SharedPreference.picNumber);
			}
		}.start();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.main_menu_kk:
			SharedPreference.is_puhao = true;
			Intent intentpk = new Intent(MainActivity.this, PhkkActivity.class);
			// intentpk.putExtra("kk", "p");
			startActivity(intentpk);
			break;
		case R.id.main_menu_lhkk:
			SharedPreference.is_puhao = false;
			Intent intentlk = new Intent(MainActivity.this, PhkkActivity.class);
			// intentlk.putExtra("kk", "l");
			startActivity(intentlk);
			break;
		case R.id.main_toperssion:
			Intent intent = new Intent(MainActivity.this,
					PerssionnalActivity.class);
			startActivity(intent);
			break;
		case R.id.main_search:
			Intent intents = new Intent(MainActivity.this, SearchActivity.class);
			startActivity(intents);
			break;
		case R.id.main_s:
			Intent intentsb = new Intent(MainActivity.this,
					SearchActivity.class);
			startActivity(intentsb);
			break;
		case R.id.main_hfcz:
			Intent intenthf = new Intent(MainActivity.this, HfczActivity.class);
			startActivity(intenthf);
			break;

		case R.id.main_LLcz:
			Intent intentLL = new Intent(MainActivity.this, LlczActivity.class);
			startActivity(intentLL);
			break;
		case R.id.down:
			Intent intentd = new Intent(MainActivity.this,
					downloadActivity.class);
			startActivity(intentd);
			break;
		case R.id.main_tohd:

			break;

		case R.id.main_shouhou:
			Toast.makeText(con, "此功能正在建设中...", 1000).show();
			// Intent intentsh=new Intent(MainActivity.this,
			// InternetRechargeActivity.class);
			// startActivity(intentsh);

			break;
		case R.id.main_more:
			Intent intentcus = new Intent(MainActivity.this,
					ContactusActivity.class);
			startActivity(intentcus);
			break;

		default:
			break;
		}
	}

	protected void startupdate() {
		// TODO Auto-generated method stub
		Intent it = new Intent(MainActivity.this, DownloadService.class);
		startService(it);
	}

	public int isNewVersion() {
		try {

			versionInfo = getXMLElements(serverUrl + xmlName);
			pastVersion = getPackageManager().getPackageInfo(getPackageName(),
					0).versionName;
			if (versionInfo != null) {
				BaseApplication.versioninfo = versionInfo.get("description");
				pic_number = versionInfo.get("photo_count");
				must_upgrade = versionInfo.get("must_upgrade");
				saveUserInfo();
//				System.out.println("pic_num:" + pic_number + "--must_upgrade--"
//						+ must_upgrade);
			}
//			System.out.println("versionInfo" + versionInfo
//					+ "---versionInfo.get---" + versionInfo.get("version")
//					+ "--pastVersion-" + pastVersion);
//			System.out.println("versionInfo.get:"
//					+ versionInfo.get(versionInfoField.version.toString()));

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null == versionInfo
				|| null == versionInfo.get("version")
				|| null == pastVersion
				|| pastVersion.equals(versionInfo.get(versionInfoField.version
						.toString())) ? 1 : 0;
	}

	public Map<String, String> getVersionInfo() {
		return versionInfo;
	}

	public Map<String, String> getXMLElements(String resourceUrl)
			throws MalformedURLException, IOException, Exception {

		URL url = new URL(BaseApplication.updateFile + xmlName);
		InputSource is = new InputSource(url.openStream());
		is.setEncoding("UTF-8");

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		ParsingXMLElements handler = new ParsingXMLElements();
		saxParser.parse(is, handler);
		return handler.getElement();
	}

	/**
	 * 保存登陆用户返回数据
	 */
	private void saveUserInfo() {
		SharedPreferences pref = getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("PIC_NUMBER", pic_number);
		editor.putString("MUST_UPGRADE", must_upgrade);
		editor.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (BaseApplication.loginbeansc != null) {

			AnsyPost.Czpost(
					BaseApplication.GET_PRICE
							+ "?terminalName="
							+ BaseApplication.loginbeansc.getMsg()
									.getUsername()
							+ "&signature="
							+ MD5Util.string2MD5("terminalName="
									+ BaseApplication.loginbeansc.getMsg()
											.getUsername()), handler);

		}

	}

	@SuppressLint("NewApi")
	private void initialize() {

		cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);

		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i);
			infos.add(info);
		}

		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1)
				.getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
		}
		// 将第�?????个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

		// 设置循环，在调用setData方法前调�?????
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置轮播时间，默�?????5000ms
		cycleViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Intent intenthd = new Intent(MainActivity.this,
						HDongActivity.class);
				intenthd.putExtra("url", webUrls[position]);
				startActivity(intenthd);
			}

		}

	};

	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图�?????
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图�?????
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存�?????
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图�?????
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次,退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
