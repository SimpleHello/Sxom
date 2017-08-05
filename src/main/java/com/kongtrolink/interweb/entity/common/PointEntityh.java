package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class PointEntityh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1616421445585717057L;
	
	private int id;
	private String value;
	private String key;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	 
	
	
}
