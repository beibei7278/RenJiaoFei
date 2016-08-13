//package com.sunsoft.zyebiz.b2e.adapter;
//
///**
// * 功能：结算中心适配器
// */
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.http.Header;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.google.gson.Gson;
//import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
//import com.sunsoft.zyebiz.b2e.common.URLInterface;
//import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
//import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
//import com.sunsoft.zyebiz.b2e.model.shopcart.BodyList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.GoodsList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.Shopcart;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenu;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuCreator;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuItem;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
//import com.sunsoft.zyebiz.b2e.swipemenulistview.SwipeMenuListView.OnSwipeListener;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//import com.sunsoft.zyebiz.b2e.wiget.CustomDialog;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Handler;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemLongClickListener;
//
//@SuppressLint("UseSparseArrays")
//public class ShoppingAdapter extends BaseAdapter {
//
//	/** 视图容器 */
//	private LayoutInflater inflater;
//	/** 缓存子控件 */
//	private ViewHolder holder;
//	/** 继承上下文 */
//	private Context mContext;
//
//	private List<BodyList> list; // 数据集合List
//	private List<GoodsList> goodslist; // 数据集合List
//
//	private int shopNum = 1;
//	private static HashMap<Integer, Boolean> isSelected;
//	private Handler mHandler;
//
//	private ShopAdapter shopAdapter;
//	private SwipeMenuListView listView;
//
//	public ShoppingAdapter(Context context, List<BodyList> list,
//			Handler mHandler) {
//		// TODO Auto-generated constructor stub
//		this.mContext = context;
//		this.list = list;
//		inflater = LayoutInflater.from(context);
//		this.mHandler = mHandler;
//
//	}
//
//	private int dp2px(int dp) {
//		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//				mContext.getResources().getDisplayMetrics());
//	}
//
//	public void showAlertDialog(Context context, String string,
//			final String goodsId, final int position) {
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
//						delShoppingDate(goodsId, position);
//						dialog.dismiss();
//					}
//				});
//
//		builder.create().show();
//
//	}
//
//	public class ViewHolder {
//		private TextView buy_account_company;
//	}
//
//	public int getCount() {
//		// TODO Auto-generated method stub
//
//		if (list == null) {
//			return 1;
//		} else {
//			return list.size();
//		}
//		// return 1;
//	}
//
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
//
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//
//		// ViewHolder holder = null;
//		final BodyList bean = list.get(position);
//
//		if (convertView == null) {
//			holder = new ViewHolder();
//			convertView = inflater.inflate(R.layout.item_shopping, null);
//
//			holder.buy_account_company = (TextView) convertView
//					.findViewById(R.id.buy_account_company);
//			shopAdapter = new ShopAdapter(mContext, list.get(position)
//					.getGoods(), mHandler, list.size());
//			listView = (SwipeMenuListView) convertView
//					.findViewById(R.id.order_all_list);
//			listView.setAdapter(shopAdapter);
//
//			convertView.setTag(holder);
//		} else {
//
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		holder.buy_account_company
//				.setText(list.get(position).getSupplierName());
//
//		goodslist = list.get(position).getGoods();
//		initSwipeMenu();
//
//		return convertView;
//	}
//
//	// 初始化左滑菜单
//	private void initSwipeMenu() {
//		SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//			@Override
//			public void create(SwipeMenu menu) {
//				// 创建编辑控件
//				// SwipeMenuItem editItem = new SwipeMenuItem(
//				// MainActivity.mainActivity);
//				// editItem.setBackground(new ColorDrawable(Color.rgb(0x33,
//				// 0xCC,
//				// 0xFF)));
//				// editItem.setWidth(dp2px(90));
//				// editItem.setTitle("编辑");
//				// editItem.setTitleSize(18);
//				// editItem.setTitleColor(Color.WHITE);
//				// menu.addMenuItem(editItem);
//
//				SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
//				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//						0x3F, 0x25)));
//				deleteItem.setWidth(dp2px(90));
//				deleteItem.setTitle("删除");
//				deleteItem.setTitleSize(18);
//				deleteItem.setTitleColor(Color.WHITE);
//				menu.addMenuItem(deleteItem);
//			}
//		};
//
//		listView.setMenuCreator(creator);
//
//		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			@Override
//			public void onMenuItemClick(final int position, SwipeMenu menu,
//					int index) {
//				System.out.println("po:" + position);
//				System.out.println("index:" + index);
//				System.out
//						.println("id:" + goodslist.get(position).getGoodsId());
//				// 删除
//				// showDialogDelete(list.get(position).getGoods().get(index).getGoodsId());
//				showAlertDialog(mContext, "删除", goodslist.get(position)
//						.getGoodsId(), position);
//
//			}
//		});
//
//		listView.setOnSwipeListener(new OnSwipeListener() {
//
//			@Override
//			public void onSwipeStart(int position) {
//				// swipe start
//			}
//
//			@Override
//			public void onSwipeEnd(int position) {
//				// swipe end
//			}
//		});
//
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				return false;
//			}
//		});
//
//	}
//
//	/**
//	 * 删除购物车的数据
//	 */
//	private void delShoppingDate(String goodsId, final int position) {
//
//		// 数据查询
//		RequestParams params = new RequestParams();
//		params.put("goodsId", goodsId);
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
//									list.remove(position);
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
//
//}
