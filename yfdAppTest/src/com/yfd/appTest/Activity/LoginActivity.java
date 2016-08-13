package com.yfd.appTest.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Fragment.LoginFragment;
import com.yfd.appTest.Fragment.LoginFragmentCz;
import com.yfd.appTest.Fragment.RegestFragment;

public class LoginActivity extends BasefragmentActivity implements
		OnClickListener {

	LoginFragment loginfragment;
	RegestFragment regestfragment;
	LoginFragmentCz loginfragmentc;
	private Fragment mContent;

	RadioGroup loginchange;

	Button loginbButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// 透明状态栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		BaseApplication.listacty.add(this);
		loginfragment = new LoginFragment();
		regestfragment = new RegestFragment();
		loginfragmentc = new LoginFragmentCz();

		loginchange = (RadioGroup) findViewById(R.id.login_tab_group);

		mContent = loginfragment;
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.loginfragmentcon, loginfragment);
		transaction.commit();
		loginchange.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

				switch (arg1) {
				case R.id.btn_login:
					switchContent(loginfragment);
					BaseApplication.which = 0;
					break;
				case R.id.btn_regest:
					switchContent(loginfragmentc);
					BaseApplication.which = 1;
					break;
				default:
					break;
				}

			}
		});

	}

	public void switchContent(Fragment to) {
		if (mContent != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) {
				transaction.hide(mContent).add(R.id.loginfragmentcon, to)
						.commit();
			} else {
				transaction.hide(mContent).show(to).commit();
			}
			mContent = to;
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
