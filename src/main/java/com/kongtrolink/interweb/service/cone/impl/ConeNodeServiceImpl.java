package com.kongtrolink.interweb.service.cone.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.message.body.device.TNodes;
import com.kongtrolink.interweb.dao.cone.ConeNodeDao;
import com.kongtrolink.interweb.entity.cone.ConeTNodes;
import com.kongtrolink.interweb.service.cone.ConeNodeService;

@Service("coneNodeService")
public class ConeNodeServiceImpl implements ConeNodeService {

	@Autowired
	ConeNodeDao coneNodeDao;
	
	/**
	 * 根据 C1接口 获取 node 数据
	 * 先删除原来的FSU 相关的NODEID
	 * 
	 */
	@Override
	public void saveNodes(TNodes[] tNodes,String uniqueCode,String fsuId) {
		// TODO Auto-generated method stub
		coneNodeDao.delNodes(fsuId, uniqueCode);//先删除表中原有数据
		if(tNodes!=null&&tNodes.length>0){
			for(TNodes node:tNodes){
				ConeTNodes cNode = new ConeTNodes(node,fsuId);
				coneNodeDao.saveNodes(cNode, uniqueCode);//插入最新的数据
			}
		}
	}

	@Override
	public List<ConeTNodes> findNodes(String fsuId,String uniqueCode) {
		// TODO Auto-generated method stub
		return coneNodeDao.findNodes(fsuId, uniqueCode);
	}

	@Override
	public TNodes[] findNodesForTNodes(String fsuId,String uniqueCode) {
		// TODO Auto-generated method stub
		 List<ConeTNodes> list = findNodes(fsuId,uniqueCode);
		 TNodes[] values = new TNodes[list.size()];
		 for(int i=0;i<list.size();i++){
			 ConeTNodes cNode = list.get(i);  
			 values[i] = new TNodes(cNode.getNodeId(),cNode.getParentId()); 
		 }
		return values;
	}

}
