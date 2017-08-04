package com.kongtrolink.interweb.entity.cone;

import java.io.Serializable;
import java.util.Date;

import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.body.device.TA;
import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.body.device.TD;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.device.TS;
import com.kongtrolink.cone.message.enumeration.EnumState;
import com.kongtrolink.cone.message.enumeration.EnumType;

public class ConeData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -261476291242698897L;
	public  static final String NAMESPACE="_c1_data";
	
	private String id;
	private EnumType type;   // 数据类型
    private int c1_id;                 // 数据标识ID, 监控对象ID数据格式为：BBBB.CC.DDD
    private String value;
    private EnumState status; // 状态
    private Date ctime;
    private String fsuId;
    private int length;
    private String dataType;
    
    public ConeData(Body data,String fsuId){
    	this.ctime = new Date();
    	this.fsuId = fsuId;
    	if (data instanceof TA) {
    		TA value = (TA)data;
    		this.type = value.getType();
        	this.c1_id = value.getId();
        	this.value = String.valueOf(value.getValue());
        	this.status = value.getStatus();	
        	this.dataType="TA";
		} else if (data instanceof TD) {
			TD value = (TD)data;
    		this.type = value.getType();
        	this.c1_id = value.getId();
        	this.value = String.valueOf(value.getValue());
        	this.status = value.getStatus();
        	this.dataType="TD";
		} else if (data instanceof TS) {
			TS value = (TS)data;
    		this.type = value.getType();
        	this.c1_id = value.getId();
        	this.value = value.getValue();
        	this.length = value.getLength();
        	this.dataType="TS";
		} 
    	
    }
    
    public ConeData(){
    	
    }
	public EnumType getType() {
		return type;
	}
	public void setType(EnumType type) {
		this.type = type;
	}
	public int getC1_id() {
		return c1_id;
	}
	public void setC1_id(int c1_id) {
		this.c1_id = c1_id;
	}
	public EnumState getStatus() {
		return status;
	}
	public void setStatus(EnumState status) {
		this.status = status;
	}
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
    
    
    
}
