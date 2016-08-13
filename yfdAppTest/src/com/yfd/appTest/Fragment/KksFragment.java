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
import com.yfd.appTest.Beans.SearchkkBeans;
import com.yfd.appTest.Beans.SearchkkBeans.Cardmsg;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.SearchkkAdapter;

public class KksFragment extends Basefragment implements OnClickListener {
	
	
	private ImageView back;
	TextView title;
	Button searchyys;
	Button searchstatusbtn;
	Button searchbtn;
	List<String> listyys=new ArrayList<String>();
	List<String> liststatus=new ArrayList<String>();
	
	List<Cardmsg> listserch=new ArrayList<Cardmsg>();
	
	String yysstr="-1",statusstr="-1";
	
	EditText uname,phonenum;
	private SearchkkBeans searchback;
	Context con;
	
	SearchkkAdapter sadpter;
	
	private Handler handler=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if(msg.obj!=null){
				
				searchback = (SearchkkBeans) Utils.jsonToBean(msg.obj.toString(), SearchkkBeans.class);
				
				if(searchback.getState().equals("1")){
					
					listserch=searchback.getMsg().getDataList();
					sadpter=new SearchkkAdapter(listserch, con);
					listview.setAdapter(sadpter);
				}else{
					
					Toast.makeText(getActivity(),"获取信息失败!", 1000).show();
				}
				
			}else{
				
				Toast.makeText(getActivity(),"请求失败，请重试~", 1000).show();
			}
			
		};
		
	};
	private ListView listview;
	private View Mview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_kksearch, null);
		con=getActivity();
	
		searchyys=(Button)Mview.findViewById(R.id.search_yysbtn);
		searchstatusbtn=(Button)Mview.findViewById(R.id.search_statusbtn);
		searchbtn=(Button)Mview.findViewById(R.id.search_kksavebtn);
		uname=(EditText)Mview.findViewById(R.id.search_namekk);
		phonenum=(EditText)Mview.findViewById(R.id.search_phonekk);
		listview=(ListView)Mview.findViewById(R.id.s_kk_listview);

		
		listyys.add("所有");
		listyys.add("远特");
		listyys.add("国美");
		listyys.add("迪信通");
		listyys.add("蜗牛");
		listyys.add("186联通");
		

		liststatus.add("所有状态");
		liststatus.add("等待开通");
		liststatus.add("正在开通");
		liststatus.add("开通成功");
		liststatus.add("开通失败");
		liststatus.add("远特加盟");
		liststatus.add("远特代理");
		searchbtn.setOnClickListener(this);
		searchyys.setOnClickListener(this);
		searchstatusbtn.setOnClickListener(this);
	
		
		return Mview;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.search_yysbtn:
			initPopWindow(listyys, searchyys);
			break;
		case R.id.search_statusbtn:
			initPopWindow(liststatus, searchstatusbtn);
			break;
		case R.id.search_kksavebtn:
			
			if(searchyys.getText().toString().equals("所有")){
				
				yysstr="-1";
				
				
			}else{
				
				yysstr=searchyys.getText().toString();
				
			}
			
			
			for(int i=0;i<liststatus.size();i++){
				
				if(liststatus.get(i).equals(searchstatusbtn.getText().toString())){
					
					statusstr=String.valueOf(i-1);
				}
				
			}
			showloading("");
			AnsyPost.getkkSearch(BaseApplication.KSEARCH_LIST+"?miUseName="+uname.getText().toString()+"&miPhone="+phonenum.getText().toString()+"&carrierOp="+Utils.getUTF8XMLString(yysstr)+"&miState="+statusstr+"&usId="+BaseApplication.loginbeans.getMsg().getUsId()+"&usCredits="+BaseApplication.loginbeans.getMsg().getUsCredits()+"&currentPage=1&pageSize=1000", handler);
			
			
			break;
		default:
			break;
		}
	}

}
