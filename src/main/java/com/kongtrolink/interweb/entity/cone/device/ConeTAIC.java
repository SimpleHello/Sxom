/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kongtrolink.interweb.entity.cone.device;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TAIC;
import com.kongtrolink.cone.message.enumeration.EnumAlarmLevel;
import com.kongtrolink.cone.message.enumeration.EnumEnable;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

/**
 * 模拟输入（遥测量）
 * @author Mosaico
 */
public class ConeTAIC  extends ConeBaseEntity{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 937395326081069496L;
	public  static final String NAMESPACE="_c1_taic";
	
	private EnumType type;
    private long c1_id;	    // [Primary key]
    private long parentId;
    private String name;	// BosyStatic.NAME_LENGTH
    private String desc;	// BodyStatic.DES_LENGTH
    private float maxVal;   // 有效上限
    private float minVal;   // 有效下限
    private EnumAlarmLevel alarmLevel; // 告警等级
    private EnumEnable alarmEnable;// 告警使能标记
    private float hiLimit1 = -99999;	// 一级告警上限（若无则为-99999）
    private float loLimit1 = -99999;
    private float hiLimit2 = -99999;
    private float loLimit2 = -99999;
    private float hiLimit3 = -99999;
    private float loLimit3 = -99999;
    private float stander;	// 标称值
    private float percision;	// 精度（小数点后尾数）
    private EnumEnable saved;//是否保存历史
    private String unit;	// BosyStatic.UNIT_LENGTH
    //------------------------------------------------------
    public ConeTAIC(){
    	
    }
    
    public ConeTAIC(TAIC taci,String fsuId){
    	this.type = taci.getType();
    	this.c1_id = taci.getId();
    	this.parentId = taci.getParentId();
    	this.name = taci.getName();
    	this.desc = taci.getDesc();
    	this.maxVal = taci.getMaxVal();
    	this.minVal = taci.getMinVal();
    	this.alarmLevel = taci.getAlarmLevel();
    	this.alarmEnable = taci.getAlarmEnable();
    	this.hiLimit1 = taci.getHiLimit1();
    	this.loLimit1 = taci.getLoLimit1();
    	this.hiLimit2 = taci.getHiLimit2();
    	this.loLimit2 = taci.getLoLimit2();
    	this.hiLimit3 = taci.getHiLimit3();
    	this.loLimit3 = taci.getLoLimit3();
    	this.stander = taci.getStander();
    	this.percision = taci.getPercision();
    	this.saved = taci.getSaved();
    	this.unit = taci.getUnit();
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
	public float getHiLimit1() {
		return hiLimit1;
	}
	public void setHiLimit1(float hiLimit1) {
		this.hiLimit1 = hiLimit1;
	}
	public float getLoLimit1() {
		return loLimit1;
	}
	public void setLoLimit1(float loLimit1) {
		this.loLimit1 = loLimit1;
	}
	public float getHiLimit2() {
		return hiLimit2;
	}
	public void setHiLimit2(float hiLimit2) {
		this.hiLimit2 = hiLimit2;
	}
	public float getLoLimit2() {
		return loLimit2;
	}
	public void setLoLimit2(float loLimit2) {
		this.loLimit2 = loLimit2;
	}
	public float getHiLimit3() {
		return hiLimit3;
	}
	public void setHiLimit3(float hiLimit3) {
		this.hiLimit3 = hiLimit3;
	}
	public float getLoLimit3() {
		return loLimit3;
	}
	public void setLoLimit3(float loLimit3) {
		this.loLimit3 = loLimit3;
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
	public EnumAlarmLevel getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(EnumAlarmLevel alarmLevel) {
		this.alarmLevel = alarmLevel;
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
	public void setStander(float stander) {
		this.stander = stander;
	}
	
	@Override
	public String toString() {
		return "TAIC [type=" + type + ", id=" + c1_id + ", parentId=" + parentId + ", name=" + name + ", desc="
				+ desc + ", maxVal=" + maxVal + ", minVal=" + minVal + ", alarmLevel=" + alarmLevel + ", alarmEnable="
				+ alarmEnable + ", hiLimit1=" + hiLimit1 + ", loLimit1=" + loLimit1 + ", hiLimit2=" + hiLimit2
				+ ", loLimit2=" + loLimit2 + ", hiLimit3=" + hiLimit3 + ", loLimit3=" + loLimit3  + ", stander=" + stander + ", percision=" + percision + ", unit="
				+ unit + "]";
	}
	
    
}
