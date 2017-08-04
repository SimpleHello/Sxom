/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TStation;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 局 ,站类 属性结构
 * @author Mosaico
 */
public class ConeTStation  extends ConeBaseEntity{
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 6722172620269860801L;
	public  static final String NAMESPACE="_c1_tstation";
	
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BosyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private Float longitude;
    private Float latitude;
    
    
    public ConeTStation(){
    	
    }
    
   
	public ConeTStation(TStation tstation, String fsuId) {
		this.type = tstation.getType();
		this.c1_id = tstation.getId();
		this.parentId = tstation.getParentId();
		this.name = tstation.getName();
		this.desc = tstation.getDesc();
		this.longitude = tstation.getLongitude();
		this.latitude = tstation.getLatitude();
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
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "TStation [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc=" + desc
				+ ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	

    
   
    
}
