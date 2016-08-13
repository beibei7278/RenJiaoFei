package com.sunsoft.zyebiz.b2e.model.Student;

/**
 * 
 */
import java.io.Serializable;
import java.util.List;

import com.sunsoft.zyebiz.b2e.model.shopcart.GoodsList;

public class StudentResult implements Serializable {
	private String userRealName;
	private String userName;
	private String schoolName;
	private String gradeNo;
	private String classNo;
	private List<ParentResult> list;
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public List<ParentResult> getList() {
		return list;
	}
	public void setList(List<ParentResult> list) {
		this.list = list;
	}
	public String getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(String gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	
	
	
	
	

}
