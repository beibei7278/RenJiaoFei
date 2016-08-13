package com.sunsoft.zyebiz.b2e.activity;

/**
 * 忘记密码页面
 * @author YinGuiChun
 */
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.Header;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class UserForgetPasswordNextActivity extends Activity implements
		OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone;
	private TextView sendCheckNum;
	private TextView edtUserPhone;
	private EditText edtUserCode;
	private String userphone;
	private String phoneNumber;
	private String message;
	private String userName;
	private Boolean isRun = true;
	private Boolean isClickCheckNum = true;

	private long timeout = 180000;
	private Handler mHandler = null;
	private Timer timer = null;
	private TimerTask timeTask;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_next);

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

	private void initDate() {
		tvMainText.setText(this.getString(R.string.forget_password_title_main));

		Bundle bundle = this.getIntent().getExtras();
		phoneNumber = bundle.getString("phoneNumber");
		userName = bundle.getString("userName");
		edtUserPhone.setText(phoneNumber);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.CONSTANT_ONE:
					System.out.println("asdasd");
					Toast.makeText(UserForgetPasswordNextActivity.this,
							"验证码失效，请重新获取", Toast.LENGTH_SHORT).show();
					isClickCheckNum = false;
					System.out.println("3_min_later:" + isClickCheckNum);
					stopThread();
					break;

				default:
					break;
				}

			};
		};

	}

	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		changeNewPhone = (TextView) findViewById(R.id.change_new_phone);
		sendCheckNum = (TextView) findViewById(R.id.forget_check_num);
		edtUserPhone = (TextView) findViewById(R.id.edt_forget_phone);
		edtUserCode = (EditText) findViewById(R.id.edt_forget_code);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);
		sendCheckNum.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.change_new_phone:
			if (EmptyUtil.isEmpty(edtUserCode.getText().toString())) {
				Toast.makeText(UserForgetPasswordNextActivity.this, "未输入短信验证码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (EmptyUtil.isEmpty(message)) {
				Toast.makeText(UserForgetPasswordNextActivity.this, "请点击获取验证码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			System.out.println("go_next:" + isClickCheckNum);
			if (!isClickCheckNum) {
				Toast.makeText(UserForgetPasswordNextActivity.this,
						"验证码失效，请重新获取", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!message.equals(edtUserCode.getText().toString())) {
				Toast.makeText(UserForgetPasswordNextActivity.this,
						"短信验证码输入不正确", Toast.LENGTH_SHORT).show();
				return;
			}
			changeNewPhone();

			break;
		case R.id.forget_check_num:
			isClickCheckNum = true;
			System.out.println("send_check_num:" + isClickCheckNum);
			stopThread();
			startThread();
			System.out.println("userphone:" + phoneNumber);
			if (!RegexUtil.isMobileNumber(phoneNumber)) {
				Toast.makeText(UserForgetPasswordNextActivity.this,
						getString(R.string.check_number_toast_phone),
						Toast.LENGTH_SHORT).show();
			} else {
				CountDownButtonHelper helper = new CountDownButtonHelper(
						sendCheckNum,
						this.getString(R.string.change_new_number_send), 60, 1);
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

	private void changeNewPhone() {
		Intent intent = new Intent();
		intent.setClass(UserForgetPasswordNextActivity.this,
				UserForgetPasswordInputActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);
		finish();
	}

	private void recCheckNum() {
		RequestParams params = new RequestParams();
		params.put("mobile", phoneNumber);
		params.put("type", "1");// 忘记密码

		params.put("token", SharedPreference.strUserToken);
		AsyncHttpUtil.post(URLInterface.MOBILE_SEND_MESSAGE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == 200) {
							String resultDate = new String(responseBody);
							CheckNumber checkNum = gson.fromJson(resultDate,
									CheckNumber.class);
							message = checkNum.getMessage();

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
		stopThread();
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

}
