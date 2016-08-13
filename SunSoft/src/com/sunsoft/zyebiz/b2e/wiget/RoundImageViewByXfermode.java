//package com.sunsoft.zyebiz.b2e.wiget;
//
//
//
//
//import java.lang.ref.WeakReference;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PorterDuff.Mode;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.RectF;
//import android.graphics.Xfermode;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.widget.ImageView;
//
///**
// * 自定义圆角图�?
// * @author Administrator
// *
// */
//public class RoundImageViewByXfermode extends ImageView {
//	/**Paint*/
//	private Paint mPaint;
//	/**定义Xfermode类型*/
//	private Xfermode mXfermode = new PorterDuffXfermode(Mode.DST_IN);
//	/**定义画布*/
//	private Bitmap mMaskBitmap;
//	/**WeakReference*/
//	private WeakReference<Bitmap> mWeakBitmap;
//
//	/**
//	 * 图片的类型，圆形or圆角
//	 */
//	private int type;
//	/**圆形类型*/
//	public static final int TYPE_CIRCLE = 0;
//	/**圆角类型*/
//	public static final int TYPE_ROUND = 1;
//	/**
//	 * 圆角大小的默认�?
//	 */
//	private static final int BODER_RADIUS_DEFAULT = 5;
//	/**
//	 * 圆角的大�?
//	 */
//	private int mBorderRadius;
//
//	/**
//	 * 构�?方法
//	 * @param context 上下�?
//	 */
//	public RoundImageViewByXfermode(Context context) {
//		this(context,null);
//
//		mPaint = new Paint();
//		mPaint.setAntiAlias(true);
//	}
//	/**
//	 * 构�?方法
//	 * @param context 上下�?
//	 * @param attrs AttributeSet
//	 */
//	public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
//		super(context, attrs);
//
//		mPaint = new Paint();
//		mPaint.setAntiAlias(true);
//		// 默认�?0dp
//		mBorderRadius = BODER_RADIUS_DEFAULT;
//		Log.e("TAG", mBorderRadius + "");
//		// 默认为ROUND
//		type = TYPE_ROUND;
//	}
//
//	/**
//	 * 测试屏幕的宽和高
//	 * @param widthMeasureSpec widthMeasureSpec
//	 * @param heightMeasureSpec heightMeasureSpec
//	 */
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//		/**
//		 * 如果类型是圆形，则强制改变view的宽高一致，以小值为�?
//		 */
//		if (type == TYPE_CIRCLE) {
//			int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
//			setMeasuredDimension(width, width);
//		}
//
//	}
//
//	/**
//	 * 初始化数�?
//	 */
//	public void invalidate() {
//		mWeakBitmap = null;
//		if (mMaskBitmap != null) {
//			mMaskBitmap.recycle();
//			mMaskBitmap = null;
//		}
//		super.invalidate();
//	}
///**
// * onDraw方法
// */
//	@SuppressLint("DrawAllocation")
//	@Override
//	protected void onDraw(Canvas canvas) {
//		//在缓存中取出bitmap
//		Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
//
//		if (null == bitmap || bitmap.isRecycled()) {
//			//拿到Drawable
//			Drawable drawable = getDrawable();
//			
//			//获取drawable的宽和高
//			int dWidth = drawable.getIntrinsicWidth();
//			int dHeight = drawable.getIntrinsicHeight();
//
//			if (drawable != null) {
//				//创建bitmap
//				bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
//						Config.ARGB_8888);
//				float scale = 1.0f;
//				//创建画布
//				Canvas drawCanvas = new Canvas(bitmap);
//				//按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
//				if (type == TYPE_ROUND) {
//					// 如果图片的宽或�?高与view的宽高不匹配，计算出�?��缩放的比例；缩放后的图片的宽高，�?��要大于我们view的宽高；�?��我们这里取大值；
//					scale = Math.max(getWidth() * 1.0f / dWidth, getHeight()
//							* 1.0f / dHeight);
//				} else {
//					scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
//				}
//				//根据缩放比例，设置bounds，相当于缩放图片�?
//				drawable.setBounds(0, 0, (int)(scale * dWidth), (int)(scale * dHeight));
//				
//				drawable.draw(drawCanvas);
//				if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
//					mMaskBitmap = getBitmap();
//				}
//				// Draw Bitmap.
//				mPaint.reset();
//				mPaint.setFilterBitmap(false);
//				mPaint.setXfermode(mXfermode);
//				//绘制形状
//				drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
//				mPaint.setXfermode(null);
//				//将准备好的bitmap绘制出来
//				canvas.drawBitmap(bitmap, 0, 0, null);
//				//bitmap缓存起来，避免每次调用onDraw，分配内�?
//				mWeakBitmap = new WeakReference<Bitmap>(bitmap);
//			}
//		}
//		//如果bitmap还存在，则直接绘制即�?
//		if (bitmap != null) {
//			mPaint.setXfermode(null);
//			canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
//			return;
//		}
//
//	}
//	/**
//	 * 绘制形状
//	 * @return Bitmap
//	 */
//	public Bitmap getBitmap() {
//		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
//				Bitmap.Config.ARGB_4444);
//		Canvas canvas = new Canvas(bitmap);
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paint.setColor(Color.BLACK);
//
//		if (type == TYPE_ROUND) {
//			canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
//					mBorderRadius, mBorderRadius, paint);
//		} else {
//			canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
//					paint);
//		}
//
//		return bitmap;
//	}
//}
