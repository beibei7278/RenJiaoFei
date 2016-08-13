package com.sunsoft.zyebiz.b2e.model.Regist;

/**
 * 登陆信息的Beanַ
 */
import java.io.Serializable;

public class LoginBean implements Serializable {
	private String title;
	private BodyResult body;
	private String message;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BodyResult getBody() {
		return body;
	}

	public void setBody(BodyResult body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	

}
