package com.kongtrolink.interweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.interweb.dao.device.InterfaceSiteDao;
import com.kongtrolink.interweb.service.InterfaceSiteService;
import com.kongtrolink.scloud.core.entity.cone.ConeSite;

@Service("interfaceSiteService")
public class InterfaceSiteServiceImpl implements InterfaceSiteService{

	@Autowired 
    private InterfaceSiteDao interfaceSiteDao;
	/**
	 * 根据ID 取得站点信息
	 * @param uniqueCode
	 * @param id
	 * @return
	 */
	@Override
	public ConeSite querySiteById(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		return interfaceSiteDao.querySiteById(uniqueCode, id);
	}

	/**
	 * 根据接口返回 修改 站点信息
	 */
	@Override
	public void updateSite(String uniqueCode, ConeSite site) {
		// TODO Auto-generated method stub
		interfaceSiteDao.updateSite(uniqueCode, site);
	}

	

}
