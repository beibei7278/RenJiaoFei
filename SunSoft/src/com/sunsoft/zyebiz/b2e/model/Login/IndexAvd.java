package com.sunsoft.zyebiz.b2e.model.Login;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "index_avd")
public class IndexAvd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	public long _id;
	@DatabaseField
	private String adverttitle;//广告标题
	@DatabaseField
	private String ggcontent;//广告内容
	@DatabaseField
	private String advertimg; //广告图片
	public IndexAvd() {
		super();
	}
	
	public IndexAvd(long _id, String adverttitle, String ggcontent,
			String advertimg) {
		super();
		this._id = _id;
		this.adverttitle = adverttitle;
		this.ggcontent = ggcontent;
		this.advertimg = advertimg;
	}

	public String getAdverttitle() {
		return adverttitle;
	}
	public void setAdverttitle(String adverttitle) {
		this.adverttitle = adverttitle;
	}
	public String getGgcontent() {
		return ggcontent;
	}
	public void setGgcontent(String ggcontent) {
		this.ggcontent = ggcontent;
	}
	public String getAdvertimg() {
		return advertimg;
	}
	public void setAdvertimg(String advertimg) {
		this.advertimg = advertimg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
