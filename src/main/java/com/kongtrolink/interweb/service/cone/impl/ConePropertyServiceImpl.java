package com.kongtrolink.interweb.service.cone.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.body.device.TAIC;
import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.body.device.TDIC;
import com.kongtrolink.cone.message.body.device.TDOC;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.device.TSC;
import com.kongtrolink.cone.message.body.device.TStation;
import com.kongtrolink.interweb.dao.cone.ConePropertyDao;
import com.kongtrolink.interweb.dao.device.InterfaceSignalDao;
import com.kongtrolink.interweb.entity.cone.common.ConeResourceConverter;
import com.kongtrolink.interweb.entity.cone.common.ConeSignalConverter;
import com.kongtrolink.interweb.entity.cone.device.ConeTAIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTAOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDevice;
import com.kongtrolink.interweb.entity.cone.device.ConeTSC;
import com.kongtrolink.interweb.entity.cone.device.ConeTStation;
import com.kongtrolink.interweb.job.ConeHistoryTask;
import com.kongtrolink.interweb.service.InterfaceDeviceService;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.interweb.service.InterfaceSiteService;
import com.kongtrolink.interweb.service.cone.ConePropertyService;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;
import com.kongtrolink.scloud.core.entity.cone.ConeSite;

@Service("conePropertyService")
public class ConePropertyServiceImpl implements ConePropertyService {

	@Autowired
	ConePropertyDao conePropertyDao; //将C1获取到的数据 完整保存到数据中

	@Autowired
	InterfaceSiteService interfaceSiteService;//
	
	@Autowired
	InterfaceFsuService interfaceFsuService;//
	
	@Autowired
	InterfaceDeviceService interfaceDeviceService;//
	
	@Autowired
	InterfaceSignalDao interfaceSignalDao;//

	private static final Logger LOGGER = LoggerFactory.getLogger(ConePropertyServiceImpl.class);
	
