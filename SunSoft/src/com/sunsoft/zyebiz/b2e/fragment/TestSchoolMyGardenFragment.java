package com.sunsoft.zyebiz.b2e.fragment;
//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：学校管理员用户之我的智园页面
// * @author YinGuiChun
// */
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.TestMyMessageActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserAboutZhiYuanActivity;
//import com.sunsoft.zyebiz.b2e.activity.TestSchoolAdviceActivity;
//import com.sunsoft.zyebiz.b2e.activity.TestSchoolMainActivity;
//import com.sunsoft.zyebiz.b2e.activity.TestMessageActivity;
//import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
//import com.sunsoft.zyebiz.b2e.wiget.CircularImage;
//import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
//import com.sunsoft.zyebiz.b2e.wiget.DateCleanManager;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//import com.umeng.analytics.MobclickAgent;
//
//public class SchoolMyGardenFragment extends Fragment implements OnClickListener {
//	private TextView tv_main_text;
//	private LinearLayout jiantou;
//	private RelativeLayout rel_shouhuo, rel_advice, rel_about_zhiyuan,
//			rel_phone;
//	private TextView myGardenPhone;
//
//	private CircularImage userPic;
//	private TextView userName;
//	private DisplayImageOptions options;
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//	private String userId;
//	SchoolMyGardenlListener mCallback;
//	private RelativeLayout cleanCache;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View mBaseView = inflater.inflate(
//				R.layout.my_garden, null);
//		NetManager.isHaveNetWork(TestSchoolMainActivity.mainActivity);
//		initView(mBaseView);
//		initDate(mBaseView);
//
//		return mBaseView;
//	}
//
//	public void onResume() {
//		super.onResume();
//		MobclickAgent.onPageStart("MainScreen");
//	}
//
//	public void onPause() {
//		super.onPause();
//		MobclickAgent.onPageEnd("MainScreen");
//	}
//
//	private void initDate(View mBaseView) {
//		tv_main_text.setText(getString(R.string.my_garden_title));
//		imageLoader.init(ImageLoaderConfiguration
//				.createDefault(TestSchoolMainActivity.mainActivity));
//		options = new DisplayImageOptions.Builder().build();
//
//		userId = SharedPreference.strUserId;
//		userName.setText(SharedPreference.strUserRealName);
//		imageLoader
//				.displayImage(SharedPreference.strUserIcon, userPic, options);
//
//	}
//
//	private void initView(View mBaseView) {
//		tv_main_text = (TextView) mBaseView.findViewById(R.id.title_main);
//		jiantou = (LinearLayout) mBaseView
//				.findViewById(R.id.img_my_garden_jiantou);
//		rel_shouhuo = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_shouhuo_address);
//		rel_advice = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_advice);
//		rel_about_zhiyuan = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_about_zhiyuan);
//		rel_phone = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_phone);
//		myGardenPhone = (TextView) mBaseView.findViewById(R.id.my_garden_phone);
//		userPic = (CircularImage) mBaseView
//				.findViewById(R.id.my_garden_usertouxiang);
//		userName = (TextView) mBaseView.findViewById(R.id.my_garden_username);
//		cleanCache = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_setting_clean_cache);
//
//		jiantou.setOnClickListener(this);
//		rel_shouhuo.setOnClickListener(this);
//		rel_advice.setOnClickListener(this);
//		rel_about_zhiyuan.setOnClickListener(this);
//		rel_phone.setOnClickListener(this);
//		myGardenPhone.setOnClickListener(this);
//		cleanCache.setOnClickListener(this);
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_my_garden_jiantou:
//			Intent intent = new Intent(getActivity(),
//					TestMyMessageActivity.class);
//			getActivity().startActivity(intent);
//			break;
//
//		case R.id.rel_my_garden_shouhuo_address:
//			if (NetManager.isWifi() || NetManager.isMoble()) {
//				Intent goChooseAddress = new Intent();
//				goChooseAddress.setClass(getActivity(),
//						TestMessageActivity.class);
//				getActivity().startActivity(goChooseAddress);
//			} else {
//				Toast.makeText(TestSchoolMainActivity.mainActivity,
//						getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			break;
//		case R.id.rel_my_garden_advice:
//			if (NetManager.isWifi() || NetManager.isMoble()) {
//				Intent goAdvice = new Intent();
//				goAdvice.setClass(getActivity(), TestSchoolAdviceActivity.class);
//				getActivity().startActivity(goAdvice);
//			} else {
//				Toast.makeText(TestSchoolMainActivity.mainActivity,
//						getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			break;
//		case R.id.rel_about_zhiyuan:
//			if (NetManager.isWifi() || NetManager.isMoble()) {
//				Intent goAboutZhiYuan = new Intent();
//				goAboutZhiYuan.setClass(getActivity(),
//						UserAboutZhiYuanActivity.class);
//				getActivity().startActivity(goAboutZhiYuan);
//			} else {
//				Toast.makeText(TestSchoolMainActivity.mainActivity,
//						getString(R.string.network_message_no),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			break;
//
//		case R.id.rel_my_garden_phone:
//		case R.id.my_garden_phone:
//			String phoneNumber = myGardenPhone.getText().toString();
//			if (!TextUtils.isEmpty(phoneNumber)) {
//				Intent phone = new Intent(Intent.ACTION_DIAL);
//				Uri data = Uri.parse("tel:" + phoneNumber);
//				phone.setData(data);
//				startActivity(phone);
//			}
//			break;
//		case R.id.rel_setting_clean_cache:
//
//			try {
//				String strCache = DateCleanManager
//						.getTotalCacheSize(TestSchoolMainActivity.mainActivity);
//				if (strCache.equals("0.0Byte")) {
//					Toast.makeText(TestSchoolMainActivity.mainActivity,
//							getString(R.string.dialog_login_cache_clean),
//							Toast.LENGTH_SHORT).show();
//				} else {
//					showAlertDialog(
//							TestSchoolMainActivity.mainActivity,
//							getString(R.string.dialog_login_cache)
//									+ DateCleanManager
//											.getTotalCacheSize(TestSchoolMainActivity.mainActivity));
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	public static void showAlertDialog(final Context context, String string) {
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
//						DateCleanManager.clearAllCache(context);
//						try {
//							String strCache = DateCleanManager
//									.getTotalCacheSize(TestSchoolMainActivity.mainActivity);
//							if (strCache.equals("0.0Byte")) {
//								Toast.makeText(TestSchoolMainActivity.mainActivity,
//										"清除完成", Toast.LENGTH_SHORT).show();
//							}
//
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//					}
//				});
//
//		builder.create().show();
//
//	}
//
//	public interface SchoolMyGardenlListener {
//		public void goConsultation();
//	}
//
//	public void onAttach(Activity activity) {
//
//		try {
//			mCallback = (SchoolMyGardenlListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnHeadlineSelectedListener");
//		}
//
//		super.onAttach(activity);
//	}
//
//}