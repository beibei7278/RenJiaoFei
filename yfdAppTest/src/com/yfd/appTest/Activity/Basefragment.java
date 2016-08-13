package com.yfd.appTest.Activity;

import java.util.List;

import com.yfd.appTest.Activity.R;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Basefragment extends Fragment{
	
	
	Dialog dialog;

	

	public void showloading(String s) {

		dialog = createLoadingDialog(getActivity(), s);
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

	  public  void initPopWindow(final List<String> list,final Button button){  
	       
	        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindowlist, null);  
	       // contentView.setBackgroundColor(color.light_gray);	          
	        final PopupWindow popupWindow = new PopupWindow(button, 300, LayoutParams.WRAP_CONTENT,true);  
	        popupWindow.setContentView(contentView); 
	        popupWindow.setFocusable(true);  
	        popupWindow.setOutsideTouchable(true); 
	        ListView listView = (ListView) contentView.findViewById(R.id.listpop);
	        
	        listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					button.setText(list.get(arg2));
					popupWindow.dismiss();
				}
			});
	       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);  
	        listView.setAdapter(new adapter(list));  
	          
	        popupWindow.setFocusable(true);  
	        popupWindow.showAsDropDown(button);  
	    } 
	
	  public class adapter extends BaseAdapter{
		  
		  List<String> lists;
		  
		  public adapter(List<String> list) {
			// TODO Auto-generated constructor stub
		lists=list;
		  }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			ViewHolder vh=null;
			
			if(view==null){
				
				vh=new ViewHolder();
				view=LayoutInflater.from(getActivity()).inflate(R.layout.poplist_item, null);
				vh.txt=(TextView)view.findViewById(R.id.poptxt);
				view.setTag(vh);
				
			}else{
				
				vh=(ViewHolder)view.getTag();
				
			}
			
			vh.txt.setText(lists.get(arg0));
			
			return view;
		}
		  
		  
		  
	  }
	  
	  class ViewHolder{
		  
		  TextView txt;
		  
	  }
	  
}
