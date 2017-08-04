package com.kongtrolink.interweb.service.cone;

import java.util.List;

import com.kongtrolink.cone.entity.ConeNotify;
import com.kongtrolink.interweb.entity.cone.ConeData;

/**
 * 保存 节点信息
 */
public interface ConeDataService {

	public void saveData(ConeData data,String uniqueCode);
	
	public List<ConeNotify> queryConeNotify(String uniqueCode,String fsuId);
	
	public void updateNotify(String uniqueCode,ConeNotify notify);

}
