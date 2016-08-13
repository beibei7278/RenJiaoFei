package com.yfd.appTest.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.MessagebackBeans;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class changephoneActivity extends BaseActivity {
	
	ImageView back;
	
	EditText phone1,name,qq;
	
	Button post;
	private MessagebackBeans bmsg;
	private Handler handler=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			
			dimissloading();
			
			if(msg.obj!=null){
				bmsg = (MessagebackBeans) Utils.jsonToBean(
						msg.obj.toString(), MessagebackBeans.class);
				
				Toast.makeText(getApplicationContext(), bmsg.getMsg(), 1000).show();
				
				
			}else{
				
				Toast.makeText(getApplicationContext(), "请求失败，请重试~", 1000).show();
			}
			
		};
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_changephone);
		
		back=(ImageView)findViewById(R.id.cpe_back);
		
		phone1=(EditText)findViewById(R.id.cphone1);
		name=(EditText)findViewById(R.id.cpname1);
		qq=(EditText)findViewById(R.id.cphqq2);
		post=(Button)findViewById(R.id.changephonebtn);
		post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postPsd();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if(BaseApplication.loginbeans.getMsg()!=null){
			
		phone1.setText(BaseApplication.loginbeans.getMsg().getUsMobile());
		name.setText(BaseApplication.loginbeans.getMsg().getUsName());
		qq.setText(BaseApplication.loginbeans.getMsg().getUsQq());
			
		}
		
	}
	
	public void postPsd(){
		
		if(Utils.isEmpty(phone1.getText().toString())){
			
			Toast.makeText(getApplicationContext(), "输入手机号", 1000).show();
			
			return;
		}
		
    if(Utils.isEmpty(name.getText().toString())){
			
			Toast.makeText(getApplicationContext(), "输入姓名", 1000).show();
			
			return;
		}
    if(Utils.isEmpty(qq.getText().toString())){
		
		Toast.makeText(getApplicationContext(), "输入QQ号码", 1000).show();
		
		return;
	}	
    
    showloading("");
    
    AnsyPost.ChanegPsd(BaseApplication.CHANGE_PHONE_URL+"?usMobile="+phone1.getText().toString()+"&usNick="+name.getText().toString()+"&usQq="+qq.getText().toString(), handler);
    
    
	}

}
