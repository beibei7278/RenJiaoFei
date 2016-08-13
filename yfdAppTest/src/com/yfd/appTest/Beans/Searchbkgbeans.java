package com.yfd.appTest.Beans;

import java.util.List;

public class Searchbkgbeans {

	String state;
	
	messagess msg;
	
 public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public messagess getMsg() {
		return msg;
	}

	public void setMsg(messagess msg) {
		this.msg = msg;
	}

public class messagess{
	
	List<datal> dataList;

	public List<datal> getDataList() {
		return dataList;
	}

	public void setDataList(List<datal> dataList) {
		this.dataList = dataList;
	}


	
}

 public class datal{
	
	String ftPhone;
	String carrierOp;
	String serviceStatus;
	String serviceType;
	String adminMsg;
	
	public String getAdminMsg() {
		return adminMsg;
	}
	public void setAdminMsg(String adminMsg) {
		this.adminMsg = adminMsg;
	}
	public String getFtPhone() {
		return ftPhone;
	}
	public void setFtPhone(String ftPhone) {
		this.ftPhone = ftPhone;
	}
	public String getCarrierOp() {
		return carrierOp;
	}
	public void setCarrierOp(String carrierOp) {
		this.carrierOp = carrierOp;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
}
	
}
