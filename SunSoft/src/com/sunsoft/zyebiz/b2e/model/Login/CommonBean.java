package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;
import java.util.List;

import com.sunsoft.zyebiz.b2e.model.Login.LoginDate;

/**
 * 相同的Bean的属性的组成：
 * 标题（title）,中间体（body）,信息（message）
 */
public class CommonBean implements Serializable{
	private String title;
	private List<LoginDate> body;
	private String message;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<LoginDate> getBody() {
		return body;
	}
	public void setBody(List<LoginDate> body) {
		this.body = body;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
