package com.kongtrolink.interweb.dao.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kongtrolink.cone.entity.AlarmExt;
import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.alarm.Alarm;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;

@Repository
public class InterfaceAlarmDao {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	public void saveDevice(String uniqueCode, Alarm alarm) {
		// TODO Auto-generated method stub
		mongoTemplate.save(alarm, uniqueCode + CollectionSuffix.ALARM);
	}
	
    /**
     * c1接口收到告警数据 进行保存
     * @param uniqueCode
     * @param fsu
     */
    public AlarmExt getAlarmById(String uniqueCode,String id){
    	 Criteria criteria = Criteria.where("_id").is(id); 
         Query query = new Query(criteria);
         return mongoTemplate.findOne(query, AlarmExt.class, 
                 uniqueCode + CollectionSuffix.ALARM);
    }
}
