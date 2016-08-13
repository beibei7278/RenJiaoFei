package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据 id 公司名称 商品图片 商品名 商品颜色 单价 总价 数量
 */
public class BodyList implements Serializable {

	private String supplierName;
	private List<GoodsList> goods;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<GoodsList> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsList> goods) {
		this.goods = goods;
	}

}
