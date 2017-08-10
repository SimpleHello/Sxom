package com.kongtrolink.interweb.dao.device;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.entity.cone.ConeHistoryData;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;

@Repository
public class InterfaceSignalDao {
	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 根据ID 查询 信号点 信息
	 * 
	 * @param uniqueCode
	 * @param
	 * @return
	 */
	public ConeSignal querySignalById(String uniqueCode, String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = new Query(criteria);
		return mongoTemplate.findOne(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public ConeSignal querySignalByC1Id(String uniqueCode, long c1_id){
		Criteria criteria = Criteria.where("c1_id").is(c1_id).and("original").is(true).andOperator(Criteria.where("c1_id").gt(0));
		Query query = new Query(criteria);
		return mongoTemplate.findOne(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}
	
	/**
	 * 1个AI 衍生 6个DI
	 * 1个c1 id 对应多个 信号信息 
	 * @param uniqueCode
	 * @param c1_id
	 * @return
	 */
	public List<ConeSignal> querySignalListByC1Id(String uniqueCode, long c1_id){
		Criteria criteria = Criteria.where("c1_id").is(c1_id).andOperator(Criteria.where("c1_id").gt(0));
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}
	
	public void delSignal(String uniqueCode, String id) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public void delSignalByC1(String uniqueCode, int c1_id) {
		// TODO Auto-generated method stub
		if(c1_id==0){
			return ;
		}
		Criteria criteria = Criteria.where("c1_id").is(c1_id);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public void saveSignal(String uniqueCode, ConeSignal signal) {
		// TODO Auto-generated method stub
		mongoTemplate.save(signal, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public void updateValue(String uniqueCode, ConeSignal signal) {
		int c1_id = signal.getC1_id();
		double value = (double) signal.getValue();
		Update update = new Update();
		update.set("ctime", new Date());
		if (value != 0) {
			update.set("value", value);
		}
		mongoTemplate.updateMulti(new Query(Criteria.where("c1_id").is(c1_id).andOperator(Criteria.where("c1_id").gt(0))), update,
				uniqueCode + CollectionSuffix.SIGNAL);
	}

	public void updateSignalById(String uniqueCode, ConeSignal signal) {
		Double value = signal.getValue();
		double threshold =  signal.getThreshold();
		Update update = new Update();
		update.set("ctime", new Date());
		if (value!=null) {
			update.set("value", value);
		}
		if (threshold != 0) {
			update.set("threshold", threshold);
		}
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(signal.getId())), update,uniqueCode + CollectionSuffix.SIGNAL);
	}
	/**
	 * 根据
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public List<ConeHistoryData> queryHistory(String uniqueCode, String signalId) {
		Criteria criteria = Criteria.where("signalId").is(signalId);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeHistoryData.class, uniqueCode + CollectionSuffix.HISTORY_DATA);
	}

	/**
	 * 根据
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public List<ConeHistoryData> queryHistoryByC1(String uniqueCode, long c1_id) {
		Criteria criteria = Criteria.where("c1_id").is(c1_id);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeHistoryData.class, uniqueCode + CollectionSuffix.HISTORY_DATA);
	}
	/**
	 * 取得 该 企业下的 所有需要轮询的信号
	 * 
	 * @param uniqueCode
	 * @return
	 */
	public List<ConeSignal> querySignalList(String uniqueCode, int skip, int limit) {
		Criteria criteria = Criteria.where("original").is(true);
		Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "tRecord"));
		query.skip((skip - 1) * limit).limit(limit);
		return mongoTemplate.find(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public List<ConeSignal> querySignalByDeviceList(String uniqueCode, String deviceId) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("original").is(true).and("deviceId").is(deviceId);
		Query query = new Query(criteria);
		return mongoTemplate.find(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public int querySignalCount(String uniqueCode) {
		Criteria criteria = Criteria.where("original").is(true);
		Query query = new Query(criteria);
		return (int) mongoTemplate.count(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}

	public int querySignalByDeviceCount(String uniqueCode, String deviceId) {
		// TODO Auto-generated method stub
		Criteria criteria = Criteria.where("original").is(true).and("deviceId").is(deviceId);
		Query query = new Query(criteria);
		return (int) mongoTemplate.count(query, ConeSignal.class, uniqueCode + CollectionSuffix.SIGNAL);
	}
	
	
	/**
	 * 新增 历史记录
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public void saveHistory(String uniqueCode,ConeHistoryData historyDate){
		mongoTemplate.save(historyDate, uniqueCode + CollectionSuffix.HISTORY_DATA);
	}
	
	/**
	 * 修改 历史记录
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @return
	 */
	public void updateHistory(String uniqueCode,ConeHistoryData historyDate) {
		Update update = new Update();
		update.set("tEnd", new Date());
		update.set("count", historyDate.getCount());
		update.set("max", historyDate.getMax());
		update.set("data", historyDate.getData());
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(historyDate.getId())), update,uniqueCode + CollectionSuffix.HISTORY_DATA);
	}
}
