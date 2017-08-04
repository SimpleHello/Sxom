package com.kongtrolink.interweb.entity.cone.common;

import java.io.Serializable;
import java.util.Date;

public class ConeBaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8509383560924610684L;

	private Date ctime;
	private String fsuId;
	
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getFsuId() {
		return fsuId;
	}

	public void setFsuId(String fsuId) {
		this.fsuId = fsuId;
	}
	
	
}
