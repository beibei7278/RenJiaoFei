package com.sunsoft.zyebiz.b2e.model.BuyAccount;
/**
 * �Ź�������֧�����������
 */
import java.io.Serializable;

public class BuyBean implements Serializable{
	private String title;
	private BodyResult body;
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
	
	

}
