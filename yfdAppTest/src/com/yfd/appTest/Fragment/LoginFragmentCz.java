package com.yfd.appTest.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.Basefragment;
import com.yfd.appTest.Activity.MainActivity;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.LoginBeansc;
import com.yfd.appTest.Beans.LoginBeansmsg;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class LoginFragmentCz extends Basefragment implements OnClickListener {

	View Mview;
	private Button loginbButton;
	private EditText uname, psd;
	Context context;
	LoginBeansc loginbeans;
	LoginBeansStr loginbeansstr;
	LoginBeansmsg loginbeansmsg;

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
					loginbeans = (LoginBeansc) Utils.jsonToBean(
							msg.obj.toString(), LoginBeansc.class);
					BaseApplication.loginbeansc = loginbeans;
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
		// TODO Auto-generated method stub

		Mview = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_loginc, null);
		context = getActivity();
		loginbButton = (Button) Mview.findViewById(R.id.loginbtnc);
		uname = (EditText) Mview.findViewById(R.id.login_unamec);
		psd = (EditText) Mview.findViewById(R.id.login_psdc);

		loginbButton.setOnClickListener(this);
		return Mview;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.loginbtnc:
			testup();
			break;

		default:
			break;
		}
	}

	public void testup() {

		if (Utils.isEmpty(uname.getText().toString())) {
			Toast.makeText(getActivity(), "输入用户名", 1000).show();
			return;
		}
		if (Utils.isEmpty(psd.getText().toString())) {
			Toast.makeText(getActivity(), "输入密码", 1000).show();
			return;
		}

		showloading("");
		AnsyPost.Login(BaseApplication.LOGIN_URLC + "?userName="
				+ uname.getText().toString() + "&passWord="
				+ psd.getText().toString(), handler);

	}

}
