package com.sunsoft.zyebiz.b2e.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {

	 public ListViewForScrollView(Context context) {
	        super(context);
	    }

	    public ListViewForScrollView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    public ListViewForScrollView(Context context, AttributeSet attrs,
	        int defStyle) {
	        super(context, attrs, defStyle);
	    }
	        
	    
	    /**
	     * 重写该方法，达到使ListView适应ScrollView的效�?
	     */
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	        MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
	}