package com.yfd.appTest.Utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yfd.appTest.Activity.BaseApplication;

public class AnsyPost {
	
	private Context context;
	
	
	public static void Login(String url,final Handler handler){
		
		//this.handler=handler;
		
		JsonObjectRequest jq = new JsonObjectRequest(url, null,new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub		
						
						Message msg=new Message();
						msg.obj=response;
						handler.sendMessage(msg);
						
					}

				    }, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Message msg=new Message();
						msg.obj=null;
						handler.sendMessage(msg);
						System.out.println("rereer---"+error.toString());
						
					}
				})

		;
		
		jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
		
		BaseApplication.Requeue.add(jq);
		
	}
	
	
	public static void ChanegPsd(String url,final Handler handler){
		
		//this.handler=handler;
		
		JsonObjectRequest jq = new JsonObjectRequest(Request.Method.POST,url, null,new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						
						Message msg=new Message();
						msg.obj=response;
						handler.sendMessage(msg);
						System.out.println("obj---"+response.toString());
					}

				    }, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Message msg=new Message();
						msg.obj=null;
						handler.sendMessage(msg);
						System.out.println("rereer---"+error.toString());
						
					}
				})
		        {
		       @Override
				public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
					// TODO Auto-generated method stub

					  HashMap<String, String> headers = new HashMap<String, String>();
					
					  headers.put("Cookie","JSESSIONID="+ BaseApplication.loginbeans.getMsg().getSessionId());  
			         
					  return headers;
				}
				
			};
			jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
		    BaseApplication.Requeue.add(jq);
		    
	    }
	public static void getkkSearch(String url,final Handler handler){
		
		//this.handler=handler;
		
		JsonObjectRequest jq = new JsonObjectRequest(url, null,new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						
						Message msg=new Message();
						msg.obj=response;
						handler.sendMessage(msg);
						System.out.println("obj---"+response.toString());
					}

				    }, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Message msg=new Message();
						msg.obj=null;
						handler.sendMessage(msg);
						System.out.println("rereer---"+error.toString());
						
					}
				})
		        {
		       @Override
				public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
					// TODO Auto-generated method stub

					  HashMap<String, String> headers = new HashMap<String, String>();
					
					  headers.put("Cookie","JSESSIONID="+ BaseApplication.loginbeans.getMsg().getSessionId());  
			         
					  return headers;
				}
				
			};
			jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
		    BaseApplication.Requeue.add(jq);
		    
	    }
public static void Saomiao(String url,final Handler handler){
		
		//this.handler=handler;
		
		JsonObjectRequest jq = new JsonObjectRequest(url, null,new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						
						Message msg=new Message();
						msg.obj=response;
						handler.sendMessage(msg);
						
					}

				    }, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Message msg=new Message();
						msg.obj=null;
						handler.sendMessage(msg);
						System.out.println("rereer---"+error.toString());
						
					}
				})
		        {
		       @Override
				public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
					// TODO Auto-generated method stub

					  HashMap<String, String> headers = new HashMap<String, String>();
					
					  headers.put("sessionID", BaseApplication.loginbeans.getMsg().getSessionId());  
			         
					  return headers;
				}
				
			};
			jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
		    BaseApplication.Requeue.add(jq);
		    
	    }

public static void Phkksave(String url,final Handler handler){
	
	//this.handler=handler;
	
	JsonObjectRequest jq = new JsonObjectRequest(url, null,new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stub
					
					Message msg=new Message();
					msg.obj=response;
					handler.sendMessage(msg);
					
				}

			    }, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					Message msg=new Message();
					msg.obj=null;
					handler.sendMessage(msg);
					System.out.println("rereer---"+error.toString());
					
				}
			})
	        {
	       @Override
			public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
				// TODO Auto-generated method stub

				  HashMap<String, String> headers = new HashMap<String, String>();
				
				  headers.put("sessionID", BaseApplication.loginbeans.getMsg().getSessionId());  
		         
				  return headers;
			}
			
		};
		jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
	    BaseApplication.Requeue.add(jq);
	    
    }
public static void Czpost(String url,final Handler handler){
	
	//this.handler=handler;
	
	JsonObjectRequest jq = new JsonObjectRequest(url, null,new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					// TODO Auto-generated method stub
					
					Message msg=new Message();
					msg.obj=response;
					handler.sendMessage(msg);
					
				}

			    }, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					Message msg=new Message();
					msg.obj=null;
					handler.sendMessage(msg);
					System.out.println("rereer---"+error.toString());
					
				}
			})
	        {
	       @Override
			public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
				// TODO Auto-generated method stub

				  HashMap<String, String> headers = new HashMap<String, String>();
				
				  headers.put("Cookie","JSESSIONID="+ BaseApplication.loginbeansc.getMsg().getIds());  
		         
				  return headers;
			}
			
		};
		jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
	    BaseApplication.Requeue.add(jq);
	    
    }

public static void getArrylistpost(String url,final Handler handler){
	
	//this.handler=handler;
	
	JsonArrayRequest jq = new JsonArrayRequest(url,new Listener<JSONArray>() {

				@Override
				public void onResponse(JSONArray response) {
					// TODO Auto-generated method stub
					
					Message msg=new Message();
					msg.obj=response;
					handler.sendMessage(msg);
					
				}

			    }, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					Message msg=new Message();
					msg.obj=null;
					handler.sendMessage(msg);
					System.out.println("rereer---"+error.toString());
					
				}
			})
	        {
	       @Override
			public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
				// TODO Auto-generated method stub

				  HashMap<String, String> headers = new HashMap<String, String>();
				
				  headers.put("Cookie","JSESSIONID="+ BaseApplication.loginbeans.getMsg().getSessionId());  
		         
				  return headers;
			}
			
		};
		jq.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 1, 1.0f));
	    BaseApplication.Requeue.add(jq);
	    
    }
}
