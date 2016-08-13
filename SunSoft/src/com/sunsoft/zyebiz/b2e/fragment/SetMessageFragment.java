package com.sunsoft.zyebiz.b2e.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunsoft.zyebiz.b2e.R;

public class SetMessageFragment extends Fragment implements OnClickListener{
	//消息推送设置界面
	private View messageView;
	//返回键
	private ImageView backImageButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageView = View.inflate(getActivity(), R.layout.fragment_set_message, null);
		return messageView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		backImageButton = (ImageView) messageView.findViewById(R.id.img_title_back);
	}

	/**
	 * 对点击事件做处理
	 */
	@Override
	public void onClick(View v) {
		int vId = v.getId();
		switch (vId) {
		case R.id.img_title_back:
			onDestroy();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
