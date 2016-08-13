package com.sunsoft.zyebiz.b2e.activity;

/**
 * 绑定手机
 * @author YinGuiChun
 */

import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.sunsoft.zyebiz.b2e.util.RegexUtil;
import com.sunsoft.zyebiz.b2e.wiget.CountDownButtonHelper;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.CountDownButtonHelper.OnFinishListener;
import com.umeng.analytics.MobclickAgent;

public class UserNewNumberActivity extends Activity implements OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone;
	private TextView sendCheckNum;
	private EditText edtUserPhone, edtUserCode;
	private String userphone;
	private String message;
	private Boolean isClickCheckNum = true;
	private TextView timeOut;
	private TimerTask timeTask;

	private long timeout = 180000;
	private Handler mHandler = null;
	private Timer timer = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_new_number);

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
	@SuppressLint("HandlerLeak")
	private void initDate() {
		tvMainText
				.setText(this.getString(R.string.change_new_phone_title_main));
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.CONSTANT_ONE:
					System.out.println("asdasd");
					Toast.makeText(UserNewNumberActivity.this, "验证码失效，请重新获取",
							Toast.LENGTH_SHORT).show();
					isClickCheckNum = false;
					stopThread();
					break;

				default:
					break;
				}

			};
		};

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		changeNewPhone = (TextView) findViewById(R.id.change_new_phone);
		sendCheckNum = (TextView) findViewById(R.id.send_yanzhengma);
		edtUserPhone = (EditText) findViewById(R.id.edt_input_phone);
		edtUserCode = (EditText) findViewById(R.id.edt_input_code);
		timeOut = (TextView) findViewById(R.id.time_out);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);
		sendCheckNum.setOnClickListener(this);
		timeOut.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			Intent intentPhone = new Intent();
			intentPhone.putExtra("userPhone", SharedPreference.strUserPhone);
			setResult(Constants.CONSTANT_ONE_HUNDRED_AND_ONE, intentPhone);
			finish();
			break;
		case R.id.change_new_phone:
			userphone = edtUserPhone.getText().toString().trim();
			System.out.println("isclick:" + isClickCheckNum);

			if (EmptyUtil.isEmpty(userphone)) {
				Toast.makeText(UserNewNumberActivity.this,
						getString(R.string.change_phone_input_phone),
						Toast.LENGTH_SHORT).show();
				return;

			}
			if (EmptyUtil.isEmpty(edtUserCode.getText().toString())) {
				Toast.makeText(UserNewNumberActivity.this,
						getString(R.string.change_phone_check_null),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (EmptyUtil.isEmpty(message)) {
				Toast.makeText(UserNewNumberActivity.this, "请点击获取验证码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!isClickCheckNum) {
				Toast.makeText(UserNewNumberActivity.this, "验证码失效，请重新获取",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!RegexUtil.isMobileNumber(userphone)) {
				Toast.makeText(UserNewNumberActivity.this,
						getString(R.string.check_number_toast_phone),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (userphone.equals(SharedPreference.strUserPhone)) {
				Toast.makeText(UserNewNumberActivity.this, "手机号已绑定，请重新输入",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (!edtUserCode.getText().toString().equals(message)) {
				Toast.makeText(UserNewNumberActivity.this,
						getString(R.string.check_number_toast_input_error),
						Toast.LENGTH_SHORT).show();
				return;

			}
			changeNewPhone();

			break;
		case R.id.send_yanzhengma:
			// isRun = true;
			isClickCheckNum = true;
			stopThread();
			startThread();
			userphone = edtUserPhone.getText().toString().trim();
			if (EmptyUtil.isEmpty(userphone)) {
				Toast.makeText(UserNewNumberActivity.this, "请输入手机号",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!RegexUtil.isMobileNumber(userphone)) {
				Toast.makeText(UserNewNumberActivity.this,
						getString(R.string.check_number_toast_phone),
						Toast.LENGTH_SHORT).show();
			} else {
				CountDownButtonHelper helper = new CountDownButtonHelper(
						sendCheckNum,
						this.getString(R.string.change_new_number_send), 60,
						Constants.CONSTANT_ONE);
				helper.setOnFinishListener(new OnFinishListener() {

					@Override
					public void finish() {
						sendCheckNum
								.setText(getString(R.string.change_new_number_send_again));
					}
				});
				helper.start();

				recCheckNum();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 停止线程
	 */
	private void stopThread() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}

		if (timeTask != null) {
			timeTask.cancel();
			timeTask = null;
		}

	}

	/**
	 * 开始线程
	 */
	private void startThread() {
		if (timer == null) {
			timer = new Timer();
		}
		if (timeTask == null) {
			timeTask = new TimerTask() {
				public void run() {
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
					timer.cancel();
					timer.purge();
				}
			};
		}
		if (timer != null && timeTask != null) {
			timer.schedule(timeTask, timeout, 1);
		}
	}

	/**
	 * 更改手机号
	 */
	private void changeNewPhone() {
		RequestParams params = new RequestParams();
		params.put("userName", SharedPreference.strUserName);
		params.put("mobilePhone", edtUserPhone.getText().toString().trim());
		params.put("token", SharedPreference.strUserToken);
		AsyncHttpUtil.post(URLInterface.ZHIYUAN_SET_CHANGE_PHONE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CheckNumber checkNum = gson.fromJson(resultDate,
									CheckNumber.class);
							String title = checkNum.getTitle();
							if (title.equals(Constants.CONSTANT_STRING_ZERO)) {
								System.out.println("aaa");
								// isRun = false;
								// isClickCheckNum = true;
								stopThread();
								Toast.makeText(UserNewNumberActivity.this,
										"绑定成功", Toast.LENGTH_SHORT).show();
								SharedPreference.strUserPhone = edtUserPhone
										.getText().toString();

								new Handler().postDelayed(new Runnable() {
									public void run() {
										Intent intentPhone = new Intent();
										intentPhone.putExtra("userPhone",
												edtUserPhone.getText()
														.toString());
										intentPhone.putExtra("finishTwo",
												"finishTwo");
										setResult(
												Constants.CONSTANT_ONE_HUNDRED_AND_ONE,
												intentPhone);
										finish();
									}
								}, Constants.CONSTANT_TWO_THOUSAND);

							} else {
								Toast.makeText(UserNewNumberActivity.this,
										checkNum.getMessage(),
										Toast.LENGTH_SHORT).show();
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}

	/**
	 * 接受验证码
	 */
	private void recCheckNum() {
		RequestParams params = new RequestParams();
		params.put("mobile", edtUserPhone.getText().toString().trim());
		params.put("type", Constants.CONSTANT_STRING_TWO);// 绑定手机
		params.put("token", SharedPreference.strUserToken);
		AsyncHttpUtil.post(URLInterface.MOBILE_SEND_MESSAGE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							CheckNumber checkNum = gson.fromJson(resultDate,
									CheckNumber.class);
							message = checkNum.getMessage();
							// Thread thread = new Thread(new ThreadShow());
							// thread.start();
							System.out.println("message:" + message);
							if ("手机号已注册。".equals(message)
									&& EmptyUtil.isNotEmpty(message)) {
								Toast.makeText(UserNewNumberActivity.this,
										"手机号已注册。", Toast.LENGTH_SHORT).show();

							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}

	protected void onDestroy() {
		super.onDestroy();
		// isRun = false;
		// isClickCheckNum = true;
		// System.out.println("isRun:"+isRun);
		stopThread();
	}

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intentPhone = new Intent();
			intentPhone.putExtra("userPhone", SharedPreference.strUserPhone);
			setResult(Constants.CONSTANT_ONE_HUNDRED_AND_ONE, intentPhone);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
