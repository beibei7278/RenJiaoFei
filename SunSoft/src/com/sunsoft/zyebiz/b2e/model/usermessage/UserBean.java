package com.sunsoft.zyebiz.b2e.model.usermessage;

import java.io.Serializable;
import java.util.List;

/**
 * ���ﳵ���� id ��˾���� ��ƷͼƬ ��Ʒ�� ��Ʒ��ɫ ���� �ܼ� ����
 */
public class UserBean implements Serializable {

	private String title;
	private BodyResult body;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BodyResult getBody() {
		return body;
	}
	public void setBody(BodyResult body) {
		this.body = body;
	}
	
	
	

}
