//package com.sunsoft.zyebiz.b2e.adapter;
//
//import java.util.List;
//
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.activity.UserGroupBuyGongGaoActivity;
//import com.sunsoft.zyebiz.b2e.model.Login.BodyResult;
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//public class SchoolStoreAdapter extends BaseAdapter {
//
//	/** 视图容器 */
//	private LayoutInflater inflater;
//	/** 缓存子控件 */
//	private ViewHolder holder;
//	/** 继承上下文 */
//	private Context mContext;
//
//	private List<BodyResult> list;
//
//	public SchoolStoreAdapter(Context context) {
//		// TODO Auto-generated constructor stub
//		this.mContext = context;
//		inflater = LayoutInflater.from(context);
//	}
//
//	public class ViewHolder {
//		private TextView tv_store_xiangqing, tv_store_message,
//				tv_store_zhushou,tv_store_consult;
//	}
//
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 1;
//	}
//
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
//
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		holder = new ViewHolder();
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.item_school_store, null);
//			// holder.tv_location = (TextView) convertView
//			// .findViewById(R.id.tv_location);
//			holder.tv_store_xiangqing = (TextView) convertView
//					.findViewById(R.id.tv_store_xiangqing);
//			holder.tv_store_message = (TextView) convertView
//					.findViewById(R.id.tv_store_message);
//			holder.tv_store_zhushou = (TextView) convertView
//					.findViewById(R.id.tv_store_zhushou);
//			holder.tv_store_consult = (TextView)convertView.findViewById(R.id.tv_store_zixun);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
////		/**
////		 * 点击详情按钮事件
////		 */
////		holder.tv_store_xiangqing.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				System.out.println("详情");
////				Intent intent = new Intent(mContext,UserGroupBuyShangPinActivity.class);
////				mContext.startActivity(intent);
////
////			}
////		});
////		/**
////		 * 点击厂家信息按钮事件
////		 */
////		holder.tv_store_message.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				System.out.println("厂家信息");
////				Intent intent = new Intent(mContext,UserBuyFactoryActivity.class);
////				mContext.startActivity(intent);
////
////			}
////		});
////		/**
////		 * 点击尺码助手按钮事件
////		 */
////		holder.tv_store_zhushou.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				System.out.println("尺码助手");
////				Intent intent = new Intent(mContext,GroupBuyChiMaActivity.class);
////				mContext.startActivity(intent);
////
////			}
////		});
////		/**
////		 * 点击在线咨询按钮事件
////		 */
////		holder.tv_store_consult.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				System.out.println("在线咨询");
////				Intent intent = new Intent(mContext,ConsultDetailActivity.class);
////				mContext.startActivity(intent);
////
////			}
////		});
//
//		return convertView;
//	}
//
//}
