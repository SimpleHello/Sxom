/**
 * *****************************************************
 * Copyright (C) Kongtrolink techology Co.ltd - All Rights Reserved
 *
 * This file is part of Kongtrolink techology Co.Ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 ******************************************************
 */
package com.kongtrolink.interweb.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;

/**
 * 
 * @author Mosaico
 */
public interface InterfaceDeviceService {
    /**
     * 根据ID 取得设备信息
     * @param uniqueCode
     * @param id
     * @return 
     */
    public ConeDevice queryDeviceById(String uniqueCode, String id);
    /**
     * 根据ID 取得设备信息
     * @param uniqueCode
     * @param id
     * @return 
     */
    public ConeDevice queryDeviceByC1Id(String uniqueCode, int c1_id);
    
    /**
     * 删除设备信息
     * @param uniqueCode
     * @param fsu
     */
    public void delDevice(String uniqueCode,ConeDevice device);
    /**
     * 根据C1 id 删除设备信息
     * @param uniqueCode
     * @param fsu
     */
    public void delDeviceByC1(String uniqueCode,ConeDevice device);
    
    /**
     * 根据 获取到的数据 新增设备
     * @param uniqueCode
     * @param device
     */
    public void saveDevice(String uniqueCode,ConeDevice device);
    
    /**
     * 根据C1接口返回的 ID 生成 DEVICE 编码
     * @param uniqueCode
     * @param siteCode
     * @param c1_id
     * @return
     */
    public String createDeviceCode(String siteCode,int c1_id,int deviceType);
    
    
    public  List<ConeDevice> queryDeviceList(String uniqueCode, String fsuId);

}
