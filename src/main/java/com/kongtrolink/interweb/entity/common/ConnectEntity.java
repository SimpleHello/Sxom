package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class ConnectEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370000486358722112L;
	
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
