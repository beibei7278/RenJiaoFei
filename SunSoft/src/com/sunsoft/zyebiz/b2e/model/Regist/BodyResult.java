package com.sunsoft.zyebiz.b2e.model.Regist;

/**
 * 我的智园：默认收货地址
 */
import java.io.Serializable;

public class BodyResult implements Serializable {
	private String userName;
	private String userRealName;
	private String mobilePhone;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	
	
	
	
	

}
