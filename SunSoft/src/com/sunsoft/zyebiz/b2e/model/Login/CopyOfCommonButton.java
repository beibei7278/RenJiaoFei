package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;
import java.util.List;

import com.sunsoft.zyebiz.b2e.model.Login.LoginDate;

/**
 * ¹«¸æ±êÇ©
 */
public class CopyOfCommonButton implements Serializable{
	private String title;
	private CopyOfBodyResult body;
	public String getTitle() {
		return title;
	}
	public CopyOfBodyResult getBody() {
		return body;
	}
	public void setBody(CopyOfBodyResult body) {
		this.body = body;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	
	
	
	
	
}
