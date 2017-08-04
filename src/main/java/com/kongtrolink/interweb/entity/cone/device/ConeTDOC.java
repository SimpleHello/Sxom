/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TDOC;
import com.kongtrolink.cone.message.enumeration.EnumEnable;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 数字输出（遥控量）
 * @author Mosaico
 */
public class ConeTDOC extends ConeBaseEntity {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166751237436551757L;
	public  static final String NAMESPACE="_c1_tdoc";
	
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BosyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private EnumEnable controlEnable;
    private String desc0;	// BodyStatic.DES_LENGTH 数字值为0时的描述
    private String desc1;	// BodyStatic.DES_LENGTH 数字值为1时的描述
    private EnumEnable saved;//是否保存历史
    
    public ConeTDOC(){
    	
    }
    
	public ConeTDOC(TDOC tdoc,String fsuId) {
		this.type = tdoc.getType();
		this.c1_id = tdoc.getId();
		this.parentId = tdoc.getParentId();
		this.name = tdoc.getName();
		this.desc = tdoc.getDesc();
		this.controlEnable = tdoc.getControlEnable();
		this.desc0 = tdoc.getDesc0();
		this.desc1 = tdoc.getDesc1();
		this.saved = tdoc.getSaved();
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
	public String getDesc0() {
		return desc0;
	}
	public void setDesc0(String desc0) {
		this.desc0 = desc0;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public EnumEnable getControlEnable() {
		return controlEnable;
	}
	public void setControlEnable(EnumEnable controlEnable) {
		this.controlEnable = controlEnable;
	}
	public EnumEnable getSaved() {
		return saved;
	}
	public void setSaved(EnumEnable saved) {
		this.saved = saved;
	}
	@Override
	public String toString() {
		return "TDOC [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc=" + desc
				+ ", controlEnable=" + controlEnable + ", desc0=" + desc0 + ", desc1=" + desc1 + ", saved=" + saved
				+ "]";
	}

    
    
}
