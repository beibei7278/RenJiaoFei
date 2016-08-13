package com.mining.app.zxing.decoding;

public class Loginbeans {
	
	public String code;
	public String message;
    public UserinfoBeans obj;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserinfoBeans getUserinfo() {
		return obj;
	}
	public void setUserinfo(UserinfoBeans userinfo) {
		this.obj = userinfo;
	}

}
