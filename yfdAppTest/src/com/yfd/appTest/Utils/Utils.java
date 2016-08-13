package com.yfd.appTest.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.yfd.appTest.Activity.RegestActivity;
import com.yfd.appTest.Beans.Messag;

public class Utils {

	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		Gson gson = new Gson();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static String getUTF8XMLString(String xml) {
		// A StringBuffer Object

		try {
			xml = URLEncoder.encode(xml, "UTF-8");
			Log.i("7878", xml);
			System.out.println(xml);

		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		return xml;

	}

	public static String getlocalSysDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		return date;

	}

	public static String getlocalSysDatehhmm() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new java.util.Date());
		return date;

	}

	public static String getlocalSysDatetime() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM-dd_HH_mm_sss");
		String date = sdf.format(new java.util.Date());
		return date + ".png";

	}

	public static int getDayofweek() {
		Calendar calendar = Calendar.getInstance();

		int week = calendar.get(Calendar.WEEK_OF_MONTH);

		int day = calendar.get(Calendar.DAY_OF_WEEK);
		// if (day==1) { day=7;
		//
		// } else { day=day-1;
		// }
		return day;
	}

	public static String GetDateJ(int m, String format) {
		Date date = new Date();
		SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String n = h.format(date);
		Timestamp time = Timestamp.valueOf(n);

		long l = time.getTime() - 24 * 60 * 60 * m * 1000;
		// long l = time.getTime() + 10*60*m*1000;
		time.setTime(l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 锟斤拷取锟斤拷前时锟戒并锟斤拷式锟斤拷之
		String newDate = sdf.format(time);
		return newDate;
	}

	// 锟斤拷锟斤拷锟斤拷前锟斤拷 锟斤拷锟斤拷
	public static String GetDateADD(int m, String format) {
		Date date = new Date();
		SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String n = h.format(date);
		Timestamp time = Timestamp.valueOf(n);
		// 锟斤拷锟斤拷锟斤拷锟较加ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷
		long l = time.getTime() + 24 * 60 * 60 * m * 1000;
		// long l = time.getTime() + 10*60*m*1000;
		time.setTime(l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 锟斤拷取锟斤拷前时锟戒并锟斤拷式锟斤拷之
		String newDate = sdf.format(time);
		return newDate;
	}

	public static String getDtaeafter(int dex) {

		int mDay;

		int month;

		int year;

		String date;

		final Calendar c = Calendar.getInstance();

		mDay = c.get(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DAY_OF_MONTH, mDay + dex);

		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);

		date = String.valueOf(year) + "-" + String.valueOf(month) + "-"
				+ String.valueOf(mDay);

		return date;
	}

	public static String getYearbeforetoday(int years) {

		Date date = new Date();
		SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd");
		String n = h.format(date);

		int year = Integer.valueOf(n.split("-")[0]) - years;

		return String.valueOf(year);

	}

	public static String get17YYMMDDSSS() {

		Date date = new Date();
		SimpleDateFormat h = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String n = h.format(date);

		return n;

	}

	/**
	 * 加载本地图片 http://bbs.3gstdy.com
	 * 
	 * @param url
	 * @return
	 */

	public static Bitmap getLoacalBitmap(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);
		// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) {// 循环判断如果压缩后图片是否大于100kb,大于继续压缩       
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;

	}

	public static class JSONParser {
	}

	public static void SaveUserinfo(Context con, String uname, String psd,
			String miUseScard,String name, String usAddr, String usId, String usMobile,String usState
			) {

		SharedPreferences mySharedPreferences = con.getSharedPreferences(
				"user", Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = mySharedPreferences.edit();

		editor.putString("uname", uname);
		editor.putString("upsd", psd);
		editor.putString("miUseScard", miUseScard);
		editor.putString("usId", usId);
		editor.putString("usAddr", usAddr);
		editor.putString("usMobile", usMobile);
		editor.putString("name", name);
		editor.putString("usState", usState);

		// 提交当前数据
		editor.commit();

	}

	public static String getUname(Context con) {

		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String name = sharedPreferences.getString("uname", "");
		return name;

	}

	public static String getUpsd(Context con) {

		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("upsd", "");
		return name;

	}

	public static String getMiUseScard(Context con) {

		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String miUseScard = sharedPreferences.getString("miUseScard", "");
		return miUseScard;

	}

	public static String getUsId(Context con) {
		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String usId = sharedPreferences.getString("usId", "");
		return usId;

	}

	public static String getUsAddr(Context con) {
		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String usAddr = sharedPreferences.getString("usAddr", "");
		return usAddr;
	}

	public static String getUsMobile(Context con) {
		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String usMobile = sharedPreferences.getString("usMobile", "");
		return usMobile;
	}

	public static String getName(Context con) {

		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("name", "");
		return name;
	}
	public static String getUsState(Context con) {
		SharedPreferences sharedPreferences = con.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		String usState = sharedPreferences.getString("usState", "");
		return usState;
	}
	
	public static void cleanRegistInfo(Context mContext) {
		SharedPreferences pref = mContext.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 利用BufferedReader实现Inputstream转换成String <功能详细描述>
	 * 
	 * @param in
	 * @return String
	 */

	public static String Inputstr2Str_Reader(InputStream in, String encode) {

		String str = "";
		try {
			if (encode == null || encode.equals("")) {
				// 默认以utf-8形式
				encode = "utf-8";
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, encode));
			StringBuffer sb = new StringBuffer();

			while ((str = reader.readLine()) != null) {
				sb.append(str).append("\n");

			}
			return sb.toString();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 利用byte数组转换InputStream------->String <功能详细描述>
	 * 
	 * @param in
	 * @return
	 * @see [类、类#方法、类#成员]
	 */

	public static String Inputstr2Str_byteArr(InputStream in, String encode) {
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[1024];
		int len = 0;
		try {
			if (encode == null || encode.equals("")) {
				// 默认以utf-8形式
				encode = "utf-8";
			}
			while ((len = in.read(b)) != -1) {
				sb.append(new String(b, 0, len, encode));
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 利用ByteArrayOutputStream：Inputstream------------>String <功能详细描述>
	 * 
	 * @param in
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String Inputstr2Str_ByteArrayOutputStream(InputStream in,
			String encode) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		try {
			if (encode == null || encode.equals("")) {
				// 默认以utf-8形式
				encode = "utf-8";
			}
			while ((len = in.read(b)) > 0) {
				out.write(b, 0, len);
			}
			return out.toString(encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 利用ByteArrayInputStream：String------------------>InputStream <功能详细描述>
	 * 
	 * @param inStr
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static InputStream Str2Inputstr(String inStr) {
		try {
			// return new ByteArrayInputStream(inStr.getBytes());
			// return new ByteArrayInputStream(inStr.getBytes("UTF-8"));
			return new StringBufferInputStream(inStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();

		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {

			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {

					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String Get32ocdeStr() {

		String code = "weixin" + get17YYMMDDSSS() + getRandom10();

		return code;

	}

	public static String getRandom10() {
		Random r = new Random();
		long num = Math.abs(r.nextLong() % 1000000000L);
		String s = String.valueOf(num);
		return s;
	}

	public static String readAssertResource(Context context,
			String strAssertFileName) {
		AssetManager assetManager = context.getAssets();
		String strResponse = "";
		try {
			InputStream ims = assetManager.open(strAssertFileName);
			strResponse = getStringFromInputStream(ims);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strResponse;
	}

	private static String getStringFromInputStream(InputStream a_is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(a_is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}

}
