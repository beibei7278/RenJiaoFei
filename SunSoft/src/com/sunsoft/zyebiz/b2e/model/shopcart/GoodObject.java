package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据
 * id 公司名称 商品图片 商品名 商品颜色 单价 总价 数量
 */
public class GoodObject implements Serializable{
	private String recId;
	private String userId;
	private String goodsId;
	private String goodsImg;
	private String goodsName;
	private String goodsPrice;
	private String marketPrice;
	private String goodsNumber;
	private boolean isChoosed;				//商品是否被选中
	private String shangpinNum;
	private String selectSize;
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public boolean isChoosed() {
		return isChoosed;
	}
	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
	public String getShangpinNum() {
		return shangpinNum;
	}
	public void setShangpinNum(String shangpinNum) {
		this.shangpinNum = shangpinNum;
	}
	public String getSelectSize() {
		return selectSize;
	}
	public void setSelectSize(String selectSize) {
		this.selectSize = selectSize;
	}


	
	

	
	
	
	
}
