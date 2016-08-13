package com.yfd.appTest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfd.appTest.adapter.ImageChaceLoader.Type;


public class ImagchoseViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private ImagchoseViewHolder(Context context, ViewGroup parent, int layoutId,
			int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * �õ�һ��ViewHolder����
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ImagchoseViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position)
	{
		ImagchoseViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ImagchoseViewHolder(context, parent, layoutId, position);
		} else
		{
			holder = (ImagchoseViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView()
	{
		return mConvertView;
	}

	/**
	 * ͨ��ؼ���Id��ȡ���ڵĿؼ������û�������views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * ΪTextView�����ַ�
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ImagchoseViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ImagchoseViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ImagchoseViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ImagchoseViewHolder setImageByUrl(int viewId, String url)
	{
		ImageChaceLoader.getInstance(3,Type.LIFO).loadImage(url, (ImageView) getView(viewId));
		
		return this;
	}

	public int getPosition()
	{
		return mPosition;
	}

}
