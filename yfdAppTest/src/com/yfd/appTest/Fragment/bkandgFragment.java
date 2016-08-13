package com.yfd.appTest.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.Basefragment;
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Beans.Olnystatebeans;
import com.yfd.appTest.Beans.Searchbkgbeans;
import com.yfd.appTest.Beans.Searchbkgbeans.datal;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.bkgSearchkkAdapter;

public class bkandgFragment extends Basefragment implements OnClickListener {
	
	
	private ImageView back;
	TextView title;
	Button searchyys;
	Button searchstatusbtn;
	Button searchbtn;
	Button bkgbtn;;
	List<String> listyys=new ArrayList<String>();
	List<String> liststatus=new ArrayList<String>();
	List<String> leixing=new ArrayList<String>();
	List<datal> dataList=new ArrayList<datal>();
	
	String yysstr="-1",statusstr="-1",leixingstr="-1";
	
	EditText phonenum;
	private Searchbkgbeans searchback;
	Context con;
	
	bkgSearchkkAdapter sadpter;
	Olnystatebeans statebeans;
	LoginBeansStr bs;
	
	private Handler handler=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if(msg.obj!=null){
				
				statebeans = (Olnystatebeans) Utils.jsonToBean(msg.obj.toString(), Olnystatebeans.class);
				
				if(statebeans.getState().equals("0")){
					
					bs = (LoginBeansStr) Utils.jsonToBean(msg.obj.toString(), LoginBeansStr.class);
					
					Toast.makeText(getActivity(),bs.getMsg(), 1000).show();
					
				}else{
					
					searchback = (Searchbkgbeans) Utils.jsonToBean(msg.obj.toString(), Searchbkgbeans.class);
				
				if(searchback.getState().equals("1")){
					
					dataList=searchback.getMsg().getDataList();
					sadpter=new bkgSearchkkAdapter(dataList, con);
					listview.setAdapter(sadpter);
				}else{
					
					Toast.makeText(getActivity(),"获取信息失败!", 1000).show();
				}
					
					
				}
				
				
				
			}else{
				
				Toast.makeText(getActivity(),"请求失败，请重试~", 1000).show();
			}
			
		};
		
	};
	private ListView listview;
	private View Mview;
	EditText kuaidinum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_bkandgsearch, null);
		con=getActivity();
	
		searchyys=(Button)Mview.findViewById(R.id.search_yysbtnad);
		searchstatusbtn=(Button)Mview.findViewById(R.id.search_statusbtnad);
		searchbtn=(Button)Mview.findViewById(R.id.search_kksavebtnad);
		phonenum=(EditText)Mview.findViewById(R.id.search_phonekkad);
		kuaidinum=(EditText)Mview.findViewById(R.id.search_kuaidinum);
		listview=(ListView)Mview.findViewById(R.id.s_kk_listviewad);
		bkgbtn=(Button)Mview.findViewById(R.id.search_leixingbtnad);
		
		listyys.add("所有");
		listyys.add("远特");
		listyys.add("国美");
		listyys.add("迪加");
		listyys.add("蜗牛");

		

		liststatus.add("所有状态");
		liststatus.add("等待");
		liststatus.add("正在");
		liststatus.add("成功");
		liststatus.add("失败");
		
		leixing.add("所有");
		leixing.add("补卡");
		leixing.add("过户");
		
		
		searchbtn.setOnClickListener(this);
		searchyys.setOnClickListener(this);
		searchstatusbtn.setOnClickListener(this);
		bkgbtn.setOnClickListener(this);
		
		return Mview;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.search_yysbtnad:
			initPopWindow(listyys, searchyys);
			break;
		case R.id.search_statusbtnad:
			initPopWindow(liststatus, searchstatusbtn);
			break;
		case R.id.search_leixingbtnad:
			initPopWindow(leixing, bkgbtn);
			break;
		case R.id.search_kksavebtnad:
			
			if(searchyys.getText().toString().equals("所有")){
				
				yysstr="-1";
				
				
			}else{
				
				yysstr=searchyys.getText().toString();
				
			}
	       if(bkgbtn.getText().toString().equals("所有")){
				
				leixingstr="-1";
				
				
			}else{
				
				leixingstr=bkgbtn.getText().toString();
				
			}
			
			for(int i=0;i<liststatus.size();i++){
				
				if(liststatus.get(i).equals(searchstatusbtn.getText().toString())){
					
					statusstr=String.valueOf(i-1);
				}
				
			}
			showloading("");
			System.out.println(BaseApplication.bkgSEARCH_LIST+"?ftPhone="+phonenum.getText().toString()+"&carrierOp="+yysstr+"&serviceStatus="+statusstr+"&courierNum="+kuaidinum.getText().toString()+"&serviceType="+Utils.getUTF8XMLString(leixingstr)+"&currentPage=1&pageSize=1000");
			AnsyPost.getkkSearch(BaseApplication.bkgSEARCH_LIST+"?ftPhone="+phonenum.getText().toString()+"&carrierOp="+yysstr+"&serviceStatus="+statusstr+"&courierNum="+kuaidinum.getText().toString()+"&serviceType="+leixingstr+"&currentPage=1&pageSize=1000", handler);
			
			
			break;
		default:
			break;
		}
	}

}
