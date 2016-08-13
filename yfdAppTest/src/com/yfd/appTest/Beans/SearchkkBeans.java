package com.yfd.appTest.Beans;

import java.util.List;

public class SearchkkBeans {

	String state;
	
	Message msg;
	
	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public class Message{
		
		String currentPage;
		String pageSize;
		String count;
		String numCount;
		String beginNum;
		String endNum;
		
		List<Cardmsg> dataList;

		public String getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(String currentPage) {
			this.currentPage = currentPage;
		}

		public String getPageSize() {
			return pageSize;
		}

		public void setPageSize(String pageSize) {
			this.pageSize = pageSize;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getNumCount() {
			return numCount;
		}

		public void setNumCount(String numCount) {
			this.numCount = numCount;
		}

		public String getBeginNum() {
			return beginNum;
		}

		public void setBeginNum(String beginNum) {
			this.beginNum = beginNum;
		}

		public String getEndNum() {
			return endNum;
		}

		public void setEndNum(String endNum) {
			this.endNum = endNum;
		}

		public List<Cardmsg> getDataList() {
			return dataList;
		}

		public void setDataList(List<Cardmsg> dataList) {
			this.dataList = dataList;
		}

	}
	
	public class Cardmsg{
		
		String miImgA1;
		String miImgA2;
		String miImgA3;
		String carrierOp;
		String miId;
		String miSim;
		String miPuk;
		String miPhone;
		String miMemo;
		String miUseScard;
		String miDealerName;
		String miUseName;
		String adminMsg;
		String miState;
		
		
		
		public String getAdminMsg() {
			return adminMsg;
		}
		public void setAdminMsg(String adminMsg) {
			this.adminMsg = adminMsg;
		}
		public String getMiState() {
			return miState;
		}
		public void setMiState(String miState) {
			this.miState = miState;
		}
		public String getMiImgA1() {
			return miImgA1;
		}
		public void setMiImgA1(String miImgA1) {
			this.miImgA1 = miImgA1;
		}
		public String getMiImgA2() {
			return miImgA2;
		}
		public void setMiImgA2(String miImgA2) {
			this.miImgA2 = miImgA2;
		}
		public String getMiImgA3() {
			return miImgA3;
		}
		public void setMiImgA3(String miImgA3) {
			this.miImgA3 = miImgA3;
		}
		public String getCarrierOp() {
			return carrierOp;
		}
		public void setCarrierOp(String carrierOp) {
			this.carrierOp = carrierOp;
		}
		public String getMiId() {
			return miId;
		}
		public void setMiId(String miId) {
			this.miId = miId;
		}
		public String getMiSim() {
			return miSim;
		}
		public void setMiSim(String miSim) {
			this.miSim = miSim;
		}
		public String getMiPuk() {
			return miPuk;
		}
		public void setMiPuk(String miPuk) {
			this.miPuk = miPuk;
		}
		public String getMiPhone() {
			return miPhone;
		}
		public void setMiPhone(String miPhone) {
			this.miPhone = miPhone;
		}
		public String getMiMemo() {
			return miMemo;
		}
		public void setMiMemo(String miMemo) {
			this.miMemo = miMemo;
		}
		public String getMiUseScard() {
			return miUseScard;
		}
		public void setMiUseScard(String miUseScard) {
			this.miUseScard = miUseScard;
		}
		public String getMiDealerName() {
			return miDealerName;
		}
		public void setMiDealerName(String miDealerName) {
			this.miDealerName = miDealerName;
		}
		public String getMiUseName() {
			return miUseName;
		}
		public void setMiUseName(String miUseName) {
			this.miUseName = miUseName;
		}
		
		
	}
}
