package com.kongtrolink.interweb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kongtrolink.cone.message.body.device.TNodes;

public class IDGenerator {
	/**
	 * 随机生成UUID
	 * 
	 * @return
	 */
	public static synchronized String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "").substring(0, 24);
		return uuidStr;// 只取24位
	}

	/**
	 * 返回 各级 节点ID 列表 map
	 * 1)根节点 AA.0.0.0
	 * 2)一个FSU节点  AA.BBB.0.0
	 * 3)多个设备节点  AA.BBB.1.0, AA.BBB.2.0.....
	 * 4)每个设备下 多个信号点  AA.BBB.1.1,AA.BBB.1.2,AA.BBB.1.3......
	 * @param i
	 * @return
	 */
	public static Map<String, List<Integer>> getNodeLevelMap(TNodes[] values) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		if (values != null && values.length > 0) {
			for (TNodes node : values) {
				int parents = (int) node.getParentId();
				if((parents>>27)>0&&(parents<<5)==0){//前5位>0 后面 27位=0 为 AA 一级 区域 
					map = insertMap(map,"aa",parents);
				}else if((parents>>17)>0&&(parents<<15)==0){//前15位>0 后面 17位=0 为 BB 二级 站点FSU级 
					map = insertMap(map,"bbb",parents);
				}else if((parents>>11)>0&&(parents<<21)==0){//前21位>0 后面 11位=0 为 CC 三级设备级
					map = insertMap(map,"cc",parents);
				}
			}
		}
		return map;
	}
	
	public static Map<String, List<Integer>> getNodeMap(TNodes[] values) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		if (values != null && values.length > 0) {
			for (TNodes node : values) {
				map = insertMap(map,String.valueOf(node.getParentId()),(int)node.getNodeId());
			}
		}
		return map;
	}

	
	public static int getNodeCount(TNodes[] values,long parentId) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		if (values != null && values.length > 0) {
			for (TNodes node : values) {
				map = insertMap(map,String.valueOf(node.getParentId()),(int)node.getNodeId());
			}
		}
		if (map.containsKey(String.valueOf(parentId))) {
			return map.get(String.valueOf(parentId)).size();
		} else {
			return 0;
		}

	}
	/**
	 * 取得 设备级别的ID列表 AA.BBB.CC.0 基本
	 * 
	 * @param values
	 * @return
	 */
	public static int[] getDeviceNodes(TNodes[] values) {
		Map<String, List<Integer>> map = getNodeLevelMap(values);
		List<Integer> deviceList = map.get("cc");
		int[] deviceL = {};
		if (deviceList != null) {
			deviceL = new int[deviceList.size()];
			for (int i = 0; i < deviceList.size(); i++) {
				Integer value = deviceList.get(i);
				deviceL[i] = (value == null ? 0 : value);
			}
		}
		return deviceL;
	}

	/**
	 * 取得 设备级别的ID列表 AA.BBB.CC.0 基本
	 * 
	 * @param values
	 * @return
	 */
	public static List<Integer> getDeviceNodesList(TNodes[] values) {
		Map<String, List<Integer>> map = getNodeLevelMap(values);
		List<Integer> deviceList = map.get("cc");
		return deviceList;
	}

	/**
	 * 取得 FSU ID AA.BBB.0.0 基本 只有1个
	 * 
	 * @param values
	 * @return
	 */
	public static Integer getFsuId(TNodes[] values) {
		Map<String, List<Integer>> map = getNodeLevelMap(values);
		List<Integer> deviceList = map.get("bbb");
		if (deviceList == null || deviceList.size() == 0) {
			return 0;
		}
		return deviceList.get(0);
	}

	public static Map<String, List<Integer>> insertMap(Map<String, List<Integer>> map, String key, Integer value) {
		if (map.containsKey(key)) {
			if (!map.get(key).contains(value)) {
				map.get(key).add(value);
			}
		} else {
			List<Integer> setx = new ArrayList<Integer>();
			setx.add(value);
			map.put(key, setx);
		}
		return map;
	}
}
