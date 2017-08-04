package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class LoginEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3006924411320213789L;

	private String username;
	private String password;
	private String key;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
