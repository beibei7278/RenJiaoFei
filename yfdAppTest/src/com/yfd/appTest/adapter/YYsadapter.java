package com.yfd.appTest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;


public class YYsadapter extends BaseAdapter{
	  
	  List<String> lists;
	  Context con;
	  public YYsadapter(Context con,List<String> list) {
		// TODO Auto-generated constructor stub
	this.lists=list;
	this.con=con;
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
		
		ViewHolder vh=null;
		
		if(view==null){
			
			vh=new ViewHolder();
			view=LayoutInflater.from(con).inflate(R.layout.poplist_item, null);
			vh.txt=(TextView)view.findViewById(R.id.poptxt);
			view.setTag(vh);
			
		}else{
			
			vh=(ViewHolder)view.getTag();
			
		}
		
		vh.txt.setText(lists.get(arg0));
		
		return view;
	}
	  
  class ViewHolder{
		  
		  TextView txt;
		  
	  }
	  
	  
}