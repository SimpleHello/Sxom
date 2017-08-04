package com.kongtrolink.interweb.dao.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;

@Repository
public class InterfaceFsuDao {
	@Autowired
    MongoTemplate mongoTemplate;
	
	 /**
     * 根据ID 查询 FSU 列表
     * @param uniqueCode
     * @param 
     * @return 
     */
    public  ConeFsu  queryFsuById(String uniqueCode, String id) {
        Criteria criteria = Criteria.where("_id").is(id); 
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ConeFsu.class, 
                uniqueCode + CollectionSuffix.FSU);
    }
    
    public List<ConeFsu> queryConeFsu(String uniqueCode) {
        Criteria criteria = Criteria.where("c1_id").exists(true).andOperator(Criteria.where("c1_id").gt(0)); 
        Query query = new Query(criteria);
        return mongoTemplate.find(query, ConeFsu.class, 
                uniqueCode + CollectionSuffix.FSU);
    }
    public void updateFsu(String uniqueCode, ConeFsu fsu) {
    	int c1_id = fsu.getC1_id();
    	int c1_parentId = fsu.getC1_parentId();
    	String name = fsu.getName();
    	String code = fsu.getCode();
    	String state = fsu.getState();
		Update update = new Update();
		update.set("_class", fsu.getClass().getName());
		update.set("state", "在线");
        if (c1_id != 0) {
            update.set("c1_id", c1_id);
        }
        if (c1_parentId != 0) {
            update.set("c1_parentId", c1_parentId);
        }
        if (name !=null&&!"".equals(name)) {
            update.set("name", name);
        }
        if (code !=null&&!"".equals(code)) {
            update.set("code", code);
        }
        if (state !=null&&!"".equals(state)) {
            update.set("state", state);
        }
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(fsu.getId())),
                update, uniqueCode+CollectionSuffix.FSU);
    }

}
