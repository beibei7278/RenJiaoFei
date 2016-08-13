package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;


/**
 * 购物车商品属�?
 * @author Administrator
 *
 */
public class ShopcartBean implements Serializable{
	private String shopcartId;					//购物车物品id
	private String commodityId;			//商品ID
	private String commodityType;		//	商品类型
	private String shopcartImg;				//商品图片资源
	private String shopcartName;			//商品名称
	private String shopcartInfo;				//商品描述
	private double shopcartPrice;			//商品单价
	private int shopcartCount;				//商品数量
	private boolean isChoosed;				//商品是否被�?�?
	

	
	public String getShopcartId() {
		return shopcartId;
	}
	public void setShopcartId(String shopcartId) {
		this.shopcartId = shopcartId;
	}
	
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public String getShopcartImg() {
		return shopcartImg;
	}
	public void setShopcartImg(String shopcartImg) {
		this.shopcartImg = shopcartImg;
	}
	public String getShopcartName() {
		return shopcartName;
	}
	public void setShopcartName(String shopcartName) {
		this.shopcartName = shopcartName;
	}
	public String getShopcartInfo() {
		return shopcartInfo;
	}
	public void setShopcartInfo(String shopcartInfo) {
		this.shopcartInfo = shopcartInfo;
	}
	
	public double getShopcartPrice() {
		return shopcartPrice;
	}
	public void setShopcartPrice(double shopcartPrice) {
		this.shopcartPrice = shopcartPrice;
	}
	public int getShopcartCount() {
		return shopcartCount;
	}
	public void setShopcartCount(int shopcartCount) {
		this.shopcartCount = shopcartCount;
	}
	public boolean isChoosed() {
		return isChoosed;
	}
	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
	@Override
	public String toString() {
		return "ShopcartBean [shopcartId=" + shopcartId + ", commodityId="
				+ commodityId + ", shopcartImg=" + shopcartImg
				+ ", shopcartName=" + shopcartName + ", shopcartInfo="
				+ shopcartInfo + ", shopcartPrice=" + shopcartPrice
				+ ", shopcartCount=" + shopcartCount + ", isChoosed="
				+ isChoosed + "]";
	}
	
	
	
	
	
}
