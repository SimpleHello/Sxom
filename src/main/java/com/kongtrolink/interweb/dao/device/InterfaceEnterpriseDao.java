/**
 * *****************************************************
 * Copyright (C) Kongtrolink techology Co.ltd - All Rights Reserved
 *
 * This file is part of Kongtrolink techology Co.Ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 ******************************************************
 */
package com.kongtrolink.interweb.dao.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kongtrolink.scloud.core.constant.CollectionSuffix;
import com.kongtrolink.scloud.core.constant.EnterpriseConst;
import com.kongtrolink.scloud.core.entity.enterprise.Enterprise;

/**
 *
 * @author Mosaico
 */
@Repository
public class InterfaceEnterpriseDao {

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 查询所有代理商企业
	 * 
	 * @return
	 */
	public List<Enterprise> findAllAgencys() {
		return mongoTemplate.find(new Query(Criteria.where("type").is(EnterpriseConst.AGENCY)), Enterprise.class,
				CollectionSuffix.ENTERPRISE);
	}

	public List<Enterprise> findAllCustomers() {
		return mongoTemplate.find(new Query(Criteria.where("type").is(EnterpriseConst.ENTERPRISE)), Enterprise.class,
				CollectionSuffix.ENTERPRISE);
	}

}
