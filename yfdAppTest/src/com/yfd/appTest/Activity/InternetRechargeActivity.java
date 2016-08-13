package com.yfd.appTest.Activity;

/**
 * 功能：物联卡充值页面
 * 
 * @author YinGuiChun
 * @date 2016-07-22
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.weixin.payActivity.WXPayActivity;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.Czbackbeans;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.LoginBeansmsg;
import com.yfd.appTest.Beans.PhonesbBeans;
import com.yfd.appTest.Beans.PsbBeans;
import com.yfd.appTest.Beans.addplBeans;
import com.yfd.appTest.Beans.addpriceBeans;
import com.yfd.appTest.Common.Constants;
import com.yfd.appTest.CustomView.CustomDialogTishi;
import com.yfd.appTest.CustomView.GrapeGridview;
import com.yfd.appTest.CustomView.SelectZfuPopupWindow;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.CheckUtil;
import com.yfd.appTest.Utils.EmptyUtil;
import com.yfd.appTest.Utils.MD5Util;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.Adapteradd;
import com.yfd.appTest.adapter.Adapterprice;
import com.yfd.appTest.zfb.activity.ZfbPayActivity;

public class InternetRechargeActivity extends BaseActivity {
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
	private PhonesbBeans phonebeans;
	private PsbBeans pbeans;
	int pricechose;
	SelectZfuPopupWindow popwindow;
	Czbackbeans czbackbeans;
	String sjp;

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
				System.out.println("llllllll---------" + msg.obj.toString());
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
				System.out.println(msg.obj.toString());
				phonebeans = (PhonesbBeans) Utils.jsonToBean(
						msg.obj.toString(), PhonesbBeans.class);
				if (phonebeans.getCode().equals("0")) {
					// phonenumprice.setText(new
					// java.text.DecimalFormat("#.00").format(Double.valueOf(phonebeans.getDisprice())*Double.valueOf(listp.get(pricechose).getPrice())));
					phonenumprice.setText(phonebeans.getDisprice());
					tijiao.setEnabled(true);
				}

			}

		};

	};

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {
		public void onClick(View v) {
			popwindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_wx:
				Intent intent = new Intent(InternetRechargeActivity.this,
						WXPayActivity.class);
				intent.putExtra("p", phonenumedit.getText().toString());
				intent.putExtra("pno", sjprice.getText().toString());
				intent.putExtra("pz", phonebeans.getDisprice());
//				intent.putExtra("pz", "0.01");
				intent.putExtra("czl", "");
				startActivity(intent);
				break;
			case R.id.btn_zfb:
				Intent intentz = new Intent(InternetRechargeActivity.this,
						ZfbPayActivity.class);
				intentz.putExtra("p", phonenumedit.getText().toString());
				intentz.putExtra("pno", sjprice.getText().toString());
				intentz.putExtra("pz", phonebeans.getDisprice());
				intentz.putExtra("czl", "");
				startActivity(intentz);
				break;

			case R.id.btn_pt:
				CustomDialogTishi.Builder builder = new CustomDialogTishi.Builder(
						con);
				builder.setMessage("话费充值确认！");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								showloading("");
								String param = "callbackUrl=http://127.0.0.1"
										+ "&customerOrderId="
										+ Utils.Get32ocdeStr()
										+ "&orderType=2"
										+ "&phoneNo="
										+ phonenumedit.getText().toString()
										+ "&scope=nation"
										+ "&spec="
										+ String.valueOf(Integer.valueOf(sjp) * 100)
										+ "&terminalName="
										+ BaseApplication.loginbeans.getMsg()
												.getUsName() + "&timeStamp="
										+ Utils.get17YYMMDDSSS();
								AnsyPost.getkkSearch(
										BaseApplication.IPC
												+ BaseApplication.HFCZ_URL
												+ "?" + param + "&signature="
												+ MD5Util.string2MD5(param),
										handlerlocalcz);

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

				break;
			default:
				break;
			}

		}

	};
	private TextView pwhere, plx, sjprice, zheprice, phonenumprice;
	private Button tijiao;
	String tname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet_recharge);
		if (BaseApplication.loginbeans.getType().equals("0")) {
			tname = BaseApplication.loginbeans.getMsg().getUsName();
		} else {
			tname = "appzfb";
		}
		addlistview = (GrapeGridview) findViewById(R.id.phonenumlist);
		pricelistview = (GrapeGridview) findViewById(R.id.pricelist);
		phonenumedit = (EditText) findViewById(R.id.phonenum_edit);
		back = (ImageView) findViewById(R.id.hfcz_back);
		pwhere = (TextView) findViewById(R.id.p_pname);
		plx = (TextView) findViewById(R.id.p_plx);
		tijiao = (Button) findViewById(R.id.tijiaocz);
		sjprice = (TextView) findViewById(R.id.sjprice);
		zheprice = (TextView) findViewById(R.id.zheprice);
		phonenumprice = (TextView) findViewById(R.id.phonenum_zheprice);
		addplbeans = new addplBeans();
		con = this;
		addplbeans.setLiul("");
		addplbeans.setPhonenum("");
		addplbeans.setPrice("");

		list.add(addplbeans);

		adapteradd = new Adapteradd(list, this);
		addlistview.setAdapter(adapteradd);

		addpriceBeans price1 = new addpriceBeans();
		price1.setName("10元");
		price1.setPrice("10");
		addpriceBeans price2 = new addpriceBeans();
		price2.setName("20元");
		price2.setPrice("20");

		addpriceBeans price3 = new addpriceBeans();
		price3.setName("30元");
		price3.setPrice("30");
		addpriceBeans price4 = new addpriceBeans();
		price4.setName("50元");
		price4.setPrice("50");
		addpriceBeans price5 = new addpriceBeans();
		price5.setName("100元");
		price5.setPrice("100");
		addpriceBeans price6 = new addpriceBeans();
		price6.setName("200元");
		price6.setPrice("200");
		addpriceBeans price7 = new addpriceBeans();
		price7.setName("300元");
		price7.setPrice("300");
		addpriceBeans price8 = new addpriceBeans();
		price8.setName("500元");
		price8.setPrice("500");

		listp.add(price1);
		listp.add(price2);
		listp.add(price3);
		listp.add(price4);
		listp.add(price5);
		listp.add(price6);
		listp.add(price7);
		listp.add(price8);
		// listp.add(price9);

		adapterprice = new Adapterprice(listp, this);
		pricelistview.setAdapter(adapterprice);
		pricelistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				adapterprice.update(arg2);
				pricechose = arg2;
				sjprice.setText(listp.get(arg2).getPrice());
				sjp = listp.get(arg2).getPrice();

//				if (CheckUtil.isMobile(phonenumedit.getText().toString())) {
//					getZheKouPrice(arg2);

					AnsyPost.ChanegPsd(
							BaseApplication.INTERNET_ZHE_KOU
									+ "?billPrice="
									+ listp.get(arg2).getPrice()
									+ "&orderType=2"
									+ "&phoneNo="
									+ phonenumedit.getText().toString()
									+ "&terminalName="
									+ tname
									+ "&signature="
									+ MD5Util.string2MD5("billPrice="
											+ listp.get(arg2).getPrice()
											+ "&orderType=2" + "&phoneNo="
											+ phonenumedit.getText().toString()
											+ "&terminalName=" + tname),
							handler);
//				}else{
//					Toast.makeText(getApplicationContext(),
//							R.string.internet_recharge_wrong_phone,
//							Constants.NUMBER_ONE_THOUSAND).show();
//				}

			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

//		phonenumedit.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//					if (phonenumedit.getText().toString().length()==13)  {
//						AnsyPost.ChanegPsd(BaseApplication.PINFO_SB
//								+ "?mobileNumber="
//								+ phonenumedit.getText().toString(), handlerp);
//					} else {
//						pwhere.setText("");
//						plx.setText("");
//					}
//				
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

		tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

//				if (CheckUtil.isMobile(phonenumedit.getText().toString())) {

					if (!Utils.isEmpty(zheprice.getText().toString())) {
						popwindow = new SelectZfuPopupWindow(
								InternetRechargeActivity.this, itemsOnClick);
						popwindow.showAtLocation(InternetRechargeActivity.this
								.findViewById(R.id.tijiaocz), Gravity.BOTTOM
								| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
					} else {
						Toast.makeText(con, "请选择金额", 1000).show();
					}

//				}else{
//					Toast.makeText(getApplicationContext(),
//							R.string.internet_recharge_wrong_phone,
//							Constants.NUMBER_ONE_THOUSAND).show();
//				}

			}
		});

	}
	private void getZheKouPrice(int arg2) {
		final Gson gson = new Gson();
		RequestParams params = new RequestParams();
		
		String signature =MD5Util.string2MD5("billPrice="
				+ listp.get(arg2).getPrice()
				+ "&orderType=2" + "&phoneNo="
				+ phonenumedit.getText().toString()
				+ "&terminalName=" + tname);

		params.put("billPrice", listp.get(arg2).getPrice());
		params.put("orderType", "2");
		params.put("phoneNo",phonenumedit.getText().toString());
		params.put("terminalName", tname);
		params.put("signature", signature);

		AsyncHttpUtil.post(
				BaseApplication.INTERNET_ZHE_KOU, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == 200) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
//							CommonBean commonBean = gson.fromJson(resultDate,
//									CommonBean.class);

						}
					}

					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(getApplicationContext(),
								"连接失败",
								Constants.NUMBER_ONE_THOUSAND).show();

					}

				

				});
	}
	
	
}
