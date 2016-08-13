package com.yfd.appTest.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfd.appTest.Activity.R;

public class RegestFragment extends Fragment {



	View Mview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_regest, null);
		
		return Mview;
	}
	

	
}
