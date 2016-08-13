package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;
import java.util.List;

public class IndexAvdResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IndexAvd> data;
	public List<IndexAvd> getData() {
		return data;
	}
	public void setData(List<IndexAvd> data) {
		this.data = data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
