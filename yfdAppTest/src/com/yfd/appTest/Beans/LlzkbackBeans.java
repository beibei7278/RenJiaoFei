package com.yfd.appTest.Beans;

public class LlzkbackBeans {

	String code;
	String msg;
	Data data;
	
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
