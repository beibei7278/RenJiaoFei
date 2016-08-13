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
import com.yfd.appTest.Beans.LoginBeansStr;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class YjfkActivity extends BaseActivity {
	
	ImageView back;
	EditText option;
	Button save;
	LoginBeansStr lbeans;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			dimissloading();
			if(msg.obj!=null){
				
				lbeans = (LoginBeansStr) Utils.jsonToBean(msg.obj.toString(), LoginBeansStr.class);
				
				Toast.makeText(getApplicationContext(), lbeans.getMsg(), 1000).show();
			}else{
				
				Toast.makeText(getApplicationContext(), "请求失败，请重试~", 1000).show();	
				
			}
			
		};
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_yjfk);
		
		back=(ImageView)findViewById(R.id.yjfk_back);
		option=(EditText)findViewById(R.id.option);
		save=(Button)findViewById(R.id.saveoption);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(Utils.isEmpty(option.getText().toString())){
					
					Toast.makeText(getApplicationContext(), "请填写内容", 1000).show();
					return;
					
				}
				showloading("");
				AnsyPost.ChanegPsd(BaseApplication.YJFK_URL+"?opinion="+option.getText().toString(), handler);
				
			}
		});
	}

}
