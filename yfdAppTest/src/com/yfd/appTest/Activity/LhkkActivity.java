package com.yfd.appTest.Activity;

import com.yfd.appTest.Activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LhkkActivity extends Activity {

	private ImageView phkk_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lhkk);
		phkk_back=(ImageView)findViewById(R.id.phkk_back);
		
		
		
		
		phkk_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	
}
