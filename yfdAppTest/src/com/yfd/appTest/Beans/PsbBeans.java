package com.yfd.appTest.Beans;
//{"state":1,"msg":{"province":"����","carrierstype":"1","mobiletype":"�ƶ�Ԥ���ѿ�"}}

public class PsbBeans {
String state;
message msg;



public String getState() {
	return state;
}



public void setState(String state) {
	this.state = state;
}



public message getMsg() {
	return msg;
}



public void setMsg(message msg) {
	this.msg = msg;
}



public class message {
	
	String province;
	String carrierstype;
	String mobiletype;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCarrierstype() {
		return carrierstype;
	}
	public void setCarrierstype(String carrierstype) {
		this.carrierstype = carrierstype;
	}
	public String getMobiletype() {
		return mobiletype;
	}
	public void setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
	}
	
	
}
}
