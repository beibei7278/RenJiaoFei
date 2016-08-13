//package com.sunsoft.zyebiz.b2e.fragment;
//
///**
// * 功能：普通用户之购物车页面
// * @author YGC
// */
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.http.Header;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.activity.UserBuySuccessActivity;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.shopcart.BodyList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.GoodsList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.Shopcart;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.NLPullRefreshView;
//import com.sunsoft.zyebiz.b2e.wiget.NLPullRefreshView.RefreshListener;
//import com.sunsoft.zyebiz.b2e.wiget.NetManager;
//import com.umeng.analytics.MobclickAgent;
//
//public class TestShopingStoreFragment extends Fragment implements OnClickListener,RefreshListener {
//	/** 标题 */
//	private TextView tvMainText;
//	private TextView numberXian, shopcart_pay_tv;
//	private RelativeLayout relShopping, relShoppingNo;
//
//	private TextView goConsult;
//
//	OnShopingStoreListener mCallback;
//	private ExpandableListView listview = null;
//	public List<BodyList> list;
//	public List<GoodsList> childlist;
//	// public ShoppingAdapter myAdapter;
//	private Context mContext;
//	private RelativeLayout relNoDate, relHaveDate;
//	private LinearLayout shopcartLi;
//	private int shopNum = 1;
//	private TextView shopcartHeJi;
//	private TextView shopcartSum; // 总计
//	private CheckBox checkAll;
//	private static boolean flag = true; // 全选或全取消
//	// private List<BodyList> body ;
//	private RelativeLayout shopcartPay;
//	private static int selectNum; // 选中商品数量
//	private List<String> group;
//	private ArrayList<GoodsList> child;
//	private MyAdapter adapter;
//	ArrayList<GoodsList> itemList;
//	private NLPullRefreshView mRefreshableView;
//	Handler handerupdate = new Handler() {
//		public void handleMessage(Message message) {
//			super.handleMessage(message);
//			mRefreshableView.finishRefresh();
//			adapter.notifyDataSetChanged();
////			testDate();
////			Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
//		};
//	};
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View mBaseView = inflater.inflate(R.layout.fragment_shoping_nomessage,
//				null);
//		NetManager.isHaveNetWork(UserMainActivity.mainActivity);
//		initView(mBaseView);
//
//		receiveShoppingDate();
////		testDate();
//		initDate();
//		return mBaseView;
//	}
//	public void onResume() {
//	    super.onResume();
//	    MobclickAgent.onPageStart("MainScreen"); //统计页面，"MainScreen"为页面名称，可自定义
//	}
//	public void onPause() {
//	    super.onPause();
//	    MobclickAgent.onPageEnd("MainScreen"); 
//	}
//
//
//	private void initDate() {
//		// TODO Auto-generated method stub
//		tvMainText.setText("购物车");
//		relShopping.setVisibility(View.GONE);
//		shopcartPay.setClickable(false);
//		checkAll.setChecked(false);
//		shopcartSum.setText("0");
//		listview.setGroupIndicator(null);
//		listview.setOnGroupClickListener(new OnGroupClickListener() {
//
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		});
//
//	}
//
//	private void initView(View mBaseView) {
//		// TODO Auto-generated method stub
//		tvMainText = (TextView) mBaseView.findViewById(R.id.title_main);
//		numberXian = (TextView) mBaseView
//				.findViewById(R.id.tv_shopcart_hengxian_number);
//		shopcart_pay_tv = (TextView) mBaseView
//				.findViewById(R.id.shopcart_pay_tv);
//		relShopping = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_shopping);
//		goConsult = (TextView) mBaseView.findViewById(R.id.bt_go_consult);
//		relShoppingNo = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_shopping_no);
//		listview = (ExpandableListView) mBaseView
//				.findViewById(R.id.shopping_list);
//		shopcartHeJi = (TextView) mBaseView.findViewById(R.id.shopcart_heji);
//		checkAll = (CheckBox) mBaseView.findViewById(R.id.shopcart_all);
//		shopcartSum = (TextView) mBaseView.findViewById(R.id.shopcart_sum);
//		shopcartPay = (RelativeLayout) mBaseView
//				.findViewById(R.id.shopcart_pay);
//		relNoDate = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_shopping_no);
//		relHaveDate = (RelativeLayout) mBaseView
//				.findViewById(R.id.rel_shopping_date);
//		shopcartLi = (LinearLayout) mBaseView.findViewById(R.id.shopcart_ll);
//		listview = (ExpandableListView) mBaseView
//				.findViewById(R.id.shopping_list);
//		mRefreshableView = (NLPullRefreshView) mBaseView.findViewById(R.id.refresh_root);
//
//		goConsult.setOnClickListener(this);
//		shopcartPay.setOnClickListener(this);
//		checkAll.setOnClickListener(this);
//		mRefreshableView.setRefreshListener(this);
//
//	}
//	
//	private void testDate(){
//		final Gson gson = new Gson();
//		String resultDate = new String("{\"title\":null,\"body\":[{\"supplierName\":\"北京制作\",\"supplierId\":\"sdfasd445\",\"goods\":[{\"goodsId\":\"sdfsdf5512\",\"goodsThumb\":\"http://192.168.1.222/pic/uniformpic/56e89725789549e0befdb54c18c088b2.jpg\",\"goodsName\":\"绛县四中 男女制服\",\"goodsColor\":\"红色\",\"goodsChi_ma\":\"175\",\"shopPrice\":\"560.56\",\"marketPrice\":\"1000.00\",\"number\":\"2\"},{\"goodsId\":\"sdfsdf5512\",\"goodsThumb\":\"http://192.168.1.222/pic/uniformpic/56e89725789549e0befdb54c18c088b2.jpg\",\"goodsName\":\"绛县四中 男女制服\",\"goodsColor\":\"红色\",\"goodsChi_ma\":\"175\",\"shopPrice\":\"452.36\",\"marketPrice\":\"1000.00\",\"number\":\"5\"}]},{\"supplierName\":\"森仕公司\",\"supplierId\":\"fdsf545455\",\"goods\":[{\"goodsId\":\"s4548sdsds12\",\"goodsThumb\":\"http://192.168.1.222/pic/uniformpic/56e89725789549e0befdb54c18c088b2.jpg\",\"goodsName\":\"dddd 男女制服\",\"goodsColor\":\"红色\",\"goodsChi_ma\":\"175\",\"shopPrice\":\"560.00\",\"marketPrice\":\"1000.00\",\"number\":\"5\"}]}]}");
//		Shopcart shopcart = gson.fromJson(resultDate,
//				Shopcart.class);
//		list = shopcart.getBody();
//
//		if (EmptyUtil.isNotEmpty(list)) {
//			isNoMessage();
//			itemList = new ArrayList<GoodsList>();
//			group = new ArrayList<String>();
//			ArrayList<ArrayList<GoodsList>> childList = new ArrayList<ArrayList<GoodsList>>();
//			for (int i = 0; i < list.size(); i++) {
//				group.add(list.get(i).getSupplierName());
//				childlist = list.get(i).getGoods();
//				child = new ArrayList<GoodsList>();
//				for (int j = 0; j < childlist.size(); j++) {
//					GoodsList goodsList = new GoodsList();
//					goodsList.setGoodsName(childlist.get(j)
//							.getGoodsName());
//					goodsList.setGoodsChi_ma(childlist.get(
//							j).getGoodsChi_ma());
//					goodsList.setGoodsColor(childlist
//							.get(j).getGoodsColor());
//					goodsList.setGoodsThumb(childlist
//							.get(j).getGoodsThumb());
//					goodsList.setMarketPrice(childlist.get(
//							j).getMarketPrice());
//					goodsList.setNumber(childlist.get(j)
//							.getNumber());
//					goodsList.setShangpinNum(childlist.get(
//							j).getShangpinNum());
//					goodsList.setShopPrice(childlist.get(j)
//							.getShopPrice());
//					child.add(goodsList);
//					itemList.add(goodsList);
//				}
//				childList.add(child);
//			}
//			System.out.println("itemList.size():"
//					+ itemList.size());
//			System.out.println("child.size():"
//					+ child.size());
//			adapter = new MyAdapter(
//					UserMainActivity.mainActivity, group,
//					childList, listview, handler, itemList);
//			listview.setAdapter(adapter);
//			for (int i = 0; i < adapter.getGroupCount(); i++) {
//				listview.expandGroup(i);
//
//			}
//
//		} else {
//			System.out.println("body:" + shopcart.getBody());
//			isNoMessage();
//		}
//	}
//
//	private void receiveShoppingDate() {
//
//		// 数据查询
//		RequestParams params = new RequestParams();
//		params.put("userId", "ff079338d226492f97e63fbe1ad9dd0d");
//		final Gson gson = new Gson();
//		AsyncHttpUtil.post(URLInterface.SHOPPING_CART_DATE, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						if (statusCode == 200) {
//							System.out.println("购物车数据地址"
//									+ URLInterface.SHOPPING_CART_DATE);
//							String resultDate = new String(responseBody);
//							Shopcart shopcart = gson.fromJson(resultDate,
//									Shopcart.class);
//							list = shopcart.getBody();
//
//							if (EmptyUtil.isNotEmpty(list)) {
//								isNoMessage();
//								itemList = new ArrayList<GoodsList>();
//								group = new ArrayList<String>();
//								ArrayList<ArrayList<GoodsList>> childList = new ArrayList<ArrayList<GoodsList>>();
//								for (int i = 0; i < list.size(); i++) {
//									group.add(list.get(i).getSupplierName());
//									childlist = list.get(i).getGoods();
//									child = new ArrayList<GoodsList>();
//									for (int j = 0; j < childlist.size(); j++) {
//										GoodsList goodsList = new GoodsList();
//										goodsList.setGoodsName(childlist.get(j)
//												.getGoodsName());
//										goodsList.setGoodsChi_ma(childlist.get(
//												j).getGoodsChi_ma());
//										goodsList.setGoodsColor(childlist
//												.get(j).getGoodsColor());
//										goodsList.setGoodsThumb(childlist
//												.get(j).getGoodsThumb());
//										goodsList.setMarketPrice(childlist.get(
//												j).getMarketPrice());
//										goodsList.setNumber(childlist.get(j)
//												.getNumber());
//										goodsList.setShangpinNum(childlist.get(
//												j).getShangpinNum());
//										goodsList.setShopPrice(childlist.get(j)
//												.getShopPrice());
//										child.add(goodsList);
//										itemList.add(goodsList);
//									}
//									childList.add(child);
//								}
//								System.out.println("itemList.size():"
//										+ itemList.size());
//								System.out.println("child.size():"
//										+ child.size());
//								adapter = new MyAdapter(
//										UserMainActivity.mainActivity, group,
//										childList, listview, handler, itemList);
//								listview.setAdapter(adapter);
//								for (int i = 0; i < adapter.getGroupCount(); i++) {
//									listview.expandGroup(i);
//
//								}
//
//							} else {
//								System.out.println("body:" + shopcart.getBody());
//								isNoMessage();
//							}
//
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//						System.out.println("222" + responseBody);
//
//					}
//				});
//	}
//
//	@SuppressLint("HandlerLeak")
//	private Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			// 选中商品后，更改总价格
//			if (msg.what == 10) {
//				double price = (Double) msg.obj;
//				if (price > 0) {
//					shopcartSum.setText("￥" + price);
//				} else {
//					shopcartSum.setText("0");
//				}
//				// 选中商品后，更改总数量
//			} else if (msg.what == 11) {
//				selectNum = (Integer) msg.obj;
//				shopcartHeJi.setText(Html.fromHtml("合计<font color=red>"
//						+ selectNum + "套</font>"));
//				// shopcartPay_tv.setText("去结算(" + selectNum + ")");
//				isAccountClick();
//			} else if (msg.what == 12) {
//				// 所有列表中的商品全部被选中，让全选按钮也被选中
//				// flag记录是否全被选中
//				flag = !(Boolean) msg.obj;
//				checkAll.setChecked((Boolean) msg.obj);
//			}
//		}
//	};
//
//	private void isAccountClick() {
//		if (selectNum == 0) {
//			shopcartPay.setBackgroundColor(0xffbebebe);
//			shopcartPay.setClickable(false);
//		} else {
//			shopcartPay.setBackgroundColor(0xffff7e00);
//			shopcartPay.setClickable(true);
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.bt_go_consult:
//			mCallback.goConsultation();
//			break;
//		case R.id.shopcart_all:
//			selectedAll();
//			break;
//		case R.id.shopcart_pay:
//			Intent goPaySuccess = new Intent();
//			goPaySuccess.setClass(getActivity(), UserBuySuccessActivity.class);
//			getActivity().startActivity(goPaySuccess);
//			UserMainActivity.mainActivity.finish();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	// 全选或全取消
//	private void selectedAll() {
//		if (itemList.size() == 0) {
//			shopcartSum.setText("0");
//			checkAll.setChecked(false);
//			shopcartPay.setClickable(false);
//			selectNum = 0;
//		}
//		for (int i = 0; i < itemList.size(); i++) {
//			MyAdapter.getIsSelected().put(i, flag);
//		}
//		adapter.notifyDataSetChanged();
//	}
//
//	/**
//	 * 回调接口:从学校商城接入首页
//	 * 
//	 */
//	public interface OnShopingStoreListener {
//		public void goConsultation();
//	}
//
//	/**
//	 * 执行回调方法
//	 */
//	@Override
//	public void onAttach(Activity activity) {
//
//		try {
//			mCallback = (OnShopingStoreListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnHeadlineSelectedListener");
//		}
//
//		super.onAttach(activity);
//	}
//
//	private void isNoMessage() {
//		if (list.size() == 0 || list == null) {
//			relHaveDate.setVisibility(View.GONE);
//			shopcartLi.setVisibility(View.GONE);
//			relNoDate.setVisibility(View.VISIBLE);
//		} else {
//			relNoDate.setVisibility(View.GONE);
//			relHaveDate.setVisibility(View.VISIBLE);
//			shopcartLi.setVisibility(View.VISIBLE);
//			shopcartHeJi.setText(Html.fromHtml("合计<font color=red>" + 0
//					+ "套</font>"));
//		}
//	}
//
//	
//	// 实现刷新RefreshListener 中方法
//	public void onRefresh(NLPullRefreshView view) {
//		// 伪处理
//		handerupdate.sendEmptyMessageDelayed(1, 2000);
//
//	}
//
//}
