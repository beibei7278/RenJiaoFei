package com.sunsoft.zyebiz.b2e.activity;

/**
 * 修改密码页面
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
import com.sunsoft.zyebiz.b2e.model.Regist.LoginBean;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserPasswordActivity extends Activity implements OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone;
	private Button btnPassword, btnConfirmPassword;
	private EditText edtPassword, edtConfirmPassword, changePasOld;
	private TextView forgetPas;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

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
		tvMainText.setText(this.getString(R.string.change_password_title_main));

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
		changeNewPhone = (TextView) findViewById(R.id.change_password_save);
		btnPassword = (Button) findViewById(R.id.btn_visible);
		btnConfirmPassword = (Button) findViewById(R.id.btn_confirm_visible);
		edtPassword = (EditText) findViewById(R.id.change_password_new);
		edtConfirmPassword = (EditText) findViewById(R.id.change_password_old_confirm);
		forgetPas = (TextView) findViewById(R.id.forget_pas);
		changePasOld = (EditText) findViewById(R.id.change_password_old);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);
		forgetPas.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.change_password_save:
			if (EmptyUtil.isEmpty(changePasOld.getText().toString())) {
				Toast.makeText(UserPasswordActivity.this,
						getString(R.string.change_password_old_null),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (EmptyUtil.isEmpty(edtPassword.getText().toString())) {
				Toast.makeText(UserPasswordActivity.this,
						getString(R.string.change_password_new_null),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (EmptyUtil.isEmpty(edtConfirmPassword.getText().toString())) {
				Toast.makeText(UserPasswordActivity.this,
						getString(R.string.change_password_confirm_null),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (changePasOld.getText().toString()
					.equals(edtPassword.getText().toString())) {
				Toast.makeText(UserPasswordActivity.this,
						getString(R.string.change_password_old_new_equil),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (edtPassword.getText().toString().length() < 6
					|| edtPassword.getText().toString().length() > 16) {
				Toast.makeText(UserPasswordActivity.this,
						"请输入6-16位的字母，数字，特殊字符组合", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!edtPassword.getText().toString()
					.equals(edtConfirmPassword.getText().toString())) {
				Toast.makeText(UserPasswordActivity.this,
						getString(R.string.forget_password_toast),
						Toast.LENGTH_SHORT).show();
				return;

			}
			savePassword();

			break;
		case R.id.forget_pas:
			Intent forgetPas = new Intent(this,
					UserForgetPasswordActivity.class);
			startActivity(forgetPas);
			break;

		default:
			break;
		}

	}

	/**
	 * 修改密码并保存
	 */
	private void savePassword() {
		RequestParams params = new RequestParams();
		params.put("userName", SharedPreference.strUserName);
		params.put("oldPassword", changePasOld.getText().toString());
		params.put("password", edtConfirmPassword.getText().toString());
		params.put("token", SharedPreference.strUserToken);

		AsyncHttpUtil.post(URLInterface.ZHIYUAN_SET_PASSWORD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == 200) {
							String resultDate = new String(responseBody);
							LoginBean regist = gson.fromJson(resultDate,
									LoginBean.class);
							String title = regist.getTitle();
							System.out.println("title:" + title);
							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									Toast.makeText(UserPasswordActivity.this,
											getString(R.string.save_success),
											Toast.LENGTH_SHORT).show();
									new Handler().postDelayed(new Runnable() {
										public void run() {
											Intent intent = new Intent();
											intent.setClass(
													UserPasswordActivity.this,
													LoginActivity.class);
											startActivity(intent);
											finish();
										}
									}, Constants.CONSTANT_TWO_THOUSAND);

								} else {
									Toast.makeText(
											UserPasswordActivity.this,
											getString(R.string.old_password_input_error),
											Toast.LENGTH_SHORT).show();
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
