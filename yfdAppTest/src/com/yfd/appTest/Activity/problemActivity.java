package com.yfd.appTest.Activity;

import com.yfd.appTest.Activity.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class problemActivity extends Activity {
	
	ImageView back;
	TextView protxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_problem);
		
		back=(ImageView)findViewById(R.id.down_back);
		protxt=(TextView)findViewById(R.id.protxt);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	//	protxt.setText(Utils.readAssertResource(this, "problem.txt").replace("\n", "\\n"));
	}

}
