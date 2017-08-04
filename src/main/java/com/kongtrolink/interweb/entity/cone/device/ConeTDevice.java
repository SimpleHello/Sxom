/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.TTime;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.enumeration.EnumDeviceType;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 设备属性的结构
 * @author Mosaico
 */
public class ConeTDevice  extends ConeBaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3480745764037569664L;
	public  static final String NAMESPACE="_c1_tdevice";
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BodyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private EnumDeviceType deviceType; // 设备 类型
    private String productor;	// BodyStatic.NAME_LENGTH 生产厂家描述
    private String version;	// BodyStatic.VER_LENGTH 版本描述
    private TTime beginRunTime; //投入运行时间
    
    
    public ConeTDevice(){
    	
    }
    

	public ConeTDevice(TDevice tdevice,String fsuId) {
		this.type = tdevice.getType();
		this.c1_id = tdevice.getId();
		this.parentId = tdevice.getParentId();
		this.name = tdevice.getName();
		this.desc = tdevice.getDesc();
		this.deviceType = tdevice.getDeviceType();
		this.productor = tdevice.getProductor();
		this.version = tdevice.getVersion();
		this.beginRunTime = tdevice.getBeginRunTime();
		super.setCtime(new Date());
    	super.setFsuId(fsuId);
	}

	public EnumType getType() {
		return type;
	}
	public void setType(EnumType type) {
		this.type = type;
	}
	
	public long getC1_id() {
		return c1_id;
	}


	public void setC1_id(long c1_id) {
		this.c1_id = c1_id;
	}


	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public EnumDeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(EnumDeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public TTime getBeginRunTime() {
		return beginRunTime;
	}
	public void setBeginRunTime(TTime beginRunTime) {
		this.beginRunTime = beginRunTime;
	}
	@Override
	public String toString() {
		return "TDevice [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc=" + desc
				+ ", deviceType=" + deviceType + ", productor=" + productor + ", version=" + version + ", beginRunTime="
				+ beginRunTime + "]";
	}
    
    

}
