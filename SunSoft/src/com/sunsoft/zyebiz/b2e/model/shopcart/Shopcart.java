package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据
 */
public class Shopcart implements Serializable{
	private String title;
	private List<BodyList> body;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<BodyList> getBody() {
		return body;
	}
	public void setBody(List<BodyList> body) {
		this.body = body;
	}
	
	
	
	
}
