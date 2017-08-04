/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TSC;
import com.kongtrolink.cone.message.enumeration.EnumEnable;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 字符串数据属性的结构定义
 * @author Mosaico
 */
public class ConeTSC extends ConeBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4564403767628090051L;
	public  static final String NAMESPACE="_c1_tsc";
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BosyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private EnumEnable alarmEnable;
    private EnumEnable saved;//是否保存历史
    
    public ConeTSC(){
    	
    }
   
	public ConeTSC(TSC tsc,String fsuId) {
		this.type = tsc.getType();
		this.c1_id = tsc.getId();
		this.parentId = tsc.getParentId();
		this.name = tsc.getName();
		this.desc = tsc.getDesc();
		this.alarmEnable = tsc.getAlarmEnable();
		this.saved = tsc.getSaved();
		super.setCtime(new Date());
    	super.setFsuId(fsuId);
	}

	public EnumType getType() {
		return type;
	}
	public void setType(EnumType type) {
		this.type = type;
	}
	public long getC1_Id() {
		return c1_id;
	}
	public void setC1_Id(long c1_id) {
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

	
	public EnumEnable getAlarmEnable() {
		return alarmEnable;
	}
	public void setAlarmEnable(EnumEnable alarmEnable) {
		this.alarmEnable = alarmEnable;
	}
	public EnumEnable getSaved() {
		return saved;
	}
	public void setSaved(EnumEnable saved) {
		this.saved = saved;
	}
	@Override
	public String toString() {
		return "TSC [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc=" + desc
				+ ", alarmEnable=" + alarmEnable + ", saved=" + saved + "]";
	}
	
    
    
}
