package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class PropertyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String str;
	
	public PropertyEntity(){
		
	} 
	public PropertyEntity(int id,String str){
		this.id = id;
		this.str = str;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
}
