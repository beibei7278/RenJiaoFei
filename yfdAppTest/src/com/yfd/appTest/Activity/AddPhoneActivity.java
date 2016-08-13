package com.yfd.appTest.Activity;

/**
 * 功能：手机号码补全页面
 * @author YinGuiChun
 */
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.FormFile;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.RegistBeans;
import com.yfd.appTest.Utils.CheckUtil;
import com.yfd.appTest.Utils.EmptyUtil;
import com.yfd.appTest.Utils.UploadMore;
import com.yfd.appTest.Utils.Utils;

public class AddPhoneActivity extends BaseActivity implements OnClickListener {

	TextView regestbtn;
	EditText uname, name, adress, psd1, psd2, input_id_card, input_user_phone;
	private LoginBeansStr lbeans;

	FormFile[] formfile;
	Map<String, String> params;
	FormFile file2;
	private RegistBeans registBeans;
	// 图片上传相关
	String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
	String PREFIX = "--", LINE_END = "\r\n";
	String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	private ImageView cleanPhone;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.obj != null) {

				lbeans = (LoginBeansStr) Utils.jsonToBean(msg.obj.toString(),
						LoginBeansStr.class);

				Toast.makeText(getApplicationContext(), lbeans.getMsg(), 1000)
						.show();

			} else {

				Toast.makeText(getApplicationContext(), "请求失败，请重试~", 1000)
						.show();

			}

		};

	};

	private Handler handlersave = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.obj != null) {
				registBeans = (RegistBeans) Utils.jsonToBean(
						msg.obj.toString(), RegistBeans.class);
				Toast.makeText(getApplicationContext(), registBeans.getMsg(),
						1000).show();
				finish();
				if (registBeans.getState() == 1) {
					BaseApplication.loginbeans.getMsg().setUsMobile(
							input_user_phone.getText().toString());
					finish();

				}

			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_phone);
		regestbtn = (TextView) findViewById(R.id.regestbtn);
		input_user_phone = (EditText) findViewById(R.id.input_user_phone);
		cleanPhone = (ImageView) findViewById(R.id.img_phone_clean);
		regestbtn.setOnClickListener(this);
		cleanPhone.setOnClickListener(this);

		initDate();

	}

	private void initDate() {
		// 用户名输入手机号码的监听
		input_user_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				isPhoneNumVisible();

			}

		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.regestbtn:// 提交
			regestAction();
			break;
		case R.id.img_phone_clean: // 清除输入的手机号码
			input_user_phone.setText("");
			break;

		default:
			break;
		}
	}

	/**
	 * 清空手机号码按钮是否可见
	 */
	private void isPhoneNumVisible() {

		if (input_user_phone.length() > 0) {
			cleanPhone.setVisibility(View.VISIBLE);
		} else {
			cleanPhone.setVisibility(View.INVISIBLE);

		}
	}

	public void regestAction() {

		if (!CheckUtil.isMobileNumber(input_user_phone.getText().toString())) {
			Toast.makeText(getApplicationContext(), "请输入正确手机号", 1000).show();
			return;
		}

		formfile = new FormFile[1];
		formfile[0] = null;

		params = new HashMap<String, String>();
		params.put("usId", Utils.getUsId(AddPhoneActivity.this));
		params.put("usIsUpdate", "0");// 表示更新数据
		params.put("usName", Utils.getUname(AddPhoneActivity.this));
		params.put("miUseScard", Utils.getMiUseScard(AddPhoneActivity.this));
		params.put("usPass", Utils.getUpsd(AddPhoneActivity.this));
		params.put("usCredits", "2");
		params.put("userName", Utils.getName(AddPhoneActivity.this));
		params.put("usAddr", Utils.getUsAddr(AddPhoneActivity.this));
		params.put("usMobile", input_user_phone.getText().toString());

		Runnable networkTask = new Runnable() {

			@Override
			public void run() {
				try {

					UploadMore.post(BaseApplication.REGIST_DATA_URL, params,
							formfile, handlersave);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if (Utils.isNetworkAvailable(AddPhoneActivity.this)) {
			new Thread(networkTask).start();
		} else {
			Toast.makeText(getApplicationContext(), "网络连接异常，请重试~", 1000).show();
		}

	}

	/**
	 * 实体返回键功能
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Toast.makeText(getApplicationContext(), "请补全手机号码", 1000).show();
		}
		return true;
		// return super.onKeyDown(keyCode, event);
	}

}
