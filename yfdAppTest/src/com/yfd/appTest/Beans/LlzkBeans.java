package com.yfd.appTest.Beans;

import java.util.List;

public class LlzkBeans {
	
String code;
String message;
String msg;
List<Data> data;

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

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

public List<Data> getData() {
	return data;
}

public void setData(List<Data> data) {
	this.data = data;
}

public class Data{
	
	String pkgids;
	String carrierstype;
	String pkgname;
	String flowprice;
	String discount;
	public String getPkgids() {
		return pkgids;
	}
	public void setPkgids(String pkgids) {
		this.pkgids = pkgids;
	}
	public String getCarrierstype() {
		return carrierstype;
	}
	public void setCarrierstype(String carrierstype) {
		this.carrierstype = carrierstype;
	}
	public String getPkgname() {
		return pkgname;
	}
	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}
	public String getFlowprice() {
		return flowprice;
	}
	public void setFlowprice(String flowprice) {
		this.flowprice = flowprice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}

}
