package com.sunsoft.zyebiz.b2e.adapter;
//package com.sunsoft.zyebiz.b2e.adapter;
///**
// * 功能：地址选择适配器
// * @author YGC
// */
//import java.util.List;
//
//import com.bufengsoft.clothingstore.R;
//import com.sunsoft.zyebiz.b2e.activity.AddressChooseActivity;
//import com.sunsoft.zyebiz.b2e.activity.ConsultDetailActivity;
//import com.sunsoft.zyebiz.b2e.activity.DeleteAddressActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyFactoryActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyGongGaoActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyShangPinActivity;
//import com.sunsoft.zyebiz.b2e.activity.GroupBuyChiMaActivity;
//import com.sunsoft.zyebiz.b2e.model.Login.BodyResult;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class AddressChooseAdapter extends BaseAdapter {
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
//	public AddressChooseAdapter(Context context) {
//		// TODO Auto-generated constructor stub
//		this.mContext = context;
//		this.list = list;
//		inflater = LayoutInflater.from(context);
//	}
//
//	public class ViewHolder {
//		private ImageView img_address_choose_sanjiao;
//		private TextView address_editor;
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
//			convertView = inflater.inflate(R.layout.item_address_choose, null);
//			
////			holder.tv_store_consult = (TextView)convertView.findViewById(R.id.tv_store_zixun);
//			holder.img_address_choose_sanjiao = (ImageView)convertView.findViewById(R.id.img_address_choose_sanjiao);
//			holder.address_editor = (TextView)convertView.findViewById(R.id.address_editor);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.img_address_choose_sanjiao.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(mContext,AddressChooseActivity.class);
//				mContext.startActivity(intent);
//			}
//		});
//		holder.address_editor.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent editorAddress = new Intent(mContext,DeleteAddressActivity.class);
//				mContext.startActivity(editorAddress);
//				
//			}
//		});
//
//		return convertView;
//	}
//
//}
