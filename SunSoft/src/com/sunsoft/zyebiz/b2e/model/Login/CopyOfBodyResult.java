package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;

public class CopyOfBodyResult implements Serializable{
	private String msgCode;
	private String msgInfo;
	private LoginResult obj;
	
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	public LoginResult getObj() {
		return obj;
	}
	public void setObj(LoginResult obj) {
		this.obj = obj;
	}
	
	
	
	
	
	

}
