package com.sunsoft.zyebiz.b2e.adapter;

/**
 * 功能：结算中心适配器
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import com.sunsoft.zyebiz.b2e.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.activity.UserMainActivity;
import com.sunsoft.zyebiz.b2e.activity.UserRetailXQActivity;
import com.sunsoft.zyebiz.b2e.application.ECApplication;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.shopcart.BodyResult;
import com.sunsoft.zyebiz.b2e.model.shopcart.GoodObject;
import com.sunsoft.zyebiz.b2e.wiget.BitmapCache;
import com.sunsoft.zyebiz.b2e.wiget.NetManager;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("UseSparseArrays")
public class ShopCartAdapter extends BaseAdapter {
	/** 视图容器 */
	private LayoutInflater inflater;
	/** 缓存子控件 */
	private ViewHolder holder;
	/** 继承上下文 */
	private Context mContext;
	private List<GoodObject> list; // 数据集合List
	private static HashMap<Integer, Boolean> isSelected;
	private Handler mHandler;
	private RequestQueue queue;
	private ImageLoader imageLoader;
	//判断网络的链接情况
	private static boolean hasNet; 
	public ShopCartAdapter(Context mContext, List<GoodObject> list2,
			Handler mHandler) {
		this.mContext = mContext;
		this.list = list2;
		inflater = LayoutInflater.from(mContext);
		this.mHandler = mHandler;
		queue = Volley.newRequestQueue(mContext);
		imageLoader = new ImageLoader(queue, new BitmapCache());
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	public class ViewHolder {
//		private TextView mXiangXi;
		private Button shopcart_sub;
		private TextView shopcart_count;
		private Button shopcart_add;
		private TextView shopcart_price_black;
		private NetworkImageView buy_account_goods_pic;
		public CheckBox shopcart_check; // 商品选择按钮
		private TextView /*buy_account_company,*/ buy_account_goods_title,
				/*buy_account_goods_color_zhi,*/ buy_account_goods_price_red,
				buy_account_goods_chima_zhi;
	}

//	public class ViewHolderr {
//		private TextView shopcart_count;
//	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ShopCartAdapter.isSelected = isSelected;
	}

	public int getCount() {
		if (list == null) {
			return 1;
		} else {
			return list.size();
		}
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		GoodObject bean = list.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_shopcart, null);
			holder.shopcart_sub = (Button) convertView
					.findViewById(R.id.shopcart_sub);
			holder.shopcart_count = (TextView) convertView
					.findViewById(R.id.shopcart_edit_count);
			holder.shopcart_add = (Button) convertView
					.findViewById(R.id.shopcart_add);
			holder.shopcart_price_black = (TextView) convertView
					.findViewById(R.id.buy_account_goods_price_black);
			holder.buy_account_goods_title = (TextView) convertView
					.findViewById(R.id.buy_account_goods_title);
			holder.buy_account_goods_price_red = (TextView) convertView
					.findViewById(R.id.buy_account_goods_price_red);
			holder.buy_account_goods_chima_zhi = (TextView) convertView
					.findViewById(R.id.buy_account_goods_chima_zhi);
			holder.buy_account_goods_pic = (NetworkImageView) convertView
					.findViewById(R.id.buy_account_goods_pic);
			holder.shopcart_check = (CheckBox) convertView
					.findViewById(R.id.shopcart_check);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.shopcart_check.setTag(position);
		holder.shopcart_sub.setTag(position);
		holder.shopcart_add.setTag(position);
		holder.shopcart_count.setTag(position);
		holder.buy_account_goods_title.setTag(position);
		holder.shopcart_check.setChecked(getIsSelected().get(position));
		holder.buy_account_goods_title.setText(bean.getGoodsName());
		holder.buy_account_goods_price_red.setText("¥"
				+ numberFormat(Double.parseDouble(bean.getGoodsPrice())));
		holder.shopcart_price_black.setText("¥"
				+ numberFormat(Double.parseDouble(bean.getMarketPrice())));
		
		
		holder.buy_account_goods_chima_zhi.setText(bean.getSelectSize());
		holder.shopcart_price_black.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG);

		final String imgUrl = list.get(position).getGoodsImg();

		if (imgUrl != null && !imgUrl.equals("")) {
			holder.buy_account_goods_pic.setDefaultImageResId(R.drawable.yifu);
			holder.buy_account_goods_pic.setErrorImageResId(R.drawable.yifu);
			holder.buy_account_goods_pic.setImageUrl(imgUrl, imageLoader);
		}

		if (list.get(position).getGoodsNumber() != null) {
			holder.shopcart_count.setText(list.get(position).getGoodsNumber());
		} else {
			list.get(position).setGoodsNumber(1 + "");
			holder.shopcart_count.setText(list.get(position).getGoodsNumber());
		}
		holder.shopcart_count.setText(list.get(position).getGoodsNumber());

		if (getIsSelected().get(position) == null) {
			holder.shopcart_check.setChecked(false);
		} else {
			holder.shopcart_check.setChecked(getIsSelected().get(position));
		}

		holder.shopcart_sub.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int position = (Integer) v.getTag();
				int shopNum = 1;
				GoodObject bean = list.get(position);
				if (bean.getGoodsNumber() != null) {
					shopNum = Integer.parseInt(bean.getGoodsNumber());
				}
				if (shopNum > 1) {
					isHaveNet();
					if(hasNet){
						shopNum--;
						bean.setGoodsNumber(shopNum + "");
						shopcartNumUpdate(position, shopNum + "");
						System.out.println("shopNum_sub:" + bean.getShangpinNum());
					}
					
				} else {
					isHaveNet();
					if(hasNet){
						Toast toast = Toast.makeText(mContext, "就剩一件了",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

				}

			}

		});
		holder.shopcart_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				int shopNum = 1;
				GoodObject bean = list.get(position);
				if (bean.getGoodsNumber() != null) {
					shopNum = Integer.parseInt(bean.getGoodsNumber());
				}
				isHaveNet();
				if(hasNet){
					shopNum++;
					bean.setGoodsNumber(shopNum + "");
					shopcartNumUpdate(position, shopNum + "");
				}
			}
		});
		holder.shopcart_check
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean flag) {
						int position = (Integer) arg0.getTag();
						getIsSelected().put(position, flag);

						for (int i = 0; i < list.size(); i++) {
							GoodObject bean = list.get(i);
							if (getIsSelected().get(i) == null) {
								bean.setChoosed(false);
							} else {
								bean.setChoosed(getIsSelected().get(i));
							}
						}
						// 选中后，发送选中商品的总价
						mHandler.sendMessage(mHandler.obtainMessage(10,
								getShopcartSum()));
						// 选中商品的数量
						mHandler.sendMessage(mHandler.obtainMessage(11,
								getShopcartNum()));
						// 选中商品的id
						mHandler.sendMessage(mHandler.obtainMessage(13,
								getShopcartId()));
						// 如果所有的物品全部被选中，则全选按钮也默认被选中
						mHandler.sendMessage(mHandler.obtainMessage(12,
								isAllSelected()));

					}
				});
		holder.buy_account_goods_title
				.setOnClickListener(new OnClickListener() {
					// 因为重写ontouch事件使onChildClickListener失效，需要设置次监听来补救
					public void onClick(View v) {
						int position = (Integer) v.getTag();
						if (!NetManager.isWifi() && !NetManager.isMoble()) {
							Toast.makeText(UserMainActivity.mainActivity,
									mContext.getString(R.string.network_message_no),
									Toast.LENGTH_SHORT).show();
							return;
						}
						Intent intent = new Intent();
						intent.setClass(mContext, UserRetailXQActivity.class);
						intent.putExtra("goodsId", list.get(position)
								.getGoodsId());
						mContext.startActivity(intent);

					}
				});

		return convertView;
	}

	/**
	 * 计算选中商品的金额
	 * 
	 * @return 返回需要付费的总金额
	 */
	@SuppressWarnings("null")
	private double getShopcartSum() {
		GoodObject bean = null;
		double shopcartSum = 0;

		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			int goodsNum = Integer.parseInt(list.get(i).getGoodsNumber());
			double goodsPrice = Double.parseDouble(list.get(i).getGoodsPrice());
			if (bean.isChoosed()) {
				shopcartSum += goodsNum * goodsPrice;
			}
		}
		return Double.valueOf(new java.text.DecimalFormat("0.00")
				.format(shopcartSum));
	}

	/**
	 * 计算选中商品的数量
	 * 
	 * @return 返回商品的数量
	 */
	private int getShopcartNum() {
		GoodObject bean = null;
		int shopcartNum = 0;
		int itemNum = 1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getGoodsNumber() == null) {
				itemNum = 1;
			} else {
				itemNum = Integer.parseInt(list.get(i).getGoodsNumber());
			}
			bean = list.get(i);
			if (bean.isChoosed()) {
				shopcartNum += itemNum;
			}
		}
		return shopcartNum;
	}

	/**
	 * 计算选中商品的id
	 * 
	 * @return 返回商品的id
	 */
	private String getShopcartId() {
		GoodObject bean = null;
		String shopcartid = "";
		String itemId = "";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getGoodsNumber() == null) {
				itemId = "";
			} else {
				itemId = list.get(i).getRecId();
			}
			bean = list.get(i);
			if (bean.isChoosed()) {
				shopcartid += itemId + ",";
			}
		}
		System.out.println("shopcartid:"+shopcartid);
		return shopcartid;
	}

	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * 
	 * @return true所有条目全部被选中 false还有条目没有被选中
	 */
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (!getIsSelected().get(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 购物车数量修改
	 */
	private void shopcartNumUpdate(final int position, String shopNum) {

		// 数据查询
		RequestParams params = new RequestParams();
		params.put("recId", list.get(position).getRecId());
		params.put("goodsNumber", shopNum);
		params.put("token", SharedPreference.strUserToken);
		final Gson gson = new Gson();
		AsyncHttpUtil.post(URLInterface.SHOPPING_CART_NUMBER, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						if (statusCode == 200) {
							String resultDate = new String(responseBody);
							BodyResult shopbean = gson.fromJson(resultDate,
									BodyResult.class);
							String title = shopbean.getTitle();
							if (title.equals("0")) {
								if (list.get(position).isChoosed()) {
									// 选中后，发送选中商品的总价
									mHandler.sendMessage(mHandler
											.obtainMessage(10, getShopcartSum()));
									// 选中商品的数量
									mHandler.sendMessage(mHandler
											.obtainMessage(11, getShopcartNum()));
								}
								notifyDataSetChanged();
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});

	}
	
	public String numberFormat(double n) {
		  NumberFormat format = NumberFormat.getInstance();
		  format.setMinimumFractionDigits( 2 );
		  format.setMaximumFractionDigits(2);
		  format.setMaximumIntegerDigits( 10 );
		  format.setMinimumIntegerDigits(1);
		  return format.format(n);
		 
	}

	private String moneyFenGe(double n) {
		if (n == 0.0) {
			DecimalFormat df = new DecimalFormat("#,###.00");
			String m = df.format(n);
			return 0 + m;
		} else {
			DecimalFormat df = new DecimalFormat("#,###.00");
			String m = df.format(n);
			return m;
		}
	}

	private void isHaveNet(){
		ConnectivityManager connectMgr = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null) {
		  hasNet = false;
			Toast toastNet = Toast.makeText(mContext, "无网络，重新加载", Toast.LENGTH_SHORT);
			toastNet.setGravity(Gravity.CENTER, 0, 0);
			toastNet.show();
		} else {
			hasNet = true;
			System.out.println("有网络");
		}
	}
}
