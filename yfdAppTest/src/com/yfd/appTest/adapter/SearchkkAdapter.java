package com.yfd.appTest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.SearchkkBeans.Cardmsg;

public class SearchkkAdapter extends BaseAdapter{
	  
	List<Cardmsg> lists;
	  Context context;
	  public SearchkkAdapter(List<Cardmsg> list,Context context) {
		// TODO Auto-generated constructor stub
	   lists=list;
	   this.context=context;
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
			view=LayoutInflater.from(context).inflate(R.layout.seach_list_item, null);
			vh.phone=(TextView)view.findViewById(R.id.kklist_p);
			vh.sfnum=(TextView)view.findViewById(R.id.kklist_sf);
			vh.yys=(TextView)view.findViewById(R.id.kklist_y);
			vh.status=(TextView)view.findViewById(R.id.kklist_s);
			view.setTag(vh);
			
		}else{
			
			vh=(ViewHolder)view.getTag();
			
		}
		vh.phone.setText(lists.get(arg0).getMiPhone());
		vh.sfnum.setText(lists.get(arg0).getAdminMsg());
		vh.yys.setText(lists.get(arg0).getCarrierOp());
		
		
		if(lists.get(arg0).getMiState().equals("0")){
			
			vh.status.setText("等待");
			
		}
if(lists.get(arg0).getMiState().equals("1")){
			
	vh.status.setText("正在");
			
		}
if(lists.get(arg0).getMiState().equals("2")){
	
	vh.status.setText("成功");
	
}
if(lists.get(arg0).getMiState().equals("3")){
	
	vh.status.setText("失败");
	
}
if(lists.get(arg0).getMiState().equals("8")){
	
	vh.status.setText("远特加盟");
	
}
if(lists.get(arg0).getMiState().equals("9")){
	
	vh.status.setText("远特代理");
	
}
		return view;
	}
	  
	  
	  class ViewHolder{
	  
	  TextView phone;
	  TextView sfnum;
	  TextView yys;
	  TextView status;
	  
}
}


	

