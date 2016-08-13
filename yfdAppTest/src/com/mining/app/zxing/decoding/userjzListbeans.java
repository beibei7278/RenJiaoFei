package com.mining.app.zxing.decoding;

import java.util.List;

public class userjzListbeans {
	
	String name;
	String age;
	String sex;
	String weight;
	String contact;
	List<listjz> details;
	String case_num;
	
	Loginbeans message;
	
	
	public String getCase_num() {
		return case_num;
	}





	public void setCase_num(String case_num) {
		this.case_num = case_num;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getAge() {
		return age;
	}





	public void setAge(String age) {
		this.age = age;
	}





	public String getSex() {
		return sex;
	}





	public void setSex(String sex) {
		this.sex = sex;
	}





	public String getWeight() {
		return weight;
	}





	public void setWeight(String weight) {
		this.weight = weight;
	}





	public String getContact() {
		return contact;
	}





	public void setContact(String contact) {
		this.contact = contact;
	}





	public List<listjz> getDetails() {
		return details;
	}





	public void setDetails(List<listjz> details) {
		this.details = details;
	}





	public class listjz{
		
		String status;
		String suggest_time;
		String visit_time;
		String num;
		String price;
		
		String goods_name;		
		
		String factory;
		String batch_num;
		
		public String getGoods_name() {
			return goods_name;
		}
		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}
		public String getFactory() {
			return factory;
		}
		public void setFactory(String factory) {
			this.factory = factory;
		}
		public String getBatch_num() {
			return batch_num;
		}
		public void setBatch_num(String batch_num) {
			this.batch_num = batch_num;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getSuggest_time() {
			return suggest_time;
		}
		public void setSuggest_time(String suggest_time) {
			this.suggest_time = suggest_time;
		}
		public String getVisit_time() {
			return visit_time;
		}
		public void setVisit_time(String visit_time) {
			this.visit_time = visit_time;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		
		
	}
	

}
