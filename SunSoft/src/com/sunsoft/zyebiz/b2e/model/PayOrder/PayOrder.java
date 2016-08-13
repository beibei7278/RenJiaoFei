package com.sunsoft.zyebiz.b2e.model.PayOrder;
/**
 * 接受后台传递的订单数据
 */
import java.io.Serializable;

public class PayOrder implements Serializable{
	private String confirm_orderId;
	private String confirm_goodsDetail;
	private String confirm_price;
	private String confirm_titleName;
	private String confirm_vircardnoin;
	private String confirm_verficationcode;
	private String confirm_merchantid;
	private String confirm_url;
	public String getConfirm_orderId() {
		return confirm_orderId;
	}
	public void setConfirm_orderId(String confirm_orderId) {
		this.confirm_orderId = confirm_orderId;
	}
	public String getConfirm_goodsDetail() {
		return confirm_goodsDetail;
	}
	public void setConfirm_goodsDetail(String confirm_goodsDetail) {
		this.confirm_goodsDetail = confirm_goodsDetail;
	}
	public String getConfirm_price() {
		return confirm_price;
	}
	public void setConfirm_price(String confirm_price) {
		this.confirm_price = confirm_price;
	}
	public String getConfirm_titleName() {
		return confirm_titleName;
	}
	public void setConfirm_titleName(String confirm_titleName) {
		this.confirm_titleName = confirm_titleName;
	}
	public String getConfirm_vircardnoin() {
		return confirm_vircardnoin;
	}
	public void setConfirm_vircardnoin(String confirm_vircardnoin) {
		this.confirm_vircardnoin = confirm_vircardnoin;
	}
	public String getConfirm_verficationcode() {
		return confirm_verficationcode;
	}
	public void setConfirm_verficationcode(String confirm_verficationcode) {
		this.confirm_verficationcode = confirm_verficationcode;
	}
	public String getConfirm_merchantid() {
		return confirm_merchantid;
	}
	public void setConfirm_merchantid(String confirm_merchantid) {
		this.confirm_merchantid = confirm_merchantid;
	}
	public String getConfirm_url() {
		return confirm_url;
	}
	public void setConfirm_url(String confirm_url) {
		this.confirm_url = confirm_url;
	}
	
	
	
	
	

}
