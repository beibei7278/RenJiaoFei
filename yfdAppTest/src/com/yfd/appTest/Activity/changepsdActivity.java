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

public class changepsdActivity extends BaseActivity {
	
	ImageView back;
	
	EditText psd1,psd2;
	
	Button post;
	private MessagebackBeans bmsg;
	private Handler handler=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			
			dimissloading();
			
			if(msg.obj==null){
				
				Toast.makeText(getApplicationContext(), "请求失败，请重试~", 1000).show();
				
			}else{
				
				bmsg = (MessagebackBeans) Utils.jsonToBean(
						msg.obj.toString(), MessagebackBeans.class);
				
				Toast.makeText(getApplicationContext(), bmsg.getMsg(), 1000).show();
			}
			
		};
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_changepsd);
		
		back=(ImageView)findViewById(R.id.cpd_back);
		
		psd1=(EditText)findViewById(R.id.cpsd1);
		psd2=(EditText)findViewById(R.id.cpsd2);
		post=(Button)findViewById(R.id.changepsdbtn);
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
	}
	
	public void postPsd(){
		
		if(Utils.isEmpty(psd1.getText().toString())){
			
			Toast.makeText(getApplicationContext(), "输入新密码", 1000).show();
			
			return;
		}
		
    if(Utils.isEmpty(psd2.getText().toString())){
			
			Toast.makeText(getApplicationContext(), "再次输入新密码", 1000).show();
			
			return;
		}
		
    
    showloading("");
    AnsyPost.ChanegPsd(BaseApplication.CHANGE_PSD_URL+"?usPass="+psd2.getText().toString(), handler);
//    if(BaseApplication.loginbeans.getType().equals("1")){
//    	
//    	}
//    else{
//    	
//    	AnsyPost.ChanegPsd(BaseApplication.CHANGE_PSD_URLLF+"?usPass="+psd2.getText().toString(), handler);
//    	
//    }
    
    
	}

}
