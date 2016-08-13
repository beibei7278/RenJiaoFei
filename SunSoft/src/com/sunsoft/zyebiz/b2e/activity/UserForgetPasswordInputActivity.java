package com.sunsoft.zyebiz.b2e.activity;

/**
 * 重新设置密码的页面
 * @author YinGuiChun
 */
import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.CheckNum.CheckNumber;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.umeng.analytics.MobclickAgent;

public class UserForgetPasswordInputActivity extends Activity implements
		OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone;
	private Button btnPassword, btnConfirmPassword;
	private EditText edtPassword, edtConfirmPassword;
	private String userName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_input);

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
		tvMainText.setText(this.getString(R.string.forget_password_title_main));

		Bundle bundle = this.getIntent().getExtras();
		userName = bundle.getString("userName");

		btnPassword.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					edtPassword
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					btnPassword.setBackgroundResource(R.drawable.xiaoyanjing);
					edtPassword.setSelection(edtPassword.getText().length());

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					edtPassword
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					btnPassword
							.setBackgroundResource(R.drawable.xiaoyanjing_biyan);
					edtPassword.setSelection(edtPassword.getText().length());

				}
				return false;
			}
		});

		btnConfirmPassword.setOnTouchListener(new Button.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					edtConfirmPassword
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					btnConfirmPassword
							.setBackgroundResource(R.drawable.xiaoyanjing);
					edtConfirmPassword.setSelection(edtConfirmPassword
							.getText().length());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					edtConfirmPassword
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					btnConfirmPassword
							.setBackgroundResource(R.drawable.xiaoyanjing_biyan);
					edtConfirmPassword.setSelection(edtConfirmPassword
							.getText().length());
				}
				return false;
			}
		});

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		changeNewPhone = (TextView) findViewById(R.id.change_new_phone);
		btnPassword = (Button) findViewById(R.id.btn_visible);
		btnConfirmPassword = (Button) findViewById(R.id.btn_confirm_visible);
		edtPassword = (EditText) findViewById(R.id.edt_change_password);
		edtConfirmPassword = (EditText) findViewById(R.id.edt_change_confirm_password);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.change_new_phone:
			if (EmptyUtil.isEmpty(edtPassword.getText().toString())) {
				Toast.makeText(UserForgetPasswordInputActivity.this, "密码不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (EmptyUtil.isEmpty(edtConfirmPassword.getText().toString())) {
				Toast.makeText(UserForgetPasswordInputActivity.this,
						"确认密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			/** 密码长度验证 */
			if (edtPassword.getText().toString().length() < Constants.CONSTANT_SIX
					|| edtPassword.getText().toString().length() > Constants.CONSTANT_SIXTEEN) {
				Toast.makeText(UserForgetPasswordInputActivity.this,
						"密码输入格式为:英文大小写字母、数字、特殊字符，长度为6-16位之内",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (!edtPassword.getText().toString()
					.equals(edtConfirmPassword.getText().toString())) {
				Toast.makeText(UserForgetPasswordInputActivity.this,
						getString(R.string.forget_password_toast),
						Toast.LENGTH_SHORT).show();
				return;

			}
			updatePassword(); // 更新密码

			break;

		default:
			break;
		}

	}

	/**
	 * 更新密码
	 */
	private void updatePassword() {
		RequestParams params = new RequestParams();
		params.put("userName", userName);
		params.put("password", edtConfirmPassword.getText().toString());

		AsyncHttpUtil.post(URLInterface.LOGIN_RESET_PASSWORD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CheckNumber checkNum = gson.fromJson(resultDate,
									CheckNumber.class);
							String message = checkNum.getMessage();
							String title = checkNum.getTitle();
							System.out.println("title:" + title);
							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									Toast.makeText(
											UserForgetPasswordInputActivity.this,
											"重置成功", Toast.LENGTH_SHORT).show();
									new Handler().postDelayed(new Runnable() {
										public void run() {
											Intent intent = new Intent();
											intent.setClass(
													UserForgetPasswordInputActivity.this,
													LoginActivity.class);
											startActivity(intent);
											finish();
										}
									}, Constants.CONSTANT_TWO_THOUSAND);

								} else {
									System.out.println(checkNum.getMessage());
								}

							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}

}
