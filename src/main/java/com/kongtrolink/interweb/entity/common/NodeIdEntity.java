package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

public class NodeIdEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3791567375001735244L;

	private int nodeId;
	
	private String nodeIds;

	private String key;
	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
