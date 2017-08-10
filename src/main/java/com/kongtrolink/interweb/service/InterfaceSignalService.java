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

import com.kongtrolink.cone.message.body.MonitorDataCone;
import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.scloud.core.entity.cone.ConeHistoryData;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;

/**
 * 
 * @author Mosaico
 */
public interface InterfaceSignalService {
	/**
	 * 根据ID 取得 信号点 信息
	 * 
	 * @param uniqueCode
	 * @param id
	 * @return
	 */
	public ConeSignal querySignalById(String uniqueCode, String id);
	/**
	 * 根据ID 取得 信号点 信息
	 * 
	 * @param uniqueCode
	 * @param id
	 * @return
	 */
	public ConeSignal querySignalByC1Id(String uniqueCode, long c1_id);
	/**
	 * 根据 ID 删除 信号点 信息
	 * 
	 * @param uniqueCode
	 * @param fsu
	 */
	public void delSignal(String uniqueCode, ConeSignal signal);

	/**
	 * 根据 C1 ID 删除 信号点 信息
	 * 
	 * @param uniqueCode
	 * @param fsu
	 */
	public void delSignalByC1(String uniqueCode, ConeSignal signal);

	/**
	 * 根据 获取到的数据 新增设备
	 * 
	 * @param uniqueCode
	 * @param device
	 */
	public void saveSignal(String uniqueCode, ConeSignal signal);

	/**
	 * 根据 实时数据 修改 single的value值 根据C1_ID进行修改
	 */
	public void updateValueData(Body coneMonitorData,String uniqueCode,String fsuId);

	/**
	 * 根据 实时数据 修改 single的value值 根据C1_ID进行修改
	 */
	public void updateValue(String uniqueCode, ConeSignal signal);
	/**
	 * 根据
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public List<ConeHistoryData> queryHistory(String uniqueCode, String signalId);
	/**
	 * 根据
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public List<ConeHistoryData> queryHistoryByC1(String uniqueCode, long c1_id);
	
	/**
	 * 根据 C1ID 取得最近一条历史记录
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public ConeHistoryData queryLastHistoryByC1(String uniqueCode, long c1_id);
	/**
	 * 根据设备 取得 该设备下的 所有需要轮询的信号
	 * 
	 * @param uniqueCode
	 * @return
	 */
	public List<ConeSignal> querySignalByDeviceList(String uniqueCode,String deviceId);
	
	
	public int querySignalByDeviceCount(String uniqueCode,String deviceId);

	/**
	 * 取得 该 企业下的 所有需要轮询的信号
	 * 
	 * @param uniqueCode
	 * @return
	 */
	public List<ConeSignal> querySignalList(String uniqueCode,int skip,int limit);

	/**
	 * 取得 该 企业下的 所有需要轮询的信号
	 * 
	 * @param uniqueCode
	 * @return
	 */
	public int querySignalCount(String uniqueCode);
	
	/**
	 * 新增 历史记录
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public void saveHistory(String uniqueCode,ConeHistoryData historyDate);
	
	/**
	 * 修改 历史记录
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public void updateHistory(String uniqueCode,ConeHistoryData historyDate);
	
	/**
	 * 根据ID 修改 属性-一般修改阀值
	 * @param uniqueCode
	 * @param signal
	 */
	public void updateSignalById(String uniqueCode, ConeSignal signal);
	
	/**
	 * 根据修改的值来处理 DO 
	 */
	public Body getBodyCone(String uniqueCode, ConeSignal signal);
	
}
