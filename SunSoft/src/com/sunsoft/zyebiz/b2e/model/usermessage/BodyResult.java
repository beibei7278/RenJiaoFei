package com.sunsoft.zyebiz.b2e.model.usermessage;

import java.io.Serializable;
import java.util.List;

/**
 * ���ﳵ���� id ��˾���� ��ƷͼƬ ��Ʒ�� ��Ʒ��ɫ ���� �ܼ� ����
 */
public class BodyResult implements Serializable {

	private String userName;
	private String password;
	private String userId;
	private String gradeNo;
	private String classNo;
	private String icon;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
	

}
