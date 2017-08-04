package com.kongtrolink.interweb.service.cone;

import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;

/**
 * 	C1 接口 获取数据之后
 * 保存 属性结构信息
 * 包含 站点信息 -FSU信息 -设备信息 - 信号点信息
 */
public interface ConePropertyService {

	public boolean saveProperty(Body[] bodys,String uniqueCode,String fsuId,String deviceId,int cDeviceType);
	
	public Body getLastConeProperty(String uniqueCode,String baseName, long c1_id);
	
	public Body getPropertyBySignal(String uniqueCode, ConeSignal signal);
}
