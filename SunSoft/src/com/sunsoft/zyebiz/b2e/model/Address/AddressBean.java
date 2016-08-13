package com.sunsoft.zyebiz.b2e.model.Address;

/**
 * 我的智园：默认收货地址
 */
import java.io.Serializable;

public class AddressBean implements Serializable {
	private String title;
	private String body;

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
	
	

}
