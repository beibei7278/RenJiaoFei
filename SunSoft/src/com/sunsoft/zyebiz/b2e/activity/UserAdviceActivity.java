package com.sunsoft.zyebiz.b2e.activity;

/**
 * 功能：意见反馈
 * @author YinGuiChun
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import com.sunsoft.zyebiz.b2e.model.shopcart.ShopcartDel;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.SpinerPopWindow;
import com.sunsoft.zyebiz.b2e.wiget.AbstractSpinerAdapter.IOnItemSelectListener;
import com.umeng.analytics.MobclickAgent;

public class UserAdviceActivity extends Activity implements OnClickListener,
		IOnItemSelectListener {

	private TextView tvMainText;
	private TextView tvTitleBack;
	private ImageView imgTitleBack;
	private TextView tvTitleRight;
	private EditText edtInput;
	private SpinerPopWindow mSpinerPopWindow;
	private TextView edtParentShenFen;
	private TextView mBtnDropDown;
	private List<String> nameList = new ArrayList<String>();
	private String msgArea;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advice);
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
		imgTitleBack.setVisibility(View.VISIBLE);
		tvMainText.setText(this.getString(R.string.advice_title_main));
		tvTitleRight.setVisibility(View.VISIBLE);
		tvTitleRight.setText(this.getString(R.string.advice_title_right));

		edtParentShenFen.setText(this.getString(R.string.my_advice_type));
		edtParentShenFen.setTextColor(0xff828282);

		String[] names = getResources().getStringArray(R.array.advice_name);
		for (int i = Constants.CONSTANT_ZERO; i < names.length; i++) {
			nameList.add(names[i]);
		}

		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, Constants.CONSTANT_ZERO);
		mSpinerPopWindow.setItemListener(this);

	}

	/**
	 * 设置意见选择
	 * 
	 * @param pos
	 *            选择位置
	 */
	private void setAdviceType(int pos) {
		if (pos >= Constants.CONSTANT_ZERO && pos <= nameList.size()) {
			String value = nameList.get(pos);
			edtParentShenFen.setText(value);

		}
	}

	/**
	 * 意见选择点击事件
	 */
	public void onItemClick(int pos) {
		setAdviceType(pos);
		switch (pos) {
		case Constants.CONSTANT_ZERO:
			msgArea = Constants.LOGIN_PARENT;
			break;
		case Constants.CONSTANT_ONE:
			msgArea = Constants.LOGIN_JIAOYUJU;
			break;
		case Constants.CONSTANT_TWO:
			msgArea = Constants.LOGIN_CHANG_SHANG;
			break;
		case Constants.CONSTANT_THREE:
			msgArea = Constants.CONSTANT_STRING_SIX;
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		tvTitleRight = (TextView) findViewById(R.id.tv_title_right);

		edtInput = (EditText) findViewById(R.id.advice_edt_input);
		mBtnDropDown = (TextView) findViewById(R.id.bt_dropdown);
		edtParentShenFen = (TextView) findViewById(R.id.edt_regist_parent_shenfen);

		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);
		tvTitleRight.setOnClickListener(this);
		mBtnDropDown.setOnClickListener(this);
		edtParentShenFen.setOnClickListener(this);

	}

	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(edtParentShenFen.getWidth());
		mSpinerPopWindow.showAsDropDown(edtParentShenFen);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;
		case R.id.tv_title_right:
			if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			}

			String strEdtInput = edtInput.getText().toString();

			if (!NetManager.isWifi() && !NetManager.isMoble()) {
				Toast.makeText(UserAdviceActivity.this,
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (EmptyUtil.isEmpty(msgArea)) {
				Toast.makeText(UserAdviceActivity.this,
						getString(R.string.my_advice_type), Toast.LENGTH_SHORT)
						.show();
				return;
			}

			if (EmptyUtil.isEmpty(strEdtInput)) {
				Toast.makeText(UserAdviceActivity.this, "反馈信息不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (edtInput.getText().length() > Constants.CONSTANT_TWO_HUNDRED) {
				Toast.makeText(UserAdviceActivity.this,
						this.getString(R.string.advice_toast_submit_no),
						Toast.LENGTH_SHORT).show();
				return;
			}
			adviceSend();

			break;
		case R.id.edt_regist_parent_shenfen:
		case R.id.bt_dropdown:
			showSpinWindow();
			break;

		default:
			break;
		}

	}

	/**
	 * 发送意见提交信息
	 */
	private void adviceSend() {
		RequestParams params = new RequestParams();
		params.put("userId", SharedPreference.strUserId);
		params.put("msgContent", edtInput.getText().toString());
		params.put("msgArea", msgArea);
		params.put("token", SharedPreference.strUserToken);

		AsyncHttpUtil.post(URLInterface.ABOUT_ZHIYUAN_ADVICE, params,
				new AsyncHttpResponseHandler() {

					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
							ShopcartDel shopcart = gson.fromJson(resultDate,
									ShopcartDel.class);
							String title = shopcart.getTitle();
							System.out.println("title:" + title);

							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									Toast.makeText(
											UserAdviceActivity.this,
											getString(R.string.advice_toast_submit),
											Toast.LENGTH_SHORT).show();
									new Handler().postDelayed(new Runnable() {
										public void run() {

											finish();
										}
									}, Constants.CONSTANT_FIVE_THOUSAND);
								}

							}

						}
					}

					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});
	}

}
