/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kongtrolink.interweb.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kongtrolink.cone.entity.ConeNotify;
import com.kongtrolink.cone.message.body.SetProperty;
import com.kongtrolink.cone.util.CInterfaceConfig;
import com.kongtrolink.interweb.all.ConeStaticMessageMap;
import com.kongtrolink.interweb.service.InterfaceEnterpriseService;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.interweb.service.cone.ConeDataService;
import com.kongtrolink.interweb.service.cone.ConePropertyService;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.enterprise.Enterprise;

/**
 * 修改数据响应 表的处理 c1接口接收到修改数据响应 将数据存在 _c1_notify表 接口服务 定时30秒一次 扫描表 取出 修改的属性 进行响应的操作
 * 最大有30秒延迟 (30秒 这个时间间隔 可以修改)
 * 
 * @author John
 *
 */
public class ConePropertyTask implements Job {

	@Autowired
	private InterfaceEnterpriseService interfaceEnterpriseService;
	@Autowired
	private InterfaceFsuService interfaceFsuService;
	@Autowired
	private ConePropertyService conePropertyService;
	@Autowired
	ConeDataService coneDataService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConePropertyTask.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("ConePropertyTask #轮询属性修改 #  开始执行..");
		try {
			// 对所有企业执行 FSU 统计并存储操作
			List<Enterprise> enterpriseList = interfaceEnterpriseService.queryAllCustomers();
			if (enterpriseList == null || enterpriseList.isEmpty()) {
				return;
			}
			List<Integer> disposedList = new ArrayList<Integer>();// 保存当前轮询已经处理过的C1_ID
			for (Enterprise enterprise : enterpriseList) {
				String uniqueCode = enterprise.getUniqueCode();
				List<ConeFsu> fsuList = interfaceFsuService.queryConeFsu(uniqueCode);
				if (fsuList != null && fsuList.size() > 0) {
					for (ConeFsu fsu : fsuList) {
						String key = fsu.getIp() + "#" + fsu.getPort();
						String fsuId = fsu.getId();
						if (ConeStaticMessageMap.configMap.containsKey(key)) {
							try {// 对已经登录的设备 进行处理
								List<ConeNotify> notifyList = coneDataService.queryConeNotify(uniqueCode, fsuId);
								LOGGER.info("当前变更数据数量为:" + notifyList.size());
								if (notifyList != null && notifyList.size() > 0) {
									for (ConeNotify notify : notifyList) {
										int c1_id = (int) notify.getC1_id();
										if (!disposedList.contains(c1_id)) {// 保存当前轮询已经处理过的C1_ID不再进行处理
											LOGGER.info("轮询变更=>c1_id：" + c1_id);
											int[] deviceInfoId = { c1_id };
											CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
											SetProperty propertyInfo = config.getInterfaceFactory().getProperty(deviceInfoId);// 获取设备本身的信息
											conePropertyService.saveProperty(propertyInfo.getValues(), uniqueCode,fsuId, null, 0);// 更新响应的设备信息
											coneDataService.updateNotify(uniqueCode, notify);// 修改状态
											disposedList.add(c1_id);
										}

									}
								}
							} catch (Exception e) {
								LOGGER.error(">>> 接口服务启动 FSU:"+key+" 轮询属性修改 异常 ERROR !!!! ");
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
		LOGGER.info("ConePropertyTask #轮询属性修改 #  执行完成!!");
	}

}
