package com.sunsoft.zyebiz.b2e.activity;

/**
 * 支付完成页面
 * @author YinGuiChun
 */
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserBuySuccessActivity extends Activity implements OnClickListener {

	private TextView tvTitleBack, titleMain;
	private ImageView imgTitleBack;
	private ImageView imgTitleRight;
	private TextView goOnBuy;
	private long exitTime = 0;
	private TextView orderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_success);
		ECApplication.getInstance().addActivity(this);
		initView();
		initDate();
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		imgTitleBack.setVisibility(View.GONE);
		imgTitleRight.setBackgroundResource(R.drawable.pay_touxiang);
		titleMain.setText(this.getString(R.string.buy_success_title_main));
		if (EmptyUtil.isNotEmpty(SharedPreference.strOrderId)) {
			orderId.setText(SharedPreference.strOrderId);
		}

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
	 * 初始化控件
	 */
	private void initView() {
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		imgTitleRight = (ImageView) findViewById(R.id.img_title_right);
		titleMain = (TextView) findViewById(R.id.title_main);
		goOnBuy = (TextView) findViewById(R.id.go_on_buy);
		orderId = (TextView) findViewById(R.id.order_id);
		tvTitleBack.setOnClickListener(this);
		imgTitleBack.setOnClickListener(this);
		imgTitleRight.setOnClickListener(this);
		goOnBuy.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_title_right:
			Intent intent = new Intent();
			intent.setClass(UserBuySuccessActivity.this, UserMainActivity.class);
			intent.putExtra(Constants.LOGIN_KEY, "PaySuccess");
			startActivity(intent);
			this.finish();
			break;
		case R.id.go_on_buy:
			UserMainActivity.isHaveDate = true;
			ECApplication.isTuangou = true;
			Intent goRetail = new Intent();
			goRetail.setClass(UserBuySuccessActivity.this,
					UserMainActivity.class);
			goRetail.putExtra(Constants.LOGIN_KEY, "goRetail");
			startActivity(goRetail);
			this.finish();
			break;

		default:
			break;
		}

	}

	/**
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > Constants.CONSTANT_TWO_THOUSAND) {
				Toast.makeText(UserMainActivity.mainActivity,
						getString(R.string.dialog_chengxu_exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				ECApplication.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
