package com.yfd.appTest.Activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.SearchkkBeans;
import com.yfd.appTest.Beans.SearchkkBeans.Cardmsg;
import com.yfd.appTest.Fragment.KksFragment;
import com.yfd.appTest.Fragment.bkandgFragment;
import com.yfd.appTest.Fragment.hfsFragment;
import com.yfd.appTest.Fragment.llsFragment;
import com.yfd.appTest.adapter.SearchkkAdapter;

public class SearchActivity extends BasefragmentActivity {
	
	
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
	private ListView listview;
	private KksFragment kks;
	private llsFragment lls;
	private hfsFragment hfs;
	private Fragment mContent;
	private bkandgFragment bkgfragment;
	RadioButton kksr,llsr,hfsr;
	private RadioButton bkgb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);
		con=this;
		back=(ImageView)findViewById(R.id.search_back);
		kksr=(RadioButton)findViewById(R.id.skh_kk);
		llsr=(RadioButton)findViewById(R.id.skh_ll);
		hfsr=(RadioButton)findViewById(R.id.skh_hf);
		bkgb=(RadioButton)findViewById(R.id.skh_bkgb);
		
		if(BaseApplication.loginbeans.getType().equals("0")){
			
			kksr.setVisibility(View.GONE);
			bkgb.setVisibility(View.GONE);
			llsr.setBackgroundResource(R.drawable.tab_leftbtn_bg);
			llsr.setChecked(true);
			
		}
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		kksr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switchContent(kks);
			}
		});
	llsr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switchContent(lls);
			}
		});
	hfsr.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchContent(hfs);
		}
	});
	
	bkgb.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchContent(bkgfragment);
		}
	});
	setDefaultFragment();
	}
	private void setDefaultFragment() {
		kks = new KksFragment();
		lls=new llsFragment();
		hfs=new hfsFragment();
		bkgfragment=new bkandgFragment();
		mContent=kks;
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if(BaseApplication.loginbeans.getType().equals("0")){
			
			transaction.add(R.id.kksfragment, lls);
		}else{
			
			transaction.add(R.id.kksfragment, kks);
			
		}
		
		
		transaction.commit();
	}
	
	public void switchContent(Fragment to) {
		if (mContent != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { 
				transaction.hide(mContent).add(R.id.kksfragment, to).commit(); 
			} else {
				transaction.hide(mContent).show(to).commit(); 
			}
			mContent = to;
		}
	
	}

}
