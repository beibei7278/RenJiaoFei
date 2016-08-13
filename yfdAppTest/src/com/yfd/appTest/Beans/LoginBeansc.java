package com.yfd.appTest.Beans;

public class LoginBeansc {

	String state;
	
	Meaasge msg;
	
	
	
	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public Meaasge getMsg() {
		return msg;
	}



	public void setMsg(Meaasge msg) {
		this.msg = msg;
	}



	public class Meaasge{
		
		String ids;
		String departmentnames;
		String stopdate;
		String username;
		public String getIds() {
			return ids;
		}
		public void setIds(String ids) {
			this.ids = ids;
		}
		public String getDepartmentnames() {
			return departmentnames;
		}
		public void setDepartmentnames(String departmentnames) {
			this.departmentnames = departmentnames;
		}
		public String getStopdate() {
			return stopdate;
		}
		public void setStopdate(String stopdate) {
			this.stopdate = stopdate;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		
		
	}
	
}
