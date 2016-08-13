package com.sunsoft.zyebiz.b2e.http;

import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpClient;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.BinaryHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.JsonHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;



public class AsyncHttpUtil {

	private static AsyncHttpClient client = new AsyncHttpClient(); // ʵ��������

	static {
		client.setMaxConnections(10); // ������������� 
		client.setConnectTimeout(10000); // ���ӳ�ʱʱ��
		client.setResponseTimeout(10000); // ���ö�ȡ��ʱʱ��
		client.setTimeout(10000); // �������ӳ�ʱ����������ã�Ĭ��Ϊ10s
	}

	// ��һ������url��ȡһ��string����
	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}
	
	// ��һ������url��ȡһ��string����
	public static void post(String urlString, AsyncHttpResponseHandler res) {
		client.post(urlString, res);
	}
	
	// url���������
	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	// url���������
	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	
	// ������������ȡjson�����������
	public static void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	// ������������ȡjson�����������
	public static void post(String urlString, JsonHttpResponseHandler res) {
		client.post(urlString, res);
	}
	
	// ����������ȡjson�����������
	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}
	
	// ����������ȡjson�����������
	public static void post(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
	
	// ��������ʹ�ã��᷵��byte����
	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}
	
	// ��������ʹ�ã��᷵��byte����
	public static void post(String uString, BinaryHttpResponseHandler bHandler) {
		client.post(uString, bHandler);
	}
	
	public static AsyncHttpClient getClient() {
		return client;
	}

}
