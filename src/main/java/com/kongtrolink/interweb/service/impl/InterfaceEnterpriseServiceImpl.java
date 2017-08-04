package com.kongtrolink.interweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.interweb.dao.device.InterfaceEnterpriseDao;
import com.kongtrolink.interweb.service.InterfaceEnterpriseService;
import com.kongtrolink.scloud.core.entity.enterprise.Enterprise;

@Service("interfaceEnterpriseService")
public class InterfaceEnterpriseServiceImpl implements InterfaceEnterpriseService {

	@Autowired
	InterfaceEnterpriseDao  interfaceEnterpriseDao;
	
	@Override
	public List<Enterprise> queryAllCustomers() {
		// TODO Auto-generated method stub
		return interfaceEnterpriseDao.findAllCustomers();
	}

}
