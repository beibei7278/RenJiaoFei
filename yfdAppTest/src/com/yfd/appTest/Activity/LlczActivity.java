package com.yfd.appTest.Activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weixin.payActivity.WXPayActivity;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.Czbackbeans;
import com.yfd.appTest.Beans.LlzkBeans;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.LoginBeansmsg;
import com.yfd.appTest.Beans.PsbBeans;
import com.yfd.appTest.Beans.addplBeans;
import com.yfd.appTest.Beans.addpriceBeans;
import com.yfd.appTest.Beans.LlzkBeans.Data;
import com.yfd.appTest.CustomView.CustomDialogTishi;
import com.yfd.appTest.CustomView.GrapeGridview;
import com.yfd.appTest.CustomView.SelectZfuPopupWindow;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.MD5Util;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.Adapteradd;
import com.yfd.appTest.adapter.Adapterprice;
import com.yfd.appTest.zfb.activity.ZfbPayActivity;

public class LlczActivity extends BaseActivity {
	List<addplBeans> list = new ArrayList<addplBeans>();
	List<addpriceBeans> listp = new ArrayList<addpriceBeans>();
	GrapeGridview addlistview;
	GrapeGridview pricelistview;
	private addplBeans addplbeans;
	private Adapteradd adapteradd;
	EditText phonenumedit;
	Adapterprice adapterprice;

