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

import com.kongtrolink.scloud.core.entity.cone.ConeFsu;

/**
 *
 * @author Mosaico
 */
public interface InterfaceFsuService {
    /**
     * 根据ID 取得FSU信息
     * @param uniqueCode
     * @param id
     * @return 
     */
    public ConeFsu queryFsuById(String uniqueCode, String id);
    
    /**
     * c1接口收到数据时 修改FSU信息
     * @param uniqueCode
     * @param fsu
     */
    public void updateFsu(String uniqueCode,ConeFsu fsu);
    
    /**
     * 根据C1接口返回的FSUID 生成 FSU 编码
     * @param uniqueCode
     * @param siteCode
     * @param c1_id
     * @return
     */
    public String createFsuCode(String siteCode,int c1_id);
    
    public List<ConeFsu> queryConeFsu(String uniqueCode);
    
}
