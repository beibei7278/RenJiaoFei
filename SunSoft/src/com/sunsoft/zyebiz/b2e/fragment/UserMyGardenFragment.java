package com.sunsoft.zyebiz.b2e.fragment;

/**
 * 功能：普通用户之我的智园页面
 * @author YinGuiChun
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.activity.UserMySettingActivity;
import com.sunsoft.zyebiz.b2e.activity.UserAboutZhiYuanActivity;
import com.sunsoft.zyebiz.b2e.activity.UserAddressChooseActivity;
import com.sunsoft.zyebiz.b2e.activity.UserAdviceActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.activity.UserSettingActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMyMessageActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMyOrderActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMyOrderTuiHuoActivity;
import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
import com.sunsoft.zyebiz.b2e.wiget.CircularImage;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserMyGardenFragment extends Fragment implements OnClickListener {
	private TextView tv_main_text;
	private LinearLayout jiantou;
	private RelativeLayout relSetting;
	private ImageView imgSetting;
	private RelativeLayout rel_my_order, rel_shouhuo, rel_advice,
			rel_about_zhiyuan, rel_phone;
	private TextView myGardenPhone;
	private TextView tvDaiFuKuan, tvDaiShouHuo, tvYiWanCheng, tvYiQuXiao,
			tvTuiHuo;
	private CircularImage userPic;
	private TextView userName;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private String userId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mBaseView = inflater.inflate(R.layout.fragment_my_garden,
				null);

		initView(mBaseView);
		initDate(mBaseView);

		return mBaseView;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); 
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void initDate(View mBaseView) {
		tv_main_text.setText(getString(R.string.my_garden_title));
		// rel_shopping.setVisibility(View.GONE);
		// commodityShop.setBackgroundResource(R.drawable.my_garden_setting);
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(UserMainActivity.mainActivity));
		options = new DisplayImageOptions.Builder().build();

		userId = SharedPreference.strUserId;
		userName.setText(SharedPreference.strUserRealName);
		imageLoader
				.displayImage(SharedPreference.strUserIcon, userPic, options);

	}

	private void initView(View mBaseView) {
		tv_main_text = (TextView) mBaseView.findViewById(R.id.title_main);
		jiantou = (LinearLayout) mBaseView
				.findViewById(R.id.img_my_garden_jiantou);
		rel_my_order = (RelativeLayout) mBaseView
				.findViewById(R.id.my_garden_rel_my_order);
		rel_shouhuo = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_my_garden_shouhuo_address);
		rel_advice = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_my_garden_advice);
		rel_about_zhiyuan = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_about_zhiyuan);
		rel_phone = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_my_garden_phone);
		imgSetting = (ImageView) mBaseView.findViewById(R.id.my_garden_setting);
		myGardenPhone = (TextView) mBaseView.findViewById(R.id.my_garden_phone);
		tvDaiFuKuan = (TextView) mBaseView
				.findViewById(R.id.my_garden_daifukuan);
		tvDaiShouHuo = (TextView) mBaseView
				.findViewById(R.id.my_garden_daishouhuo);
		tvYiWanCheng = (TextView) mBaseView
				.findViewById(R.id.my_garden_yiwancheng);
		tvYiQuXiao = (TextView) mBaseView.findViewById(R.id.my_garden_yiquxiao);
		tvTuiHuo = (TextView) mBaseView.findViewById(R.id.my_garden_tuihuo);
		relSetting = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_my_garden_setting);
		userPic = (CircularImage) mBaseView
				.findViewById(R.id.my_garden_usertouxiang);
		userName = (TextView) mBaseView.findViewById(R.id.my_garden_username);

		jiantou.setOnClickListener(this);
		rel_my_order.setOnClickListener(this);
		rel_shouhuo.setOnClickListener(this);
		rel_advice.setOnClickListener(this);
		rel_about_zhiyuan.setOnClickListener(this);
		rel_phone.setOnClickListener(this);
		imgSetting.setOnClickListener(this);
		myGardenPhone.setOnClickListener(this);
		tvDaiFuKuan.setOnClickListener(this);
		tvDaiShouHuo.setOnClickListener(this);
		tvYiWanCheng.setOnClickListener(this);
		tvYiQuXiao.setOnClickListener(this);
		tvTuiHuo.setOnClickListener(this);
		relSetting.setOnClickListener(this);

	}
	/**
	 * 对点击事件做出处理
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_my_garden_jiantou:
			Intent intent = new Intent(getActivity(),
					UserMyMessageActivity.class);
			getActivity().startActivity(intent);
			break;

		case R.id.rel_my_garden_shouhuo_address:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goChooseAddress = new Intent();
				goChooseAddress.setClass(getActivity(),
						UserAddressChooseActivity.class);
				getActivity().startActivity(goChooseAddress);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.rel_my_garden_advice:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goAdvice = new Intent();
				goAdvice.setClass(getActivity(), UserAdviceActivity.class);
				getActivity().startActivity(goAdvice);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.rel_about_zhiyuan:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goAboutZhiYuan = new Intent();
				goAboutZhiYuan.setClass(getActivity(),
						UserAboutZhiYuanActivity.class);
				getActivity().startActivity(goAboutZhiYuan);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.rel_my_garden_setting:  //设置按钮
		case R.id.my_garden_setting:
//			Intent goSetting = new Intent();
//			goSetting.setClass(UserMainActivity.mainActivity,
//					UserSettingActivity.class);
//			startActivity(goSetting);
			
			Intent goSetting = new Intent();
			goSetting.setClass(UserMainActivity.mainActivity,
					UserMySettingActivity.class);
			startActivity(goSetting);
			break;
		case R.id.rel_my_garden_phone:
		case R.id.my_garden_phone:
			String phoneNumber = myGardenPhone.getText().toString();
			if (!TextUtils.isEmpty(phoneNumber)) {
				Intent phone = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + phoneNumber);
				phone.setData(data);
				startActivity(phone);
			}
			break;
		case R.id.my_garden_rel_my_order: //我的订单
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goMyOrder = new Intent();
				goMyOrder.setClass(getActivity(), UserMyOrderActivity.class);
				goMyOrder.putExtra("type", "-1");
				goMyOrder.putExtra("userId", userId);
				getActivity().startActivity(goMyOrder);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.my_garden_daifukuan:  //待付款
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goDaifukuanr = new Intent();
				goDaifukuanr.setClass(getActivity(), UserMyOrderActivity.class);
				goDaifukuanr.putExtra("type", "0");
				goDaifukuanr.putExtra("userId", userId);
				getActivity().startActivity(goDaifukuanr);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.my_garden_daishouhuo: 
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goDaishouhuo = new Intent();
				goDaishouhuo.setClass(getActivity(), UserMyOrderActivity.class);
				goDaishouhuo.putExtra("type", "1");
				goDaishouhuo.putExtra("userId", userId);
				getActivity().startActivity(goDaishouhuo);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.my_garden_yiwancheng:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goYiwancheng = new Intent();
				goYiwancheng.setClass(getActivity(), UserMyOrderActivity.class);
				goYiwancheng.putExtra("type", "2");
				goYiwancheng.putExtra("userId", userId);
				getActivity().startActivity(goYiwancheng);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.my_garden_yiquxiao:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goYiquxiao = new Intent();
				goYiquxiao.setClass(getActivity(), UserMyOrderActivity.class);
				goYiquxiao.putExtra("type", "3");
				goYiquxiao.putExtra("userId", userId);
				getActivity().startActivity(goYiquxiao);
			} else {
				Toast.makeText(UserMainActivity.mainActivity,getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.my_garden_tuihuo:
			if (NetManager.isWifi() || NetManager.isMoble()) {
				Intent goTuihuo = new Intent();
				goTuihuo.setClass(getActivity(),
						UserMyOrderTuiHuoActivity.class);
				goTuihuo.putExtra("type", "0");
				goTuihuo.putExtra("userId", userId);
				getActivity().startActivity(goTuihuo);
			} else {
				Toast.makeText(UserMainActivity.mainActivity, getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}

	}

}
