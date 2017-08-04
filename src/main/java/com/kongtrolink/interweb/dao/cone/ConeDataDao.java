package com.kongtrolink.interweb.dao.cone;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kongtrolink.cone.entity.ConeNotify;
import com.kongtrolink.interweb.entity.cone.ConeData;

@Repository
public class ConeDataDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public void saveData(ConeData data, String uniqueCode) {
		// TODO Auto-generated method stub
		mongoTemplate.save(data, uniqueCode + ConeData.NAMESPACE);
	}

	public List<ConeNotify> queryConeNotify(String uniqueCode, String fsuId) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("enable").is(1).and("fsuId").is(fsuId);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeNotify.class, uniqueCode + ConeNotify.NAMESPACE);
	}

	public void updateConeNotify(String uniqueCode, ConeNotify notify) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("c1_id").is(notify.getC1_id()).andOperator(Criteria.where("c1_id").gt(0));
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("enable", 0);
		update.set("disTime", new Date());
		mongoTemplate.updateMulti(query, update, uniqueCode + ConeNotify.NAMESPACE);
	}
}
