package com.yfd.appTest.Activity;

import com.yfd.appTest.Activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInfoActivity extends Activity {
	
	private TextView username,name,phone,qq;
	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_myinfo);
		username=(TextView)findViewById(R.id.my_username);
		name=(TextView)findViewById(R.id.my_name);
		phone=(TextView)findViewById(R.id.my_phone);
		qq=(TextView)findViewById(R.id.my_qq);
		back=(ImageView)findViewById(R.id.myinfo_back);
		
		if(BaseApplication.which==0){
		if(BaseApplication.loginbeans.getMsg()!=null){
			
			username.setText(BaseApplication.loginbeans.getMsg().getUsName());
			name.setText(BaseApplication.loginbeans.getMsg().getUsNick());
			phone.setText(BaseApplication.loginbeans.getMsg().getUsMobile());
			qq.setText(BaseApplication.loginbeans.getMsg().getUsQq());
			
		}
		}else{
			
			name.setText(BaseApplication.loginbeansc.getMsg().getUsername());
			
		}
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	

}
