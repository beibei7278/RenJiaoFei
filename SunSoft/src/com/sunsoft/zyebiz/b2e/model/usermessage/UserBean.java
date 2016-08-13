package com.sunsoft.zyebiz.b2e.model.usermessage;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据 id 公司名称 商品图片 商品名 商品颜色 单价 总价 数量
 */
public class UserBean implements Serializable {

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
