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
package com.kongtrolink.interweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.util.IdAnalyzeUtil;
import com.kongtrolink.interweb.dao.device.InterfaceFsuDao;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;

/**
 * C1接口 获取FSU信息
 * @author Mosaico
 */
@Service("interfaceFsuServiceImpl")
public class InterfaceFsuServiceImpl implements InterfaceFsuService {

	@Autowired 
    private InterfaceFsuDao interfaceFsuDao;
    
	 /**
     * 根据ID 取得FSU信息
     * @param uniqueCode
     * @param id
     * @return 
     */
	@Override
	public ConeFsu queryFsuById(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		return interfaceFsuDao.queryFsuById(uniqueCode, id);
	}

	 /**
     * c1接口收到数据时 修改FSU信息
     * @param uniqueCode
     * @param fsu
     */
	@Override
	public void updateFsu(String uniqueCode, ConeFsu fsu) {
		// TODO Auto-generated method stub
		interfaceFsuDao.updateFsu(uniqueCode, fsu);
	} 
	
	/**
     * 创建 FSU 编码
     * c1_id中 BBB 为 10位 最多 1024 不超过 4位十进制
     * @param uniqueCode
     * @param siteId
     * @return 
     */
    @Override
    public String createFsuCode(String siteCode,int c1_id) {
    	int c1_FsuId =  IdAnalyzeUtil.getDefiniteId(c1_id,1);//0:aa 区域,1:bbb 站点/FSU,2:cc 设备,3:dd 信号点
        // 生成 FSU 流水号，若 fsuCode 已存在，则流水号自增 1
        String fsuCode = siteCode + String.format("%04d", c1_FsuId);
        return fsuCode;
    }

	@Override
	public List<ConeFsu> queryConeFsu(String uniqueCode) {
		// TODO Auto-generated method stub
		return interfaceFsuDao.queryConeFsu(uniqueCode);
	}
    
}
