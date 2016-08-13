package com.sunsoft.zyebiz.b2e.activity;

/**
 * 绑定手机页面
 * @author YinGuiChun
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserPhoneActivity extends Activity implements OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone, changePhoneNum;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_new_phone);

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

	/**
	 * 初始化数据
	 */
	private void initDate() {
		tvMainText
				.setText(this.getString(R.string.change_new_phone_title_main));
		changePhoneNum.setText(SharedPreference.strUserPhone);

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		changeNewPhone = (TextView) findViewById(R.id.change_new_phone);
		changePhoneNum = (TextView) findViewById(R.id.change_new_phone_num);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			Intent intentPhone = new Intent();
			intentPhone.putExtra("userPhone", SharedPreference.strUserPhone);
			System.out.println("back userPhone:"
					+ SharedPreference.strUserPhone);
			setResult(Constants.CONSTANT_ONE_HUNDRED_AND_TWO, intentPhone);
			finish();
			break;
		case R.id.change_new_phone:
			Intent intent = new Intent();
			intent.setClass(UserPhoneActivity.this, UserNewNumberActivity.class);
			startActivityForResult(intent,
					Constants.CONSTANT_ONE_HUNDRED_AND_ONE);
			break;

		default:
			break;
		}

	}

	/**
	 * 三层返回处理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.CONSTANT_ONE_HUNDRED_AND_ONE) {
			Bundle userPhone = data.getExtras();
			if (userPhone != null) {
				changePhoneNum.setText(userPhone.getString("userPhone"));
				if (EmptyUtil.isNotEmpty(userPhone.getString("finishTwo"))) {
					Intent intentPhone = new Intent();
					intentPhone.putExtra("userPhone",
							SharedPreference.strUserPhone);
					System.out.println("back userPhone:"
							+ SharedPreference.strUserPhone);
					setResult(Constants.CONSTANT_ONE_HUNDRED_AND_TWO,
							intentPhone);
					finish();
				}
				System.out.println("user new phone："
						+ userPhone.getString("userPhone"));
			} else {
				changePhoneNum.setText(SharedPreference.strUserPhone);
			}

		}

	}

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intentPhone = new Intent();
			intentPhone.putExtra("userPhone", SharedPreference.strUserPhone);
			setResult(Constants.CONSTANT_ONE_HUNDRED_AND_TWO, intentPhone);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
