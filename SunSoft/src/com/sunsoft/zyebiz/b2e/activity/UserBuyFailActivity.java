package com.sunsoft.zyebiz.b2e.activity;

/**
 * 支付失败页
 * @author YinGuiChun
 */
import java.util.HashMap;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserBuyFailActivity extends Activity implements OnClickListener {

	private TextView tvTitleBack, titleMain;
	private ImageView imgTitleBack;
	private ImageView imgTitleRight;
	private long exitTime = 0;
	private TextView payFailGoOrder, payFailGoStore;
	HashMap<String, String> authInfo;
	private TextView orderId;
	private LinearLayout payFail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_fail);
		ECApplication.getInstance().addActivity(this);
		SharedPreference.getOrderMessage(UserBuyFailActivity.this);
		System.out.println("fail_confirm_id:" + SharedPreference.strOrderId);
		initView();
		initDate();
		if (EmptyUtil.isNotEmpty(SharedPreference.strOrderId)) {
			orderId.setText(SharedPreference.strOrderId);
		}

	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		imgTitleBack.setVisibility(View.GONE);
		imgTitleRight.setBackgroundResource(R.drawable.pay_touxiang);
		titleMain.setText(getString(R.string.buy_success_title_main));
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		imgTitleRight = (ImageView) findViewById(R.id.img_title_right);
		titleMain = (TextView) findViewById(R.id.title_main);
		payFailGoStore = (TextView) findViewById(R.id.user_payfail_gostore);
		payFailGoOrder = (TextView) findViewById(R.id.user_payfail_goorder);
		orderId = (TextView) findViewById(R.id.order_id);
		payFail = (LinearLayout) findViewById(R.id.pay_fail);

		tvTitleBack.setOnClickListener(this);
		imgTitleBack.setOnClickListener(this);
		imgTitleRight.setOnClickListener(this);
		payFailGoOrder.setOnClickListener(this);
		payFailGoStore.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			Intent goIndex = new Intent();
			goIndex.setClass(UserBuyFailActivity.this, UserMainActivity.class);
			goIndex.putExtra(Constants.LOGIN_KEY, "goIndex");
			startActivity(goIndex);
			this.finish();
			break;
		case R.id.img_title_right:
			Intent intent = new Intent();
			intent.setClass(UserBuyFailActivity.this, UserMainActivity.class);
			intent.putExtra(Constants.LOGIN_KEY, "PaySuccess");
			startActivity(intent);
			this.finish();
			break;
		case R.id.user_payfail_goorder:
			Intent goorder = new Intent();
			goorder.setClass(this, UserMyOrderActivity.class);
			goorder.putExtra("type", "-1");
			goorder.putExtra("userId", SharedPreference.strUserId);
			startActivity(goorder);
			break;
		case R.id.user_payfail_gostore:
			UserMainActivity.isHaveDate = true;
			ECApplication.isTuangou = true;
			Intent goStore = new Intent();
			goStore.setClass(UserBuyFailActivity.this, UserMainActivity.class);
			goStore.putExtra(Constants.LOGIN_KEY, "goRetail");
			startActivity(goStore);
			this.finish();
			break;

		default:
			break;
		}

	}

	/**
	 * 实体返回键点击事件
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
