package com.sunsoft.zyebiz.b2e.model.Student;

/**
 * �ҵ���԰��Ĭ���ջ���ַ
 */
import java.io.Serializable;

public class StudentBean implements Serializable {
	private String title;
	private StudentResult body;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StudentResult getBody() {
		return body;
	}

	public void setBody(StudentResult body) {
		this.body = body;
	}

	
	
	

}
