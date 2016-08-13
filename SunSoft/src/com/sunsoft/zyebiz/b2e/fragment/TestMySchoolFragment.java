//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：学校用户登录之我的学校页面
// * @author YGC
// */
//import org.apache.http.Header;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.activity.UserAboutZhiYuanActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserAddressChooseActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserAdviceActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.activity.SettingActivity;
//import com.sunsoft.zyebiz.b2e.activity.MyMessageActivity;
//import com.sunsoft.zyebiz.b2e.activity.MyOrderActivity;
//import com.sunsoft.zyebiz.b2e.activity.MyOrderTuiHuoActivity;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.usermessage.BodyResult;
//import com.sunsoft.zyebiz.b2e.model.usermessage.UserBean;
//import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.CircularImage;
//import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
//
//public class TestMySchoolFragment extends Fragment implements OnClickListener {
//	/** 标题 */
//	private TextView tv_main_text;
//	private LinearLayout jiantou;
//	private RelativeLayout relSetting;
//	private ImageView imgSetting;
//	private RelativeLayout rel_my_order, rel_shouhuo, rel_advice,
//			rel_about_zhiyuan, rel_phone;
//	private TextView myGardenPhone;
//	private TextView tvDaiFuKuan, tvDaiShouHuo, tvYiWanCheng, tvYiQuXiao,
//			tvTuiHuo;
//	private CircularImage userPic;
//	private TextView userName, userGrade;
//	/** DisplayImageOptions是用于设置图片显示的类 */
//	private DisplayImageOptions options;
//	/** 获得网络图片 */
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//	private String userId;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View mBaseView = inflater.inflate(R.layout.fragment_my_school, null);
//
////		initView(mBaseView);
////		initDate(mBaseView);
//
//		return mBaseView;
//	}
//
//	private void initDate(View mBaseView) {
//		// TODO Auto-generated method stub
//		tv_main_text.setText("我的学校");
//		// rel_shopping.setVisibility(View.GONE);
//		// commodityShop.setBackgroundResource(R.drawable.my_garden_setting);
//		/** imageLoader初始化 */
//		imageLoader.init(ImageLoaderConfiguration
//				.createDefault(UserMainActivity.mainActivity));
//		/** 使用DisplayImageOptions.Builder()创建DisplayImageOptions */
//		options = new DisplayImageOptions.Builder().build();
//		
//		userId = SharedPreference.strUserId;
//		userName.setText(SharedPreference.strUserName);
//		if(EmptyUtil.isNotEmpty(SharedPreference.strUserGrade)&&EmptyUtil.isNotEmpty(SharedPreference.strUserClass)){
//			userGrade.setText(SharedPreference.strUserGrade + SharedPreference.strUserClass);
//		}
//		imageLoader.displayImage(SharedPreference.strUserIcon,userPic, options);
//
//	}
//
//	private void initView(View mBaseView) {
//		// TODO Auto-generated method stub
//		tv_main_text = (TextView) mBaseView.findViewById(R.id.title_main);
//		jiantou = (LinearLayout) mBaseView
//				.findViewById(R.id.img_my_garden_jiantou);
//		rel_my_order = (RelativeLayout) mBaseView
//				.findViewById(R.id.my_garden_rel_my_order);
//		rel_shouhuo = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_shouhuo_address);
//		rel_advice = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_advice);
//		rel_about_zhiyuan = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_about_zhiyuan);
//		rel_phone = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_phone);
//		imgSetting = (ImageView) mBaseView.findViewById(R.id.my_garden_setting);
//		myGardenPhone = (TextView) mBaseView.findViewById(R.id.my_garden_phone);
//		tvDaiFuKuan = (TextView) mBaseView
//				.findViewById(R.id.my_garden_daifukuan);
//		tvDaiShouHuo = (TextView) mBaseView
//				.findViewById(R.id.my_garden_daishouhuo);
//		tvYiWanCheng = (TextView) mBaseView
//				.findViewById(R.id.my_garden_yiwancheng);
//		tvYiQuXiao = (TextView) mBaseView.findViewById(R.id.my_garden_yiquxiao);
//		tvTuiHuo = (TextView) mBaseView.findViewById(R.id.my_garden_tuihuo);
//		relSetting = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_my_garden_setting);
//		userPic = (CircularImage) mBaseView
//				.findViewById(R.id.my_garden_usertouxiang);
//		userName = (TextView) mBaseView.findViewById(R.id.my_garden_username);
//		userGrade = (TextView) mBaseView.findViewById(R.id.my_garden_usergrade);
//
//		jiantou.setOnClickListener(this);
//		rel_my_order.setOnClickListener(this);
//		rel_shouhuo.setOnClickListener(this);
//		rel_advice.setOnClickListener(this);
//		rel_about_zhiyuan.setOnClickListener(this);
//		rel_phone.setOnClickListener(this);
//		imgSetting.setOnClickListener(this);
//		myGardenPhone.setOnClickListener(this);
//		tvDaiFuKuan.setOnClickListener(this);
//		tvDaiShouHuo.setOnClickListener(this);
//		tvYiWanCheng.setOnClickListener(this);
//		tvYiQuXiao.setOnClickListener(this);
//		tvTuiHuo.setOnClickListener(this);
//		relSetting.setOnClickListener(this);
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.img_my_garden_jiantou:
//			Intent intent = new Intent(getActivity(), MyMessageActivity.class);
//			getActivity().startActivity(intent);
//			break;
//	
//		case R.id.rel_my_garden_shouhuo_address:
//			Intent goChooseAddress = new Intent();
//			goChooseAddress
//					.setClass(getActivity(), UserAddressChooseActivity.class);
//			getActivity().startActivity(goChooseAddress);
//			break;
//		case R.id.rel_my_garden_advice:
//			Intent goAdvice = new Intent();
//			goAdvice.setClass(getActivity(), UserAdviceActivity.class);
//			getActivity().startActivity(goAdvice);
//			break;
//		case R.id.rel_about_zhiyuan:
////			Intent goAboutZhiYuan = new Intent();  
////			goAboutZhiYuan.setClassName(MainActivity.mainActivity,"com.sunsoft.zyebiz.b2e.activity.AboutZhiYuanActivity");//打开一个activity  
////			MainActivity.mainActivity.startActivity(goAboutZhiYuan);  
////			MainActivity.mainActivity.overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);  
//			
//			Intent goAboutZhiYuan = new Intent();
//			goAboutZhiYuan.setClass(getActivity(), UserAboutZhiYuanActivity.class);
//			getActivity().startActivity(goAboutZhiYuan);
//			break;
//		
//		case R.id.rel_my_garden_setting:
//		case R.id.my_garden_setting:
//			Intent goSetting = new Intent();
//			goSetting.setClass(UserMainActivity.mainActivity,
//					SettingActivity.class);
//			startActivity(goSetting);
//			break;
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
//		case R.id.my_garden_rel_my_order:
//			Intent goMyOrder = new Intent();
//			goMyOrder.setClass(getActivity(), MyOrderActivity.class);
//			goMyOrder.putExtra("type", "-1");
//			goMyOrder.putExtra("userId", userId);
//			getActivity().startActivity(goMyOrder);
//			break;
//		case R.id.my_garden_daifukuan:
//			Intent goDaifukuanr = new Intent();
//			goDaifukuanr.setClass(getActivity(), MyOrderActivity.class);
//			goDaifukuanr.putExtra("type", "0");
//			goDaifukuanr.putExtra("userId", userId);
//			getActivity().startActivity(goDaifukuanr);
//			
//			break;
//		case R.id.my_garden_daishouhuo:
//			Intent goDaishouhuo = new Intent();
//			goDaishouhuo.setClass(getActivity(), MyOrderActivity.class);
//			goDaishouhuo.putExtra("type", "1");
//			goDaishouhuo.putExtra("userId", userId);
//			getActivity().startActivity(goDaishouhuo);
//			break;
//		case R.id.my_garden_yiwancheng:
//			Intent goYiwancheng = new Intent();
//			goYiwancheng.setClass(getActivity(), MyOrderActivity.class);
//			goYiwancheng.putExtra("type", "2");
//			goYiwancheng.putExtra("userId", userId);
//			getActivity().startActivity(goYiwancheng);
//			break;
//		case R.id.my_garden_yiquxiao:
//			Intent goYiquxiao = new Intent();
//			goYiquxiao.setClass(getActivity(), MyOrderActivity.class);
//			goYiquxiao.putExtra("type", "3");
//			goYiquxiao.putExtra("userId", userId);
//			getActivity().startActivity(goYiquxiao);
//			break;
//		case R.id.my_garden_tuihuo:
//			Intent goTuihuo = new Intent();
//			goTuihuo.setClass(getActivity(), MyOrderTuiHuoActivity.class);
//			goTuihuo.putExtra("type", "0");
//			goTuihuo.putExtra("userId", userId);
//			getActivity().startActivity(goTuihuo);
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
////	/**
////	 * 接受购物车的数据
////	 */
////	private void receiveUserMessage() {
////		RequestParams params = new RequestParams();
////		params.put("username", "0000000");
////		final Gson gson = new Gson();
////		AsyncHttpUtil.post(URLInterface.USER_MESSAGE, params,
////				new AsyncHttpResponseHandler() {
////					@Override
////					public void onSuccess(int statusCode, Header[] headers,
////							byte[] responseBody) {
////						if (statusCode == 200) {
////							String resultDate = new String(responseBody);
////							UserBean userBean = gson.fromJson(resultDate,
////									UserBean.class);
////							BodyResult bodyResult = userBean.getBody();
////							if (EmptyUtil.isNotEmpty(bodyResult)) {
////								
////
////							} else {
////								System.out.println("body:" + userBean.getBody());
////							}
////
////						}
////					}
////
////					@Override
////					public void onFailure(int statusCode, Header[] headers,
////							byte[] responseBody, Throwable error) {
////						System.out.println("222" + responseBody);
////
////					}
////				});
////	}
//
//}
