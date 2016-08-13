package com.yfd.appTest.CustomView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;

public class SelectZfuPopupWindow extends PopupWindow {


	private Button btn_take_photo, btn_pick_photo, btn_pick_pt, btn_cancel;
	private View mMenuView;
	RelativeLayout ptzhifu;
	RelativeLayout wxzhifu;
	RelativeLayout lfzfb;
    int come;
	public SelectZfuPopupWindow(Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.alert_chosezhifu_dialog, null);
		btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_wx);
		btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_zfb);
		btn_pick_pt = (Button) mMenuView.findViewById(R.id.btn_pt);
		ptzhifu=(RelativeLayout)mMenuView.findViewById(R.id.lf_ptzf);
		wxzhifu=(RelativeLayout)mMenuView.findViewById(R.id.z_wxzhifu);
		lfzfb=(RelativeLayout)mMenuView.findViewById(R.id.lf_zfb);
		//Toast.makeText(context, BaseApplication.loginbeans.getType(), 1000).show();
		
		if(BaseApplication.loginbeans.getType().equals("0")){
			
			ptzhifu.setVisibility(View.VISIBLE);
			wxzhifu.setVisibility(View.GONE);
			lfzfb.setVisibility(View.GONE);
		}else{
			
			ptzhifu.setVisibility(View.GONE);
			wxzhifu.setVisibility(View.VISIBLE);
			lfzfb.setVisibility(View.VISIBLE);
		}
		
		btn_cancel = (Button) mMenuView.findViewById(R.id.btnzf_cancel);
		//ȡ��ť
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//��ٵ�����
				dismiss();
			}
		});
		
		
		
		//���ð�ť����
		btn_pick_photo.setOnClickListener(itemsOnClick);
		btn_take_photo.setOnClickListener(itemsOnClick);
		btn_pick_pt.setOnClickListener(itemsOnClick);
		//����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		//����SelectPicPopupWindow��������Ŀ�
		this.setWidth(LayoutParams.FILL_PARENT);
		//����SelectPicPopupWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		//����SelectPicPopupWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.AnimBottom);
		//ʵ��һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//����SelectPicPopupWindow��������ı���
		this.setBackgroundDrawable(dw);
		//mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ�����������ٵ�����
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

}
