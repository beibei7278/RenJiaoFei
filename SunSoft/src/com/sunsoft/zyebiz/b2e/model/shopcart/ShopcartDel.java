package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 删除购物车数据
 */
public class ShopcartDel implements Serializable{
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
