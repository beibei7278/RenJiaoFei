package com.sunsoft.zyebiz.b2e.adapter;

/**
 * 功能：学生信息页面之家长信息适配器
 * @author YGC
 */
import java.util.List;

import com.sunsoft.zyebiz.b2e.R;
import com.sunsoft.zyebiz.b2e.model.Student.ParentResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	/** 视图容器 */
	private LayoutInflater inflater;
	/** 缓存子控件 */
	private ViewHolder holder;
	/** 继承上下文 */
	private Context mContext;

	private List<ParentResult> list;

	public MessageAdapter(Context context, List<ParentResult> list) {
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public class ViewHolder {
		private TextView parentName, parentIdentity, parentPhone;
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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_parent_message, null);
			holder.parentName = (TextView) convertView
					.findViewById(R.id.parent_name);
			holder.parentIdentity = (TextView) convertView
					.findViewById(R.id.parent_identity);
			holder.parentPhone = (TextView) convertView
					.findViewById(R.id.parent_phone);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.parentName.setText(list.get(position).getParentName());
		if (list.get(position).getParentIdentity().equals("2")) {
			holder.parentIdentity.setText("母亲");
		} else if (list.get(position).getParentIdentity().equals("3")) {
			holder.parentIdentity.setText("其他");
		} else {
			holder.parentIdentity.setText("父亲");
		}

		holder.parentPhone.setText(list.get(position).getParentPhone());

		return convertView;
	}

}
