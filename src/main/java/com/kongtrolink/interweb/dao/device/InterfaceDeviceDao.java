package com.kongtrolink.interweb.dao.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;

@Repository
public class InterfaceDeviceDao {
	@Autowired
    MongoTemplate mongoTemplate;
	
	/**
     * 根据ID 查询  设备信息
     * @param uniqueCode
     * @param 
     * @return 
     */
    public  ConeDevice queryDeviceById(String uniqueCode, String id) {
        Criteria criteria = Criteria.where("_id").is(id); 
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ConeDevice.class, 
                uniqueCode + CollectionSuffix.DEVICE);
    }
    
    /**
     * 根据C1 查询  设备信息
     * @param uniqueCode
     * @param 
     * @return 
     */
    public ConeDevice queryDeviceByC1Id(String uniqueCode, int c1_id) {
		// TODO Auto-generated method stub
    	 Criteria criteria = Criteria.where("c1_id").is(c1_id).andOperator(Criteria.where("c1_id").gt(0));
         Query query = new Query(criteria);
         return mongoTemplate.findOne(query, ConeDevice.class, 
                 uniqueCode + CollectionSuffix.DEVICE);
	}
	public void delDevice(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("id").is(id); 
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ConeDevice.class, uniqueCode + CollectionSuffix.DEVICE);
	}

	public void delDeviceByC1(String uniqueCode, int c1_id) {
		// TODO Auto-generated method stub
		if(c1_id==0){
			return ;
		}
		Criteria criteria = Criteria.where("c1_id").is(c1_id).andOperator(Criteria.where("c1_id").gt(0)); 
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ConeDevice.class, uniqueCode + CollectionSuffix.DEVICE);
	}
	public void saveDevice(String uniqueCode, ConeDevice device) {
		// TODO Auto-generated method stub
		mongoTemplate.save(device, uniqueCode + CollectionSuffix.DEVICE);
	}
	
	public  List<ConeDevice> queryDeviceList(String uniqueCode, String fsuId) {
        Criteria criteria = Criteria.where("fsuId").is(fsuId); 
        Query query = new Query(criteria);
        return mongoTemplate.find(query, ConeDevice.class, 
                uniqueCode + CollectionSuffix.SITE);
    }
}
