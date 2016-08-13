package com.mining.app.zxing.decoding;

import android.content.Context;

public class Util {

	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}
	
	  public static boolean isEmpty( String input ) 
		{
			if ( input == null || "".equals( input ) )
				return true;
			
			for ( int i = 0; i < input.length(); i++ ) 
			{
				char c = input.charAt( i );
				if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
				{
					return false;
				}
			}
			return true;
		}
}
