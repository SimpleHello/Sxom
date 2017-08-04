package com.kongtrolink.interweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.message.body.MonitorDataCone;
import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.enumeration.EnumState;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.interweb.dao.device.InterfaceSignalDao;
import com.kongtrolink.interweb.entity.cone.ConeData;
import com.kongtrolink.interweb.service.InterfaceSignalService;
import com.kongtrolink.interweb.service.cone.ConeDataService;
import com.kongtrolink.scloud.core.entity.cone.ConeHistoryData;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;

@Service("interfaceSingleService")
public class InterfaceSignalServiceImpl implements InterfaceSignalService {

	@Autowired
	InterfaceSignalDao  interfaceSingleDao;
	
	@Autowired
	private ConeDataService coneDataService;
	
	/**
	 *  根据ID 取得 信号点 信息
	 */
	@Override
	public ConeSignal querySignalById(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalById(uniqueCode, id);
	}

	/**
     * 根据 ID 删除  信号点  信息
     * @param uniqueCode
     * @param fsu
     */
	@Override
	public void delSignal(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		interfaceSingleDao.delSignal(uniqueCode, signal.getId());
	}

	/**
     * 根据 C1 ID 删除  信号点  信息
     * @param uniqueCode
     * @param fsu
     */
	@Override
	public void delSignalByC1(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		interfaceSingleDao.delSignalByC1(uniqueCode, signal.getC1_id());
	}

	/**
     * 根据 获取到的数据 新增设备
     * @param uniqueCode
     * @param device
     */
	@Override
	public void saveSignal(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		interfaceSingleDao.saveSignal(uniqueCode, signal);
	}

	/**
     * 根据 实时数据 修改 single的value值 根据C1_ID进行修改
     */
	@Override
	public void updateValueData(Body coneMonitorData,String uniqueCode,String fsuId) {
		// TODO Auto-generated method stub
		ConeData data = new ConeData(coneMonitorData, fsuId);
		coneDataService.saveData(data, uniqueCode);// 保存到 数据中
		String dataType = data.getDataType();
		if("TA".equals(dataType)||"TD".equals(dataType)){
			ConeSignal signal = new ConeSignal();
			signal.setC1_id(data.getC1_id());
			signal.setValue(Double.valueOf(data.getValue()));
			interfaceSingleDao.updateValue(uniqueCode, signal);
		}
		
	}

	@Override
	public void updateValue(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		interfaceSingleDao.updateValue(uniqueCode, signal);
	}
	@Override
	public List<ConeHistoryData> queryHistory(String uniqueCode, String signalId) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.queryHistory(uniqueCode, signalId);
	}

	@Override
	public List<ConeSignal> querySignalList(String uniqueCode,int skip,int limit) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalList(uniqueCode,skip,limit);
	}

	@Override
	public int querySignalCount(String uniqueCode) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalCount(uniqueCode);
	}

	@Override
	public List<ConeSignal> querySignalByDeviceList(String uniqueCode, String deviceId) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalByDeviceList(uniqueCode, deviceId);
	}

	@Override
	public int querySignalByDeviceCount(String uniqueCode, String deviceId) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalByDeviceCount(uniqueCode, deviceId);
	}

	@Override
	public List<ConeHistoryData> queryHistoryByC1(String uniqueCode, long c1_id) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.queryHistoryByC1(uniqueCode, c1_id);
	}

	@Override
	public ConeHistoryData queryLastHistoryByC1(String uniqueCode, long c1_id) {
		// TODO Auto-generated method stub
		
		List<ConeHistoryData> list = queryHistoryByC1(uniqueCode, c1_id);
		if(list==null||list.size()==0){
			return null;
		}else{
			ConeHistoryData result = null;
			int bucket = 0;
			for(ConeHistoryData history:list){
				if(history.getBucket()>bucket){
					result = history;
					bucket = history.getBucket();
				}
			}
			return result;
		}
	}

	@Override
	public ConeSignal querySignalByC1Id(String uniqueCode, long c1_id) {
		// TODO Auto-generated method stub
		return interfaceSingleDao.querySignalByC1Id(uniqueCode, c1_id);
	}

	@Override
	public void saveHistory(String uniqueCode, ConeHistoryData historyDate) {
		// TODO Auto-generated method stub
		interfaceSingleDao.saveHistory(uniqueCode, historyDate);
	}

	@Override
	public void updateHistory(String uniqueCode, ConeHistoryData historyDate) {
		// TODO Auto-generated method stub
		interfaceSingleDao.updateHistory(uniqueCode, historyDate);
	}
	
	@Override
	public void updateSignalById(String uniqueCode, ConeSignal signal) {
		interfaceSingleDao.updateSignalById(uniqueCode, signal);
	}

	@Override
	public MonitorDataCone getMonitorDataCone(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		String type = signal.getType();
		String alarmLevel = signal.getAlarmLevel();
		alarmLevel = (alarmLevel==null?"0":alarmLevel);
		MonitorDataCone data = new MonitorDataCone(getEnumType(type),(long)signal.getC1_id(),signal.getValue().longValue(),EnumState.toType(Integer.valueOf(alarmLevel)));
		return data;
	}
	
	private EnumType getEnumType(String str){
		switch(str){
			case "遥信":return EnumType.DI;
			case "遥测":return EnumType.AI;
			case "遥控":return EnumType.DO;
			case "遥调":return EnumType.AO;
		}
		return EnumType.AI;
	}
	

}
