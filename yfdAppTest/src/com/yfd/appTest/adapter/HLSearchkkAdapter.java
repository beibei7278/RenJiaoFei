package com.yfd.appTest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.HLsbeans;

public class HLSearchkkAdapter extends BaseAdapter {

	List<HLsbeans> lists;
	Context context;
	int what;

	public HLSearchkkAdapter(List<HLsbeans> list, Context context, int what) {
		// TODO Auto-generated constructor stub
		lists = list;
		this.context = context;
		this.what = what;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub

		ViewHolder vh = null;

		if (view == null) {

			vh = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.llcx_listitem,
					null);
			vh.phone = (TextView) view.findViewById(R.id.hfcx_phonell);
			vh.Dnum = (TextView) view.findViewById(R.id.hfcx_dnumll);
			vh.price = (TextView) view.findViewById(R.id.hfcx_cpricell);
			vh.status = (TextView) view.findViewById(R.id.hfcx_statusll);
			vh.ll = (TextView) view.findViewById(R.id.hfcx_cpricellll);
			view.setTag(vh);

		} else {

			vh = (ViewHolder) view.getTag();

		}
		vh.phone.setText(lists.get(arg0).getMobilenumber());
		vh.Dnum.setText(lists.get(arg0).getSystemid());

		if (what == 0) {
			vh.price.setVisibility(View.GONE);
			vh.ll.setText(lists.get(arg0).getBillsize());

		} else {

			vh.price.setText(lists.get(arg0).getBillsize());
			vh.ll.setText(lists.get(arg0).getCzll());
		}

		if (lists.get(arg0).getCzzt().equals("czcg")) {

			vh.status.setText("成功");

		} else {

			vh.status.setText("失败");

		}

		return view;
	}

	class ViewHolder {

		TextView phone;
		TextView Dnum;
		TextView price;
		TextView status;
		TextView ll;
	}
}
