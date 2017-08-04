/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kongtrolink.interweb.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.message.body.SetDynAccessMode;
import com.kongtrolink.cone.message.body.SetDynAccessModeAck;
import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.body.device.TNodes;
import com.kongtrolink.cone.message.enumeration.DataAccessMode;
import com.kongtrolink.cone.util.CInterfaceConfig;
import com.kongtrolink.interweb.all.ConeStaticMessageMap;
import com.kongtrolink.interweb.entity.cone.ConeData;
import com.kongtrolink.interweb.service.InterfaceEnterpriseService;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.interweb.service.InterfaceSignalService;
import com.kongtrolink.interweb.service.cone.ConeDataService;
import com.kongtrolink.interweb.service.cone.ConeNodeService;
import com.kongtrolink.interweb.util.IDGenerator;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.cone.ConeHistoryData;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;
import com.kongtrolink.scloud.core.entity.embedded.TelemetryData;
import com.kongtrolink.scloud.core.entity.enterprise.Enterprise;

/**
 * _history 表的处理
 * 
 * @author John
 *
 */

public class ConeHistoryTask implements Job {

	@Autowired
	private InterfaceSignalService interfaceSignalService;

	@Autowired
	private InterfaceEnterpriseService interfaceEnterpriseService;

	@Autowired
	private InterfaceFsuService interfaceFsuService;
	@Autowired
	private ConeNodeService coneNodeService;
	@Autowired
	ConeDataService coneDataService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConeHistoryTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("ConeHistoryTask #轮询历史记录#  开始执行..");
		setExecute();
		LOGGER.info("ConeHistoryTask  #轮询历史记录#  执行完成!!");
	}

	public void setExecute() {
		try {
			// 对所有企业执行 FSU 统计并存储操作
			List<Enterprise> enterpriseList = interfaceEnterpriseService.queryAllCustomers();
			if (enterpriseList == null || enterpriseList.isEmpty()) {
				return;
			}
			for (Enterprise enterprise : enterpriseList) {
				String uniqueCode = enterprise.getUniqueCode();
				List<ConeFsu> fsuList = interfaceFsuService.queryConeFsu(uniqueCode);
				if (fsuList != null && fsuList.size() > 0) {
					for (ConeFsu fsu : fsuList) {

						String key = fsu.getIp() + "#" + fsu.getPort();
						if (ConeStaticMessageMap.configMap.containsKey(key)) {
							try {// 对已经登录的设备 进行处理
								TNodes[] nodeList = coneNodeService.findNodesForTNodes(fsu.getId(), uniqueCode);
								List<Integer> deviceIdList = IDGenerator.getDeviceNodesList(nodeList);// 解析出所有的设备ID多个设备节点AA.BBB.1.0AA.BBB.2.0.....
								if (deviceIdList != null && deviceIdList.size() > 0) {
									for (Integer device_c1Id : deviceIdList) {
										LOGGER.info("轮询设备=>device c1_id：" + device_c1Id);
										// int childCount =
										// IDGenerator.getNodeCount(nodeList,device_c1Id);
										SetDynAccessMode setDynAccessMode = new SetDynAccessMode();
										setDynAccessMode.setGroupID(1);
										setDynAccessMode.setMode(DataAccessMode.ASK_ANSWER);
										setDynAccessMode.setPollingTime(1);
										int[] setDynAccessModeIds = { device_c1Id }; // 取所有设备的ID:AA.BBB.1.0,AA.BBB.2.0,AA.BBB.3.0
										setDynAccessMode.setCount(1);
										setDynAccessMode.setIds(setDynAccessModeIds);
										CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
										SetDynAccessModeAck setDynAccessModeAck = config.getInterfaceFactory()
												.setDynAccessMode(setDynAccessMode);// 取得设备信号点的实时数据
										LOGGER.info("轮询设备获取数据=>device c1_id：" + device_c1Id + " 数据:"
												+ setDynAccessModeAck.toString());
										saveDateAccess(setDynAccessModeAck, fsu.getId(), uniqueCode);// 保存数据库的操作
									}
								}
							} catch (Exception e) {
								LOGGER.error(">>> 接口服务启动 FSU:"+key+" 轮询设备获取数据 异常  ERROR !!!!");
								ConeStaticMessageMap.configMap.remove(key);
								fsu.setState("离线");
								interfaceFsuService.updateFsu(uniqueCode, fsu);
								e.printStackTrace();
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存实时数据 并 更新历史数据
	 * 
	 * @param setDynAccessModeAck
	 */
	private void saveDateAccess(SetDynAccessModeAck setDynAccessModeAck, String fsuId, String uniqueCode) {
		if (setDynAccessModeAck != null && setDynAccessModeAck.getValues() != null
				&& setDynAccessModeAck.getValues().length > 0) {
			for (Body coneMonitorData : setDynAccessModeAck.getValues()) {
				ConeData data = new ConeData(coneMonitorData, fsuId);
				coneDataService.saveData(data, uniqueCode);// 保存到 数据中
				ConeHistoryData coneHistoryData = interfaceSignalService.queryLastHistoryByC1(uniqueCode,data.getC1_id());//查询最近一条数据信息
				String dataType = data.getDataType();
				if(dataType==null||"".equals(dataType)||"TS".equals(dataType)){
					continue;
				}
				if (coneHistoryData == null) {
					coneHistoryData = new ConeHistoryData();
					ConeSignal signal = interfaceSignalService.querySignalByC1Id(uniqueCode, data.getC1_id());
					if (signal != null) {
						coneHistoryData.setSignalId(signal.getId());
						coneHistoryData.setMeasurement(signal.getMeasurement());
						coneHistoryData.settStart(new Date());
						coneHistoryData.settEnd(new Date());
						coneHistoryData.setBucket(1);
						coneHistoryData.setCount(1);
						coneHistoryData.setMax(1);
						coneHistoryData.setC1_id(data.getC1_id());
						List<TelemetryData> telList = new ArrayList<TelemetryData>();
						TelemetryData telData = new TelemetryData();
						telData.setValue(Double.valueOf(data.getValue()));
						telData.settRecord(new Date());
						telList.add(telData);
						coneHistoryData.setData(telList);
						interfaceSignalService.saveHistory(uniqueCode, coneHistoryData);
					}
				} else if (coneHistoryData.getMax() == 0) { // 最近一条历史数据 数据量满的数据
															// = 需要新增一条数据
					coneHistoryData.setId(null);
					coneHistoryData.settStart(new Date());
					coneHistoryData.settEnd(new Date());
					coneHistoryData.setBucket(coneHistoryData.getBucket() + 1);
					coneHistoryData.setCount(1);
					coneHistoryData.setMax(1);
					coneHistoryData.setC1_id(data.getC1_id());
					List<TelemetryData> telList = new ArrayList<TelemetryData>();
					TelemetryData telData = new TelemetryData();
					telData.setValue(Double.valueOf(data.getValue()));
					telData.settRecord(new Date());
					telList.add(telData);
					coneHistoryData.setData(telList);
					interfaceSignalService.saveHistory(uniqueCode, coneHistoryData);
				} else if (coneHistoryData.getMax() == 1) {// 最近一条历史数据数据量差1条满的数据修改原来数据 并修改
					int count = coneHistoryData.getCount() + 1;// 新增后的数据条数
					coneHistoryData.setCount(count);
					coneHistoryData.setMax(count == 10001 ? 0 : 1);// 满10001条数据时该行数据已满
					coneHistoryData.setC1_id(data.getC1_id());
					List<TelemetryData> telList = coneHistoryData.getData();
					TelemetryData telData = new TelemetryData();
					telData.setValue(Double.valueOf(data.getValue()));
					telData.settRecord(new Date());
					telList.add(telData);
					coneHistoryData.setData(telList);
					interfaceSignalService.updateHistory(uniqueCode, coneHistoryData);
				}
			}
		}
	}

}
