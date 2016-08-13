package com.sunsoft.zyebiz.b2e.http;

import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpClient;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.BinaryHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.JsonHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;



public class AsyncHttpUtil {

	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

	static {
		client.setMaxConnections(10); // 设置最大连接数 
		client.setConnectTimeout(10000); // 连接超时时间
		client.setResponseTimeout(10000); // 设置读取超时时间
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
	}

	// 用一个完整url获取一个string对象
	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}
	
	// 用一个完整url获取一个string对象
	public static void post(String urlString, AsyncHttpResponseHandler res) {
		client.post(urlString, res);
	}
	
	// url里面带参数
	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	// url里面带参数
	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	
	// 不带参数，获取json对象或者数组
	public static void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	// 不带参数，获取json对象或者数组
	public static void post(String urlString, JsonHttpResponseHandler res) {
		client.post(urlString, res);
	}
	
	// 带参数，获取json对象或者数组
	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}
	
	// 带参数，获取json对象或者数组
	public static void post(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	
	// 下载数据使用，会返回byte数据
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}
	
	// 下载数据使用，会返回byte数据
	public static void post(String uString, BinaryHttpResponseHandler bHandler) {
		client.post(uString, bHandler);
	}
	
	public static AsyncHttpClient getClient() {
		return client;
	}

}
