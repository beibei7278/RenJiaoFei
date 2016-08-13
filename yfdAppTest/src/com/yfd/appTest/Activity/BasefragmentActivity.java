package com.yfd.appTest.Activity;

import com.yfd.appTest.Activity.R;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasefragmentActivity extends FragmentActivity {
	
	
	Dialog dialog;
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}
	

	public void showloading(String s) {

		dialog = createLoadingDialog(this, s);
		dialog.show();

	}

	public void dimissloading() {

		dialog.dismiss();
	}

	public boolean dilagShow() {

		if (dialog.isShowing()) {

			return true;

		}

		return false;

	}


	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dilag, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
	
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
	
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		return loadingDialog;

	}

	
}
