package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;
import java.util.List;

import com.sunsoft.zyebiz.b2e.model.Login.LoginDate;

/**
 * 登陆时的信息
 */
public class CommonButton implements Serializable{
	private String title;  //0表示成功，1表示失败
	private BodyResult body;
	private String message;
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public BodyResult getBody() {
		return body;
	}

	public void setBody(BodyResult body) {
		this.body = body;
	}


	
	
	
	
	
}
