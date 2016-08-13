package com.yfd.appTest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.addpriceBeans;

public class Adapterprice extends BaseAdapter {

	List<addpriceBeans> list=new ArrayList<addpriceBeans>();
	Context con;
	
	int pos=-1;
	public Adapterprice(List<addpriceBeans> list,Context con) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.con=con;
	}
	
	
	public void update(int pos){
		
		this.pos=pos;
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder vh=null;
		
		if (view==null) {
			vh=new ViewHolder();
			view=LayoutInflater.from(con).inflate(R.layout.price_item, null);
			vh.name=(TextView)view.findViewById(R.id.pricenameitem);

			view.setTag(vh);
		}
		else{
			
			vh=(ViewHolder)view.getTag();
			
		}
		vh.name.setText(list.get(position).getName());
		
		if(pos==position){
			
			vh.name.setBackgroundResource(R.drawable.select_yellow);
			
		}
		else{
			
			vh.name.setBackgroundResource(R.drawable.not_select_gray);
			
		}
		return view;
	}
	
	class ViewHolder{
		
		TextView name;

		
	}

}
