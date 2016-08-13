package com.yfd.appTest.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Fragment.GuohuFragment;
import com.yfd.appTest.Fragment.bukaFragment;

public class ShouhouActivity extends BasefragmentActivity {
	private Fragment mContent;
	
	private ImageView back;

	private bukaFragment buka;

	private GuohuFragment guohu;
	
	private RadioButton bk,gh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_shouhou);
		back=(ImageView)findViewById(R.id.shcl_back);
		bk=(RadioButton)findViewById(R.id.sh_bk);
		gh=(RadioButton)findViewById(R.id.sh_gh);
		setDefaultFragment() ;
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
bk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switchContent(buka);
			}
		});

gh.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switchContent(guohu);
	}
});
	}
	
	private void setDefaultFragment() {
		buka = new bukaFragment(getApplicationContext());
		guohu=new GuohuFragment(getApplicationContext());

		mContent=buka;
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.shcon, buka);
		transaction.commit();
	}
	
	public void switchContent(Fragment to) {
		if (mContent != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { 
				transaction.hide(mContent).add(R.id.shcon, to).commit(); 
			} else {
				transaction.hide(mContent).show(to).commit(); 
			}
			mContent = to;
		}
	
	}

}
