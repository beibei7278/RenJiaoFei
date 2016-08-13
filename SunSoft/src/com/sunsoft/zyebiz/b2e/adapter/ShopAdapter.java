//package com.sunsoft.zyebiz.b2e.adapter;
//
///**
// * 功能：结算中心适配器
// */
//import java.util.HashMap;
//import java.util.List;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.application.ECApplication;
//import com.sunsoft.zyebiz.b2e.model.BuyAccount.BuyBean;
//import com.sunsoft.zyebiz.b2e.model.shopcart.BodyList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.GoodsList;
//import com.sunsoft.zyebiz.b2e.model.shopcart.ShopcartBean;
//import com.sunsoft.zyebiz.b2e.universalimage.core.DisplayImageOptions;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoader;
//import com.sunsoft.zyebiz.b2e.universalimage.core.ImageLoaderConfiguration;
//import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Paint;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView.ScaleType;
//
//@SuppressLint("UseSparseArrays")
//public class ShopAdapter extends BaseAdapter {
//
//	/** 视图容器 */
//	private LayoutInflater inflater;
//	/** 缓存子控件 */
//	private ViewHolder holder;
//	/** 继承上下文 */
//	private Context mContext;
//
//	private List<GoodsList> list; // 数据集合List
//
//	private int shopNum = 1;
//	private static HashMap<Integer, Boolean> isSelected;
//	private Handler mHandler;
//	/** DisplayImageOptions是用于设置图片显示的类 */
//	private DisplayImageOptions options;
//	/** 获得网络图片 */
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//	/** 外层的个数 */
//	int count;
//
//	// final Button shopcart_sub = holder.shopcart_sub;
//	//
//	// final Button shopcart_add = holder.shopcart_add;
//
//	public ShopAdapter(Context context, List<GoodsList> list, Handler mHandler,
//			int count) {
//		// TODO Auto-generated constructor stub
//		this.mContext = context;
//		this.list = list;
//		inflater = LayoutInflater.from(context);
//		this.mHandler = mHandler;
//		this.count = count;
//
//		isSelected = new HashMap<Integer, Boolean>();
//		initDate();
//	}
//
//	public class ViewHolder {
//		private TextView mXiangXi;
//		private Button shopcart_sub;
//		private TextView shopcart_count;
//		private Button shopcart_add;
//		private TextView shopcart_price_black;
//		private ImageView buy_account_goods_pic;
//		public CheckBox shopcart_check; // 商品选择按钮
//		private TextView buy_account_company, buy_account_goods_title,
//				buy_account_goods_color_zhi, buy_account_goods_price_red,
//				buy_account_goods_chima_zhi;
//	}
//
//	public class ViewHolderr {
//		private TextView shopcart_count;
//	}
//
//	// 初始化isSelected的数据
//	private void initDate() {
//		for (int i = 0; i < list.size(); i++) {
//			getIsSelected().put(i, false);
//		}
//	}
//
//	public static HashMap<Integer, Boolean> getIsSelected() {
//		return isSelected;
//	}
//
//	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
//		ShopAdapter.isSelected = isSelected;
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
//		final GoodsList bean = list.get(position);
//
//		if (convertView == null) {
//			holder = new ViewHolder();
//			convertView = inflater.inflate(R.layout.item_shop, null);
//			holder.shopcart_sub = (Button) convertView
//					.findViewById(R.id.shopcart_sub);
//			holder.shopcart_count = (TextView) convertView
//					.findViewById(R.id.shopcart_edit_count);
//			holder.shopcart_add = (Button) convertView
//					.findViewById(R.id.shopcart_add);
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
//			convertView.setTag(holder);
//		} else {
//
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.shopcart_check.setTag(position);
//		holder.shopcart_sub.setTag(position);
//		holder.shopcart_add.setTag(position);
//		holder.shopcart_count.setTag(position);
//
//		if (getIsSelected().get(position) == null) {
//			holder.shopcart_check.setChecked(false);
//		} else {
//			holder.shopcart_check.setChecked(getIsSelected().get(position));
//		}
//		holder.shopcart_check
//				.setOnCheckedChangeListener(new CheckBoxChangedListener());
//
//		holder.shopcart_count.setText(list.get(position).getShangpinNum());
//
//		holder.shopcart_sub.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				int position = (Integer) v.getTag();
//				if (list.get(position).getShangpinNum() != null) {
//					shopNum = Integer.parseInt(list.get(position)
//							.getShangpinNum());
//				}
//				System.out.println("11" + holder.shopcart_count.getTag());
//				System.out.println("position" + position);
//
//				if (shopNum > 1) {
//					shopNum--;
//					list.get(position).setShangpinNum(shopNum + "");
//					System.out.println("shopNum_sub:"
//							+ list.get(position).getShangpinNum());
//				}
//				notifyDataSetChanged();
//
//			}
//
//		});
//		holder.shopcart_add.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				int position = (Integer) v.getTag();
//				if (list.get(position).getShangpinNum() != null) {
//					shopNum = Integer.parseInt(list.get(position)
//							.getShangpinNum());
//				}
//				shopNum++;
//				list.get(position).setShangpinNum(shopNum + "");
//				System.out.println("shopNum_add:"
//						+ list.get(position).getShangpinNum());
//				notifyDataSetChanged();
//
//			}
//		});
//
//		holder.buy_account_goods_title.setText(list.get(position)
//				.getGoodsName());
//		holder.buy_account_goods_color_zhi.setText(list.get(position)
//				.getGoodsColor());
//		holder.buy_account_goods_price_red.setText(list.get(position)
//				.getShopPrice());
//		holder.shopcart_price_black
//				.setText(list.get(position).getMarketPrice());
//		holder.buy_account_goods_chima_zhi.setText(list.get(position)
//				.getGoodsChi_ma());
//
//		holder.shopcart_price_black.getPaint().setFlags(
//				Paint.STRIKE_THRU_TEXT_FLAG);
//
//		/**
//		 * imageLoader初始化
//		 */
//		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//		// ** 使用DisplayImageOptions.Builder()创建DisplayImageOptions *//*
//
//		options = new DisplayImageOptions.Builder().build();
//
//		String picPath;
//		if (EmptyUtil.isEmpty(list.get(position).getGoodsThumb())) {
//			picPath = "expertimg/default.png";
//		} else {
//			picPath = list.get(position).getGoodsThumb();
//		}
//		imageLoader
//				.displayImage(picPath, holder.buy_account_goods_pic, options);
//		holder.buy_account_goods_pic.setScaleType(ScaleType.CENTER_CROP);
//		System.out.println("list.get(position).getGoodsName():"
//				+ list.get(position).getGoodsName());
//		return convertView;
//	}
//
//	// CheckBox选择改变监听器
//	private final class CheckBoxChangedListener implements
//			OnCheckedChangeListener {
//		@Override
//		public void onCheckedChanged(CompoundButton cb, boolean flag) {
//			int position = (Integer) cb.getTag();
//			getIsSelected().put(position, flag);
//			for (int i = 0; i < list.size(); i++) {
//				GoodsList bean = list.get(i);
//				// bean.setChoosed(getIsSelected().get(i));
//				if (getIsSelected().get(i) == null) {
//					bean.setChoosed(false);
//				} else {
//					bean.setChoosed(getIsSelected().get(i));
//				}
//			}
//			// 选中后，发送选中商品的总价
//			mHandler.sendMessage(mHandler.obtainMessage(10, getShopcartSum()));
//			// 选中商品的数量
//			mHandler.sendMessage(mHandler.obtainMessage(11, getShopcartNum()));
//			// 如果所有的物品全部被选中，则全选按钮也默认被选中
//			mHandler.sendMessage(mHandler.obtainMessage(12, isAllSelected()));
//		}
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
//
//		for (int i = 0; i < list.size(); i++) {
//			bean = list.get(i);
//			if (bean.isChoosed()) {
//				if (bean.getShangpinNum() == null) {
//					bean.setShangpinNum("1");
//				}
//				shopcartSum += Integer.parseInt(list.get(i).getShangpinNum())
//						* Double.parseDouble(list.get(i).getShopPrice());
//			}
//		}
//		return Double.valueOf(new java.text.DecimalFormat("0.00")
//				.format(shopcartSum));
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
//
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getShangpinNum() == null) {
//				itemNum = 1;
//			} else {
//				itemNum = Integer.parseInt(list.get(i).getShangpinNum());
//			}
//			bean = list.get(i);
//			if (bean.isChoosed()) {
//				shopcartNum += itemNum;
//			}
//		}
//
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
//
//		for (int i = 0; i < list.size(); i++) {
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
//	}
//
//	/**
//	 * 计算选中商品的数量
//	 * 
//	 * @return 商品的数量
//	 */
//	private int getItemNum() {
//		GoodsList bean = null;
//		String shopNum = "";
//
//		for (int i = 0; i < list.size(); i++) {
//			bean = list.get(i);
//			if (bean.getShangpinNum().equals("0")
//					|| bean.getShangpinNum() == null) {
//				holder.shopcart_count.setText(1 + "");
//			} else {
//				shopNum += bean.getShangpinNum();
//
//			}
//
//		}
//
//		holder.shopcart_count.setText(bean.getShangpinNum());
//		return Integer.parseInt(bean.getShangpinNum());
//
//	}
//
//}
