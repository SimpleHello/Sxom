/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.enumeration.EnumEnable;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 模拟输出（遥调量）
 * @author Mosaico
 */
public class ConeTAOC extends ConeBaseEntity{
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083086209664150309L;
	public  static final String NAMESPACE="_c1_taoc";
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BosyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private float maxVal;   // 有效上限
    private float minVal;   // 有效下限
    private EnumEnable controlEnable;
    private float stander;	// 标称值
    private float percision;	// 精度（小数点后尾数）
    private EnumEnable saved;//是否保存历史
    private String unit;	// BosyStatic.UNIT_LENGTH
    
    public ConeTAOC(){
    	
    }
    
	public ConeTAOC(TAOC taoc,String fsuId) {
		this.type = taoc.getType();
		this.c1_id = taoc.getId();
		this.parentId = taoc.getParentId();
		this.name = taoc.getName();
		this.desc = taoc.getDesc();
		this.maxVal = taoc.getMaxVal();
		this.minVal = taoc.getMinVal();
		this.controlEnable = taoc.getControlEnable();
		this.stander = taoc.getStander();
		this.percision = taoc.getPercision();
		this.saved = taoc.getSaved();
		this.unit = taoc.getUnit();
		super.setCtime(new Date());
    	super.setFsuId(fsuId);
	}

	public EnumType getType() {
		return type;
	}
	public void setType(EnumType type) {
		this.type = type;
	}
	public float getStander() {
		return stander;
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
	public float getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(float maxVal) {
		this.maxVal = maxVal;
	}
	public float getMinVal() {
		return minVal;
	}
	public void setMinVal(float minVal) {
		this.minVal = minVal;
	}
	public float getPercision() {
		return percision;
	}
	public void setPercision(float percision) {
		this.percision = percision;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public void setStander(float stander) {
		this.stander = stander;
	}
	@Override
	public String toString() {
		return "TAOC [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc=" + desc
				+ ", maxVal=" + maxVal + ", minVal=" + minVal + ", controlEnable=" + controlEnable + ", stander="
				+ stander + ", percision=" + percision + ", saved=" + saved + ", unit=" + unit + "]";
	}
	

    
}
