package com.mining.app.zxing.decoding;

import java.util.List;

public class phoneSearchBeans {
	
	String total;
	
	
	List<userinfo> list;
	
	
	Usercasenum caseinfo;
	
	





	public Usercasenum getCaseinfo() {
		return caseinfo;
	}


	public void setCaseinfo(Usercasenum caseinfo) {
		this.caseinfo = caseinfo;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public List<userinfo> getList() {
		return list;
	}


	public void setList(List<userinfo> list) {
		this.list = list;
	}


	public class userinfo{
		
		String id;
		String case_num;
		String age;
		String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCase_num() {
			return case_num;
		}
		public void setCase_num(String case_num) {
			this.case_num = case_num;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
		
	}
	


}
