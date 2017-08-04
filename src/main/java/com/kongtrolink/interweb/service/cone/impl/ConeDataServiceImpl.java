package com.kongtrolink.interweb.service.cone.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.entity.ConeNotify;
import com.kongtrolink.interweb.dao.cone.ConeDataDao;
import com.kongtrolink.interweb.entity.cone.ConeData;
import com.kongtrolink.interweb.service.cone.ConeDataService;

@Service("coneDataService")
public class ConeDataServiceImpl implements ConeDataService {

	
	@Autowired
	ConeDataDao coneDataDao;
	
	@Override
	public void saveData(ConeData data, String uniqueCode) {
		// TODO Auto-generated method stub
		coneDataDao.saveData(data, uniqueCode);
	}

	@Override
	public List<ConeNotify> queryConeNotify(String uniqueCode,String fsuId) {
		// TODO Auto-generated method stub
		return coneDataDao.queryConeNotify(uniqueCode, fsuId);
	}

	@Override
	public void updateNotify(String uniqueCode, ConeNotify notify) {
		// TODO Auto-generated method stub
		coneDataDao.updateConeNotify(uniqueCode, notify);
	}

}
