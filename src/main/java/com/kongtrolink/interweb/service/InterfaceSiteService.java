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
package com.kongtrolink.interweb.service;

import com.kongtrolink.scloud.core.entity.cone.ConeSite;

/**
 * 
 * @author Mosaico
 */
public interface InterfaceSiteService {
	/**
	 * 根据ID 取得站点信息
	 * @param uniqueCode
	 * @param id
	 * @return
	 */
	public ConeSite querySiteById(String uniqueCode, String id);

	/**
	 * 根据接口返回 修改 站点信息
	 * @param uniqueCode
	 * @param site
	 */
	public void updateSite(String uniqueCode, ConeSite site);

}
