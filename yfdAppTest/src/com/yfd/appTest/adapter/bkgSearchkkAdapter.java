package com.yfd.appTest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.Searchbkgbeans.datal;
import com.yfd.appTest.Utils.Utils;

public class bkgSearchkkAdapter extends BaseAdapter{
	  
	List<datal> lists;
	  Context context;
	  public bkgSearchkkAdapter(List<datal> list,Context context) {
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
			view=LayoutInflater.from(context).inflate(R.layout.bkgseach_list_item, null);
			vh.phone=(TextView)view.findViewById(R.id.kklist_pb);
			vh.sfnum=(TextView)view.findViewById(R.id.kklist_sfb);
			vh.yys=(TextView)view.findViewById(R.id.kklist_yb);
			vh.status=(TextView)view.findViewById(R.id.kklist_sb);
			vh.huifu=(TextView)view.findViewById(R.id.kklist_huifu);
			view.setTag(vh);
			
		}else{
			
			vh=(ViewHolder)view.getTag();
			
		}
		vh.phone.setText(lists.get(arg0).getFtPhone());
		vh.sfnum.setText(lists.get(arg0).getServiceType());
		vh.yys.setText(lists.get(arg0).getCarrierOp());
		
		if(!Utils.isEmpty(lists.get(arg0).getAdminMsg())){
			
			
				vh.huifu.setText(lists.get(arg0).getAdminMsg());
		}
		
	
		
		if(lists.get(arg0).getServiceStatus().equals("0")){
			
			vh.status.setText("等待");
			
		}
if(lists.get(arg0).getServiceStatus().equals("1")){
			
	vh.status.setText("正在");
			
		}
if(lists.get(arg0).getServiceStatus().equals("2")){
	
	vh.status.setText("成功");
	
}
if(lists.get(arg0).getServiceStatus().equals("3")){
	
	vh.status.setText("失败");
	
}

		return view;
	}
	  
	  
	  class ViewHolder{
	  
	  TextView phone;
	  TextView sfnum;
	  TextView yys;
	  TextView status;
	  TextView huifu;
	  
}
}


	

