package com.kongtrolink.interweb.service.cone;

import java.util.List;

import com.kongtrolink.cone.message.body.device.TNodes;
import com.kongtrolink.interweb.entity.cone.ConeTNodes;

/**
 * 保存 节点信息
 */
public interface ConeNodeService {

	public void saveNodes(TNodes[] tNodes,String uniqueCode,String fsuId);
	
	List<ConeTNodes> findNodes(String fsuId,String uniqueCode);
	
	TNodes[] findNodesForTNodes(String fsuId,String uniqueCode);
}
