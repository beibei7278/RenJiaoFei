package com.yfd.appTest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.addplBeans;

public class Adapteradd extends BaseAdapter {

	List<addplBeans> list=new ArrayList<addplBeans>();
	Context con;
	public Adapteradd(List<addplBeans> list,Context con) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.con=con;
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
			view=LayoutInflater.from(con).inflate(R.layout.phonelist_item, null);
			vh.name=(TextView)view.findViewById(R.id.pname);
			vh.num=(EditText)view.findViewById(R.id.phonenum_edit);
		    vh.add=(ImageView)view.findViewById(R.id.phoneadd);
			vh.del=(ImageView)view.findViewById(R.id.phonedel);
			view.setTag(vh);
		}
		else{
			
			vh=(ViewHolder)view.getTag();
			
		}
		
		return view;
	}
	
	class ViewHolder{
		
		TextView name;
		EditText num;
		ImageView add;
		ImageView del;
		
	}

}