	ImageView back;
	Context con;
	private LoginBeansmsg loginbeansmsg;
	private LoginBeansStr loginbeansstr;
	private LlzkBeans phonebeans;
	private PsbBeans pbeans;
	int pricechose;
	SelectZfuPopupWindow popwindow;
	private Czbackbeans czbackbeans;
	String tname;
	List<Data> listdata = new ArrayList<Data>();
	long lastClick;
	private Handler handlerlocalcz = new Handler() {

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if (msg.obj != null) {
				czbackbeans = (Czbackbeans) Utils.jsonToBean(
						msg.obj.toString(), Czbackbeans.class);
				Toast.makeText(getApplicationContext(),
						czbackbeans.getMessage(), 1000).show();
			} else {
				Toast.makeText(getApplicationContext(), "请求失败！", 1000).show();
			}

		};

	};
	private Handler handlerp = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj == null) {
				Toast.makeText(con, "获取号码信息失败", 1000).show();
			} else {
				loginbeansmsg = (LoginBeansmsg) Utils.jsonToBean(
						msg.obj.toString(), LoginBeansmsg.class);
				if (loginbeansmsg.getState().equals("0")) {
					loginbeansstr = (LoginBeansStr) Utils.jsonToBean(
							msg.obj.toString(), LoginBeansStr.class);
					Toast.makeText(con, loginbeansstr.getMsg(), 1000).show();
				} else {
					pbeans = (PsbBeans) Utils.jsonToBean(msg.obj.toString(),
							PsbBeans.class);
					pwhere.setText(pbeans.getMsg().getProvince());
					plx.setText(pbeans.getMsg().getMobiletype());
				}

			}

		};

	};

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.obj == null) {
				Toast.makeText(con, "获取折扣信息失败", 1000).show();
			} else {
				listp.clear();
				phonebeans = (LlzkBeans) Utils.jsonToBean(msg.obj.toString(),
						LlzkBeans.class);
				if (phonebeans.getCode().equals("0")) {
					// phonenumprice.setText(Integer.valueOf(phonebeans.getDisprice())*Integer.valueOf(listp.get(pricechose).getPrice()));
					// phonenumprice.setText(phonebeans.getDisprice());
					for (int i = 0; i < phonebeans.getData().size(); i++) {
						addpriceBeans price = new addpriceBeans();
						price.setName(phonebeans.getData().get(i).getPkgname()
								+ "M");
						price.setPrice(phonebeans.getData().get(i)
								.getFlowprice());
						listp.add(price);
					}

					adapterprice = new Adapterprice(listp, con);
					pricelistview.setAdapter(adapterprice);
//					if (phonebeans.getData().size() > 0) {
//
//					}

				} else {
					Toast.makeText(con, phonebeans.getMessage(), 1000).show();
				}

			}

		};

	};

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			// tijiao.setClickable(true);
			popwindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_wx:

				Intent intent = new Intent(LlczActivity.this,
						WXPayActivity.class);
				intent.putExtra("p", phonenumedit.getText().toString());
				intent.putExtra("pz", phonebeans.getData().get(pricechose)
						.getFlowprice());
				intent.putExtra("pno", phonebeans.getData().get(pricechose)
						.getDiscount());
				intent.putExtra("czl", phonebeans.getData().get(pricechose)
						.getPkgname());
				startActivity(intent);

				break;
			case R.id.btn_zfb:

				Intent intentl = new Intent(LlczActivity.this,
						ZfbPayActivity.class);
				intentl.putExtra("p", phonenumedit.getText().toString());
				intentl.putExtra("pz", phonebeans.getData().get(pricechose)
						.getFlowprice());
				intentl.putExtra("pno", phonebeans.getData().get(pricechose)
						.getDiscount());
				intentl.putExtra("czl", phonebeans.getData().get(pricechose)
						.getPkgname());
				startActivity(intentl);

				break;
			case R.id.btn_pt:

				CustomDialogTishi.Builder builder = new CustomDialogTishi.Builder(
						con);
				builder.setMessage("流量充值确认！");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								showloading("");
								String param = "callbackUrl=http://127.0.0.1"
										+ "&customerOrderId="
										+ Utils.Get32ocdeStr()
										+ "&orderType=1"
										+ "&phoneNo="
										+ phonenumedit.getText().toString()
										+ "&scope=nation"
										+ "&spec="
										+ phonebeans.getData().get(pricechose)
												.getPkgname()
										+ "&terminalName=" + tname
										+ "&timeStamp="
										+ Utils.get17YYMMDDSSS();
								AnsyPost.getkkSearch(
										BaseApplication.IPC
												+ BaseApplication.HFCZ_URL
												+ "?" + param + "&signature="
												+ MD5Util.string2MD5(param),
										handlerlocalcz);
								System.out.println(BaseApplication.IPC
										+ BaseApplication.HFCZ_URL + "?"
										+ param + "&signature="
										+ MD5Util.string2MD5(param));

								dialog.dismiss();
							}
						});

				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								;
								dialog.dismiss();
							}
						});

				builder.create().show();

			default:
				break;
			}

		}

	};
	private TextView pwhere, plx, sjprice, zheprice, phonenumprice;
	private Button tijiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.llcz_activity);

		if (BaseApplication.loginbeans.getType().equals("0"))

		{

			tname = BaseApplication.loginbeans.getMsg().getUsName();

		} else {

			tname = "appzfb";

		}

		addlistview = (GrapeGridview) findViewById(R.id.phonenumlistl);
		pricelistview = (GrapeGridview) findViewById(R.id.pricelistl);
		phonenumedit = (EditText) findViewById(R.id.phonenum_editl);
		back = (ImageView) findViewById(R.id.hfcz_backl);
		pwhere = (TextView) findViewById(R.id.p_pnamel);
		plx = (TextView) findViewById(R.id.p_plxl);
		tijiao = (Button) findViewById(R.id.tijiaoczl);

		sjprice = (TextView) findViewById(R.id.sjpricel);
		zheprice = (TextView) findViewById(R.id.zhepricel);
		phonenumprice = (TextView) findViewById(R.id.phonenum_zhepricel);
		addplbeans = new addplBeans();
		con = this;
		addplbeans.setLiul("");
		addplbeans.setPhonenum("");
		addplbeans.setPrice("");

		list.add(addplbeans);

		adapteradd = new Adapteradd(list, this);
		addlistview.setAdapter(adapteradd);

		pricelistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapterprice.update(arg2);
				pricechose = arg2;
				tijiao.setEnabled(true);
				sjprice.setText(phonebeans.getData().get(arg2).getFlowprice());
				phonenumprice.setText(String.valueOf(Double.valueOf(phonebeans
						.getData().get(arg2).getDiscount())
						* Double.valueOf(phonebeans.getData().get(arg2)
								.getFlowprice()) / 10));
				if (!Utils.isEmpty(phonenumedit.getText().toString())) {

					if (phonenumedit.getText().toString().length() == 11) {

					} else {

						Toast.makeText(con, "请输入正确手机号", 1000).show();

					}
				} else {

					Toast.makeText(con, "请输入手机号", 1000).show();
				}

			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		phonenumedit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!Utils.isEmpty(phonenumedit.getText().toString())) {

					if (phonenumedit.getText().toString().length() == 11) {

						AnsyPost.ChanegPsd(BaseApplication.PINFO_SB
								+ "?mobileNumber="
								+ phonenumedit.getText().toString(), handlerp);

						AnsyPost.ChanegPsd(
								BaseApplication.PHPNENUM_SB
										+ "?billPrice=10&orderType=1"
										+ "&phoneNo="
										+ phonenumedit.getText().toString()
										+ "&terminalName="
										+ tname
										+ "&signature="
										+ MD5Util
												.string2MD5("billPrice=10&orderType=1"
														+ "&phoneNo="
														+ phonenumedit
																.getText()
																.toString()
														+ "&terminalName="
														+ tname), handler);
					} else {

						pwhere.setText("");
						plx.setText("");

					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis() - lastClick <= 1000) {
					Toast.makeText(con, "请稍后", 1000).show();
					return;
				}
				lastClick = System.currentTimeMillis();
				popwindow = new SelectZfuPopupWindow(LlczActivity.this,
						itemsOnClick);

				popwindow.showAtLocation(
						LlczActivity.this.findViewById(R.id.tijiaoczl),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			}
		});

	}

}
