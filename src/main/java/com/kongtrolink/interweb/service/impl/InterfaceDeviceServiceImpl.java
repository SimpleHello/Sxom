package com.kongtrolink.interweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.util.IdAnalyzeUtil;
import com.kongtrolink.interweb.dao.device.InterfaceDeviceDao;
import com.kongtrolink.interweb.service.InterfaceDeviceService;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;

@Service("interfaceDeviceService")
public class InterfaceDeviceServiceImpl implements InterfaceDeviceService {

	@Autowired
	InterfaceDeviceDao interfaceDeviceDao;
	
	/**
     * 根据ID 取得设备信息
     * @param uniqueCode
     * @param id
     * @return 
     */
	@Override
	public ConeDevice queryDeviceById(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		return interfaceDeviceDao.queryDeviceById(uniqueCode, id);
	}

	/**
     * 删除设备信息
     */
	@Override
	public void delDevice(String uniqueCode, ConeDevice device) {
		// TODO Auto-generated method stub
		interfaceDeviceDao.delDevice(uniqueCode, device.getId());
	}

	/**
     * 根据 获取到的数据 新增设备
     * @param uniqueCode
     * @param device
     */
	@Override
	public void saveDevice(String uniqueCode, ConeDevice device) {
		// TODO Auto-generated method stub
		interfaceDeviceDao.saveDevice(uniqueCode, device);
	}

	/**
     * 根据C1 id 删除设备信息
     * @param uniqueCode
     */
	@Override
	public void delDeviceByC1(String uniqueCode, ConeDevice device) {
		// TODO Auto-generated method stub
		interfaceDeviceDao.delDeviceByC1(uniqueCode, device.getC1_id());
	}

	@Override
	public String createDeviceCode(String siteCode, int c1_id, int deviceType) {
		// TODO Auto-generated method stub
		int c1_deviceId =  IdAnalyzeUtil.getDefiniteId(c1_id,2);//0:aa 区域,1:bbb 站点/FSU,2:cc 设备,3:dd 信号点
		// 10位 站点编号+ 3位 设备类型+4为ID
		String deviceCode = siteCode + String.format("%03d", deviceType)+ String.format("%04d", c1_deviceId);
		return deviceCode;
	}

	@Override
	public List<ConeDevice> queryDeviceList(String uniqueCode, String fsuId) {
		// TODO Auto-generated method stub
		return interfaceDeviceDao.queryDeviceList(uniqueCode, fsuId);
	}

	@Override
	public ConeDevice queryDeviceByC1Id(String uniqueCode, int c1_id) {
		// TODO Auto-generated method stub
		return interfaceDeviceDao.queryDeviceByC1Id(uniqueCode, c1_id);
	}

}
