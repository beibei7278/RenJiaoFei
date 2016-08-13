package com.yfd.appTest.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationService;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.Basefragment;
import com.yfd.appTest.Activity.MainActivity;
import com.yfd.appTest.Activity.PerssionnalActivity;
import com.yfd.appTest.Activity.RegestActivity;
import com.yfd.appTest.Activity.XieYiActivity;
import com.yfd.appTest.Beans.LoginBeans;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.LoginBeansc;
import com.yfd.appTest.Beans.LoginBeansmsg;
import com.yfd.appTest.Beans.Loginlfbeans;
import com.yfd.appTest.Beans.Messag;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class LoginFragment extends Basefragment implements OnClickListener {

	View Mview;
	// 百度地图
	public LocationService locationService;
	public Vibrator mVibrator;
	private Button loginbButton;
	private EditText uname, psd;
	Context context;
	LoginBeans loginbeans;
	LoginBeansStr loginbeansstr;
	Loginlfbeans loginlfbeans;
	LoginBeansmsg loginbeansmsg;
	LoginBeansc loginbeansc;
	String lat, lon;
	String conuttry;
	String provice;
	String city;
	String street;
	String area;
	String adress;
	Button zhuce;
	CheckBox checkbox;
	TextView tvXieYi;

	private Handler handlerlf = new Handler() {

		public void handleMessage(android.os.Message msg) {

			dimissloading();
			if (msg.obj == null) {
				Toast.makeText(getActivity(), "登录失败，请重试~", 1000).show();
			} else {
				loginbeansmsg = (LoginBeansmsg) Utils.jsonToBean(
						msg.obj.toString(), LoginBeansmsg.class);
				if (loginbeansmsg.getState().equals("0")) {

					loginbeansstr = (LoginBeansStr) Utils.jsonToBean(
							msg.obj.toString(), LoginBeansStr.class);

					Toast.makeText(getActivity(), loginbeansstr.getMsg(), 1000)
							.show();
				} else {
					BaseApplication.loginbeans = new LoginBeans();
					loginbeansc = (LoginBeansc) Utils.jsonToBean(
							msg.obj.toString(), LoginBeansc.class);
					Messag mssg = new Messag();
					mssg.setUsName(loginbeansc.getMsg().getUsername());
					mssg.setSessionId(loginbeansc.getMsg().getIds());
					BaseApplication.loginbeans.setMsg(mssg);
					;
					BaseApplication.loginbeans.setType("0");
					Utils.SaveUserinfo(context, uname.getText().toString(), psd
							.getText().toString(), loginbeans.getMsg().getMiUseScard(),
							loginbeans.getMsg().getUserName(), loginbeans.getMsg().getUsAddr(),
							loginbeans.getMsg().getUsId(), loginbeans.getMsg().getUsMobile(),loginbeans.getMsg().getUsState());

					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);
					getActivity().finish();
					dimissloading();
				}

			}

		};

	};
	private Handler handlerif = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.obj == null) {
				dimissloading();
				Toast.makeText(getActivity(), "登录失败，请重试~", 1000).show();
			} else {
				loginbeansstr = (LoginBeansStr) Utils.jsonToBean(
						msg.obj.toString(), LoginBeansStr.class);
				if (loginbeansstr.getUsType().equals("1")) {
					AnsyPost.Login(BaseApplication.LOGIN_URL + "?usName="
							+ uname.getText().toString() + "&usPass="
							+ psd.getText().toString() + "&longitude=" + lon
							
							+ "&latitude=" + lat + "&country=" + conuttry
							+ "&province=" + provice + "&city=" + city
							+ "&area=" + area + "&street=" + street, handler);
				} else {
					AnsyPost.Login(BaseApplication.LOGIN_URL_L + "?userName="
							+ uname.getText().toString() + "&passWord="
							+ psd.getText().toString() + "&longitude=" + lon
							+ "&latitude=" + lat + "&country=" + conuttry
							+ "&province=" + provice + "&city=" + city
							+ "&area=" + area + "&street=" + street, handlerlf);
				}

			}

		};

	};

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			dimissloading();
			if (msg.obj == null) {
				Toast.makeText(getActivity(), "登录失败，请重试~", 1000).show();
			} else {
				loginbeansmsg = (LoginBeansmsg) Utils.jsonToBean(
						msg.obj.toString(), LoginBeansmsg.class);
				if (loginbeansmsg.getState().equals("0")) {
					loginbeansstr = (LoginBeansStr) Utils.jsonToBean(
							msg.obj.toString(), LoginBeansStr.class);
					Toast.makeText(getActivity(), loginbeansstr.getMsg(), 1000)
							.show();
				} else {
					loginbeans = (LoginBeans) Utils.jsonToBean(
							msg.obj.toString(), LoginBeans.class);
					BaseApplication.loginbeans = loginbeans;
					BaseApplication.loginbeans.setType("1");
					Utils.SaveUserinfo(context, uname.getText().toString(), psd
							.getText().toString(), loginbeans.getMsg().getMiUseScard(),
							loginbeans.getMsg().getUserName(), loginbeans.getMsg().getUsAddr(),
							loginbeans.getMsg().getUsId(), loginbeans.getMsg().getUsMobile(),loginbeans.getMsg().getUsState());
					System.out.println("usId:"+loginbeans.getMsg().getUsId());

					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);
					getActivity().finish();
					dimissloading();
				}

			}

		};

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Mview = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_login, null);
		context = getActivity();
		loginbButton = (Button) Mview.findViewById(R.id.loginbtn);
		zhuce = (Button) Mview.findViewById(R.id.lzhuce);
		tvXieYi = (TextView) Mview.findViewById(R.id.login_xieyi);
		uname = (EditText) Mview.findViewById(R.id.login_uname);
		psd = (EditText) Mview.findViewById(R.id.login_psd);
		checkbox = (CheckBox) Mview.findViewById(R.id.checkbox);

		if (!Utils.isEmpty(Utils.getUname(context))) {
			uname.setText(Utils.getUname(context));
		}
		if (!Utils.isEmpty(Utils.getUpsd(context))) {
			psd.setText(Utils.getUpsd(context));
		}
		loginbButton.setOnClickListener(this);
		zhuce.setOnClickListener(this);
		tvXieYi.setOnClickListener(this);
		/***
		 * 初始化定位sdk，建议在Application中创建
		 */
		locationService = ((BaseApplication) getActivity().getApplication()).locationService;
		// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		locationService.setLocationOption(locationService
				.getDefaultLocationClientOption());
		locationService.start();// 定位SDK
		checkbox.setChecked(true);

		tvXieYi.setText(Html
				.fromHtml("我已阅读并同意<font color='red'><b>《用户协议》</b></font>"));

		return Mview;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.loginbtn:
			testup();
			break;

		case R.id.lzhuce:
			Utils.cleanRegistInfo(getActivity());
			Intent intent = new Intent(getActivity(), RegestActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.login_xieyi:
			Intent xieyi = new Intent(getActivity(), XieYiActivity.class);
			getActivity().startActivity(xieyi);
			break;

		default:
			break;
		}
	}

	/**
	 * 登陆界面：登陆按钮点击执行方法
	 */
	public void testup() {
		if (Utils.isEmpty(uname.getText().toString())) {
			Toast.makeText(getActivity(), "输入用户名", 1000).show();
			return;
		}
		if (Utils.isEmpty(psd.getText().toString())) {
			Toast.makeText(getActivity(), "输入密码", 1000).show();
			return;
		}

		if (!checkbox.isChecked()) {
			Toast.makeText(getActivity(), "请选择同意协议", 1000).show();
			return;
		}

		showloading("");

		AnsyPost.Login(BaseApplication.IFLORK_URL + "?usName="
				+ uname.getText().toString(), handlerif);

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onStop() {
		locationService.unregisterListener(mListener); // 注销掉监听
		locationService.stop(); // 停止定位服务
		super.onStop();
	}

	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location
					&& location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\nfloor : ");
				sb.append(location.getBuildingName());
				sb.append("\nprovice : ");
				sb.append(location.getProvince());
				sb.append("\nDescribe: ");
				sb.append(location.getLocationDescribe());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");

				if (location.getPoiList() != null
						&& !location.getPoiList().isEmpty()) {
					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {

					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}

				conuttry = location.getCountry();
				city = location.getCity();
				provice = location.getProvince();
				street = location.getAddrStr();
				area = location.getDistrict();
				adress = location.getAddrStr();
				lon = String.valueOf(location.getLongitude());
				lat = String.valueOf(location.getLatitude());

				BaseApplication.lat = lat;
				BaseApplication.lon = lon;
				// logMsg(sb.toString());
				// Toast.makeText(getActivity().getApplicationContext(),
				// sb.toString(), 1000).show();
			}
		}
	};

}
