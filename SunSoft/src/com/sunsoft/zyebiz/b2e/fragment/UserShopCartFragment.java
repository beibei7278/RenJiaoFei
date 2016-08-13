package com.sunsoft.zyebiz.b2e.fragment;

/**
 * 功能：购物车页面修改后单List
 * @author YinGuiChun
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.activity.UserBuyAccountActivity;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.adapter.ShopCartAdapter;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.shopcart.BodyResult;
import com.sunsoft.zyebiz.b2e.model.shopcart.GoodObject;
import com.sunsoft.zyebiz.b2e.model.shopcart.ShopcartDel;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenu;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuCreator;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuItem;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.sunsoft.zyebiz.b2e.wiget.ShopCartPullRefreshView;
import com.sunsoft.zyebiz.b2e.wiget.ShopCartPullRefreshView.RefreshListener;
import com.umeng.analytics.MobclickAgent;

public class UserShopCartFragment extends Fragment implements OnClickListener,
		RefreshListener {
	private TextView tvMainText;
	private RelativeLayout relShopping;
	private TextView goConsult;
	private OnShopCartListener mCallback;
	private SwipeMenuListView listview = null;
	public List<GoodObject> list;
	private RelativeLayout relNoDate, relHaveDate, relNoNetWork;
	private LinearLayout shopcartLi;
	private TextView shopcartHeJi, shopcartSum, tvCheckAll, tvWenZiCheckAll,
			mLoadAgain;
	private CheckBox checkAll;
	private static boolean flag = true;
	private RelativeLayout shopcartPay;
	private static int selectNum;
	private ShopCartAdapter adapter;
	private View headerView;
	private String strShopCartId;
	private ShopCartPullRefreshView mRefreshableView;

	Handler handerupdate = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			mRefreshableView.finishRefresh();
			isNetWorkCon();
			flag = false;
			selectedAll();
			flag = true;
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mBaseView = inflater.inflate(R.layout.fragment_shoping_nomessage,
				null);
		initView(mBaseView);
		isNetWorkCon();
		initDate();
		initSwipeMenu();
		return mBaseView;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void initDate() {
		tvMainText.setText(getString(R.string.shoocart));
		relShopping.setVisibility(View.GONE);
		shopcartPay.setClickable(false);
		checkAll.setChecked(false);
		shopcartSum.setText("0.00");
		listview.addHeaderView(headerView, null, false);
		mLoadAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		list = new ArrayList<GoodObject>();
		adapter = new ShopCartAdapter(UserMainActivity.mainActivity, list,
				handler);
		listview.setAdapter(adapter);
		shopcartHeJi.setText(Html.fromHtml("合计<font color=red>" + 0
				+ "套</font>"));
		flag = false;
		selectedAll();
		flag = true;

	}

	private void initView(View mBaseView) {
		tvMainText = (TextView) mBaseView.findViewById(R.id.title_main);
		// numberXian = (TextView) mBaseView
		// .findViewById(R.id.tv_shopcart_hengxian_number);
		// shopcart_pay_tv = (TextView) mBaseView
		// .findViewById(R.id.shopcart_pay_tv);
		relShopping = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_shopping);
		goConsult = (TextView) mBaseView.findViewById(R.id.bt_go_consult);
		listview = (SwipeMenuListView) mBaseView
				.findViewById(R.id.shopping_list);
		shopcartHeJi = (TextView) mBaseView.findViewById(R.id.shopcart_heji);
		checkAll = (CheckBox) mBaseView.findViewById(R.id.shopcart_all);
		shopcartSum = (TextView) mBaseView.findViewById(R.id.shopcart_sum);
		shopcartPay = (RelativeLayout) mBaseView
				.findViewById(R.id.shopcart_pay);
		relNoDate = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_shopping_no);
		relHaveDate = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_shopping_date);
		shopcartLi = (LinearLayout) mBaseView.findViewById(R.id.shopcart_ll);
		mRefreshableView = (ShopCartPullRefreshView) mBaseView
				.findViewById(R.id.refresh_root);
		headerView = UserMainActivity.mainActivity.getLayoutInflater().inflate(
				R.layout.listhead, null);
		tvCheckAll = (TextView) mBaseView.findViewById(R.id.tv_shopcart_all);
		tvWenZiCheckAll = (TextView) mBaseView
				.findViewById(R.id.tv_wenzi_check_all);
		relNoNetWork = (RelativeLayout) mBaseView
				.findViewById(R.id.rel_shopping_network_no);
		mLoadAgain = (TextView) mBaseView.findViewById(R.id.bt_load_again);
		// relSetNetwork = (RelativeLayout) mBaseView
		// .findViewById(R.id.rel_no_work);

		goConsult.setOnClickListener(this);
		shopcartPay.setOnClickListener(this);
		checkAll.setOnClickListener(this);
		mRefreshableView.setRefreshListener(this);
		tvCheckAll.setOnClickListener(this);
		tvWenZiCheckAll.setOnClickListener(this);
		mLoadAgain.setOnClickListener(this);
		// relSetNetwork.setOnClickListener(this);

	}

	private void receiveShoppingDate() {
		RequestParams params = new RequestParams();
		params.put("userId", SharedPreference.strUserId);
		params.put("token", SharedPreference.strUserToken);
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.SHOPPING_CART_DATE, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);

							BodyResult shopbean = gson.fromJson(resultDate,
									BodyResult.class);
							System.out.println("111resultDate:" + resultDate);
							if (EmptyUtil.isNotEmpty(shopbean.getBody())) {
								list = new ArrayList<GoodObject>();
								list.clear();
								list = shopbean.getBody();

								if (EmptyUtil.isNotEmpty(list)) {

									isNoMessage();
									adapter = new ShopCartAdapter(
											UserMainActivity.mainActivity,
											list, handler);
									listview.setAdapter(adapter);

								} else {
									isNoMessage();
								}
							} else {
								isNoMessage();
							}

						} else {
							System.out.println("statusCode:" + statusCode);
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						relHaveDate.setVisibility(View.GONE);
						shopcartLi.setVisibility(View.GONE);
						relNoDate.setVisibility(View.GONE);
						relNoNetWork.setVisibility(View.VISIBLE);

					}
				});
	}

	private void initSwipeMenu() {
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {

				SwipeMenuItem deleteItem = new SwipeMenuItem(
						UserMainActivity.mainActivity);
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setTitle(getString(R.string.delete));
				deleteItem.setTitleSize(18);
				deleteItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(deleteItem);
			}
		};

		listview.setMenuCreator(creator);

		listview.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {

				showDialogDelete(position);

			}
		});

		listview.setOnSwipeListener(new OnSwipeListener() {

			public void onSwipeStart(int position) {
			}

			public void onSwipeEnd(int position) {
			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return false;
			}
		});

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void showDialogDelete(final int position) {
		CustomDialog.Builder builder = new CustomDialog.Builder(
				UserMainActivity.mainActivity);
		builder.setMessage(getString(R.string.shorcart_delete_title));
		builder.setTitle(getString(R.string.dialog_login_title));
		builder.setPositiveButton(getString(R.string.dialog_login_cancle),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(getString(R.string.dialog_login_ok),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (!NetManager.isWifi() && !NetManager.isMoble()) {
							Toast.makeText(UserMainActivity.mainActivity,
									getString(R.string.network_message_no),
									Toast.LENGTH_SHORT).show();

						} else {
							flag = false;
							selectedAll();
							flag = true;
							delShoppingDate(list.get(position).getRecId(),
									position);
						}

						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	public void showAlertDialog(Context context, String string,
			final int position) {

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Constants.CONSTANT_TEN) {
				double price = (Double) msg.obj;
				System.out.println("price:" + price);
				if (price > Constants.CONSTANT_ZERO) {
					System.out.println("moneyFenGe(price).toString():");
					shopcartSum.setText("￥" + numberFormat(price));
				} else {
					shopcartSum.setText("0.00");
				}
			} else if (msg.what == Constants.CONSTANT_ELEVEN) {
				selectNum = (Integer) msg.obj;
				System.out.println("selectNum:" + selectNum);
				shopcartHeJi.setText(Html.fromHtml("合计<font color=red>"
						+ numberFenGe(selectNum) + "套</font>"));
				isAccountClick();
			} else if (msg.what == Constants.CONSTANT_TWELFTH) {
				flag = !(Boolean) msg.obj;
				checkAll.setChecked((Boolean) msg.obj);
			} else if (msg.what == Constants.CONSTANT_THIRTEENTH) {
				strShopCartId = (String) msg.obj;
				System.out.println("strShopCartId:" + strShopCartId);

			}
		}
	};

	private String moneyFenGe(double n) {
		if (n == 0.00) {
			return n + "";
		} else {
			DecimalFormat df = new DecimalFormat("#,###.00");
			String m = df.format(n);
			return m;
		}

	}

	private String numberFenGe(double n) {
		DecimalFormat df = new DecimalFormat("#,###");
		String m = df.format(n);
		return m;
	}

	public String numberFormat(double n) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(Constants.CONSTANT_TWO);
		format.setMaximumFractionDigits(Constants.CONSTANT_TWO);
		format.setMaximumIntegerDigits(Constants.CONSTANT_TEN);
		format.setMinimumIntegerDigits(Constants.CONSTANT_ONE);
		return format.format(n);

	}

	private void isAccountClick() {
		if (selectNum == Constants.CONSTANT_ZERO) {
			shopcartPay.setBackgroundColor(0xffbebebe);
			shopcartPay.setClickable(false);
		} else {
			shopcartPay.setBackgroundColor(0xffff7e00);
			shopcartPay.setClickable(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_go_consult:
			mCallback.goSchoolStore();
			break;
		case R.id.tv_wenzi_check_all:
		case R.id.tv_shopcart_all:
		case R.id.shopcart_all:
			selectedAll();
			break;
		case R.id.shopcart_pay:
			if (!NetManager.isWifi() && !NetManager.isMoble()) {
				Toast.makeText(UserMainActivity.mainActivity,
						getString(R.string.network_message_no),
						Toast.LENGTH_SHORT).show();
				return;
			}
			Intent goPaySuccess = new Intent();
			goPaySuccess.setClass(getActivity(), UserBuyAccountActivity.class);
			goPaySuccess.putExtra("recId", strShopCartId.substring(
					Constants.CONSTANT_ZERO, strShopCartId.length()
							- Constants.CONSTANT_ONE));
			getActivity().startActivity(goPaySuccess);
			break;
		case R.id.bt_load_again:
			isNetWorkCon();
			break;
		// case R.id.rel_no_work:
		// Intent intent = null;
		//
		// if (android.os.Build.VERSION.SDK_INT > 10) {
		// intent = new Intent(Settings.ACTION_SETTINGS);
		// } else {
		// intent = new Intent();
		// ComponentName component = new ComponentName(
		// "com.android.settings",
		// "com.android.settings.WirelessSettings");
		// intent.setComponent(component);
		// intent.setAction("android.intent.action.VIEW");
		// }
		// UserMainActivity.mainActivity.startActivity(intent);
		//
		// break;

		default:
			break;
		}

	}

	private void selectedAll() {
		if (list.size() == Constants.CONSTANT_ZERO) {
			shopcartSum.setText("0.00");
			checkAll.setChecked(false);
			selectNum = Constants.CONSTANT_ZERO;

		}
		System.out.println("list.size():" + list.size());
		for (int i = Constants.CONSTANT_ZERO; i < list.size(); i++) {
			ShopCartAdapter.getIsSelected().put(i, flag);
		}
		adapter.notifyDataSetChanged();
	}

	public interface OnShopCartListener {
		public void goSchoolStore();
	}

	public void onAttach(Activity activity) {

		try {
			mCallback = (OnShopCartListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}

		super.onAttach(activity);
	}

	private void isNoMessage() {
		if (NetManager.isWifi() || NetManager.isMoble()) {
			relNoNetWork.setVisibility(View.GONE);
			if (list.size() == Constants.CONSTANT_ZERO || list == null) {
				relHaveDate.setVisibility(View.GONE);
				shopcartLi.setVisibility(View.GONE);
				relNoDate.setVisibility(View.VISIBLE);
			} else {
				relNoDate.setVisibility(View.GONE);
				relHaveDate.setVisibility(View.VISIBLE);
				shopcartLi.setVisibility(View.VISIBLE);
			}
		} else {
			relHaveDate.setVisibility(View.GONE);
			shopcartLi.setVisibility(View.GONE);
			relNoDate.setVisibility(View.GONE);
			relNoNetWork.setVisibility(View.VISIBLE);

		}

	}

	private void isNetWorkCon() {
		if (NetManager.isWifi() || NetManager.isMoble()) {
			relNoNetWork.setVisibility(View.GONE);
			receiveShoppingDate();
		} else {
			Toast.makeText(UserMainActivity.mainActivity,
					getString(R.string.network_message_no), Toast.LENGTH_SHORT)
					.show();
			relHaveDate.setVisibility(View.GONE);
			shopcartLi.setVisibility(View.GONE);
			relNoDate.setVisibility(View.GONE);
			relNoNetWork.setVisibility(View.VISIBLE);
		}
	}

	public void onRefresh(ShopCartPullRefreshView view) {
		handerupdate.sendEmptyMessageDelayed(Constants.CONSTANT_ONE,
				Constants.CONSTANT_TWO_THOUSAND);

	}

	private void delShoppingDate(String recId, final int position) {
		RequestParams params = new RequestParams();
		params.put("recId", recId);
		params.put("token", SharedPreference.strUserToken);
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.SHOPPING_CART_DEL, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							ShopcartDel shopcart = gson.fromJson(resultDate,
									ShopcartDel.class);
							String title = shopcart.getTitle();

							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									list.remove(position);
									flag = false;
									selectedAll();
									flag = true;
									adapter.notifyDataSetChanged();
									initSwipeMenu();
									isNoMessage();
								}

							}

						}
					}

					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						isNoMessage();
					}
				});
	}

}
