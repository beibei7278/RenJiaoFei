package com.yfd.appTest.adapter;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.ImagechoseActivity;


public class MyImagechoseAdapter extends CommonAdapter<String>
{

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路�?
	 */
	private String mDirPath;
	Context context;
	public MyImagechoseAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath)
	{
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.context=context;
	}

	@Override
	public void convert(final ImagchoseViewHolder helper, final String item)
	{
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
				helper.setImageResource(R.id.id_item_select,R.drawable.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
		
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		
		mImageView.setColorFilter(null);
		//设置ImageView的点击事�?
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反�?
			@Override
			public void onClick(View v)
			{

//				// 已经选择过该图片
//				if (mSelectedImage.contains(mDirPath + "/" + item))
//				{
//					mSelectedImage.remove(mDirPath + "/" + item);
//					mSelect.setImageResource(R.drawable.picture_unselected);
//					mImageView.setColorFilter(null);
//				} else
//				
//				{
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.pictures_selected);
					mSelect.setVisibility(View.VISIBLE);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
//				}
					
					Activity activity = (Activity) context;
					Intent intent = new Intent(activity,ImagechoseActivity.class);
                    String passString = mDirPath + "/" + item;
                    intent.putExtra("path1", passString);
                    activity.setResult(BaseApplication.FromImg, intent);
                    activity.finish();
				
			}
		});
		
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item))
		{
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
}
