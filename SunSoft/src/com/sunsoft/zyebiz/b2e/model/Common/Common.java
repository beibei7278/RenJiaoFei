package com.sunsoft.zyebiz.b2e.model.Common;

import java.io.Serializable;
import java.util.List;

import com.sunsoft.zyebiz.b2e.model.Login.LoginDate;

/**
 * ¹«¸æ±êÇ©
 */
public class Common implements Serializable{
	private String title;
	private String body;
	private String message;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
	
	
	
}