	/**
	 * 保存信号点 到数据库中
	 */
	@Override
	public boolean saveProperty(Body[] bodys, String uniqueCode, String fsuId,String deviceId,int cDeviceType) {
		// TODO Auto-generated method stub
		try{
			ConeFsu  findFsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);//根据ID 查询FSU信息
			ConeSite findSite = interfaceSiteService.querySiteById(uniqueCode, findFsu.getSiteId());//根据ID 查询站点信息
			String siteId = findSite.getId();
			String siteCode = findSite.getCode();
			if (bodys != null && bodys.length > 0) {
				for (Body data : bodys) {
					if (data instanceof TAIC) {
						savePropertyTAIC((TAIC)data,uniqueCode,siteId,fsuId,siteCode,deviceId,cDeviceType);
					} else if (data instanceof TAOC) {
						savePropertyTAOC((TAOC)data,uniqueCode,siteId,fsuId,siteCode,deviceId,cDeviceType);
					} else if (data instanceof TDevice) {
						savePropertyTDevice((TDevice)data,uniqueCode,siteId,fsuId,siteCode,deviceId);
					} else if (data instanceof TDIC) {
						savePropertyTDIC((TDIC)data,uniqueCode,siteId,fsuId,siteCode,deviceId,cDeviceType);
					} else if (data instanceof TDOC) {
						savePropertyTDOC((TDOC)data,uniqueCode,siteId,fsuId,siteCode,deviceId,cDeviceType);
					} else if (data instanceof TSC) {
						ConeTSC entity = new ConeTSC((TSC) data, fsuId);
						conePropertyDao.saveProperty(entity, uniqueCode + ConeTSC.NAMESPACE);
					} else if (data instanceof TStation) {
						savePropertyTStation((TStation)data,uniqueCode,siteId,fsuId,siteCode);
					}
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	private void savePropertyTStation(TStation data,String uniqueCode,String siteId,String fsuId,String siteCode){
		ConeTStation entity = new ConeTStation((TStation) data, fsuId);//取得 站点信息 只进行更新操作
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTStation.NAMESPACE);
		//站点信息 同步更新
		ConeSite site = ConeResourceConverter.convert((TStation)data);
		site.setId(siteId);
		interfaceSiteService.updateSite(uniqueCode, site);//取得 站点信息 只进行更新操作
		//FSU信息 同步更新
		ConeFsu cfsu = ConeResourceConverter.convertConeFsu((TStation)data);
		String fsuCode = interfaceFsuService.createFsuCode(siteCode, site.getC1_id());
		cfsu.setId(fsuId);
		cfsu.setCode(fsuCode);
		cfsu.setState("在线");
		interfaceFsuService.updateFsu(uniqueCode, cfsu);
	}
	
	private void savePropertyTDevice(TDevice data,String uniqueCode,String siteId,String fsuId,String siteCode,String deviceId){
		ConeTDevice entity = new ConeTDevice(data, fsuId);
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTDevice.NAMESPACE);//将C1获取到的数据 完整保存到数据中
		int deviceType = entity.getDeviceType().getType();
		String deviceCode = interfaceDeviceService.createDeviceCode(siteCode, (int)entity.getC1_id(), deviceType);
		ConeDevice coneDevice = ConeResourceConverter.convert((TDevice) data, siteId, fsuId, deviceCode);
		coneDevice.setId(deviceId);//输入设备ID
		/**
		 * 数据库中查询 是否具有该设备
		 */
		LOGGER.info("开始进行 device 操作");
		ConeDevice coneDevicedb =interfaceDeviceService.queryDeviceByC1Id(uniqueCode, (int)entity.getC1_id());
		if(coneDevicedb!=null){//如果 部位空 则返回
			LOGGER.info("找到一条 数据");
			coneDevice.setId(coneDevicedb.getId());//ID 保持一直
		}else if(deviceId==null){
			return ;//如果 数据库不存在 且 没有传入有效deviceId
		}
		interfaceDeviceService.delDeviceByC1(uniqueCode, coneDevice);//先删除原有数据库设备属性
		LOGGER.info("save device");
		interfaceDeviceService.saveDevice(uniqueCode, coneDevice);
	}

	private void savePropertyTAIC(TAIC data,String uniqueCode,String siteId,String fsuId,String siteCode,String deviceId,int cDeviceType){
		ConeTAIC entity = new ConeTAIC(data, fsuId);
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTAIC.NAMESPACE); //将C1获取到的数据 完整保存到数据中
		String deviceCode = interfaceDeviceService.createDeviceCode(siteCode, (int)entity.getC1_id(), cDeviceType);
		List<ConeSignal> listDb = interfaceSignalDao.querySignalListByC1Id(uniqueCode,  (int)entity.getC1_id());
		List<ConeSignal> list = ConeResourceConverter.convertTAICTo((TAIC) data, deviceId, siteId, deviceCode, cDeviceType,listDb);
		interfaceSignalDao.delSignalByC1(uniqueCode, (int)entity.getC1_id());//先删除数据库原有信号点
		if(list!=null&&list.size()>0){
			for(ConeSignal signal:list){
				interfaceSignalDao.saveSignal(uniqueCode, signal);
			}
		}
	}
	private void savePropertyTAOC(TAOC data,String uniqueCode,String siteId,String fsuId,String siteCode,String deviceId,int cDeviceType){
		ConeTAOC entity = new ConeTAOC((TAOC) data, fsuId);
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTAOC.NAMESPACE);
		String deviceCode = interfaceDeviceService.createDeviceCode(siteCode, (int)entity.getC1_id(), cDeviceType);
		ConeSignal signal = ConeResourceConverter.convertTAOCTo((TAOC) data, deviceId, siteId, deviceCode, cDeviceType);
		ConeSignal signaldb = interfaceSignalDao.querySignalByC1Id(uniqueCode, (int)entity.getC1_id());
		if(signaldb!=null){//如果 部位空 则返回
			signal.setId(signaldb.getId());//ID 保持一直
			signal.setDeviceId(signaldb.getDeviceId());//deviceId 保存一直
			signal.setCode(signaldb.getCode());//code 保存一直
		}else if(deviceId==null){
			return ;//如果 数据库不存在 且 没有传入有效deviceId
		}
		interfaceSignalDao.delSignalByC1(uniqueCode, (int)entity.getC1_id());//先删除数据库原有信号点
		LOGGER.info("save taoc");
		interfaceSignalDao.saveSignal(uniqueCode, signal);
	}
	private void savePropertyTDIC(TDIC data,String uniqueCode,String siteId,String fsuId,String siteCode,String deviceId,int cDeviceType){
		ConeTDIC entity = new ConeTDIC(data, fsuId);
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTDIC.NAMESPACE);
		String deviceCode = interfaceDeviceService.createDeviceCode(siteCode, (int)entity.getC1_id(), cDeviceType);
		ConeSignal signal = ConeResourceConverter.convertTDICTo((TDIC) data, deviceId, siteId, deviceCode, cDeviceType);
		ConeSignal signaldb = interfaceSignalDao.querySignalByC1Id(uniqueCode, (int)entity.getC1_id());
		if(signaldb!=null){//如果 部位空 则返回
			signal.setId(signaldb.getId());//ID 保持一直
			signal.setDeviceId(signaldb.getDeviceId());//deviceId 保存一直
			signal.setCode(signaldb.getCode());//code 保存一直
		}else if(deviceId==null){
			return ;//如果 数据库不存在 且 没有传入有效deviceId
		}
		interfaceSignalDao.delSignalByC1(uniqueCode, (int)entity.getC1_id());//先删除数据库原有信号点
		LOGGER.info("save TDIC");
		interfaceSignalDao.saveSignal(uniqueCode, signal);
	}
	private void savePropertyTDOC(TDOC data,String uniqueCode,String siteId,String fsuId,String siteCode,String deviceId,int cDeviceType){
		ConeTDOC entity = new ConeTDOC((TDOC) data, fsuId);
		conePropertyDao.saveProperty(entity, uniqueCode + ConeTDOC.NAMESPACE);
		String deviceCode = interfaceDeviceService.createDeviceCode(siteCode, (int)entity.getC1_id(), cDeviceType);
		ConeSignal signal = ConeResourceConverter.convertTDOCTo((TDOC) data, deviceId, siteId, deviceCode, cDeviceType);
		ConeSignal signaldb = interfaceSignalDao.querySignalByC1Id(uniqueCode, (int)entity.getC1_id());
		if(signaldb!=null){//如果 部位空 则返回
			signal.setId(signaldb.getId());//ID 保持一直
			signal.setDeviceId(signaldb.getDeviceId());//deviceId 保存一直
			signal.setCode(signaldb.getCode());//code 保存一直
		}else if(deviceId==null){
			return ;//如果 数据库不存在 且 没有传入有效deviceId
		}
		interfaceSignalDao.delSignalByC1(uniqueCode, (int)entity.getC1_id());//先删除数据库原有信号点
		LOGGER.info("save TDOC");
		interfaceSignalDao.saveSignal(uniqueCode, signal);
	}
	/**
	 * 取得 数据库中 最新的1条数据
	 */
	@Override
	public Body getLastConeProperty(String uniqueCode,String baseName, long c1_id) {
		// TODO Auto-generated method stub
		String collectionName = uniqueCode+baseName;//数据库表名
		switch(baseName){
			case "_c1_taic":
				ConeTAIC coneTAIC = conePropertyDao.getProperty(collectionName, c1_id, ConeTAIC.class);
				if(coneTAIC!=null){
					TAIC taic = ConeSignalConverter.converToTAIC(coneTAIC);
					return taic;
				}
				break;
			case "_c1_taoc":
				ConeTAOC coneTAOC = conePropertyDao.getProperty(collectionName, c1_id, ConeTAOC.class);
				if(coneTAOC!=null){
					TAOC taoc = ConeSignalConverter.converToTAOC(coneTAOC);
					return taoc;
				}
				break;
			case "_c1_tdic":
				ConeTDIC coneTDIC = conePropertyDao.getProperty(collectionName, c1_id, ConeTDIC.class);
				if(coneTDIC!=null){
					TDIC tdic = ConeSignalConverter.converToTDIC(coneTDIC);
					return tdic;
				}
				break;
			case "_c1_tdoc":
				ConeTDOC coneTDOC = conePropertyDao.getProperty(collectionName, c1_id, ConeTDOC.class);
				if(coneTDOC!=null){
					TDOC tdoc = ConeSignalConverter.converToTDOC(coneTDOC);
					return tdoc;
				}
				break;
				
		}
		return null;
	}
	/**
	 * 当前修改的阀值 主要针对 taic 的 告警
	 */
	@Override
	public Body getPropertyBySignal(String uniqueCode, ConeSignal signal){
		String baseName = signal.getSourceData();
		long c1_id = signal.getC1_id();
		String collectionName = uniqueCode + baseName;//数据库表名
		switch(baseName){
			case "_c1_taic":
				int taicType = signal.getTaicType();//若修改 此处阀值 
				ConeTAIC coneTAIC = conePropertyDao.getProperty(collectionName, c1_id, ConeTAIC.class);
				float threshold = signal.getThreshold().floatValue();
				if(coneTAIC!=null){
					if(taicType==11){ //1级告警上限
						coneTAIC.setHiLimit1(threshold);
					}else if(taicType==12){ //1级告警下限
						coneTAIC.setLoLimit1(threshold);
					}else if(taicType==21){//2级告警上限
						coneTAIC.setHiLimit2(threshold);
					}else if(taicType==22){//2级告警下限
						coneTAIC.setLoLimit2(threshold);
					}else if(taicType==31){//3级告警上限
						coneTAIC.setHiLimit3(threshold);
					}else if(taicType==31){//3级告警下限
						coneTAIC.setLoLimit3(threshold);
					}
					TAIC taic = ConeSignalConverter.converToTAIC(coneTAIC);
					return taic;
				}
				break;
			case "_c1_taoc":
				ConeTAOC coneTAOC = conePropertyDao.getProperty(collectionName, c1_id, ConeTAOC.class);
				if(coneTAOC!=null){
					TAOC taoc = ConeSignalConverter.converToTAOC(coneTAOC);
					return taoc;
				}
				break;
			case "_c1_tdic":
				ConeTDIC coneTDIC = conePropertyDao.getProperty(collectionName, c1_id, ConeTDIC.class);
				if(coneTDIC!=null){
					TDIC tdic = ConeSignalConverter.converToTDIC(coneTDIC);
					return tdic;
				}
				break;
			case "_c1_tdoc":
				ConeTDOC coneTDOC = conePropertyDao.getProperty(collectionName, c1_id, ConeTDOC.class);
				if(coneTDOC!=null){
					TDOC tdoc = ConeSignalConverter.converToTDOC(coneTDOC);
					return tdoc;
				}
				break;
				
		}
		return null;
	}
}
