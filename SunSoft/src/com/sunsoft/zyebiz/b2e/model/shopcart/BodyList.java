package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * ���ﳵ���� id ��˾���� ��ƷͼƬ ��Ʒ�� ��Ʒ��ɫ ���� �ܼ� ����
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
