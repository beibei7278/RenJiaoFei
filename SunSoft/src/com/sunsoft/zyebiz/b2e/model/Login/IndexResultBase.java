package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;

public class IndexResultBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String code;
	private IndexAvdResult result;
	public IndexAvdResult getResult() {
		return result;
	}
	public void setResult(IndexAvdResult result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
