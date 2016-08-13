package com.sunsoft.zyebiz.b2e.activity;

/**
 * 忘记密码页面
 * @author YinGuiChun
 */
import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.Regist.LoginBean;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.umeng.analytics.MobclickAgent;

public class UserForgetPasswordActivity extends Activity implements
		OnClickListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView changeNewPhone;
	private EditText edtUserName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);

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

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);

		changeNewPhone = (TextView) findViewById(R.id.change_new_phone);
		edtUserName = (EditText) findViewById(R.id.edt_forget_phone);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		changeNewPhone.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.change_new_phone: // 下一步
			inquireXueJi(); // 查询学籍
			break;

		default:
			break;
		}

	}

	/**
	 * 查询学籍
	 */
	private void inquireXueJi() {
		RequestParams params = new RequestParams();
		params.put("userName", edtUserName.getText().toString());

		AsyncHttpUtil.post(URLInterface.USER_MESSAGE, params,
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

							if (EmptyUtil.isNotEmpty(regist.getBody())) {
								String phoneNumber = regist.getBody()
										.getMobilePhone();
								String userName = regist.getBody()
										.getUserName();
								Intent intent = new Intent();
								intent.setClass(
										UserForgetPasswordActivity.this,
										UserForgetPasswordNextActivity.class);
								intent.putExtra("phoneNumber", phoneNumber);
								intent.putExtra("userName", userName);
								startActivity(intent);
								finish();

							} else {
								Toast.makeText(UserForgetPasswordActivity.this,
										regist.getMessage(), Toast.LENGTH_SHORT)
										.show();
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
