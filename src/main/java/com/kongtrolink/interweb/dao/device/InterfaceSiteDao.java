package com.kongtrolink.interweb.dao.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.cone.ConeSite;

@Repository
public class InterfaceSiteDao {
	@Autowired
    MongoTemplate mongoTemplate;
	
	 /**
     * 根据ID 查询 站点信息
     * @param uniqueCode
     * @param 
     * @return 
     */
    public  ConeSite querySiteById(String uniqueCode, String id) {
        Criteria criteria = Criteria.where("_id").is(id); 
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ConeSite.class, 
                uniqueCode + CollectionSuffix.SITE);
    }
    
    public void updateSite(String uniqueCode, ConeSite site) {
    	int c1_id = site.getC1_id();
    	int c1_parentId = site.getC1_parentId();
    	String name = site.getName();
    	String coordinate = site.getCoordinate();
		Update update = new Update();
        if (c1_id != 0) {
            update.set("c1_id", c1_id);
        }
        if (c1_parentId != 0) {
            update.set("c1_parentId", c1_parentId);
        }
        if (coordinate !=null&&!"".equals(coordinate)) {
            update.set("coordinate", coordinate);
        }
        if (name !=null&&!"".equals(name)) {
            update.set("name", name);
        }
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(site.getId())),
                update, uniqueCode+CollectionSuffix.SITE);
    }
}
