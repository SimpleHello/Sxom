package com.kongtrolink.interweb.dao.cone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kongtrolink.interweb.entity.cone.ConeTNodes;

@Repository
public class ConeNodeDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public void saveNodes(ConeTNodes tNodes, String uniqueCode) {
		// TODO Auto-generated method stub
		mongoTemplate.save(tNodes, uniqueCode + ConeTNodes.NAMESPACE);
	}

	public void delNodes(String fsuId, String uniqueCode) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("fsuId").is(fsuId);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ConeTNodes.class, uniqueCode + ConeTNodes.NAMESPACE);
	}

	public List<ConeTNodes> findNodes(String fsuId, String uniqueCode) {
		Criteria criteria = Criteria.where("fsuId").is(fsuId);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeTNodes.class, uniqueCode + ConeTNodes.NAMESPACE);

	}
}
