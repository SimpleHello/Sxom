package com.kongtrolink.interweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.entity.AlarmExt;
import com.kongtrolink.interweb.dao.alarm.InterfaceAlarmDao;
import com.kongtrolink.interweb.service.InterfaceAlarmService;
import com.kongtrolink.scloud.core.entity.alarm.Alarm;

@Service("interfaceAlarmService")
public class InterfaceAlarmServiceImpl implements InterfaceAlarmService {

	@Autowired
	InterfaceAlarmDao interfaceAlarmDao;
	
	@Override
	public void saveAlarm(String uniqueCode, Alarm alarm) {
		// TODO Auto-generated method stub
		interfaceAlarmDao.saveDevice(uniqueCode, alarm);
	}

	@Override
	public AlarmExt getAlarmById(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		return interfaceAlarmDao.getAlarmById(uniqueCode, id);
	}

}
