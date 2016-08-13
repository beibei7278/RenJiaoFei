//package com.sunsoft.zyebiz.b2e.adapter;
///**
// * 功能：结算中心适配器
// */
//import java.util.List;
//import com.sunsoft.zyebiz.b2e.R;
//import com.sunsoft.zyebiz.b2e.model.BuyAccount.BuyBean;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class AccountAdapter extends BaseAdapter {
//
//	/** 视图容器 */
//	private LayoutInflater inflater;
//	/** 缓存子控件 */
//	private ViewHolder holder;
//	/** 继承上下文 */
//	private Context mContext;
//	private List<BuyBean> list;
//
//	public AccountAdapter(Context context/*,List<BuyBean> list*/) {
//		this.mContext = context;
////		this.list = list;
//		inflater = LayoutInflater.from(context);
//	}
//
//	public class ViewHolder {
//		private TextView mXiangXi;
//	}
//
//	public int getCount() {
//		// TODO Auto-generated method stub
////		return list.size();
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
//			convertView = inflater.inflate(R.layout.item_school, null);
//			// holder.tv_location = (TextView) convertView
//			// .findViewById(R.id.tv_location);
//			
//
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		return convertView;
//	}
//
//}
