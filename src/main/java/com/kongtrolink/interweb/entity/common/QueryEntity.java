package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class QueryEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1263606152754215635L;

	private String id;
	private String value;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
