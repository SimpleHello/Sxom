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

import com.kongtrolink.cone.entity.AlarmExt;
import com.kongtrolink.scloud.core.entity.alarm.Alarm;

/**
 *
 * @author Mosaico
 */
public interface InterfaceAlarmService {

    /**
     * c1接口收到告警数据 进行保存
     * @param uniqueCode
     * @param fsu
     */
    public void saveAlarm(String uniqueCode,Alarm alarm);
    
    /**
     * c1接口收到告警数据 进行保存
     * @param uniqueCode
     * @param fsu
     */
    public AlarmExt getAlarmById(String uniqueCode,String id);
    
}
