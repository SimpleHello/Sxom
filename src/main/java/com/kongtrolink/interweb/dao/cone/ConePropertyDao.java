package com.kongtrolink.interweb.dao.cone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

@Repository
public class ConePropertyDao {

	@Autowired
	MongoTemplate mongoTemplate;

	public void saveProperty(Object data, String collectionName) {
		// TODO Auto-generated method stub
		mongoTemplate.save(data, collectionName);
	}

	/**
	 * 获取数据库里面最新的1条数据
	 * @param collectionName
	 * @param c1_id
	 * @param clazz
	 * @return
	 */
	public <T extends ConeBaseEntity> T getProperty(String collectionName, long c1_id, Class<T> clazz) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("c1_id").is(c1_id);
		Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "ctime"));
		query.limit(1);
		return mongoTemplate.findOne(query, clazz, collectionName);
	}
}
