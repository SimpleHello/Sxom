package com.kongtrolink.interweb.all;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kongtrolink.cone.util.CInterfaceConfig;

/**
 * C接口 一些信息保存
 * @author John
 *
 */
public class ConeStaticMessageMap {
	
	/**
	 * 保存 FSU 登录连接
	 * map key：IP#PORT
	 * 同一个链接 只保留1个实例
	 */
	public static Map<String,CInterfaceConfig> configMap =  new ConcurrentHashMap<String, CInterfaceConfig>();
}
