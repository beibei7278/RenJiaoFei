package com.sunsoft.zyebiz.b2e.model.UpDate;

import java.io.Serializable;
/**
 * 版本更新Bean
 * @author Administrator
 *
 */
public class VersionUpDate implements Serializable{
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
