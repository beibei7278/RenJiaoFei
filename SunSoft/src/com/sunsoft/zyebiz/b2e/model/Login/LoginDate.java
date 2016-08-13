package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;
/**
 *登陆界面的Bean
 * @author Administrator
 *
 */
public class LoginDate implements Serializable{
	private String title;
	private String adLink;
	private String adCode;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdLink() {
		return adLink;
	}
	public void setAdLink(String adLink) {
		this.adLink = adLink;
	}
	public String getAdCode() {
		return adCode;
	}
	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}
	
	
	
	
	

}
