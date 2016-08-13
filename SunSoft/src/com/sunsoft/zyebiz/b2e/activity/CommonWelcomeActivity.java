package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：欢迎页面
 * @author YinGuiChun
 */
import java.util.ArrayList;
import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.adapter.BasePagerAdapter;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.util.SPUtils;
import com.sunsoft.zyebiz.b2e.wiget.CreateShut;
import com.sunsoft.zyebiz.b2e.wiget.Dialog;
import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class CommonWelcomeActivity extends Activity implements
		OnPageChangeListener, OnClickListener {
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private TextView startButton;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		new CreateShut(this);
		images = new int[] { R.drawable.welcome_01, R.drawable.welcome_02,
				R.drawable.welcome_03 };
		initView();

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		startButton = (TextView) findViewById(R.id.start_Button);
		startButton.setOnClickListener(this);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length];
		for (int i = Constants.CONSTANT_ZERO; i < images.length; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == Constants.CONSTANT_ZERO) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i]);
		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.start_Button) {
			SPUtils.put(context, "First", false);
			Intent goMain = new Intent();
			goMain.setClass(CommonWelcomeActivity.this, LoginActivity.class);
			startActivity(goMain);

			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			this.finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		if (arg0 == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);

		} else {
			startButton.setVisibility(View.INVISIBLE);
		}
		for (int i = Constants.CONSTANT_ZERO; i < indicators.length; i++) {
			indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			if (arg0 != i) {
				indicators[i]
						.setBackgroundResource(R.drawable.indicators_default);
			}
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
	 * 实体返回键事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == Constants.CONSTANT_ZERO) {
			Dialog.DialogExit(CommonWelcomeActivity.this,
					getString(R.string.dialog_login_exit));
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
