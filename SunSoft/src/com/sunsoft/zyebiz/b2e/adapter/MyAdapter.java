//package com.sunsoft.zyebiz.b2e.adapter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import org.apache.http.Header;
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.activity.UserRetailXQActivity;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.shopcart.GoodsList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.Shopcart;
//import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
//import com.sunsoft.zyebiz.b2e.wiget.FrontViewToMove;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Handler;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView.ScaleType;
//
///**
// * expandableListView适配器
// * 
// */
//public class MyAdapter extends BaseExpandableListAdapter {
//	private Context context;
//	private List<String> groupList;
//	private ArrayList<ArrayList<GoodsList>> childList;
//	/** DisplayImageOptions是用于设置图片显示的类 */
//	private DisplayImageOptions options;
//	/** 获得网络图片 */
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//
//	GoodsList goodsList;
//	ExpandableListView listview;
//	private static HashMap<Integer, Boolean> isSelected;
//	private Handler mHandler;
//
//	private Button shopcart_sub;
//	private TextView shopcart_count;
//	private Button shopcart_add;
//
//	ArrayList<GoodsList> itemList;
//	int shopNum = 1;
//
//	public MyAdapter(Context context, List<String> group2,
//			ArrayList<ArrayList<GoodsList>> childList,
//			ExpandableListView listview, Handler mHandler,
//			ArrayList<GoodsList> child) {
//		this.context = context;
//		this.groupList = group2;
//		this.childList = childList;
//		this.listview = listview;
//		this.mHandler = mHandler;
//		this.itemList = child;
//
//		isSelected = new HashMap<Integer, Boolean>();
//		initDate();
//	}
//
//	// 初始化isSelected的数据
//	private void initDate() {
//		for (int i = 0; i < itemList.size(); i++) {
//			getIsSelected().put(i, false);
//		}
//	}
//
//	public static HashMap<Integer, Boolean> getIsSelected() {
//		return isSelected;
//	}
//
//	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
//		MyAdapter.isSelected = isSelected;
//	}
//
//	public Object getChild(int groupPosition, int childPosition) {
//		return childList.get(groupPosition).get(childPosition);
//	}
//
//	public long getChildId(int groupPosition, int childPosition) {
//		return childPosition;
//	}
//
//	public int getChildrenCount(int groupPosition) {
//		return childList.get(groupPosition).size();
//	}
//
//	public View getChildView(final int groupPosition, final int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//
//		ViewHolder holder;
//		if (convertView == null) {
//			convertView = LayoutInflater.from(context).inflate(
//					R.layout.item_shop, null);
//			holder = new ViewHolder();
//
//			shopcart_sub = (Button) convertView.findViewById(R.id.shopcart_sub);
//			holder.shopcart_count = (TextView) convertView
//					.findViewById(R.id.shopcart_edit_count);
//			shopcart_add = (Button) convertView.findViewById(R.id.shopcart_add);
//			holder.shopcart_price_black = (TextView) convertView
//					.findViewById(R.id.buy_account_goods_price_black);
//			holder.buy_account_company = (TextView) convertView
//					.findViewById(R.id.buy_account_company);
//			holder.buy_account_goods_title = (TextView) convertView
//					.findViewById(R.id.buy_account_goods_title);
//			holder.buy_account_goods_color_zhi = (TextView) convertView
//					.findViewById(R.id.buy_account_goods_color_zhi);
//			holder.buy_account_goods_price_red = (TextView) convertView
//					.findViewById(R.id.buy_account_goods_price_red);
//			holder.buy_account_goods_chima_zhi = (TextView) convertView
//					.findViewById(R.id.buy_account_goods_chima_zhi);
//			holder.buy_account_goods_pic = (ImageView) convertView
//					.findViewById(R.id.buy_account_goods_pic);
//			holder.shopcart_check = (CheckBox) convertView
//					.findViewById(R.id.shopcart_check);
//			holder.button = (Button) convertView.findViewById(R.id.btn_delete);
//			holder.frontView = (LinearLayout) convertView
//					.findViewById(R.id.id_front);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		goodsList = (GoodsList) getChild(groupPosition, childPosition);
//
//		holder.buy_account_goods_title.setText(goodsList.getGoodsName());
//		if (goodsList.getShangpinNum() != null) {
//			holder.shopcart_count.setText(goodsList.getShangpinNum());
//		} else {
//			goodsList.setShangpinNum(1 + "");
//			holder.shopcart_count.setText(goodsList.getShangpinNum());
//		}
//		holder.shopcart_count.setText(goodsList.getShangpinNum());
//
//		int childCount = 0;
//		for (int i = 0; i < groupPosition; i++) {
//			childCount += getChildrenCount(i);
//		}
//		if (getIsSelected().get(childCount + childPosition) == null) {
//			holder.shopcart_check.setChecked(false);
//		} else {
//			holder.shopcart_check.setChecked(getIsSelected().get(
//					childCount + childPosition));
//		}
//		holder.shopcart_check
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton arg0,
//							boolean flag) {
//						childList.get(groupPosition).get(childPosition)
//								.setChoosed(flag);
//
//						int childCount = 0;
//						for (int i = 0; i < groupPosition; i++) {
//							childCount += getChildrenCount(i);
//						}
//						getIsSelected().put(childCount + childPosition, flag);
//
//						for (int i = 0; i < itemList.size(); i++) {
//							GoodsList bean = itemList.get(i);
//							if (getIsSelected().get(i) == null) {
//								bean.setChoosed(false);
//							} else {
//								bean.setChoosed(getIsSelected().get(i));
//							}
//						}
//
//						// 选中后，发送选中商品的总价
//						mHandler.sendMessage(mHandler.obtainMessage(10,
//								getShopcartSum()));
//						// 选中商品的数量
//						mHandler.sendMessage(mHandler.obtainMessage(11,
//								getShopcartNum()));
//						// 如果所有的物品全部被选中，则全选按钮也默认被选中
//						mHandler.sendMessage(mHandler.obtainMessage(12,
//								isAllSelected()));
//					}
//				});
//
//		shopcart_sub.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				goodsList = (GoodsList) getChild(groupPosition, childPosition);
//				// int shopNum = 1;
//				if (goodsList.getShangpinNum() != null) {
//					shopNum = Integer.parseInt(goodsList.getShangpinNum());
//				}
//				if (shopNum > 1) {
//					shopNum--;
//					goodsList.setShangpinNum(shopNum + "");
//				} else {
//					Toast toast = Toast.makeText(context, "就剩一件了",
//							Toast.LENGTH_SHORT);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//
//				}
//				 if (goodsList.isChoosed()) {
//				 // 选中后，发送选中商品的总价
//				 mHandler.sendMessage(mHandler.obtainMessage(10,
//				 getShopcartSum()));
//				 // 选中商品的数量
//				 mHandler.sendMessage(mHandler.obtainMessage(11,
//				 getShopcartNum()));
//				 }
//				notifyDataSetChanged();
//
//			}
//
//		});
//		shopcart_add.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				goodsList = (GoodsList) getChild(groupPosition, childPosition);
//				System.out.println(" goodsList " + goodsList + " "
//						+ groupPosition + " " + childPosition);
//				// int shopNum = 1;
//				if (goodsList.getShangpinNum() != null) {
//					shopNum = Integer.parseInt(goodsList.getShangpinNum());
//				}
//				shopNum++;
//				goodsList.setShangpinNum(shopNum + "");
//
//				 if (goodsList.isChoosed()) {
//				 // 选中后，发送选中商品的总价
//				 mHandler.sendMessage(mHandler.obtainMessage(10,
//				 getShopcartSum()));
//				 // 选中商品的数量
//				 mHandler.sendMessage(mHandler.obtainMessage(11,
//				 getShopcartNum()));
//				 }
//				notifyDataSetChanged();
//
//			}
//		});
//
//		holder.buy_account_goods_title.setText(goodsList.getGoodsName());
//		holder.buy_account_goods_color_zhi.setText(goodsList.getGoodsColor());
//		holder.buy_account_goods_price_red.setText(goodsList.getShopPrice());
//		holder.shopcart_price_black.setText(goodsList.getMarketPrice());
//		holder.buy_account_goods_chima_zhi.setText(goodsList.getGoodsChi_ma());
//
//		holder.shopcart_price_black.getPaint().setFlags(
//				Paint.STRIKE_THRU_TEXT_FLAG);
//
//		/**
//		 * imageLoader初始化
//		 */
//		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//		// ** 使用DisplayImageOptions.Builder()创建DisplayImageOptions *//*
//
//		options = new DisplayImageOptions.Builder().build();
//
//		String picPath;
//		if (EmptyUtil.isEmpty(goodsList.getGoodsThumb())) {
//			picPath = "expertimg/default.png";
//		} else {
//			picPath = goodsList.getGoodsThumb();
//		}
//		imageLoader
//				.displayImage(picPath, holder.buy_account_goods_pic, options);
//		holder.buy_account_goods_pic.setScaleType(ScaleType.CENTER_CROP);
//
//		holder.buy_account_goods_title
//				.setOnClickListener(new OnClickListener() {
//					// 因为重写ontouch事件使onChildClickListener失效，需要设置次监听来补救
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent();
//						intent.setClass(context, UserRetailXQActivity.class);
//						context.startActivity(intent);
//
//					}
//				});
//
//		new FrontViewToMove(holder.frontView, listview);
//		// 关键语句，使用自己写的类来对frontView的ontouch事件复写，实现视图滑动效果
//
//		holder.button.setOnClickListener(new OnClickListener() {
//			// 为button绑定事件，可以用此按钮来实现删除事件
//
//			@Override
//			public void onClick(View v) {
//				goodsList = (GoodsList) getChild(groupPosition, childPosition);
//				System.out.println("goodsList.getGoodsId():"
//						+ goodsList.getGoodsId());
//				showAlertDialog(context, "是否删除商品", goodsList.getGoodsId(),
//						childPosition, groupPosition);
//
//			}
//		});
//
//		return convertView;
//
//	}
//
//	public void showAlertDialog(Context context, String string,
//			final String goodsId, final int childPosition,
//			final int groupPosition) {
//
//		CustomDialog.Builder builder = new CustomDialog.Builder(context);
//		builder.setMessage(string);
//		builder.setTitle("提示");
//		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				// 设置你的操作事项
//			}
//		});
//
//		builder.setNegativeButton("确定",
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						delShoppingDate(goodsId, childPosition, groupPosition);
//						dialog.dismiss();
//					}
//				});
//
//		builder.create().show();
//
//	}
//
//	public Object getGroup(int groupPosition) {
//		return groupList.get(groupPosition);
//	}
//
//	public int getGroupCount() {
//		return groupList.size();
//	}
//
//	public long getGroupId(int groupPosition) {
//		return groupPosition;
//	}
//
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if (convertView == null) {
//			convertView = LayoutInflater.from(context).inflate(
//					R.layout.item_shopping, null);
//			holder = new ViewHolder();
//			holder.buy_account_company = (TextView) convertView
//					.findViewById(R.id.buy_account_company);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.buy_account_company.setText(groupList.get(groupPosition));
//		return convertView;
//	}
//
//	/**
//	 * 计算选中商品的金额
//	 * 
//	 * @return 返回需要付费的总金额
//	 */
//	@SuppressWarnings("null")
//	private double getShopcartSum() {
//		GoodsList bean = null;
//		double shopcartSum = 0;
//		for (int i = 0; i < itemList.size(); i++) {
//			bean = itemList.get(i);
//			if (bean.isChoosed()) {
//				if (bean.getShangpinNum() == null) {
//					bean.setShangpinNum("1");
//				}
//				shopcartSum += Integer.parseInt(itemList.get(i)
//						.getShangpinNum())
//						* Double.parseDouble(itemList.get(i).getShopPrice());
//			}
//		}
//
//		System.out.println("总金额："
//				+ Double.valueOf(new java.text.DecimalFormat("0.00")
//						.format(shopcartSum)));
//		return Double.valueOf(new java.text.DecimalFormat("0.00")
//				.format(shopcartSum));
//
//	}
//
//	/**
//	 * 计算选中商品的数量
//	 * 
//	 * @return 返回商品的数量
//	 */
//	private int getShopcartNum() {
//		GoodsList bean = null;
//		int shopcartNum = 0;
//		int itemNum = 1;
//		for (int i = 0; i < itemList.size(); i++) {
//			if (itemList.get(i).getShangpinNum() == null) {
//				itemNum = 1;
//			} else {
//				itemNum = Integer.parseInt(itemList.get(i).getShangpinNum());
//			}
//			bean = itemList.get(i);
//			if (bean.isChoosed()) {
//				shopcartNum += itemNum;
//			}
//		}
//		System.out.println("商品数量：" + shopcartNum);
//		return shopcartNum;
//	}
//
//	/**
//	 * 判断是否购物车中所有的商品全部被选中
//	 * 
//	 * @return true所有条目全部被选中 false还有条目没有被选中
//	 */
//	private boolean isAllSelected() {
//		boolean flag = true;
//		for (int i = 0; i < itemList.size(); i++) {
//			if (getIsSelected().get(i) == null) {
//				break;
//			} else {
//				if (!getIsSelected().get(i)) {
//					flag = false;
//					break;
//				}
//			}
//
//		}
//
//		return flag;
//
//	}
//
//	class ViewHolder {
//		private View frontView;
//		TextView textView;
//		// private Button shopcart_sub;
//		private TextView shopcart_count;
//		// private Button shopcart_add;
//		private TextView shopcart_price_black;
//		private ImageView buy_account_goods_pic;
//		public CheckBox shopcart_check; // 商品选择按钮
//		private TextView buy_account_company, buy_account_goods_title,
//				buy_account_goods_color_zhi, buy_account_goods_price_red,
//				buy_account_goods_chima_zhi;
//		private Button button; // 用于执行删除的button
//		// private View frontView;
//	}
//
//	public boolean hasStableIds() {
//		return true;
//	}
//
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		return true;
//	}
//
//	/**
//	 * 删除购物车的数据
//	 */
//	private void delShoppingDate(String goodsId, final int childPosition,
//			final int groupPosition) {
//
//		// 数据查询
//		RequestParams params = new RequestParams();
//		goodsList = (GoodsList) getChild(groupPosition, childPosition);
//		System.out.println("groupPosition" + groupPosition);
//		System.out.println("position" + childPosition);
//		goodsId = goodsList.getGoodsId();
//		System.out.println("goodsId:" + goodsId);
//		params.put("recId", "9c0584addc0948a1990025368e491934");
//		final Gson gson = new Gson();
//		AsyncHttpUtil.post(URLInterface.SHOPPING_CART_DEL, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						if (statusCode == 200) {
//							String resultDate = new String(responseBody);
//							Shopcart shopcart = gson.fromJson(resultDate,
//									Shopcart.class);
//							String title = shopcart.getTitle();
//							System.out.println("title:" + title);
//
//							if (EmptyUtil.isNotEmpty(title)) {
//								if (title.equals("1")) {
//									// 本地删除
//									System.out.println("本地删除");
//									itemList = (ArrayList<GoodsList>) childList
//											.get(groupPosition);
//									itemList.remove(childPosition);
//									if (itemList.size() == 0) {
//										groupList.remove(groupPosition);
//									}
//									notifyDataSetChanged();
//								}
//
//							} else {
//								System.out.println("body:" + shopcart.getBody());
//
//							}
//
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//						System.out.println("222" + responseBody);
//					}
//				});
//	}
//}
