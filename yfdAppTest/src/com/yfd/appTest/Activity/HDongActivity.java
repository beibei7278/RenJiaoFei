package com.yfd.appTest.Activity;

import com.yfd.appTest.Activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class HDongActivity extends Activity {
	
	ImageView back;
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_hd);
		
		Intent intent=getIntent();
		String url=intent.getStringExtra("url");
		
		back=(ImageView)findViewById(R.id.hd_back);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		init(url);
	}
	private void init(String url){
		webview = (WebView) findViewById(R.id.hdwebview);
        //WebView����web��Դ
		webview.loadUrl(url);
        //����WebViewĬ��ʹ�õ����ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
		webview.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ���������������
             view.loadUrl(url);
            return true;
        }
       });
    }
}
