package com.sunsoft.zyebiz.b2e.model.shopcart;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车数据
 * id 公司名称 商品图片 商品名 商品颜色 单价 总价 数量
 */
public class GoodsList implements Serializable{
	private String goodsId;
	
	private String goodsThumb;
	private String goodsName;
	private String goodsColor;
	private String shopPrice;
	private String marketPrice;
	private String number;
	private String goodsChi_ma;
	private boolean isChoosed;				//商品是否被选中
	private String shangpinNum;

	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getGoodsThumb() {
		return goodsThumb;
	}
	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsColor() {
		return goodsColor;
	}
	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}
	public String getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(String shopPrice) {
		this.shopPrice = shopPrice;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getGoodsChi_ma() {
		return goodsChi_ma;
	}
	public void setGoodsChi_ma(String goodsChi_ma) {
		this.goodsChi_ma = goodsChi_ma;
	}
	
	public String getShangpinNum() {
		return shangpinNum;
	}
	public void setShangpinNum(String shangpinNum) {
		this.shangpinNum = shangpinNum;
	}
	public boolean isChoosed() {
		return isChoosed;
	}
	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}
	
	
	
	
	
}
