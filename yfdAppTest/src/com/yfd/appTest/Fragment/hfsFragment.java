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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.Basefragment;
import com.yfd.appTest.Beans.HLsbeans;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.HLSearchkkAdapter;

public class hfsFragment extends Basefragment{
	
	private HLsbeans searchback;
	private View Mview;
	private Context con;
	private EditText ddtxt;
	private ListView txtshow;
	
	String status;
	HLSearchkkAdapter adapter;
	List<HLsbeans> lists=new ArrayList<HLsbeans>();
	Handler handler=new Handler(){	
		

		public void handleMessage(android.os.Message msg) {
			
			dimissloading();
			
			if(msg.obj!=null){
				
				lists = new Gson().fromJson(msg.obj.toString(), new TypeToken<List<HLsbeans>>(){}.getType());
				
				if(lists.size()>0){
					
					adapter=new HLSearchkkAdapter(lists, con,0);
					txtshow.setAdapter(adapter);
				}
				else{
					
					Toast.makeText(getActivity(), "未查询到此手机号相关信息", 1000).show();
					
				}					
			}
			else{
				Toast.makeText(getActivity(), "获取信息失败，请重试~", 1000).show();
			}
		};
		
	};
 private Button save;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

			Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hfsearch, null);
			
			ddtxt=(EditText)Mview.findViewById(R.id.search_namehfs);
			txtshow=(ListView)Mview.findViewById(R.id.hfs_txt);
			save=(Button)Mview.findViewById(R.id.search_hfsavebtn);
			con=getActivity();
			save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					if(Utils.isEmpty(ddtxt.getText().toString())){
						
						Toast.makeText(getActivity(), "输入手机号", 1000).show();
						
						return;
						
					}
					showloading("");
					AnsyPost.getArrylistpost(BaseApplication.searchHF_URL+"?mobilenumber="+ddtxt.getText().toString()+"&pageNo=1&pageSize=1000", handler);
				
//				System.out.println(BaseApplication.searchHF_URL+"?mobilenumber="+ddtxt.getText().toString()+"&pageNo=1&pageSize=1000");
				}
			});
			
			
			
		return Mview;
	}
	
	

}
