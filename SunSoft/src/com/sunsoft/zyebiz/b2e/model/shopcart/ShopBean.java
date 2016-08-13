package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据
 */
public class ShopBean implements Serializable{
	private String title;
	private BodyResult body;
	public String getTitle() {
		return title;
	}
	public BodyResult getBody() {
		return body;
	}
	public void setBody(BodyResult body) {
		this.body = body;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	
	
	
}
